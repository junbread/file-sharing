create procedure create_subscription_pay_procedure()
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

