create table items
(
	id int auto_increment
		primary key,
	author_id varchar(20) not null,
	category_id int not null,
	type enum('PROGRAM', 'IMAGE', 'VIDEO', 'AUDIO', 'DOCUMENT') not null,
	name varchar(100) not null,
	size int not null,
	url varchar(200) not null,
	update_time timestamp default current_timestamp() not null,
	hardware enum('MAC', 'PC', 'WORKSTATION', 'ALL') default 'ALL' null,
	os enum('MAC', 'WINDOWS', 'LINUX', 'ALL') default 'ALL' null,
	description varchar(1000) null,
	deleted tinyint(1) default 0 not null,
	constraint items_categories_id_fk
		foreign key (category_id) references categories (id),
	constraint items_users_id_fk
		foreign key (author_id) references users (id)
			on update cascade on delete cascade
);

