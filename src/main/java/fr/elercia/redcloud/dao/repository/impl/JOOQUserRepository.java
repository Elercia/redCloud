package fr.elercia.redcloud.dao.repository.impl;

import fr.elercia.redcloud.dao.entity.BaseMapper;
import fr.elercia.redcloud.dao.entity.UserBase;
import fr.elercia.redcloud.dao.generated.tables.records.DirectoryRecord;
import fr.elercia.redcloud.dao.generated.tables.records.UserPrivilegeRecord;
import fr.elercia.redcloud.dao.generated.tables.records.UserRecord;
import fr.elercia.redcloud.dao.repository.UserRepository;
import fr.elercia.redcloud.exceptions.DatabaseRuntimeException;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static fr.elercia.redcloud.dao.generated.Tables.DIRECTORY;
import static fr.elercia.redcloud.dao.generated.tables.User.USER;
import static fr.elercia.redcloud.dao.generated.tables.UserPrivilege.USER_PRIVILEGE;

@Repository
public class JOOQUserRepository extends JooqUtilityRepository<UserBase> implements UserRepository {

    @Autowired
    public JOOQUserRepository(@Qualifier("dsl") DSLContext jooq) {
        super(jooq, USER);
    }

    @Override
    public UserBase add(UserBase entity) {

        return null;
    }

    @Override
    public void update(UserBase entity) {

    }

    @Override
    public void delete(UserBase entity) {

        int numberOfDelete = jooq.deleteFrom(USER).where(USER.ID.eq(entity.getId())).execute();
        if (numberOfDelete == 0) {
            throw new DatabaseRuntimeException("User not found found for delete (id:" + entity.getId() + ")");
        }
    }

    @Override
    public List<UserBase> findAll() {
        return createSelectQuery().fetch().map(this::map);
    }

    @Override
    public UserBase findById(int id) {
        return map(createSelectQuery()
                .where(USER.ID.eq(id))
                .fetchOne());
    }

    @Override
    public UserBase findByResourceId(UUID id) {
        return map(createSelectQuery()
                .where(USER.RESOURCE_ID.eq(id.toString()))
                .fetchOne());
    }

    @Override
    public List<UserBase> findByName(String name) {
        return createSelectQuery()
                .where(USER.NAME.eq(name))
                .fetch().map(this::map);
    }

    @Override
    protected UserBase map(Record record) {

        UserRecord userRecord = record.into(USER);
        UserPrivilegeRecord userPrivilegeRecord = record.into(USER_PRIVILEGE);
        DirectoryRecord directoryRecord = record.into(DIRECTORY);

        return BaseMapper.recordToBase(userRecord, directoryRecord, userPrivilegeRecord);
    }

    @Override
    protected SelectJoinStep<Record> createSelectQuery() {
        return jooq.select().from(USER)
                .innerJoin(USER_PRIVILEGE)
                .on(USER.ID.eq(USER_PRIVILEGE.USER_ID))
                .innerJoin(DIRECTORY)
                .on(USER.ROOT_DIRECTORY_ID.eq(DIRECTORY.ID));
    }
}