package fr.elercia.redcloud.stresstest;

import com.google.common.collect.Lists;
import fr.elercia.redcloud.api.controllers.params.Route;
import fr.elercia.redcloud.api.dto.entity.*;
import fr.elercia.redcloud.config.SecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class StressTestTask implements Callable<Boolean> {

    private static final Logger LOG = LoggerFactory.getLogger(StressTestTask.class);

    private int id;
    private String accountName;
    private RestTemplate testRestTemplate;
    private List<UUID> availableFoldersUuid;

    public StressTestTask(int id) {
        this.id = id;
        this.accountName = StressTest.USER_NAME_PREFIX + id;
        this.testRestTemplate = new RestTemplate();
        this.availableFoldersUuid = new ArrayList<>();
    }

    @Override
    public Boolean call() throws Exception {

        try {
            // create account
            createAccount();

            // login into this account
            login();

            // loop X times
            for (int i = 0; i < StressTest.NUMBER_OF_FILES_TO_UPLOAD; i++) {

                int folderId = ThreadLocalRandom.current().nextInt(0, availableFoldersUuid.size());

                UUID folderResourceId = availableFoldersUuid.get(folderId);

                // create a sub directory in one of the accessible folders
                UUID subFolderId = createSubFolder(folderResourceId);
                availableFoldersUuid.add(subFolderId);

                // upload a file to this folder
                uploadFile(subFolderId);
            }


            logout();
        } catch (Throwable t) {
            LOG.error("Task {} : error {}", id, t.getMessage());
            return false;
        }

        return true;
    }


    private void createAccount() {

        ResponseEntity<UserDto> response = testRestTemplate.postForEntity(StressTest.ROOT_URI + Route.USERS, new CreateUserDto(accountName, "password"), UserDto.class);
        LOG.debug("Task {} : Created user", id);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException();
        }

        availableFoldersUuid.add(response.getBody().getRootFolder().getResourceId());
    }

    private void login() {

        ResponseEntity<TokenDto> response = testRestTemplate.postForEntity(StressTest.ROOT_URI + Route.LOGIN, new LoginDto(accountName, "password"), TokenDto.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException();
        }

        testRestTemplate.setInterceptors(Lists.newArrayList((ClientHttpRequestInterceptor) (request, body, execution) -> {
            request.getHeaders().set(SecurityConstants.REQUEST_HEADER_NAME, SecurityConstants.TOKEN_TYPE + " " + response.getBody().getAccessToken());
            return execution.execute(request, body);
        }));

        LOG.debug("Task {} : Login", id);
    }

    private void logout() {

        testRestTemplate.delete(StressTest.ROOT_URI + Route.LOGOUT);

        LOG.debug("Task {} : Logout", id);
    }

    private void uploadFile(UUID subFolderId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", getTestFile());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<FileDto> response = testRestTemplate.postForEntity(StressTest.ROOT_URI + Route.DIRECTORY_UPLOAD_FILE,
                requestEntity,
                FileDto.class, subFolderId);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException();
        }

        LOG.debug("Task {} : upload file", id);
    }

    private FileSystemResource getTestFile() {
        return new FileSystemResource(new File(this.getClass().getClassLoader().getResource("testUploadFile.txt").getFile()));
    }

    private UUID createSubFolder(UUID folderResourceId) {

        ResponseEntity<FolderDto> response = testRestTemplate.postForEntity(StressTest.ROOT_URI + Route.DIRECTORY,
                new CreateFolderDto("somedir" + availableFoldersUuid.size()),
                FolderDto.class, folderResourceId);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException();
        }

        UUID subFolderResourceId = response.getBody().getResourceId();

        LOG.debug("Task {} : create sub folder", id);

        return subFolderResourceId;
    }
}
