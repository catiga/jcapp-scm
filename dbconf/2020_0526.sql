

ALTER TABLE `order_info` ADD COLUMN `remark` VARCHAR(255);

ALTER TABLE `order_info` ADD COLUMN `admin_memo` VARCHAR(255);

ALTER TABLE `data_goods_sku` ADD COLUMN `cost_price` DECIMAL(12,2);
ALTER TABLE `data_goods_sku` ADD COLUMN `weight` DECIMAL(12,2);

ALTER TABLE `data_freight_tpl_info` ADD COLUMN `package_price` DECIMAL(12,2);
ALTER TABLE `data_freight_tpl_info` ADD COLUMN `freight_type` CHAR(2) DEFAULT '00';

alter table `data_freight_tpl_rule` modify column `ff` DECIMAL(10,2);
alter table `data_freight_tpl_rule` modify column `fm` DECIMAL(10,2);
alter table `data_freight_tpl_rule` modify column `cfm` DECIMAL(10,2);
alter table `data_freight_tpl_rule` modify column `a_time` DATETIME(0);
alter table `data_freight_tpl_rule` add column `cff` DECIMAL(10,2);
alter table `data_freight_tpl_rule` add column `freemoney_flag` tinyint(4) NOT NULL DEFAULT 0;
alter table `data_freight_tpl_rule` add column `freemoney_value` DECIMAL(10,2);
alter table `data_freight_tpl_rule` add column `freewn_flag` tinyint(4) NOT NULL DEFAULT 0;
alter table `data_freight_tpl_rule` add column `freewn_value` DECIMAL(10,2);

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

CREATE TABLE `data_shopping_cart` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL,
  `ap_id` bigint(20) DEFAULT NULL,
  `basic_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `goods_id` bigint(20) DEFAULT NULL,
  `goods_no` varchar(255) DEFAULT NULL,
  `goods_name` varchar(255) DEFAULT NULL,
  `goods_pic` varchar(255) DEFAULT NULL,
  `goods_sku_id` bigint(20) DEFAULT NULL,
  `goods_sku_no` varchar(255) DEFAULT NULL,
  `goods_sku_name` varchar(255) DEFAULT NULL,
  `weight` decimal(10,2) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `add_price` decimal(12,2) DEFAULT NULL,
  `retail_price` decimal(12,2) DEFAULT NULL,
  `is_on_sale` int(11) NOT NULL DEFAULT '1',
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


