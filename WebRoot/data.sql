drop table if exists `t_user`;
create table `t_user` (
 `id` int not null primary key auto_increment,
 `username` varchar(24) not null,
 `password` varchar(64) not null,
 unique key `idx_username`(`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into t_user values (1,'admin','czit2019');

drop table if exists `t_upload`;
create table `t_upload` (
 `id` int not null primary key auto_increment,
 `upload_time` datetime not null,
 `username` varchar(24) not null,
 `original_name` varchar(64) not null,
 `uudi_name` varchar(64) not null,
 `absolute_path` varchar(164) not null,
 key `idx_username`(`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;