create view subscribers_stat as select `u`.`id`                                                              AS `user_id`,
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

