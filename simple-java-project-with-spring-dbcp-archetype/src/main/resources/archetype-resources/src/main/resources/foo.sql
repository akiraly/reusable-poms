--liquibase formatted sql

--changeset akiraly:1

create table bar (
	bar_uuid char(22) primary key,
	bar_prop varchar(50) not null
);

create table foo (
	foo_id bigint auto_increment primary key,
	bar_uuid char(22) not null,
	name varchar(50) not null,
	dt timestamp not null,
	foreign key (bar_uuid) references bar(bar_uuid)
);
