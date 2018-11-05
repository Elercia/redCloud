package fr.elercia.redcloud.dao.repository.impl;

import fr.elercia.redcloud.dao.entity.BaseMapper;
import fr.elercia.redcloud.dao.entity.DirectoryBase;
import fr.elercia.redcloud.dao.generated.tables.records.DirectoryRecord;
import fr.elercia.redcloud.dao.repository.DirectoryRepository;
import fr.elercia.redcloud.exceptions.DatabaseRuntimeException;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static fr.elercia.redcloud.dao.generated.tables.Directory.DIRECTORY;

@Repository
public class JOOQDirectoryRepository extends JOOQUtilityRepository<DirectoryRecord, DirectoryBase> implements DirectoryRepository {

    @Autowired
    public JOOQDirectoryRepository(@Qualifier("getDSLContext") DSLContext jooq) {
        super(jooq, DIRECTORY);
    }

    @Override
    public DirectoryBase add(DirectoryBase entity) {

        DirectoryRecord directoryRecord = mapToRecord(entity);

        try {
            directoryRecord.insert();
        } catch (Throwable t) {
            throw new DatabaseRuntimeException("Directory insert failed", t);
        }

        return findById(directoryRecord.getId());
    }

    @Override
    public void update(DirectoryBase entity) {

        DirectoryRecord directoryRecord = mapToRecord(entity);

        try {
            directoryRecord.update();
        } catch (Throwable t) {
            throw new DatabaseRuntimeException("Directory update failed", t);
        }
    }

    @Override
    public void delete(int id) {

        int numberOfDelete = jooq.deleteFrom(DIRECTORY).where(DIRECTORY.ID.eq(id)).execute();
        if (numberOfDelete == 0) {
            throw new DatabaseRuntimeException("Directory not found found for delete (id:" + id + ")");
        }
    }

    @Override
    public List<DirectoryBase> findAll() {
        return createSelectQuery().fetch().map(this::map);
    }

    @Override
    public DirectoryBase findById(int id) {
        return map(createSelectQuery()
                .where(DIRECTORY.ID.eq(id))
                .fetchOne());
    }

    @Override
    public DirectoryBase findByResourceId(UUID id) {
        return map(createSelectQuery()
                .where(DIRECTORY.RESOURCE_ID.eq(id.toString()))
                .fetchOne());
    }

    @Override
    protected DirectoryBase mapToBase(Record record) {

        DirectoryRecord directoryRecord = record.into(DIRECTORY);

        return BaseMapper.recordToBase(directoryRecord);
    }

    @Override
    protected DirectoryRecord mapToRecord(DirectoryBase base) {

        DirectoryRecord directoryRecord = createRecord();

        directoryRecord.setId(base.getId());
        directoryRecord.setName(base.getName());
        directoryRecord.setParentId(base.getParentId());
        directoryRecord.setResourceId(base.getResourceId().toString());
        directoryRecord.setCreationDate(new Timestamp(base.getCreationDate().getTime()));
        directoryRecord.setUserId(base.getUserId());

        return directoryRecord;
    }
}