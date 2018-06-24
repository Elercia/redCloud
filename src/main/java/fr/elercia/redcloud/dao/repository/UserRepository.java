package fr.elercia.redcloud.dao.repository;

import fr.elercia.redcloud.dao.entity.UserBase;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends GenericCrudRepository<UserBase> {
    UserBase findByResourceId(UUID id);

    List<UserBase> findByName(String name);
}
