package fr.elercia.redcloud.dao.repository;

import fr.elercia.redcloud.dao.entity.UserBase;

import java.util.UUID;

public interface UserRepository extends GenericCrudRepository<UserBase> {
    UserBase findByResourceId(UUID id);
}
