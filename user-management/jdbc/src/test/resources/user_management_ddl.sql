DROP table IF EXISTS USER_DETAIL;
DROP table IF EXISTS  ADDRESS;
DROP table IF EXISTS  CONFIRMATION_TOKEN;
DROP table IF EXISTS  Session;
DROP table IF EXISTS  authority;

CREATE  TABLE USER_DETAIL (
   user_id bigint not null,
  email varchar(100),
  timezone varchar(60),
  screenName varchar(120),
  firstName varchar(60),
  lastName varchar(60),
  gender  varchar(20),
  birthday date,
  passwordHash  varchar(120),
  passwordSalt  varchar(120),
  locale  varchar(60),
  confirmed varchar(1) DEFAULT 'N',
  locked varchar(1),
  deleted varchar(1),
  createBy bigint,
  createdAt date,
  modifiedBy bigint,
  modifiedAt date,
  PRIMARY KEY(user_id)
);

CREATE  TABLE ADDRESS (
   user_id bigint not null,
   address_type varchar(20),
   country varchar(120),
   province_state varchar(120),
   city varchar(120),
   zipcode varchar(20),
   addressline1 varchar(256),
   addressline2 varchar(256),
   active_flg varchar(1) DEFAULT 'Y',
   PRIMARY KEY(user_id, address_type)
);

CREATE  TABLE CONFIRMATION_TOKEN (
  id bigint not null,
  user_id bigint not null,
  token_value varchar(128),
  token_type varchar(30),
  valid varchar(1) DEFAULT 'Y',
  payload varchar(400),
  expiresAt date,
  usedAt date,
  PRIMARY KEY(id)
);

CREATE  TABLE Session (
   id bigint not null,
   user_id bigint not null,
   token_value varchar(256),
   valid varchar(1) DEFAULT 'Y',
   expiresAt date,
   lastUsedAt date,
   issuedAt date,
   removedAt date,
   deleted varchar(1) DEFAULT 'N',
   PRIMARY KEY(id)
);

create table authority (
  user_id integer not null,
  authorities varchar(255)
);
