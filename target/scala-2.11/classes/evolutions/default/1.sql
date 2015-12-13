# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table segment (
  id                        integer auto_increment not null,
  title                     varchar(255),
  author                    varchar(255),
  content                   varchar(255),
  constraint pk_segment primary key (id))
;

create table story (
  id                        integer auto_increment not null,
  next_seg_id               integer,
  constraint pk_story primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table segment;

drop table story;

SET FOREIGN_KEY_CHECKS=1;

