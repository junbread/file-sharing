create table dependencies
(
	id int auto_increment
		primary key,
	item_id int not null,
	require_item_id int not null,
	constraint dependencies_items_id_fk
		foreign key (item_id) references items (id)
			on update cascade on delete cascade,
	constraint dependencies_items_id_fk2
		foreign key (require_item_id) references items (id)
			on update cascade on delete cascade
);

