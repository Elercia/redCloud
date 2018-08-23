/*
 * This file is generated by jOOQ.
 */
package fr.elercia.redcloud.dao.generated.tables.records;


import fr.elercia.redcloud.dao.generated.tables.User;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


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
public class UserRecord extends UpdatableRecordImpl<UserRecord> implements Record6<Integer, String, String, String, Timestamp, String> {

    private static final long serialVersionUID = -1035463897;

    /**
     * Setter for <code>redcloud.user.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>redcloud.user.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>redcloud.user.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>redcloud.user.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>redcloud.user.user_type</code>.
     */
    public void setUserType(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>redcloud.user.user_type</code>.
     */
    public String getUserType() {
        return (String) get(2);
    }

    /**
     * Setter for <code>redcloud.user.hashedPassword</code>.
     */
    public void setHashedpassword(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>redcloud.user.hashedPassword</code>.
     */
    public String getHashedpassword() {
        return (String) get(3);
    }

    /**
     * Setter for <code>redcloud.user.creation_date</code>.
     */
    public void setCreationDate(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>redcloud.user.creation_date</code>.
     */
    public Timestamp getCreationDate() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>redcloud.user.resource_id</code>.
     */
    public void setResourceId(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>redcloud.user.resource_id</code>.
     */
    public String getResourceId() {
        return (String) get(5);
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
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, String, String, Timestamp, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, String, String, Timestamp, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return User.USER.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return User.USER.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return User.USER.USER_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return User.USER.HASHEDPASSWORD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return User.USER.CREATION_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return User.USER.RESOURCE_ID;
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
    public String component2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getUserType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getHashedpassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component5() {
        return getCreationDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getResourceId();
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
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getUserType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getHashedpassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getCreationDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getResourceId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRecord value3(String value) {
        setUserType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRecord value4(String value) {
        setHashedpassword(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRecord value5(Timestamp value) {
        setCreationDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRecord value6(String value) {
        setResourceId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRecord values(Integer value1, String value2, String value3, String value4, Timestamp value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserRecord
     */
    public UserRecord() {
        super(User.USER);
    }

    /**
     * Create a detached, initialised UserRecord
     */
    public UserRecord(Integer id, String name, String userType, String hashedpassword, Timestamp creationDate, String resourceId) {
        super(User.USER);

        set(0, id);
        set(1, name);
        set(2, userType);
        set(3, hashedpassword);
        set(4, creationDate);
        set(5, resourceId);
    }
}
