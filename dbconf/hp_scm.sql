/*
 Navicat MySQL Data Transfer

 Source Server         : 10.3.66.160
 Source Server Type    : MySQL
 Source Server Version : 50643
 Source Host           : 10.3.66.160:3306
 Source Schema         : hp_scm

 Target Server Type    : MySQL
 Target Server Version : 50643
 File Encoding         : 65001

 Date: 28/04/2019 22:17:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bscmb_provider
-- ----------------------------
DROP TABLE IF EXISTS `bscmb_provider`;
CREATE TABLE `bscmb_provider`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `a_time` datetime(0) NOT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `pid` bigint(20) NOT NULL,
  `num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `province_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `city_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `zone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `zone_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `contact_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `contact_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for catalog_dsc
-- ----------------------------
DROP TABLE IF EXISTS `catalog_dsc`;
CREATE TABLE `catalog_dsc`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cat_id` bigint(20) NULL DEFAULT NULL,
  `dsc_id` bigint(20) NULL DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_category_info
-- ----------------------------
DROP TABLE IF EXISTS `data_category_info`;
CREATE TABLE `data_category_info`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `proj_id` bigint(20) NULL DEFAULT NULL,
  `parent_id` bigint(20) NULL DEFAULT NULL,
  `cat_icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cat_name_cn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cat_name_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `seq` int(11) NULL DEFAULT 0,
  `cat_show` tinyint(4) NOT NULL DEFAULT 1,
  `click_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0000',
  `click_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cat_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cat_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `hierars` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `show_dsc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_freight_tpl_info
-- ----------------------------
DROP TABLE IF EXISTS `data_freight_tpl_info`;
CREATE TABLE `data_freight_tpl_info`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ft_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `proj_id` bigint(20) NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `is_def_sys` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_freight_tpl_rule
-- ----------------------------
DROP TABLE IF EXISTS `data_freight_tpl_rule`;
CREATE TABLE `data_freight_tpl_rule`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ftpl` bigint(20) NULL DEFAULT NULL,
  `ac` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ff` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fm` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cfm` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fpt` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fpv` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_g_f_sm
-- ----------------------------
DROP TABLE IF EXISTS `data_g_f_sm`;
CREATE TABLE `data_g_f_sm`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `g_id` bigint(20) NULL DEFAULT NULL,
  `tycode` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NULL DEFAULT NULL,
  `flag` tinyint(4) NULL DEFAULT NULL,
  `pid` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_gd_merge_info
-- ----------------------------
DROP TABLE IF EXISTS `data_gd_merge_info`;
CREATE TABLE `data_gd_merge_info`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cost_price` decimal(10, 2) NULL DEFAULT NULL,
  `rec_price` decimal(10, 2) NULL DEFAULT NULL,
  `sale_price` decimal(10, 2) NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_gd_merge_item
-- ----------------------------
DROP TABLE IF EXISTS `data_gd_merge_item`;
CREATE TABLE `data_gd_merge_item`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `gpid` bigint(20) NULL DEFAULT NULL,
  `fid` bigint(20) NULL DEFAULT NULL,
  `fno` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fkid` bigint(20) NULL DEFAULT NULL,
  `fkno` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fkname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `num` decimal(10, 6) NULL DEFAULT NULL,
  `spec_unit` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `unit` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cost_price` decimal(10, 2) NULL DEFAULT NULL,
  `rec_price` decimal(10, 2) NULL DEFAULT NULL,
  `sale_price` decimal(10, 2) NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `it_code` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '100',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_gf_sm_catalog
-- ----------------------------
DROP TABLE IF EXISTS `data_gf_sm_catalog`;
CREATE TABLE `data_gf_sm_catalog`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `g_id` bigint(20) NULL DEFAULT NULL,
  `c_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `tycode` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `c_id`(`c_id`) USING BTREE,
  INDEX `g_id`(`g_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_goods_category
-- ----------------------------
DROP TABLE IF EXISTS `data_goods_category`;
CREATE TABLE `data_goods_category`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `g_id` bigint(20) NULL DEFAULT NULL,
  `c_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `tycode` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `c_id`(`c_id`) USING BTREE,
  INDEX `g_id`(`g_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_goods_content
-- ----------------------------
DROP TABLE IF EXISTS `data_goods_content`;
CREATE TABLE `data_goods_content`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `goods_id` bigint(20) NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_goods_info
-- ----------------------------
DROP TABLE IF EXISTS `data_goods_info`;
CREATE TABLE `data_goods_info`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `goods_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品编号',
  `goods_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `goods_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '产品价格',
  `goods_original_price` decimal(10, 2) NULL DEFAULT NULL,
  `cost_price` decimal(10, 2) NULL DEFAULT NULL,
  `goods_material` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '产品材料',
  `goods_picturelink` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品图片链接',
  `goods_picturelink_big` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_state` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0000' COMMENT '产品状态：上架、下架',
  `goods_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '产品备注',
  `goods_picturelink_middle` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `proj_id` bigint(20) NOT NULL DEFAULT 2,
  `model_id` bigint(20) NULL DEFAULT NULL,
  `unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `config` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `weight` float NULL DEFAULT NULL,
  `spec_unit` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `freepost` tinyint(4) NOT NULL DEFAULT 1,
  `ftpl` bigint(20) NULL DEFAULT NULL,
  `local` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1',
  `limit_buy_num` int(11) NOT NULL DEFAULT 0,
  `stock` int(11) NULL DEFAULT 1,
  `gt` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  `goods_out_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_code` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '快速检索关键词',
  `other_id` bigint(20) NULL DEFAULT NULL COMMENT '其他信息表id',
  `a_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `proj_id`(`proj_id`) USING BTREE,
  INDEX `goods_state`(`goods_state`) USING BTREE,
  INDEX `goods_id`(`goods_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '产品基础信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_goods_model
-- ----------------------------
DROP TABLE IF EXISTS `data_goods_model`;
CREATE TABLE `data_goods_model`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `proj_id` bigint(20) NOT NULL,
  `m_name_cn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `m_name_en` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `a_time` datetime(0) NOT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_goods_model_kv
-- ----------------------------
DROP TABLE IF EXISTS `data_goods_model_kv`;
CREATE TABLE `data_goods_model_kv`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `gm_id` bigint(20) NULL DEFAULT NULL,
  `attr_k` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_goods_op_stock_batch
-- ----------------------------
DROP TABLE IF EXISTS `data_goods_op_stock_batch`;
CREATE TABLE `data_goods_op_stock_batch`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NULL DEFAULT NULL,
  `in_house` tinyint(4) NOT NULL DEFAULT 1,
  `op_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `bname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `binfo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NOT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `uid` bigint(20) NULL DEFAULT NULL,
  `uname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `store_id` bigint(20) NULL DEFAULT NULL,
  `store_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `aim_store_id` bigint(20) NULL DEFAULT NULL,
  `aim_store_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流水号',
  `is_def_sys` tinyint(4) NOT NULL DEFAULT 0,
  `s_wh_id` bigint(20) NULL DEFAULT NULL,
  `s_wh_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `a_wh_id` bigint(20) NULL DEFAULT NULL,
  `a_wh_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_goods_package
-- ----------------------------
DROP TABLE IF EXISTS `data_goods_package`;
CREATE TABLE `data_goods_package`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cost_price` decimal(10, 2) NULL DEFAULT NULL,
  `rec_price` decimal(10, 2) NULL DEFAULT NULL,
  `sale_price` decimal(10, 2) NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_goods_package_item
-- ----------------------------
DROP TABLE IF EXISTS `data_goods_package_item`;
CREATE TABLE `data_goods_package_item`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `gpid` bigint(20) NULL DEFAULT NULL,
  `fid` bigint(20) NULL DEFAULT NULL,
  `fno` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fkid` bigint(20) NULL DEFAULT NULL,
  `fkno` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fkname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cost_price` decimal(10, 2) NULL DEFAULT NULL,
  `rec_price` decimal(10, 2) NULL DEFAULT NULL,
  `sale_price` decimal(10, 2) NULL DEFAULT NULL,
  `num` decimal(10, 2) NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `it_code` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '100',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_goods_package_item_vert
-- ----------------------------
DROP TABLE IF EXISTS `data_goods_package_item_vert`;
CREATE TABLE `data_goods_package_item_vert`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `gpid` bigint(20) NULL DEFAULT NULL,
  `item_id` bigint(20) NULL DEFAULT NULL,
  `fid` bigint(20) NULL DEFAULT NULL,
  `fno` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fkid` bigint(20) NULL DEFAULT NULL,
  `fkno` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fkname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `num` decimal(11, 2) NULL DEFAULT NULL,
  `fdd_price` decimal(10, 2) NULL DEFAULT NULL,
  `cost_price` decimal(10, 2) NULL DEFAULT NULL,
  `rec_price` decimal(10, 2) NULL DEFAULT NULL,
  `sale_price` decimal(10, 2) NULL DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `it_code` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '100',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_goods_sku
-- ----------------------------
DROP TABLE IF EXISTS `data_goods_sku`;
CREATE TABLE `data_goods_sku`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `goods_id` bigint(20) NOT NULL,
  `sku_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品详细编号',
  `sku_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '产品详细价格(扩展)',
  `sku_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品图片链接',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '产品备注',
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `stock` int(11) NULL DEFAULT 1,
  `skus` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '产品详细信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_goods_stock
-- ----------------------------
DROP TABLE IF EXISTS `data_goods_stock`;
CREATE TABLE `data_goods_stock`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `stock` decimal(10, 2) NULL DEFAULT NULL,
  `unit` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `weight` float NULL DEFAULT NULL,
  `spec_unit` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `in_house` tinyint(4) NOT NULL DEFAULT 1,
  `op_type` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `store_id` bigint(20) NULL DEFAULT NULL,
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wh_id` bigint(20) NULL DEFAULT NULL,
  `wh_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_id` bigint(20) NULL DEFAULT NULL,
  `goods_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_sku_id` bigint(20) NULL DEFAULT NULL,
  `goods_sku_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_sku_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parainfo` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cost_price` decimal(10, 2) NULL DEFAULT NULL,
  `product_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `quality_date` int(11) NULL DEFAULT NULL,
  `auid` bigint(20) NULL DEFAULT NULL,
  `auname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `batchno` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `opbatchid` bigint(20) NULL DEFAULT NULL,
  `opbatchsn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` bigint(20) NULL DEFAULT NULL,
  `form_id` bigint(20) NULL DEFAULT NULL,
  `origdata` bigint(20) NULL DEFAULT NULL,
  `tax_fee` decimal(10, 2) NULL DEFAULT NULL,
  `take_item_id` bigint(20) NULL DEFAULT NULL,
  `take_id` bigint(20) NULL DEFAULT NULL,
  `stunicode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `goods_id`(`goods_id`) USING BTREE,
  INDEX `store_id`(`store_id`) USING BTREE,
  INDEX `goods_sku_id`(`goods_sku_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_goodsinfo_img
-- ----------------------------
DROP TABLE IF EXISTS `data_goodsinfo_img`;
CREATE TABLE `data_goodsinfo_img`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `goods_id` bigint(20) NULL DEFAULT NULL,
  `predomain` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `img_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0000',
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `content_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ct_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `tycode` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '100',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_gs_form
-- ----------------------------
DROP TABLE IF EXISTS `data_gs_form`;
CREATE TABLE `data_gs_form`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `batch_id` bigint(20) NULL DEFAULT NULL,
  `batch_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wh_id` bigint(20) NULL DEFAULT NULL,
  `wh_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `happen_time` datetime(0) NULL DEFAULT NULL,
  `happen_uid` bigint(20) NULL DEFAULT NULL,
  `happen_uname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `pid` bigint(20) NULL DEFAULT NULL,
  `op_uid` bigint(20) NULL DEFAULT NULL,
  `op_uname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `aim_wh_id` bigint(20) NULL DEFAULT NULL,
  `aim_wh_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `origdata` bigint(20) NULL DEFAULT NULL,
  `providerid` bigint(20) NULL DEFAULT NULL,
  `providername` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_inventory_check
-- ----------------------------
DROP TABLE IF EXISTS `data_inventory_check`;
CREATE TABLE `data_inventory_check`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NULL DEFAULT NULL,
  `icname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icinfo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icno` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ictype` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `ouid` bigint(20) NULL DEFAULT NULL,
  `ouname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `icss` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `storeid` bigint(20) NULL DEFAULT NULL,
  `storename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wh_id` bigint(20) NULL DEFAULT NULL,
  `wh_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_inventory_check_item
-- ----------------------------
DROP TABLE IF EXISTS `data_inventory_check_item`;
CREATE TABLE `data_inventory_check_item`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NULL DEFAULT NULL,
  `icid` bigint(20) NULL DEFAULT NULL,
  `goods_id` bigint(20) NULL DEFAULT NULL,
  `goods_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `spec` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `unit` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_sku_id` bigint(20) NULL DEFAULT NULL,
  `goods_sku_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_sku_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `recnum` decimal(10, 2) NULL DEFAULT NULL,
  `realnum` decimal(10, 2) NULL DEFAULT NULL,
  `money` decimal(10, 2) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cuid` bigint(20) NULL DEFAULT NULL,
  `cuname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dsc_setting
-- ----------------------------
DROP TABLE IF EXISTS `dsc_setting`;
CREATE TABLE `dsc_setting`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `codec` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `pid` bigint(20) NULL DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for inventory_warning_setting_detail
-- ----------------------------
DROP TABLE IF EXISTS `inventory_warning_setting_detail`;
CREATE TABLE `inventory_warning_setting_detail`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `total_id` bigint(20) NULL DEFAULT NULL,
  `wh_id` bigint(20) NULL DEFAULT NULL,
  `up_limit` decimal(10, 2) NULL DEFAULT NULL,
  `low_limit` decimal(10, 2) NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for inventory_warning_setting_total
-- ----------------------------
DROP TABLE IF EXISTS `inventory_warning_setting_total`;
CREATE TABLE `inventory_warning_setting_total`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `goods_id` bigint(20) NULL DEFAULT NULL,
  `goods_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_sku_id` bigint(20) NULL DEFAULT NULL,
  `goods_sku_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_sku_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `totuplimit` decimal(10, 2) NULL DEFAULT NULL,
  `totlowlimit` decimal(10, 2) NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NULL DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `pid` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '付款时间',
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '订单总价',
  `pay_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '应付金额',
  `oss` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dss` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `buyerid` bigint(20) NULL DEFAULT NULL,
  `buyername` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `buyerphone` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `buyerprovincecode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `buyerprovincename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `buyercitycode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `buyercityname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `buyerzonecode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `buyerzonename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `buyeraddr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `buyerpoint` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` bigint(20) NULL DEFAULT NULL,
  `store_id` bigint(20) NULL DEFAULT NULL,
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ouid` bigint(20) NULL DEFAULT NULL,
  `ouname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pay_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `admin_memo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NULL DEFAULT NULL,
  `goods_id` bigint(20) NULL DEFAULT NULL,
  `goods_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_sku_id` bigint(20) NULL DEFAULT NULL,
  `goods_sku_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_sku_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tycode` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `buy_num` decimal(10, 2) NULL DEFAULT NULL,
  `unit_amount` decimal(10, 2) NULL DEFAULT NULL,
  `pay_amount` decimal(10, 2) NULL DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `fss` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for order_item_pack
-- ----------------------------
DROP TABLE IF EXISTS `order_item_pack`;
CREATE TABLE `order_item_pack`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NULL DEFAULT NULL,
  `order_item_id` bigint(20) NULL DEFAULT NULL,
  `goods_id` bigint(20) NULL DEFAULT NULL,
  `goods_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_sku_id` bigint(20) NULL DEFAULT NULL,
  `goods_sku_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_sku_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tycode` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `buy_num` decimal(10, 2) NULL DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for order_nerm
-- ----------------------------
DROP TABLE IF EXISTS `order_nerm`;
CREATE TABLE `order_nerm`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NULL DEFAULT NULL,
  `caller_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `table_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `room_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_warehouse_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_warehouse_info`;
CREATE TABLE `sys_warehouse_info`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `level` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `contact_person` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `contact_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `province_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `zone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `zone_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `a_time` timestamp(0) NULL DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `pid` bigint(20) NULL DEFAULT NULL,
  `store_id` bigint(20) NULL DEFAULT NULL,
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sort` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `data_shopping_cart`;
CREATE TABLE `data_shopping_cart` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL,
  `ap_id` bigint(20) DEFAULT NULL,
  `basic_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `goods_id` bigint(20) DEFAULT NULL,
  `goods_name` varchar(255) DEFAULT NULL,
  `goods_pic` varchar(255) DEFAULT NULL,
  `goods_sku_id` bigint(20) DEFAULT NULL,
  `goods_sku_name` varchar(255) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `add_price` decimal(10,2) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for order_ship
-- ----------------------------
DROP TABLE IF EXISTS `order_ship`;
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

-- ----------------------------
-- Table structure for data_dic_express
-- ----------------------------
DROP TABLE IF EXISTS `data_dic_express`;
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

SET FOREIGN_KEY_CHECKS = 1;
