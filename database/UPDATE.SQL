/* 20181221 */
CREATE TABLE `data_goods_package_item_vert` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `gpid` bigint(20) DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `fid` bigint(20) DEFAULT NULL,
  `fno` varchar(255) DEFAULT NULL,
  `fname` varchar(255) DEFAULT NULL,
  `fkid` bigint(20) DEFAULT NULL,
  `fkno` varchar(255) DEFAULT NULL,
  `fkname` varchar(255) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `num` decimal(11,2) DEFAULT NULL,
  `fdd_price` decimal(10,2) DEFAULT NULL,
  `cost_price` decimal(10,2) DEFAULT NULL,
  `rec_price` decimal(10,2) DEFAULT NULL,
  `sale_price` decimal(10,2) DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `it_code` char(3) NOT NULL DEFAULT '100',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/* 20181226 */
CREATE TABLE `order_item_pack` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) DEFAULT NULL,
  `order_item_id` bigint(20) DEFAULT NULL,
  `goods_id` bigint(20) DEFAULT NULL,
  `goods_no` varchar(255) DEFAULT NULL,
  `goods_name` varchar(255) DEFAULT NULL,
  `goods_sku_id` bigint(20) DEFAULT NULL,
  `goods_sku_no` varchar(255) DEFAULT NULL,
  `goods_sku_name` varchar(255) DEFAULT NULL,
  `tycode` char(3) DEFAULT NULL,
  `buy_num` decimal(10,2) DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  `remark` varchar(255) DEFAULT NULL,
  `pic_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1277 DEFAULT CHARSET=utf8;

/* 20190108 */
CREATE TABLE `order_nerm` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) DEFAULT NULL,
  `caller_num` varchar(255) DEFAULT NULL,
  `table_num` varchar(255) DEFAULT NULL,
  `room_num` varchar(255) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

