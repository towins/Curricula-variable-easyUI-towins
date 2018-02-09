-- 数据库脚本
DROP DATABASE IF EXISTS  scss;
CREATE DATABASE scss DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

use scss;

CREATE TABLE course
(
  id bigint(19) PRIMARY KEY COMMENT '唯一标识',
  name VARCHAR(80) NOT NULL COMMENT '课程名称',
  teacher VARCHAR(30) NOT NULL COMMENT '任课老师',
  intro VARCHAR(200) COMMENT '课程简介',
  service_teacher VARCHAR(30) NOT NULL COMMENT '班主任',
  service_teacher_tel VARCHAR(20) NOT NULL COMMENT '班主任电话',
  begin_teach_time DATETIME  COMMENT '开始上课时间',
  end_teach_time DATETIME  COMMENT '结束上课时间',
  begin_select_time DATETIME  COMMENT '开始选课时间',
  end_select_time DATETIME  COMMENT '结束选课时间',
  classroom VARCHAR(20) NOT NULL COMMENT '教室',
  max_amount int(4) DEFAULT 30 NOT NULL COMMENT '上课人数上限',
  current_amount int(4) DEFAULT 0 NOT NULL COMMENT '当前已选人数',
  credit int(2) DEFAULT 3 NOT NULL COMMENT '学分',
  status CHAR(2) DEFAULT 'AC' NOT NULL COMMENT '状态：AC可选；CM完成；PS备选'
);

CREATE TABLE cadre
(
  id bigint(19) PRIMARY KEY COMMENT '唯一标识',
  name VARCHAR(30) NOT NULL COMMENT '姓名',
  password VARCHAR(36) NOT NULL COMMENT '密码',
  emp_card VARCHAR(16) NOT NULL COMMENT '工作证号',
  gender VARCHAR(2) DEFAULT '男' NOT NULL COMMENT '性别',
  tel VARCHAR(20) NOT NULL COMMENT '电话',
  email VARCHAR(80) NOT NULL COMMENT '邮箱'
);

#CREATE TABLE sc_opt
#(
#	id bigint(19) PRIMARY KEY COMMENT '唯一标识',
#	cadre_id bigint(19) NOT NULL  COMMENT '唯一标识',
#	course_id bigint(19) NOT NULL COMMENT '唯一标识',
#	opt_type char(2) NOT NULL COMMENT '操作类型：选课：XK、退课：TK、排队：PD',
#	opt_time datetime DEFAULT current_timestamp NOT NULL COMMENT '操作时间'
#);

CREATE TABLE sc_info
(
	cadre_id bigint(19) NOT NULL COMMENT '唯一标识',
	course_id bigint(19) NOT NULL COMMENT '唯一标识',
	opt_type char(2) NOT NULL COMMENT '操作类型：选课：XK、退课：TK、排队：PD',
	opt_time datetime DEFAULT current_timestamp NOT NULL COMMENT '操作时间',
	attendance char(2) DEFAULT 'CQ' NOT NULL COMMENT '出勤情况：出勤：CQ、缺勤：QQ',
	primary key id_pk (cadre_id,course_id)
);

CREATE TABLE sys_params
(
	id varchar(50) primary key,
	value varchar(100) NOT NULL
);

INSERT INTO cadre VALUES
(1,'张小偿',md5('1'),'20106221122','女','1333333333','958370187@qq.com'),
(2,'刘匀空',md5('1'),'20106221123','男','1333333333','15963338693@163.com'),
(3,'赵成仓',md5('1'),'20106221124','男','1333333333','test3@163.com'),
(4,'封昆',md5('1'),'20106221121','男','1333333333','test4@163.com'),
(5,'于楠',md5('1'),'20106221126','女','1333333333','447668591@qq.com'),
(6,'钱云',md5('1'),'20160220010','女','1333333333','test6@163.com'),
(7,'许中良',md5('1'),'20160220011','男','1333333333','test7@163.com'),
(8,'李权',md5('1'),'20160220012','男','1333333333','test8@163.com'),
(9,'刘书有',md5('1'),'20160220013','男','1333333333','test9@163.com'),
(10,'葛纯希',md5('1'),'20160220014','女','1333333333','test10@163.com'),
(11,'张汉国',md5('1'),'20160220015','男','1333333333','test11@163.com'),
(12,'方舍',md5('1'),'20160220016','男','1333333333','test12@163.com'),
(13,'刘天会',md5('1'),'20160220017','女','1333333333','test13@163.com'),
(14,'周传伟',md5('1'),'20160220018','男','1333333333','test14@163.com'),
(15,'王云海',md5('1'),'20160220019','男','1333333333','test15@163.com');

INSERT INTO course VALUES
(1,'Java初级','刘小亮','','聂空','13322233333','2017-12-10 14:30:00','2017-12-10 16:00:00','2017-11-25 00:00:00','2017-12-05 23:59:59','218',50,0,3,'CM'),
(2,'数学趣谈','赵述','','聂空','13322233333','2018-01-03 14:30:00','2018-01-03 16:00:00','2018-01-15 00:00:00','2018-01-25 23:59:59','218',50,1,3,'AC'),
(3,'周易浅析','任仙','','聂空','13322233333','2018-01-25 10:30:00','2018-01-25 12:00:00','2018-01-05 00:00:00','2018-01-15 23:59:59','212',150,0,5,'AC'),
(4,'中国近代史1','王永忠','','余远','18322283382','2018-01-12 13:00:00','2018-01-12 16:00:00','2018-01-15 00:00:00','2018-02-01 23:59:59','1018',30,30,2,'AC'),
(5,'马克思的一生','张传国','','余远','18322283382','2018-01-11 14:30:00','2018-01-11 16:00:00','2018-01-15 00:00:00','2018-02-03 23:59:59','218',50,0,3,'PS'),
(6,'当代经济','刘方才','','聂空','13322233333','2018-01-28 09:30:00','2018-01-28 12:00:00','2018-01-10 00:00:00','2018-01-25 23:59:59','2018',30,0,2,'PS'),
(7,'宋诗','方会','','余远','18322283382','2018-02-10 14:30:00','2018-02-10 16:00:00','2018-01-25 00:00:00','2018-02-05 23:59:59','218',50,0,3,'PS'),
(8,'唐宋八大家','常彬','','余远','18322283382','2018-02-11 14:30:00','2018-02-11 16:00:00','2018-01-25 00:00:00','2018-02-05 23:59:59','218',50,0,3,'PS'),
(9,'Java中级','李成','','余远','18322283382','2018-03-10 14:30:00','2018-03-10 16:00:00','2018-02-15 00:00:00','2018-03-02 23:59:59','218',50,0,3,'PS'),
(10,'明清经济社会形态','封鹏','','聂空','13322233333','2018-01-05 14:30:00','2018-01-05 16:00:00','2018-01-25 00:00:00','2018-01-23 23:59:59','218',50,1,3,'PS'),
(11,'法律与生活','蒯艳','','聂空','13322233333','2018-02-15 14:30:00','2018-02-15 16:00:00','2018-01-25 00:00:00','2018-02-10 23:59:59','218',50,0,3,'PS'),
(12,'Java高级','徐文广','','聂空','13322233333','2018-02-17 14:30:00','2018-02-17 16:00:00','2018-01-25 00:00:00','2018-02-12 23:59:59','218',50,0,3,'PS'),
(13,'考古故事','钱贡要','','聂空','13322233333','2018-02-19 14:30:00','2018-02-19 16:00:00','2018-01-25 00:00:00','2018-02-13 23:59:59','218',50,0,3,'PS'),
(14,'书法入门','赵引','','余远','18322283382','2018-04-10 14:30:00','2018-04-10 16:00:00','2018-03-15 00:00:00','2018-04-05 23:59:59','218',50,0,3,'PS'),
(15,'计算机的维修','房可','','余远','18322283382','2018-04-15 14:30:00','2018-04-15 16:00:00','2018-03-30 00:00:00','2018-04-10 23:59:59','218',50,0,5,'PS'),
(16,'隶书','吴悬','','余远','18322283382','2018-04-02 14:30:00','2018-04-02 16:00:00','2018-03-15 00:00:00','2018-03-28 23:59:59','2018',50,0,2,'PS'),
(17,'大脑的保养','孙一顺','','余远','18322283382','2018-02-01 14:30:00','2018-02-01 16:00:00','2018-01-15 00:00:00','2018-02-05 23:59:59','218',50,0,3,'PS'),
(18,'利己主意者宣言','刘欣','','余远','18322283382','2018-02-02 14:30:00','2018-02-02 16:00:00','2018-01-15 00:00:00','2018-02-05 23:59:59','218',50,0,3,'PS'),
(19,'互联网的前瞻','周先','','余远','18322283382','2018-02-03 14:30:00','2018-02-03 16:00:00','2018-01-15 00:00:00','2018-02-05 23:59:59','218',50,0,2,'PS'),
(20,'英文口语提高','胡娟','','余远','18322283382','2018-03-10 14:30:00','2018-03-10 16:00:00','2018-02-25 00:00:00','2018-03-07 23:59:59','218',50,0,1,'PS');

INSERT INTO sc_info (cadre_id,course_id,opt_type,opt_time) VALUES
(1,2,'XK','2018-01-23 10:30:22'),
(1,4,'XK','2018-01-13 10:30:22'),
(1,5,'TK','2018-01-23 10:30:22'),
(1,10,'XK','2018-01-23 10:30:22'),
(2,4,'PD','2018-01-23 10:30:22'),
(3,4,'PD','2018-01-23 08:30:22'),
(1,18,'XK','2018-01-23 10:30:22'),
(2,18,'XK','2018-01-23 10:30:22'),
(3,18,'TK','2018-01-23 10:30:22'),
(4,18,'PD','2018-01-23 10:30:22'),
(5,18,'XK','2018-01-23 10:30:22');

INSERT INTO sys_params VALUES
('MIN_CREDIT','15'),
('MAX_CREDIT','25'),
('COUNT_BEGIN_DATE','2017-09-01 00:00:00'),
('COUNT_END_DATE','2018-03-01 23:59:59');

commit;


-- 查询在可选时间范围内，且某个干部没有选中或排队的课程
-- SELECT * FROM course WHERE current_timestamp BETWEEN begin_select_time AND end_select_time
--AND id NOT IN (SELECT course_id FROM sc_info WHERE (opt_type='XK' OR opt_type='PD') AND cadre_id=1);

-- 查询某个干部在可退课范围内的课程
--SELECT * FROM course WHERE current_timestamp < date_add(end_select_time, interval -1 day)
--AND id IN (SELECT course_id FROM sc_info WHERE (opt_type='XK' OR opt_type='PD') AND cadre_id=1);

-- 查询某个干部已经选中或排队，但还没有上课的课程。
--SELECT * FROM course WHERE current_timestamp < begin_teach_time
--AND id IN (SELECT course_id FROM sc_info WHERE (opt_type='XK' OR opt_type='PD') AND cadre_id=1);

-- 类型Oracle的merge，当主键存在执行update，否则执行insert into
--replace into sc_info (cadre_id,course_id,opt_type,opt_time) values(1,2,'AA',current_timestamp);
--replace into sc_info (cadre_id,course_id,opt_type,opt_time) values(2,2,'XK',current_timestamp);