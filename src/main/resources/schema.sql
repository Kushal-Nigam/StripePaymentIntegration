create table if not exists PAYMENTS(
id int auto_increment primary key,
payment_intent_id varchar(255) unique,
email varchar(255),
amount int,
currency varchar(3),
created bigint
);

create table if not exists CHARGE(
id int auto_increment primary key,
charge_id varchar(255) unique,
payment_intent_id varchar(255) unique,
email varchar(255),
name varchar(255),
comment varchar(200),
amount int,
country_code varchar(5)
);