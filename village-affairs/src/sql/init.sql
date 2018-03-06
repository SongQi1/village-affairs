
insert into tb_user_status (id, status, status_desc)
values (1, 'normal', '正常');

insert into tb_user_status (id, status, status_desc)
values (2, 'locked', '锁定');






insert into tb_role (id, description, role_key, role_value)
values (1, '这是超级管理员', 'ROLE_ADMIN', '超级管理员');

insert into tb_role (id, description, role_key, role_value)
values (2, null, 'ROLE_USER', '普通用户');




insert into tb_permission values (1, 'y', '文件删除', 'file:delete');
insert into tb_permission values (2, 'y', '项目删除', 'project:delete');
insert into tb_permission values (3, 'y', '项目审核', 'project:check');
insert into tb_permission values (4, 'y', '文件上传', 'file:upload');
insert into tb_permission values (5, 'y', '图片上传', 'image:upload');
insert into tb_permission values (6, 'y', '视频上传', 'vedio:upload');


insert into r_role_permission values (1, '1');
insert into r_role_permission values (1, '2');
insert into r_role_permission values (1, '3');
insert into r_role_permission values (1, '4');
insert into r_role_permission values (1, '5');
insert into r_role_permission values (1, '6');

insert into r_role_permission values (2, '1');
insert into r_role_permission values (2, '2');
insert into r_role_permission values (2, '4');
insert into r_role_permission values (2, '5');
insert into r_role_permission values (2, '6');



insert into tb_sys_user (id, chinese_name, create_time, degree, is_deleted, department_key, email, failed_cnt, job_position, job_title, last_change_pwd_time, last_login_time, mobile_no, password, qq, user_name, weixin, user_status_id, community) values ('1', 'admin', '2016-10-30 08:56:45', null, 'n', null, 'admin@qq.com', '0', null, null, null, '2016-10-30 11:40:03', null, '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', null, 'admin', null, '1', 'penglang,pengxii,pengxin,pengchen,pengyuan,penglai');

insert into r_sysuser_role (sysuser_id, role_id) values(1,1);
