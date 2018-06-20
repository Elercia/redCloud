package redcloud.dao.repository;

import redcloud.dao.schema.tables.records.UserRecord;

import java.util.List;
import java.util.UUID;

public interface UserRepository {

    UserRecord addUser(UserRecord user);

    List<UserRecord> findAll();

    UserRecord findById(Long id);

    UserRecord findByResourceId(UUID id);
}
