CREATE DATABASE springboot_mybatis_test;

CREATE TABLE t_user(
    user_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL
) ENGAIN=INNODB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;