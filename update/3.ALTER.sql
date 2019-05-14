/*20180901*/
ALTER TABLE `order_info` ADD COLUMN `buyerpoint` VARCHAR(255) AFTER `buyeraddr`;

/*20180904*/
ALTER TABLE `data_goods_stock` ADD COLUMN `take_id` BIGINT(20) AFTER `tax_fee`;
ALTER TABLE `data_goods_stock` ADD COLUMN `take_item_id` BIGINT(20) AFTER `tax_fee`;


/*20180906*/
ALTER TABLE `order_item` ADD COLUMN `pic_url` VARCHAR(255) AFTER `remark`;


/*20180911*/
CREATE TABLE `inventory_warning_setting_total` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` bigint(20) DEFAULT NULL,
  `goods_no` varchar(255) DEFAULT NULL,
  `goods_name` varchar(255) DEFAULT NULL,
  `goods_sku_id` bigint(20) DEFAULT NULL,
  `goods_sku_no` varchar(255) DEFAULT NULL,
  `goods_sku_name` varchar(255) DEFAULT NULL,
  `totuplimit` decimal(10,2) DEFAULT NULL,
  `totlowlimit` decimal(10,2) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NULL DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  `pid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `inventory_warning_setting_detail` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `total_id` bigint(20) DEFAULT NULL,
  `wh_id` bigint(20) DEFAULT NULL,
  `up_limit` decimal(10,2) DEFAULT NULL,
  `low_limit` decimal(10,2) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;


/**20180919**/
ALTER TABLE order_info ADD COLUMN `pay_type` VARCHAR (20);


/**20181002**/
ALTER TABLE data_goods_stock ADD COLUMN `stunicode` VARCHAR (255);


/**20181101**/
CREATE TABLE `catalog_dsc` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `cat_id` bigint(20) DEFAULT NULL,
  `dsc_id` bigint(20) DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

CREATE TABLE `dsc_setting` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(16) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `pid` bigint(20) DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

ALTER TABLE data_category_info ADD COLUMN `show_dsc` VARCHAR (255);

/**20181102**/
ALTER TABLE dsc_setting CHANGE COLUMN code codec VARCHAR(16);


