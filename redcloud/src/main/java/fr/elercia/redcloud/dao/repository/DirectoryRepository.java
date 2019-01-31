package fr.elercia.redcloud.dao.repository;

import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DirectoryRepository extends CrudRepository<Directory, Integer> {

    Directory findByResourceId(UUID resourceId);

    Directory findByParentDirectoryIsNullAndUser(User user);
}
