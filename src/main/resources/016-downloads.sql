create table downloads
(
	id int auto_increment
		primary key,
	user_id varchar(20) not null,
	item_id int not null,
	download_time timestamp default current_timestamp() not null,
	constraint downloads_items_id_fk
		foreign key (item_id) references items (id)
			on update cascade,
	constraint downloads_users_id_fk
		foreign key (user_id) references users (id)
			on update cascade on delete cascade
);

