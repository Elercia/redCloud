package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.api.dto.entity.MoveFileDto;
import fr.elercia.redcloud.business.entity.File;
import fr.elercia.redcloud.dao.repository.FileRepository;
import fr.elercia.redcloud.exceptions.FileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FileService {

    private FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {

        this.fileRepository = fileRepository;
    }

    public File find(UUID fileId) throws FileNotFoundException {

        File file = fileRepository.findByResourceId(fileId);

        if(file == null) {
            throw new FileNotFoundException();
        }

        return file;
    }

    public byte[] getFileContent(File file) {
        return null;
    }

    public void delete(File file) {
    }

    public void move(File file, MoveFileDto moveFileDto) {
    }
}
