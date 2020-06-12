create or replace table categories
(
    id int auto_increment
        primary key,
    name varchar(20) not null
);

create or replace table constants
(
    id varchar(20) not null
        primary key,
    value bigint not null
);

create or replace table users
(
    id varchar(20) not null
        primary key,
    pw char(40) not null,
    name varchar(20) not null,
    role enum('SUBSCRIBER', 'PROVIDER', 'ADMIN') default 'SUBSCRIBER' not null,
    account_number varchar(20) not null,
    address varchar(100) null,
    phone varchar(13) null,
    birthday date null,
    join_date date default current_timestamp() not null,
    subscription tinyint(1) default 0 not null,
    last_login_time timestamp null
);

create or replace table bills
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

create or replace table items
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

create or replace table dependencies
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

create or replace table downloads
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

create or replace trigger downloads_insert_payment_trigger
    after insert
    on downloads
    for each row
begin
    declare item_size int(11) default 0;
    declare download_rate bigint(20) default 0;
    declare uploader_id varchar(20) default NULL;
    begin
        select value into download_rate from constants where id = 'download_rate';
        select size, author_id into item_size, uploader_id from items where id = new.item_id;

        insert into payments (user_id, type, amount, due_date, comment)
        values (new.user_id,
                'PAYMENT',
                item_size * download_rate,
                adddate(last_day(curdate()), 1),
                'DOWNLOAD');
        insert into payments (user_id, type, amount, due_date, comment)
        values (uploader_id,
                'INCOME',
                item_size * download_rate,
                adddate(last_day(curdate()), 1),
                'DOWNLOAD');
    end;

end;

create or replace trigger items_update_time_trigger
    before update
    on items
    for each row
begin
    if old.deleted = new.deleted then
        set new.update_time = current_timestamp();
    end if;
end;

create or replace table payments
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

create or replace trigger users_insert_joining_fee_trigger
    after update
    on users
    for each row
begin
    declare joining_fee bigint(20) default 0;

    if new.role = 'PROVIDER' and old.last_login_time is null and new.last_login_time is not null then
        select value into joining_fee from constants where id = 'joining_fee';
        insert into payments (user_id, type, amount, due_date, comment)
        values (new.id, 'PAYMENT', joining_fee, adddate(last_day(curdate()), 1), 'JOINING');
    end if;
end;

create or replace view dependencies_ext as select `d`.`item_id`  AS `item_id`,
                                                  `i`.`id`       AS `require_item_id`,
                                                  `i`.`name`     AS `require_item_name`,
                                                  `i`.`size`     AS `require_item_size`,
                                                  `i`.`hardware` AS `require_item_hardware`,
                                                  `i`.`os`       AS `require_item_os`,
                                                  `i`.`url`      AS `require_item_url`
                                           from (`project`.`dependencies` `d`
                                                    join `project`.`items` `i` on (`d`.`require_item_id` = `i`.`id`));

create or replace view downloads_ext as select `d`.`id`            AS `id`,
                                               `u`.`id`            AS `user_id`,
                                               `u`.`name`          AS `user_name`,
                                               `i`.`id`            AS `item_id`,
                                               `i`.`name`          AS `item_name`,
                                               `d`.`download_time` AS `download_time`
                                        from ((`project`.`downloads` `d` join `project`.`users` `u` on (`d`.`user_id` = `u`.`id`))
                                                 join `project`.`items` `i` on (`d`.`item_id` = `i`.`id`));

create or replace view items_ext as select `i`.`id`                                                                                      AS `id`,
                                           `i`.`type`                                                                                    AS `type`,
                                           `i`.`name`                                                                                    AS `name`,
                                           `i`.`size`                                                                                    AS `size`,
                                           `i`.`url`                                                                                     AS `url`,
                                           `i`.`update_time`                                                                             AS `update_time`,
                                           `i`.`hardware`                                                                                AS `hardware`,
                                           `i`.`os`                                                                                      AS `os`,
                                           `i`.`description`                                                                             AS `description`,
                                           (select count(0) from `project`.`downloads` where `project`.`downloads`.`item_id` = `i`.`id`) AS `download_cnt`,
                                           `c`.`id`                                                                                      AS `category_id`,
                                           `c`.`name`                                                                                    AS `category_name`,
                                           `u`.`id`                                                                                      AS `author_id`,
                                           `u`.`name`                                                                                    AS `author_name`,
                                           `i`.`deleted`                                                                                 AS `deleted`
                                    from ((`project`.`items` `i` join `project`.`categories` `c` on (`i`.`category_id` = `c`.`id`))
                                             join `project`.`users` `u` on (`i`.`author_id` = `u`.`id`));

create or replace view providers_stat as select `items_ext`.`author_id`                                                                 AS `user_id`,
                                                `items_ext`.`author_name`                                                               AS `user_name`,
                                                ifnull(sum(`items_ext`.`download_cnt`) over ( partition by `items_ext`.`author_id`), 0) AS `user_download_cnt`,
                                                `items_ext`.`id`                                                                        AS `item_id`,
                                                `items_ext`.`name`                                                                      AS `item_name`,
                                                `items_ext`.`download_cnt`                                                              AS `item_download_cnt`
                                         from `project`.`items_ext`;

create or replace view subscribers_stat as select `u`.`id`                                                              AS `user_id`,
                                                  `u`.`name`                                                            AS `user_name`,
                                                  ifnull(sum(`d`.`download_cnt`) over ( partition by `d`.`user_id`), 0) AS `user_download_cnt`,
                                                  `d`.`item_id`                                                         AS `item_id`,
                                                  `d`.`item_name`                                                       AS `item_name`,
                                                  `d`.`download_cnt`                                                    AS `item_download_cnt`
                                           from (`project`.`users` `u`
                                                    join (select `downloads_ext`.`user_id`   AS `user_id`,
                                                                 `downloads_ext`.`item_id`   AS `item_id`,
                                                                 `downloads_ext`.`item_name` AS `item_name`,
                                                                 count(0)                    AS `download_cnt`
                                                          from `project`.`downloads_ext`
                                                          group by `downloads_ext`.`user_id`, `downloads_ext`.`item_id`) `d` on (`u`.`id` = `d`.`user_id`))
                                           where `u`.`role` = 'SUBSCRIBER';

create or replace procedure create_subscription_pay_procedure()
begin
    declare subscription_fee bigint default 0;
    declare upload_rate bigint default 0;
    select value into subscription_fee from constants where id = 'subscription';
    select value into upload_rate from constants where id = 'upload_rate';

    /* Providers */
    insert into payments (user_id, type, amount, due_date, comment)
    select author_id,
           'PAYMENT',
           sum(size) * upload_rate,
           adddate(last_day(curdate()), 1),
           'SUBSCRIPTION'
    from items
    group by author_id;

    /* Subscribers */
    insert into payments (user_id, type, amount, due_date, comment)
    select id,
           'PAYMENT',
           subscription_fee,
           adddate(last_day(curdate()), 1),
           'SUBSCRIPTION'
    from users
    where role = 'SUBSCRIBER'
      and  subscription = true;
end;

create or replace procedure delete_unused_items_procedure()
begin
    declare threshold bigint default 0;
    select value into threshold from constants where id = 'delete_threshold';

    update items
    set deleted = true
    where deleted = false
      and (select count(*)
           from downloads
           where item_id = id
             and month(download_time) = (month(curdate()) - 1)
           group by item_id
          ) < threshold;
end;

create or replace procedure issue_bills_procedure()
begin
    declare update_date timestamp default current_timestamp();

    /* Issue new bills */
    insert into bills (user_id, issue_date, income, payment, status)
    select id,
           update_date,
           ifnull((select sum(amount)
                   from payments
                   where user_id = users.id
                     and issue_bill_id is null
                     and type = 'INCOME'), 0) +
           ifnull((select sum(income) from bills where user_id = users.id and status = 'PENDING'), 0),
           ifnull((select sum(amount)
                   from payments
                   where user_id = users.id
                     and issue_bill_id is null
                     and type = 'PAYMENT'), 0) +
           ifnull((select sum(payment) from bills where user_id = users.id and status = 'PENDING'), 0),
           'PENDING'
    from users;

    /* Expire old bills */
    update bills
    set status = 'FINISHED'
    where issue_date <> update_date;

    /* Update pending payments */
    update payments
    set issue_bill_id = (select id from bills where user_id = payments.user_id and status = 'PENDING')
    where issue_bill_id is null;
end;

create or replace event if not exists update_database_informations on schedule
    every '1' MONTH
        starts adddate(last_day(curdate()), 1)
    enable
    do
    begin
        call create_subscription_pay_procedure();
        call delete_unused_items_procedure();
        call issue_bills_procedure();
    end;
