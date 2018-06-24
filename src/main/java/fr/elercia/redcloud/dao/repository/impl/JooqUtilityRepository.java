package fr.elercia.redcloud.dao.repository.impl;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.impl.TableImpl;

public abstract class JooqUtilityRepository<T> {

    protected DSLContext jooq;
    private TableImpl<?> table;

    protected JooqUtilityRepository(DSLContext jooq, TableImpl<?> table) {

        this.jooq = jooq;
        this.table = table;
    }

    protected abstract T map(Record record);

    protected SelectJoinStep<Record> createSelectQuery() {
        return jooq.select().from(table);
    }
}
