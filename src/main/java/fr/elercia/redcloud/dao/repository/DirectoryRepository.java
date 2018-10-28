package fr.elercia.redcloud.dao.repository;

import fr.elercia.redcloud.dao.entity.DirectoryBase;
import fr.elercia.redcloud.dao.entity.UserBase;
import fr.elercia.redcloud.dao.generated.tables.Directory;

import java.util.List;
import java.util.UUID;

public interface DirectoryRepository extends GenericCrudRepository<DirectoryBase> {

    DirectoryBase findByResourceId(UUID id);
}
