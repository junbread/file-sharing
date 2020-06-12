create view dependencies_ext as select `d`.`item_id`  AS `item_id`,
       `i`.`id`       AS `require_item_id`,
       `i`.`name`     AS `require_item_name`,
       `i`.`size`     AS `require_item_size`,
       `i`.`hardware` AS `require_item_hardware`,
       `i`.`os`       AS `require_item_os`,
       `i`.`url`      AS `require_item_url`
from (`project`.`dependencies` `d`
         join `project`.`items` `i` on (`d`.`require_item_id` = `i`.`id`));

