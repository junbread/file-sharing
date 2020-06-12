create table users
(
	id varchar(20) not null,
	pw char(40) not null,
	name varchar(20) not null,
	role enum('SUBSCRIBER', 'PROVIDER', 'ADMIN') default 'SUBSCRIBER' not null,
	account_number varchar(20) not null,
	address varchar(100) null,
	phone varchar(13) null,
	birthday date null,
	join_date date default current_timestamp() not null,
	subscription tinyint(1) default 0 not null,
	last_login_time timestamp null,
	primary key (id)
);

