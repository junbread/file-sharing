create table bills
(
	id int auto_increment
		primary key,
	user_id varchar(20) not null,
	issue_date timestamp default current_timestamp() null,
	income bigint default 0 not null,
	payment bigint default 0 not null,
	status enum('PENDING', 'FINISHED') default 'PENDING' not null,
	constraint bills_users_id_fk
		foreign key (user_id) references users (id)
			on update cascade on delete cascade
);

