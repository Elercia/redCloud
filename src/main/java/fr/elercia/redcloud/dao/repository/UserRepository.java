package fr.elercia.redcloud.dao.repository;

import fr.elercia.redcloud.dao.generated.tables.records.UserRecord;

import java.util.UUID;

public interface UserRepository extends GenericCrudRepository<UserRecord> {
    UserRecord findByResourceId(UUID id);
}
