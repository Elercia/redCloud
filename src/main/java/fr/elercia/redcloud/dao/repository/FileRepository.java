package fr.elercia.redcloud.dao.repository;

import fr.elercia.redcloud.dao.entity.FileBase;

import java.util.List;
import java.util.UUID;

public interface FileRepository extends GenericCrudRepository<FileBase> {
    FileBase findByResourceId(UUID id);

    List<FileBase> findFiles(int directoryId);
}
