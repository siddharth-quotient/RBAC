create table role (role_id bigint not null auto_increment, role_description varchar(255), role_name varchar(255), primary key (role_id)) engine=InnoDB;
alter table role add constraint UK_iubw515ff0ugtm28p8g3myt0h unique (role_name);

