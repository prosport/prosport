# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table images (
  id                        bigint not null,
  name                      varchar(255),
  product_id                bigint,
  color                     varchar(255) not null,
  filename                  varchar(255) not null,
  constraint uq_images_name unique (name),
  constraint pk_images primary key (id))
;

create table products (
  id                        bigint not null,
  name                      varchar(255),
  created_at                TIMESTAMP DEFAULT now() not null,
  modified_at               TIMESTAMP DEFAULT now() not null,
  category_id               bigint not null,
  semantic_url              varchar(255) not null,
  articul                   varchar(255) not null,
  description               varchar(255) not null,
  short_description         varchar(255) not null,
  rating                    float not null,
  views_count               integer not null,
  author                    varchar(255),
  constraint uq_products_name unique (name),
  constraint pk_products primary key (id))
;

create table product_categories (
  id                        bigint not null,
  name                      varchar(255),
  created_at                TIMESTAMP DEFAULT now() not null,
  modified_at               TIMESTAMP DEFAULT now() not null,
  parent_id                 bigint,
  constraint uq_product_categories_name unique (name),
  constraint pk_product_categories primary key (id))
;

create table users (
  id                        bigint not null,
  email                     varchar(255) not null,
  password                  varchar(255),
  role                      varchar(9),
  registed_at               TIMESTAMP DEFAULT now() not null,
  is_blocked                boolean,
  constraint ck_users_role check (role in ('admin','manager','moderator','n/a')),
  constraint uq_users_email unique (email),
  constraint pk_users primary key (id))
;

create sequence images_seq;

create sequence products_seq;

create sequence product_categories_seq;

create sequence users_seq;

alter table images add constraint fk_images_product_1 foreign key (product_id) references products (id);
create index ix_images_product_1 on images (product_id);
alter table products add constraint fk_products_category_2 foreign key (category_id) references product_categories (id);
create index ix_products_category_2 on products (category_id);
alter table product_categories add constraint fk_product_categories_parent_3 foreign key (parent_id) references product_categories (id);
create index ix_product_categories_parent_3 on product_categories (parent_id);



# --- !Downs

drop table if exists images cascade;

drop table if exists products cascade;

drop table if exists product_categories cascade;

drop table if exists users cascade;

drop sequence if exists images_seq;

drop sequence if exists products_seq;

drop sequence if exists product_categories_seq;

drop sequence if exists users_seq;

