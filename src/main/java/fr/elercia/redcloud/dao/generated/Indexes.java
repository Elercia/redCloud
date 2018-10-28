/*
 * This file is generated by jOOQ.
 */
package fr.elercia.redcloud.dao.generated;


import fr.elercia.redcloud.dao.generated.tables.Directory;
import fr.elercia.redcloud.dao.generated.tables.File;
import fr.elercia.redcloud.dao.generated.tables.User;

import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>redcloud</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index DIRECTORY_DIRECTORY_DIRECTORY_ID_FK = Indexes0.DIRECTORY_DIRECTORY_DIRECTORY_ID_FK;
    public static final Index DIRECTORY_DIRECTORY_ID_UINDEX = Indexes0.DIRECTORY_DIRECTORY_ID_UINDEX;
    public static final Index DIRECTORY_PRIMARY = Indexes0.DIRECTORY_PRIMARY;
    public static final Index FILE_FILE_DIRECTORY_ID_FK = Indexes0.FILE_FILE_DIRECTORY_ID_FK;
    public static final Index FILE_FILE_ID_UINDEX = Indexes0.FILE_FILE_ID_UINDEX;
    public static final Index FILE_FILE_RESOURCE_ID_UINDEX = Indexes0.FILE_FILE_RESOURCE_ID_UINDEX;
    public static final Index FILE_PRIMARY = Indexes0.FILE_PRIMARY;
    public static final Index USER_PRIMARY = Indexes0.USER_PRIMARY;
    public static final Index USER_USER_ID_UINDEX = Indexes0.USER_USER_ID_UINDEX;
    public static final Index USER_USER_NAME_UINDEX = Indexes0.USER_USER_NAME_UINDEX;
    public static final Index USER_USER_RESOURCE_ID_UINDEX = Indexes0.USER_USER_RESOURCE_ID_UINDEX;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index DIRECTORY_DIRECTORY_DIRECTORY_ID_FK = Internal.createIndex("directory_directory_id_fk", Directory.DIRECTORY, new OrderField[] { Directory.DIRECTORY.PARENT_ID }, false);
        public static Index DIRECTORY_DIRECTORY_ID_UINDEX = Internal.createIndex("directory_id_uindex", Directory.DIRECTORY, new OrderField[] { Directory.DIRECTORY.ID }, true);
        public static Index DIRECTORY_PRIMARY = Internal.createIndex("PRIMARY", Directory.DIRECTORY, new OrderField[] { Directory.DIRECTORY.ID }, true);
        public static Index FILE_FILE_DIRECTORY_ID_FK = Internal.createIndex("file_directory_id_fk", File.FILE, new OrderField[] { File.FILE.DIRECTORY_ID }, false);
        public static Index FILE_FILE_ID_UINDEX = Internal.createIndex("file_id_uindex", File.FILE, new OrderField[] { File.FILE.ID }, true);
        public static Index FILE_FILE_RESOURCE_ID_UINDEX = Internal.createIndex("file_resource_id_uindex", File.FILE, new OrderField[] { File.FILE.RESOURCE_ID }, true);
        public static Index FILE_PRIMARY = Internal.createIndex("PRIMARY", File.FILE, new OrderField[] { File.FILE.ID }, true);
        public static Index USER_PRIMARY = Internal.createIndex("PRIMARY", User.USER, new OrderField[] { User.USER.ID }, true);
        public static Index USER_USER_ID_UINDEX = Internal.createIndex("user_id_uindex", User.USER, new OrderField[] { User.USER.ID }, true);
        public static Index USER_USER_NAME_UINDEX = Internal.createIndex("user_name_uindex", User.USER, new OrderField[] { User.USER.NAME }, true);
        public static Index USER_USER_RESOURCE_ID_UINDEX = Internal.createIndex("user_resource_id_uindex", User.USER, new OrderField[] { User.USER.RESOURCE_ID }, true);
    }
}
