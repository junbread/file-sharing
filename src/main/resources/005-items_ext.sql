create view items_ext as select `i`.`id`                                                                                      AS `id`,
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

