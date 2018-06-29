package fr.elercia.redcloud.dao.repository.impl;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.impl.TableImpl;

public abstract class JooqUtilityRepository<R extends Record, T> {

    protected DSLContext jooq;
    private TableImpl<R> table;

    protected JooqUtilityRepository(DSLContext jooq, TableImpl<R> table) {

        this.jooq = jooq;
        this.table = table;
    }

    protected abstract T mapToBase(Record record);

    protected abstract R mapToRecord(T base);

    protected SelectJoinStep<Record> createSelectQuery() {
        return jooq.select().from(table);
    }

    protected R createRecord() {
        return jooq.newRecord(table);
    }
}
