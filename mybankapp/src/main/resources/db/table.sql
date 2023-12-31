create database mybank;

-- 유저 테이블 설계 하기 
drop table user_tb;

create table user_tb(
 id int auto_increment primary key, 
 username varchar(50) not null unique, 
 password varchar(100) not null, 
 fullname varchar(50) not null, 
 created_at timestamp not null default now()
);


-- 계좌 정보 테이블 
create table account_tb(
 id int auto_increment primary key, 
 number varchar(30) not null unique, 
 password varchar(30) not null, 
 balance bigint not null comment '계좌잔액', 
 created_at timestamp not null default now(), 
 user_id int 
);


create table history_tb(
 id int auto_increment primary key comment '거래내역 ID',
 amount bigint not null comment '거래금액', 
 w_account_id int comment '출금 계좌 id', 
 d_account_id int comment '입금 계좌 id',
 w_balance bigint comment '출금 요청 후 계좌 잔액', 
 d_balance bigint comment '입금 요청 후 계좌 잔액',
 created_at timestamp not null default now()
);

alter table user_tb
add column origin_file_name varchar(100);

alter table user_tb
add column upload_file_name varchar(100);