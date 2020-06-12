create trigger items_update_time_trigger
	before update
	on items
	for each row
	begin
    if old.deleted = new.deleted then
        set new.update_time = current_timestamp();
    end if;
end;

