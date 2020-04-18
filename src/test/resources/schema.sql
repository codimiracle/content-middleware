/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : content_service

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2020-04-17 23:15:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for content
-- ----------------------------
DROP TABLE IF EXISTS `content`;
CREATE TABLE `content` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '内容id',
  `type` varchar(255) DEFAULT NULL COMMENT '内容类型（topic: 话题, comment: 评论, review: 点评, book: 图书）',
  `owner_id` int(11) DEFAULT NULL COMMENT '拥有者id',
  `comments` int(11) DEFAULT '0' COMMENT '评论数',
  `likes` bigint(11) DEFAULT '0' COMMENT '点赞数',
  `dislikes` bigint(11) DEFAULT '0',
  `reposts` bigint(11) DEFAULT '0' COMMENT '转发数',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标识',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted_at` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for content_article
-- ----------------------------
DROP TABLE IF EXISTS `content_article`;
CREATE TABLE `content_article` (
  `content_id` int(11) NOT NULL COMMENT '内容id',
  `target_content_id` int(11) DEFAULT NULL COMMENT '内容对内容id',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `words` bigint(20) DEFAULT NULL COMMENT '字数',
  `article_type` varchar(255) DEFAULT NULL COMMENT '内容源类型（html: HTML代码, plaintext: 纯文本）',
  `article_source` mediumtext COMMENT '内容源',
  `status` varchar(255) DEFAULT NULL COMMENT '内容状态',
  `reads` bigint(20) DEFAULT '0' COMMENT '阅读数',
  PRIMARY KEY (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for content_examination
-- ----------------------------
DROP TABLE IF EXISTS `content_examination`;
CREATE TABLE `content_examination` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `target_content_id` int(11) DEFAULT NULL,
  `from_status` varchar(255) DEFAULT NULL,
  `to_status` varchar(255) DEFAULT NULL,
  `examiner_id` int(11) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `revoked` tinyint(1) DEFAULT NULL COMMENT '撤销',
  `examined_at` datetime DEFAULT NULL COMMENT '评审时间',
  `revoked_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for content_likes
-- ----------------------------
DROP TABLE IF EXISTS `content_likes`;
CREATE TABLE `content_likes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `liker_id` int(11) DEFAULT NULL COMMENT '点赞用户',
  `content_id` int(11) DEFAULT NULL COMMENT '点赞内容',
  `type` char(7) DEFAULT NULL COMMENT '点赞类型（喜欢：like，不喜欢：unlike)',
  `hited` tinyint(1) DEFAULT NULL COMMENT '点赞（1：已点赞，0：取消点赞）',
  `hited_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for content_mention
-- ----------------------------
DROP TABLE IF EXISTS `content_mention`;
CREATE TABLE `content_mention` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content_id` int(11) NOT NULL,
  `mention_user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for content_rate
-- ----------------------------
DROP TABLE IF EXISTS `content_rate`;
CREATE TABLE `content_rate` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评分Id',
  `content_id` int(11) DEFAULT NULL COMMENT '内容Id',
  `type` varchar(255) DEFAULT NULL COMMENT '评分类型',
  `rate` float DEFAULT '0' COMMENT '评分',
  `rate_count` int(11) DEFAULT '0',
  `rate_sum` double DEFAULT '0',
  `rate_avg` double DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for content_reference
-- ----------------------------
DROP TABLE IF EXISTS `content_reference`;
CREATE TABLE `content_reference` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reference_target_type` varchar(255) DEFAULT NULL COMMENT '引用类型',
  `reference_target_id` varchar(255) DEFAULT NULL COMMENT '图书id 或者 内容id',
  `content_id` int(11) DEFAULT NULL COMMENT '内容id',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标识',
  `referenced_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for content_tags
-- ----------------------------
DROP TABLE IF EXISTS `content_tags`;
CREATE TABLE `content_tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '内容对标签Id',
  `content_id` int(11) DEFAULT NULL COMMENT '内容Id',
  `tag_id` int(11) DEFAULT NULL COMMENT '标签Id',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标识',
  `deleted_at` varchar(255) DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
