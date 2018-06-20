package redcloud.dao.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import redcloud.dao.schema.Tables;
import redcloud.dao.schema.tables.records.UserRecord;

import java.util.List;
import java.util.UUID;

@Repository
public class JOOQUserRepository implements UserRepository {

    private final DSLContext jooq;
 
    @Autowired
    public JOOQUserRepository(@Qualifier("dsl") DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public UserRecord addUser(UserRecord user) {
        return jooq.insertInto(Tables.USER)
                .set(user)
                .returning()
                .fetchOne();
    }

    @Override
    public List<UserRecord> findAll() {
        return null;
    }

    @Override
    public UserRecord findById(Long id) {
        return null;
    }

    @Override
    public UserRecord findByResourceId(UUID id) {
        return null;
    }
}