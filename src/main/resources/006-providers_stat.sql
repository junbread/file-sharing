create view providers_stat as select `items_ext`.`author_id`                                                                 AS `user_id`,
       `items_ext`.`author_name`                                                               AS `user_name`,
       ifnull(sum(`items_ext`.`download_cnt`) over ( partition by `items_ext`.`author_id`), 0) AS `user_download_cnt`,
       `items_ext`.`id`                                                                        AS `item_id`,
       `items_ext`.`name`                                                                      AS `item_name`,
       `items_ext`.`download_cnt`                                                              AS `item_download_cnt`
from `project`.`items_ext`;

