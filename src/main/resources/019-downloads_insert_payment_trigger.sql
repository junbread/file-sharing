create trigger downloads_insert_payment_trigger
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

