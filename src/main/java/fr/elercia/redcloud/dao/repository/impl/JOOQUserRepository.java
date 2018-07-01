package fr.elercia.redcloud.dao.repository.impl;

import fr.elercia.redcloud.dao.entity.BaseMapper;
import fr.elercia.redcloud.dao.entity.UserBase;
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

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static fr.elercia.redcloud.dao.generated.tables.User.USER;
import static fr.elercia.redcloud.dao.generated.tables.UserPrivilege.USER_PRIVILEGE;

@Repository
public class JOOQUserRepository extends JooqUtilityRepository<UserRecord, UserBase> implements UserRepository {

    @Autowired
    public JOOQUserRepository(@Qualifier("getDSLContext") DSLContext jooq) {
        super(jooq, USER);
    }

    @Override
    public UserBase add(UserBase entity) {

        UserRecord userRecord = mapToRecord(entity);

        try {
            userRecord.insert();
        } catch (Throwable t) {
            throw new DatabaseRuntimeException("User insert failed", t);
        }

        return findByName(userRecord.getName()).get(0);
    }

    @Override
    public void update(UserBase entity) {

        UserRecord userRecord = mapToRecord(entity);

        try {
            userRecord.update();
        } catch (Throwable t) {
            throw new DatabaseRuntimeException("User update failed", t);
        }
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
        return createSelectQuery().fetch().map(this::mapToBase);
    }

    @Override
    public UserBase findById(int id) {
        return mapToBase(createSelectQuery()
                .where(USER.ID.eq(id))
                .fetchOne());
    }

    @Override
    public UserBase findByResourceId(UUID id) {
        return mapToBase(createSelectQuery()
                .where(USER.RESOURCE_ID.eq(id.toString()))
                .fetchOne());
    }

    @Override
    public List<UserBase> findByName(String name) {
        return createSelectQuery()
                .where(USER.NAME.eq(name))
                .fetch().map(this::mapToBase);
    }

    @Override
    protected UserBase mapToBase(Record record) {

        UserRecord userRecord = record.into(USER);
        UserPrivilegeRecord userPrivilegeRecord = record.into(USER_PRIVILEGE);

        return BaseMapper.recordToBase(userRecord, userPrivilegeRecord);
    }

    @Override
    protected UserRecord mapToRecord(UserBase base) {

        UserRecord userRecord = createRecord();

        userRecord.setId(base.getId());
        userRecord.setName(base.getName());
        userRecord.setPassword(base.getPassword());
        userRecord.setResourceId(base.getResourceId().toString());
        userRecord.setCreationDate(new Timestamp(base.getCreationDate().getTime()));

        return userRecord;
    }

    @Override
    protected SelectJoinStep<Record> createSelectQuery() {
        return jooq.select().from(USER)
                .innerJoin(USER_PRIVILEGE)
                .on(USER.ID.eq(USER_PRIVILEGE.USER_ID));
    }
}