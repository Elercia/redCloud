package fr.elercia.redcloud.dao.repository;

import fr.elercia.redcloud.business.entity.drive.DriveFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileRepository extends CrudRepository<DriveFile, Integer> {

    DriveFile findByResourceId(UUID resourceId);

}
