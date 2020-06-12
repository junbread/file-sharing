create procedure delete_unused_items_procedure()
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

