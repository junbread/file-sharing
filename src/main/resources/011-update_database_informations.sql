create event update_database_informations on schedule
	every '1' MONTH
	starts adddate(last_day(curdate()), 1)
	enable
	do
	begin
        call create_subscription_pay_procedure();
        call delete_unused_items_procedure();
        call issue_bills_procedure();
    end;

