create table payments
(
	id int auto_increment
		primary key,
	user_id varchar(20) not null,
	type enum('INCOME', 'PAYMENT') default 'PAYMENT' not null,
	amount bigint not null,
	due_date date not null,
	comment varchar(20) null,
	issue_bill_id int null,
	constraint payments_bills_id_fk
		foreign key (issue_bill_id) references bills (id)
			on update cascade,
	constraint payments_users_id_fk
		foreign key (user_id) references users (id)
			on update cascade on delete cascade
);

