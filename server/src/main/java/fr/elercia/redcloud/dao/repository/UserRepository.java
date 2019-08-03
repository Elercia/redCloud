package fr.elercia.redcloud.dao.repository;

import fr.elercia.redcloud.business.entity.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<AppUser, Integer> {
    AppUser findByResourceId(UUID id);

    AppUser findByName(String name);
}
