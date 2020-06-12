create procedure issue_bills_procedure()
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

