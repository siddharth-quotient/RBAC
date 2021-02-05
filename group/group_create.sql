create table group_table (group_id bigint not null auto_increment, group_description varchar(255), group_name varchar(255), primary key (group_id)) engine=InnoDB;
alter table group_table add constraint UK_5awb2k4ure0fi6kk52ui3hyvk unique (group_name);
