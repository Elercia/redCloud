/*
 * This file is generated by jOOQ.
 */
package fr.elercia.redcloud.dao.generated.tables;


import fr.elercia.redcloud.dao.generated.Indexes;
import fr.elercia.redcloud.dao.generated.Keys;
import fr.elercia.redcloud.dao.generated.Redcloud;
import fr.elercia.redcloud.dao.generated.tables.records.UserRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class User extends TableImpl<UserRecord> {

    private static final long serialVersionUID = 1205163036;

    /**
     * The reference instance of <code>redcloud.user</code>
     */
    public static final User USER = new User();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserRecord> getRecordType() {
        return UserRecord.class;
    }

    /**
     * The column <code>redcloud.user.id</code>.
     */
    public final TableField<UserRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>redcloud.user.name</code>.
     */
    public final TableField<UserRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>redcloud.user.user_type</code>.
     */
    public final TableField<UserRecord, String> USER_TYPE = createField("user_type", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>redcloud.user.hashedPassword</code>.
     */
    public final TableField<UserRecord, String> HASHEDPASSWORD = createField("hashedPassword", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "");

    /**
     * The column <code>redcloud.user.creation_date</code>.
     */
    public final TableField<UserRecord, Timestamp> CREATION_DATE = createField("creation_date", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("current_timestamp(6)", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>redcloud.user.resource_id</code>.
     */
    public final TableField<UserRecord, String> RESOURCE_ID = createField("resource_id", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * Create a <code>redcloud.user</code> table reference
     */
    public User() {
        this(DSL.name("user"), null);
    }

    /**
     * Create an aliased <code>redcloud.user</code> table reference
     */
    public User(String alias) {
        this(DSL.name(alias), USER);
    }

    /**
     * Create an aliased <code>redcloud.user</code> table reference
     */
    public User(Name alias) {
        this(alias, USER);
    }

    private User(Name alias, Table<UserRecord> aliased) {
        this(alias, aliased, null);
    }

    private User(Name alias, Table<UserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Redcloud.REDCLOUD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.USER_PRIMARY, Indexes.USER_USER_ID_UINDEX, Indexes.USER_USER_NAME_UINDEX, Indexes.USER_USER_RESOURCE_ID_UINDEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UserRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserRecord> getPrimaryKey() {
        return Keys.KEY_USER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserRecord>> getKeys() {
        return Arrays.<UniqueKey<UserRecord>>asList(Keys.KEY_USER_PRIMARY, Keys.KEY_USER_USER_ID_UINDEX, Keys.KEY_USER_USER_NAME_UINDEX, Keys.KEY_USER_USER_RESOURCE_ID_UINDEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User as(String alias) {
        return new User(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User as(Name alias) {
        return new User(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(String name) {
        return new User(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(Name name) {
        return new User(name, null);
    }
}
