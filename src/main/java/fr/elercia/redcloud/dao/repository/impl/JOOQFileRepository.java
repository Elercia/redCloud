package fr.elercia.redcloud.dao.repository.impl;

import fr.elercia.redcloud.dao.entity.BaseMapper;
import fr.elercia.redcloud.dao.entity.FileBase;
import fr.elercia.redcloud.dao.generated.tables.records.FileRecord;
import fr.elercia.redcloud.dao.repository.FileRepository;
import fr.elercia.redcloud.exceptions.DatabaseRuntimeException;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static fr.elercia.redcloud.dao.generated.tables.File.FILE;

@Repository
public class JOOQFileRepository extends JOOQUtilityRepository<FileRecord, FileBase> implements FileRepository {

    @Autowired
    public JOOQFileRepository(@Qualifier("getDSLContext") DSLContext jooq) {
        super(jooq, FILE);
    }

    @Override
    public FileBase add(FileBase entity) {

        FileRecord fileRecord = mapToRecord(entity);

        try {
            fileRecord.insert();
        } catch (Throwable t) {
            throw new DatabaseRuntimeException("File insert failed", t);
        }

        return findById(fileRecord.getId());
    }

    @Override
    public void update(FileBase entity) {

        FileRecord fileRecord = mapToRecord(entity);

        try {
            fileRecord.update();
        } catch (Throwable t) {
            throw new DatabaseRuntimeException("File update failed", t);
        }
    }

    @Override
    public void delete(int id) {

        int numberOfDelete = jooq.deleteFrom(FILE).where(FILE.ID.eq(id)).execute();
        if (numberOfDelete == 0) {
            throw new DatabaseRuntimeException("File not found found for delete (id:" + id + ")");
        }
    }

    @Override
    public List<FileBase> findAll() {
        return createSelectQuery().fetch().map(this::mapToBase);
    }

    @Override
    public FileBase findById(int id) {
        return mapToBase(createSelectQuery()
                .where(FILE.ID.eq(id))
                .fetchOne());
    }

    @Override
    public FileBase findByResourceId(UUID id) {
        return mapToBase(createSelectQuery()
                .where(FILE.RESOURCE_ID.eq(id.toString()))
                .fetchOne());
    }

    @Override
    protected FileBase mapToBase(Record record) {

        FileRecord fileRecord = record.into(FILE);

        return BaseMapper.recordToBase(fileRecord);
    }

    @Override
    protected FileRecord mapToRecord(FileBase base) {

        FileRecord fileRecord = createRecord();

        fileRecord.setId(base.getId());
        fileRecord.setName(base.getName());
        fileRecord.setResourceId(base.getResourceId().toString());
        fileRecord.setCreationDate(new Timestamp(base.getCreationDate().getTime()));
        fileRecord.setDirectoryId(base.getDirectoryId());

        return fileRecord;
    }
}