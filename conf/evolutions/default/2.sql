# Static pages table
# --- !Ups
create table IF NOT EXISTS pages (
  id                        bigint not null,
  name                      varchar(255) not null,
  url                       varchar(255),
  content                   text,
  constraint uq_pages_name unique (name),
  constraint pk_pages primary key (id));

create sequence IF NOT EXISTS pages_seq;

# --- !Downs
drop table if exists pages cascade;
drop sequence if exists pages_seq;

