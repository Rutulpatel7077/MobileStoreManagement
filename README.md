# MobileStoreManagement
The Mobile Store Management built in JAVA</br>
<img src="http://image3.mouthshut.com/images/ImagesR/2008/10/The-Mobile-Store-Pune-925102720-7767102-1.jpg" style="text-aligh:centre;"/>

# Changes You need to do.

Run This SQL CODE

```javascript

DROP DATABASE IF EXISTS java;

CREATE DATABASE java;
USE java;

DROP TABLE IF EXISTS mobiles;
DROP TABLE IF EXISTS sales;
DROP TABLE IF EXISTS users;

SET SQL_SAFE_UPDATES=0;

create table mobiles 
(
    stockNumber INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    dateInstock DATE,
    dateOfSelling date,
    make VARCHAR(32),
    operatingSystem VARCHAR(32),
    model VARCHAR (32),
    color VARCHAR(32),
    imei LONG,
    ram INT,
    storageCapacity INT,
    website VARCHAR(32),
    imageFile VARCHAR(100),
    sold enum('false','true') default 'false',
    purchasePrice double,
    sellingPrice  double

);

create table users(
userId int auto_increment primary key,
admin boolean default 0,
firstName varchar(30),
lastName varchar(30),
phoneNumber varchar(13),
birthday date,
password varchar(255),
imageFile varchar(100)
);

ALTER table users add COLUMN salt BLOB;


create TABLE sales(
salesId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
purchasePrice Double NOT NULL,
sellingPrice Double NOT NULL,
dateOfSelling DATE NOT NULL
);
```
# Change and put your sql localhost credentials
 Update ConnectionPassword.java in Views folder
 
# Add your very first user in users table 
 Comment out line line 22 and uncomment line line 28 in MobileStoreManagement.java 
 so that allow you to add user in table and then you can use everything regular
