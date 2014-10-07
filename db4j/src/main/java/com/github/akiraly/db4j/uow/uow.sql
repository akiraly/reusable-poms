--liquibase formatted sql

--changeset akiraly:1
create table uow (
	uow_id bigint auto_increment primary key,
	user varchar(50) not null
);
