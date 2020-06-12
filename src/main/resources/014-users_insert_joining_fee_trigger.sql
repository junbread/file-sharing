create trigger users_insert_joining_fee_trigger
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

