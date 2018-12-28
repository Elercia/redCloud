package fr.elercia.redcloud.dao.repository;

import fr.elercia.redcloud.business.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByResourceId(UUID id);

    User findByName(String name);
}
