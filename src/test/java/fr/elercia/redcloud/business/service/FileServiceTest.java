package fr.elercia.redcloud.business.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@SpringBootTest
@Transactional(rollbackFor = Throwable.class)
class FileServiceTest {

    @Mock
    private FileSystemUtils fileSystemUtils;

    @Autowired
    @InjectMocks
    private FileService fileService;


    @BeforeEach
    public void setUp() throws Exception {
        // Initialize mocks created above
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void find_knowId_returnExpected() {

    }

    @Test
    void find_unknownId_throw() {

    }

    @Test
    void delete_expectDeleteCall() {

    }

    @Test
    void move_validDirectory_moveDone() {

    }

    @Test
    void move_directoryFilesWithSameName_throw() {

    }

    @ParameterizedTest
    @MethodSource("createValidParametersForStoreFile")
    void storeFile_withNameParameter_valid(String fileName) {

    }

    private static Stream<String> createValidParametersForStoreFile() {
        return Stream.of(
                "file",
                "file-",
                "file-file.jpg",
                "sss"
        );
    }

    @ParameterizedTest
    @MethodSource("createInvalidParametersForStoreFile")
    void storeFile_withNameParameter_invalid(String fileName) {

    }

    private static Stream<String> createInvalidParametersForStoreFile() {
        return Stream.of(
                "file",
                "file-",
                "file-file.jpg",
                "sss"
        );
    }
}