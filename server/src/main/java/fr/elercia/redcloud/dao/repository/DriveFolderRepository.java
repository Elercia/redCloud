package fr.elercia.redcloud.dao.repository;

import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.entity.drive.DriveFolder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DriveFolderRepository extends CrudRepository<DriveFolder, Integer> {

    DriveFolder findByResourceId(UUID resourceId);

    /**
     * Find the root folder for this user.
     * <p>
     * A root folder dont have any parent
     *
     * @param user The user
     * @return The root drive folder
     */
    DriveFolder findByparentDriveFolderIsNullAndUser(User user);
}
