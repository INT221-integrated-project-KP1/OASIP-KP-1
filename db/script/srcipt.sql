SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

CREATE TABLE IF NOT EXISTS `mydb`.`eventCategory` (
  `event_category_id` INT NOT NULL AUTO_INCREMENT,
  `event_category_name` VARCHAR(100) NOT NULL,
  `event_category_description` VARCHAR(500) NULL,
  `event_duration` INT NOT NULL,
  constraint `eventCategory` Check (event_duration <= 480),
  PRIMARY KEY (`event_category_id`),
  UNIQUE INDEX `eventCategoryName_UNIQUE` (`event_category_name` ASC) VISIBLE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `mydb`.`event` (
  `booking_id` INT NOT NULL AUTO_INCREMENT,
  `booking_name` VARCHAR(100) NOT NULL,
  `booking_email` VARCHAR(255) NOT NULL,
  `event_notes` VARCHAR(500) NULL,
  `event_start_time` DATETIME NOT NULL,
  `event_category_id` INT NOT NULL,
  `event_duration` INT NOT NULL,
  PRIMARY KEY (`booking_id`),
  INDEX `fk_event_eventCategory_idx` (`event_category_id` ASC) VISIBLE,
  constraint `event` Check (booking_email like '%@%' and event_duration <= 480),
  CONSTRAINT `fk_event_eventCategory`
    FOREIGN KEY (`event_category_id`)
    REFERENCES `mydb`.`eventCategory` (`event_category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



use mydb;
ALTER TABLE eventCategory AUTO_INCREMENT=1;
ALTER TABLE event AUTO_INCREMENT=1;
set names utf8;
insert into eventCategory (event_category_name,event_category_description,event_duration) values ('Project Management Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย project management clinic ในวิชา INT221 integrated project I ให้นักศึกษาเตรียมเอกสารที่เกี่ยวข้อง เพื่อแสดงระหว่างขอคำปรึกษา',30);
insert into eventCategory (event_category_name,event_category_description,event_duration)  values ('DevOps/Infra Clinic','Use this event category for DevOps/Infra clinic.',30);
insert into eventCategory (event_category_name,event_category_description,event_duration)  values ('Database Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย database clinic ในวิชา INT221 integrated project I',15);
insert into eventCategory (event_category_name,event_category_description,event_duration)  values ('Client-side Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย client-side clinic ในวิชา INT221 integrated project I',30);
insert into eventCategory (event_category_name,event_category_description,event_duration)  values ('Server-side Clinic',null,30);

insert into event(booking_name,booking_email,event_notes,event_start_time,event_category_id,event_duration) values ('Somchai Jaidee (OR-7)','somchai.jai@mail.kmutt.ac.th',null,'2022-05-23 13:30:00',2,30);
insert into event (booking_name,booking_email,event_notes,event_start_time,event_category_id,event_duration)values ('Somsri Rakdee (SJ-3)','somsri.rak@mail.kmutt.ac.th','ขอปรึกษาปัญหาเพื่อนไม่ช่วยงาน','2022-04-27 09:30:00',1,30);
insert into event (booking_name,booking_email,event_notes,event_start_time,event_category_id,event_duration)values ('สมเกียรติ ขยันเรียน กลุ่ม TT-4','somkiat.kay@kmutt.ac.th',null,'2022-05-23 16:30:00',3,15);

DROP TABLE IF EXISTS `myuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `myuser` (
  `name` varchar(100) NOT NULL,
  `email` varchar(50) NOT NULL,
  `role` enum('ADMIN','LECTURER','STUDENT') NOT NULL,
  `createdOn` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedOn` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id` int NOT NULL AUTO_INCREMENT,
    `password` varchar(90) NOT NULL ,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;


CREATE TABLE IF NOT EXISTS `mydb`.`event_category_owner` (
  `user_id` INT NULL,
  `event_category_id` INT NOT NULL,
  `event_category_owner_id` INT NOT NULL,
  INDEX `fk_myuser_has_eventcategory_eventcategory1_idx` (`event_category_id` ASC) VISIBLE,
  INDEX `fk_myuser_has_eventcategory_myuser1_idx` (`user_id` ASC) VISIBLE,
  PRIMARY KEY (`event_category_owner_id`),
  CONSTRAINT `fk_myuser_has_eventcategory_myuser1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`myuser` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_myuser_has_eventcategory_eventcategory1`
    FOREIGN KEY (`event_category_id`)
    REFERENCES `mydb`.`eventcategory` (`event_category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


CREATE USER IF NOT EXISTS  'dev'@'%' IDENTIFIED BY '123';
GRANT ALL PRIVILEGES ON * . * TO 'dev'@'%';
FLUSH PRIVILEGES;