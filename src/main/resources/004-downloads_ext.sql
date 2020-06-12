create view downloads_ext as select `d`.`id`            AS `id`,
       `u`.`id`            AS `user_id`,
       `u`.`name`          AS `user_name`,
       `i`.`id`            AS `item_id`,
       `i`.`name`          AS `item_name`,
       `d`.`download_time` AS `download_time`
from ((`project`.`downloads` `d` join `project`.`users` `u` on (`d`.`user_id` = `u`.`id`))
         join `project`.`items` `i` on (`d`.`item_id` = `i`.`id`));

