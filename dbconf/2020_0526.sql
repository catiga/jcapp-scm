

ALTER TABLE `order_info` ADD COLUMN `remark` VARCHAR(255);

ALTER TABLE `order_info` ADD COLUMN `admin_memo` VARCHAR(255);

ALTER TABLE `data_goods_sku` ADD COLUMN `cost_price` DECIMAL(12,2);
ALTER TABLE `data_goods_sku` ADD COLUMN `weight` DECIMAL(12,2);

CREATE TABLE `order_ship` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `sender_name` varchar(255) DEFAULT NULL,
  `sender_phone` varchar(255) DEFAULT NULL,
  `sender_prov_code` varchar(255) DEFAULT NULL,
  `sender_prov_name` varchar(255) DEFAULT NULL,
  `sender_city_code` varchar(255) DEFAULT NULL,
  `sender_city_name` varchar(255) DEFAULT NULL,
  `sender_zone_code` varchar(255) DEFAULT NULL,
  `sender_zone_name` varchar(255) DEFAULT NULL,
  `receiver_name` varchar(255) DEFAULT NULL,
  `receiver_phone` varchar(255) DEFAULT NULL,
  `receiver_prov_code` varchar(255) DEFAULT NULL,
  `receiver_prov_name` varchar(255) DEFAULT NULL,
  `receiver_city_code` varchar(255) DEFAULT NULL,
  `receiver_city_name` varchar(255) DEFAULT NULL,
  `receiver_zone_code` varchar(255) DEFAULT NULL,
  `receiver_zone_name` varchar(255) DEFAULT NULL,
  `receiver_address` varchar(255) DEFAULT NULL,
  `ship_type` varchar(10) DEFAULT NULL,
  `exp_id` bigint(20) DEFAULT NULL,
  `exp_code` varchar(64) DEFAULT NULL,
  `exp_name` varchar(255) DEFAULT NULL,
  `exp_odd` varchar(255) DEFAULT NULL,
  `exp_str` varchar(255) DEFAULT NULL,
  `exp_price` decimal(10,2) DEFAULT NULL,
  `exp_tips` varchar(255) DEFAULT NULL,
  `exp_value` decimal(10,2) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `data_dic_express` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `sort_order` tinyint(4) DEFAULT NULL,
  `MonthCode` varchar(255) DEFAULT NULL,
  `CustomerName` varchar(255) DEFAULT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT '0',
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


