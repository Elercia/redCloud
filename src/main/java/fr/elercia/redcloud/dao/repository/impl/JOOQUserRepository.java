package fr.elercia.redcloud.dao.repository.impl;

import fr.elercia.redcloud.dao.generated.tables.records.UserRecord;
import fr.elercia.redcloud.dao.repository.UserRepository;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static fr.elercia.redcloud.dao.generated.tables.User.USER;

@Repository
public class JOOQUserRepository implements UserRepository {

    private DSLContext jooq;

    @Autowired
    public JOOQUserRepository(@Qualifier("dsl") DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public UserRecord add(UserRecord entity) {

        return null;
    }

    @Override
    public void update(UserRecord entity) {

    }

    @Override
    public void delete(UserRecord entity) {

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
        return jooq.selectFrom(USER)
                .where(USER.RESOURCE_ID.eq(id.toString()))
                .fetchOne();
    }
}