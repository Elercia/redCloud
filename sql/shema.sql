
drop table if exists directory;
drop table if exists user;

create table user
(
  id            INT(10) auto_increment
    primary key,
  name          VARCHAR(50)                            not null,
  user_type     VARCHAR(50)                            not null,
  hashedPassword      VARCHAR(64)                            not null,
  creation_date TIMESTAMP(6) default CURRENT_TIMESTAMP not null,
  resource_id   VARCHAR(50)                            not null,
  constraint user_name_uindex
  unique (name),
  constraint user_resource_id_uindex
  unique (resource_id)
);

create table directory
(
  id            INT(10) auto_increment
    primary key,
  name          VARCHAR(50)                            not null,
  resource_id   VARCHAR(50)                            not null,
  user_id       INT(10)                                not null,
  parent_id     INT(10)                                null,
  creation_date TIMESTAMP(6) default CURRENT_TIMESTAMP not null,
  constraint directory_user_id_fk
  foreign key (user_id) references user (id),
  constraint directory_directory_id_fk
  foreign key (parent_id) references directory (id)
);

create index directory_directory_id_fk
  on directory (parent_id);

create unique index directory_id_uindex
  on directory (id);

create index directory_user_id_fk
  on directory (user_id);

create unique index user_id_uindex
  on user (id);