create database car_rental;

use car_rental;

create table users(
	email_id varchar(25) primary key,
    first_name varchar(25),
    last_name varchar(25),
    password varchar(25),
    contact_no varchar(10),
    address varchar(100),
    is_admin boolean default false
);

create table vehicle_type(
	type_id int primary key,
    price_per_day double,
    type varchar(25)
);

create table vehicle(
	registration_no varchar(25) primary key,
    brand varchar(25),
    model varchar(25),
    color varchar(25),
    is_available boolean default true,
    type_id int,
    foreign key(type_id) references vehicle_type(type_id)

);

create table rental(
	rental_id int primary key auto_increment,
    email_id varchar(25),
    registration_no varchar(25),
    start_date date,
    return_date date,
    total_amount double,
    foreign key(email_id) references users(email_id),
    foreign key(registration_no) references vehicle(registration_no)
);

insert into vehicle_type values (1,1500,'Sedan'),(2,1400,'SUV'),(3,1400,'Hatchback');

insert into users values ('saurav@gmail.com','Saurav','Somani','sskvss43','7874591789','38/7,Radhe krishna park-1,Jamnagar',true);

insert into vehicle values('GJ 10 AD 5986','Audi','A8','black',true,1);
insert into vehicle values('GJ 10 AD 4321','Audi','Q7','black',true,2);
insert into vehicle values('GJ 10 AD 7895','Audi','X5','black',true,3);