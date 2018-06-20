/*
 * This file is generated by jOOQ.
 */
package redcloud.dao.schema.tables;


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

import redcloud.dao.schema.Indexes;
import redcloud.dao.schema.Keys;
import redcloud.dao.schema.Redcloud;
import redcloud.dao.schema.tables.records.UserPrivilegeRecord;


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
public class UserPrivilege extends TableImpl<UserPrivilegeRecord> {

    private static final long serialVersionUID = 1051294521;

    /**
     * The reference instance of <code>redcloud.user_privilege</code>
     */
    public static final UserPrivilege USER_PRIVILEGE = new UserPrivilege();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserPrivilegeRecord> getRecordType() {
        return UserPrivilegeRecord.class;
    }

    /**
     * The column <code>redcloud.user_privilege.id</code>.
     */
    public final TableField<UserPrivilegeRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>redcloud.user_privilege.user_id</code>.
     */
    public final TableField<UserPrivilegeRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>redcloud.user_privilege.type</code>.
     */
    public final TableField<UserPrivilegeRecord, String> TYPE = createField("type", org.jooq.impl.SQLDataType.VARCHAR(500).nullable(false), this, "");

    /**
     * Create a <code>redcloud.user_privilege</code> table reference
     */
    public UserPrivilege() {
        this(DSL.name("user_privilege"), null);
    }

    /**
     * Create an aliased <code>redcloud.user_privilege</code> table reference
     */
    public UserPrivilege(String alias) {
        this(DSL.name(alias), USER_PRIVILEGE);
    }

    /**
     * Create an aliased <code>redcloud.user_privilege</code> table reference
     */
    public UserPrivilege(Name alias) {
        this(alias, USER_PRIVILEGE);
    }

    private UserPrivilege(Name alias, Table<UserPrivilegeRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserPrivilege(Name alias, Table<UserPrivilegeRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.USER_PRIVILEGE_PRIMARY, Indexes.USER_PRIVILEGE_PRIVILEGE_ID_UINDEX, Indexes.USER_PRIVILEGE_USER_PRIVILEGE_USER_ID_FK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UserPrivilegeRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER_PRIVILEGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserPrivilegeRecord> getPrimaryKey() {
        return Keys.KEY_USER_PRIVILEGE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserPrivilegeRecord>> getKeys() {
        return Arrays.<UniqueKey<UserPrivilegeRecord>>asList(Keys.KEY_USER_PRIVILEGE_PRIMARY, Keys.KEY_USER_PRIVILEGE_PRIVILEGE_ID_UINDEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserPrivilege as(String alias) {
        return new UserPrivilege(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserPrivilege as(Name alias) {
        return new UserPrivilege(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserPrivilege rename(String name) {
        return new UserPrivilege(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserPrivilege rename(Name name) {
        return new UserPrivilege(name, null);
    }
}
