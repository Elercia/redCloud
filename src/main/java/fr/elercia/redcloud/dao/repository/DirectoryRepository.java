package fr.elercia.redcloud.dao.repository;

import fr.elercia.redcloud.dao.entity.DirectoryBase;

import java.util.List;
import java.util.UUID;

public interface DirectoryRepository extends GenericCrudRepository<DirectoryBase> {

    DirectoryBase findByResourceId(UUID id);

    DirectoryBase findRootDirectory(int userId);

    List<DirectoryBase> findSubDirectories(int parentId);
}
