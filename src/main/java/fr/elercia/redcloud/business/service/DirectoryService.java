package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.BusinessMapper;
import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.SimpleDirectory;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.dao.entity.DirectoryBase;
import fr.elercia.redcloud.dao.entity.FileBase;
import fr.elercia.redcloud.dao.repository.DirectoryRepository;
import fr.elercia.redcloud.dao.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectoryService {

    private DirectoryRepository directoryRepository;
    private FileRepository fileRepository;

    @Autowired
    public DirectoryService(DirectoryRepository directoryRepository, FileRepository fileRepository) {

        this.directoryRepository = directoryRepository;
        this.fileRepository = fileRepository;
    }

    public Directory createRootDirectory(User user) {

        SimpleDirectory directory = new SimpleDirectory("root", user);

        DirectoryBase base = BusinessMapper.mapToBase(directory, null);
        base = directoryRepository.add(base);

        return BusinessMapper.mapToDirectory(base, user);
    }

    public Directory findRootDirectory(User user) {

        DirectoryBase rootBase = directoryRepository.findRootDirectory(user.getId());

        List<DirectoryBase> subDirectoryBases = directoryRepository.findSubDirectories(rootBase.getId());
        List<FileBase> fileBases = fileRepository.findFiles(rootBase.getId());

        return BusinessMapper.mapToDirectory(rootBase, subDirectoryBases, fileBases, user);
    }
}
