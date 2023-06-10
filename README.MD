##树洞

##ziliao
[Github OAuth](https://docs.github.com/en/apps/oauth-apps/building-oauth-apps/creating-an-oauth-app/)

##by tc

##脚本
`sql
create table user
(
id           int auto_increment
primary key,
account_id   varchar(100) null,
name         varchar(50)  null,
token        char(36)     null,
gmt_create   bigint       null,
gmt_modified bigint       null
);
`