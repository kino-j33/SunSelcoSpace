create table room (
   id            serial         not null
  ,name          varchar(100)   not null
  ,location      varchar(100)
  ,capacity      integer
  ,overview      varchar(100)   not null
  ,fee           integer        not null
  ,image         varchar(100)
  ,introduction  varchar(500)
  ,primary key (id)
);

create table account (
   id        char(8)     not null
  ,password  char(32)    not null
  ,name      varchar(15) not null
  ,primary key (id)
);

create table booking (
   id                        serial       not null
  ,room_id                   integer      not null
  ,account_id                char(8)      not null
  ,booking_date              date         not null
  ,purpose                   varchar(100)
  ,deleted                   char(1)      not null default '0'
  ,record_creation_timestamp timestamp    without time zone   not null
  ,record_update_timestamp   timestamp    without time zone   not null
  ,primary key (id)
);