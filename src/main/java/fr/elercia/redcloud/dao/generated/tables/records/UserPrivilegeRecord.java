/*
 * This file is generated by jOOQ.
 */
package fr.elercia.redcloud.dao.generated.tables.records;


import fr.elercia.redcloud.dao.generated.tables.UserPrivilege;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;


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
public class UserPrivilegeRecord extends UpdatableRecordImpl<UserPrivilegeRecord> implements Record3<Integer, Integer, Byte> {

    private static final long serialVersionUID = -1912528738;

    /**
     * Setter for <code>redcloud.user_privilege.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>redcloud.user_privilege.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>redcloud.user_privilege.user_id</code>.
     */
    public void setUserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>redcloud.user_privilege.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(1);
    }

    /**
     * Create a detached, initialised UserPrivilegeRecord
     */
    public UserPrivilegeRecord(Integer id, Integer userId, Byte superAdmin) {
        super(UserPrivilege.USER_PRIVILEGE);

        set(0, id);
        set(1, userId);
        set(2, superAdmin);
    }

    /**
     * Getter for <code>redcloud.user_privilege.SUPER_ADMIN</code>.
     */
    public Byte getSuperAdmin() {
        return (Byte) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * Setter for <code>redcloud.user_privilege.SUPER_ADMIN</code>.
     */
    public void setSuperAdmin(Byte value) {
        set(2, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, Integer, Byte> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return UserPrivilege.USER_PRIVILEGE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return UserPrivilege.USER_PRIVILEGE.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, Integer, Byte> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field3() {
        return UserPrivilege.USER_PRIVILEGE.SUPER_ADMIN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component3() {
        return getSuperAdmin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserPrivilegeRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserPrivilegeRecord value2(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value3() {
        return getSuperAdmin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserPrivilegeRecord value3(Byte value) {
        setSuperAdmin(value);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserPrivilegeRecord
     */
    public UserPrivilegeRecord() {
        super(UserPrivilege.USER_PRIVILEGE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserPrivilegeRecord values(Integer value1, Integer value2, Byte value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }
}
