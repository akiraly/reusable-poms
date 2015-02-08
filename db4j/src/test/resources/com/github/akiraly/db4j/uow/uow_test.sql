--liquibase formatted sql

--changeset akiraly:1

create table foo (
	foo_id bigint auto_increment primary key,
	bar varchar(50) not null,
	dt timestamp not null,
	local_date date not null
);

create table audited_foo (
	audited_foo_id bigint auto_increment primary key,
	bar varchar(50) not null, 
	create_uow_id bigint not null,
	update_uow_id bigint not null,
	foreign key (create_uow_id) references uow(uow_id),
	foreign key (update_uow_id) references uow(uow_id)
);

create table audited_foo_uuid (
	audited_foo_uuid char(22) primary key,
	bar varchar(50) not null, 
	create_uow_id bigint not null,
	update_uow_id bigint not null,
	foreign key (create_uow_id) references uow(uow_id),
	foreign key (update_uow_id) references uow(uow_id)
);

create table foo_uuid (
	foo_id char(22) primary key,
	bar varchar(50) not null,
	dt timestamp not null
);
