package fr.elercia.redcloud.dao.repository.impl;

import fr.elercia.redcloud.dao.entity.BaseMapper;
import fr.elercia.redcloud.dao.entity.UserBase;
import fr.elercia.redcloud.dao.generated.tables.records.UserRecord;
import fr.elercia.redcloud.dao.repository.UserRepository;
import fr.elercia.redcloud.exceptions.DatabaseRuntimeException;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static fr.elercia.redcloud.dao.generated.tables.User.USER;

@Repository
public class JOOQUserRepository extends JOOQUtilityRepository<UserRecord, UserBase> implements UserRepository {

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

        return findByName(userRecord.getName());
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
    public void delete(int id) {

        int numberOfDelete = jooq.deleteFrom(USER).where(USER.ID.eq(id)).execute();
        if (numberOfDelete == 0) {
            throw new DatabaseRuntimeException("User not found found for delete (id:" + id + ")");
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
    public UserBase findByName(String name) {
        return map(createSelectQuery()
                .where(USER.NAME.eq(name))
                .fetchOne());
    }

    @Override
    protected UserBase mapToBase(Record record) {

        if(record == null) {
            return null;
        }

        UserRecord userRecord = record.into(USER);

        return BaseMapper.recordToBase(userRecord);
    }

    @Override
    protected UserRecord mapToRecord(UserBase base) {

        UserRecord userRecord = createRecord();

        userRecord.setId(base.getId());
        userRecord.setName(base.getName());
        userRecord.setHashedpassword(base.getPassword());
        userRecord.setResourceId(base.getResourceId().toString());
        userRecord.setCreationDate(new Timestamp(base.getCreationDate().getTime()));
        userRecord.setUserType(base.getUserType().toString());

        return userRecord;
    }
}