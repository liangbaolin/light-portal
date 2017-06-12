
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
   address_id integer not null,
   address_type varchar(20),
   country varchar(120),
   province_state varchar(120),
   city varchar(120),
   zipcode varchar(20),
   addressline1 varchar(256),
   addressline2 varchar(256),
   active_flg varchar(1) DEFAULT 'Y'
   PRIMARY KEY(user_id, address_id)
)


CREATE  TABLE Confirmation_Token (
   id bigint not null
   user_id bigint not null,
   confirmation_type  varchar(20),
   token_value varchar(120),
   payload varchar(1200),
   valid varchar(1) DEFAULT 'Y',
   expiresAt timestamp,
   usedAt timestamp,
   createBy bigint,
   createdAt date,
   modifiedBy bigint,
   modifiedAt date,
   PRIMARY KEY(id)
)

create table authority (
  user_id integer not null,
  authorities varchar(255)
)
