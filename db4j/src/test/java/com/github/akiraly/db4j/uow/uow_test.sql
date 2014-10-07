--liquibase formatted sql

--changeset akiraly:1

--include file:com/github/akiraly/db4j/uow/uow.sql

create table foo (
	foo_id bigint auto_increment primary key,
	bar varchar(50) not null
);

create table audited_foo (
	audited_foo_id bigint auto_increment primary key,
	bar varchar(50) not null, 
	create_uow_id bigint not null,
	update_uow_id bigint not null,
	foreign key (create_uow_id) references uow(uow_id),
	foreign key (update_uow_id) references uow(uow_id)
);
