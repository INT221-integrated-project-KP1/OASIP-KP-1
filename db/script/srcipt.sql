-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8mb3 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`eventCategory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`eventCategory` (
  `event_category_id` INT NOT NULL AUTO_INCREMENT,
  `event_category_name` VARCHAR(100) NOT NULL,
  `event_category_description` VARCHAR(500) NULL DEFAULT NULL,
  `event_duration` INT NOT NULL,
  PRIMARY KEY (`event_category_id`),
  UNIQUE INDEX `eventCategoryName_UNIQUE` (`event_category_name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `mydb`.`event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`event` (
  `booking_id` INT NOT NULL AUTO_INCREMENT,
  `booking_name` VARCHAR(100) NOT NULL,
  `booking_email` VARCHAR(255) NOT NULL,
  `event_notes` VARCHAR(500) NULL DEFAULT NULL,
  `event_start_time` DATETIME NOT NULL,
  `event_category_id` INT NOT NULL,
  `event_duration` INT NOT NULL,
  `attachment` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`booking_id`),
  INDEX `fk_event_eventCategory_idx` (`event_category_id` ASC) VISIBLE,
  CONSTRAINT `fk_event_eventCategory`
    FOREIGN KEY (`event_category_id`)
    REFERENCES `mydb`.`eventCategory` (`event_category_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `mydb`.`myuser`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`myuser` (
  `name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `role` ENUM('ADMIN', 'LECTURER', 'STUDENT') NOT NULL,
  `createdOn` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedOn` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id` INT NOT NULL AUTO_INCREMENT,
  `password` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `mydb`.`event_category_owner`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`event_category_owner` (
  `event_category_owner_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL DEFAULT NULL,
  `event_category_id` INT NOT NULL,
  PRIMARY KEY (`event_category_owner_id`),
  INDEX `fk_myuser_has_eventCategory_eventCategory1_idx` (`event_category_id` ASC) VISIBLE,
  INDEX `fk_myuser_has_eventCategory_myuser1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_myuser_has_eventCategory_eventCategory1`
    FOREIGN KEY (`event_category_id`)
    REFERENCES `mydb`.`eventCategory` (`event_category_id`),
  CONSTRAINT `fk_myuser_has_eventCategory_myuser1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`myuser` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


CREATE USER IF NOT EXISTS  'dev'@'%' IDENTIFIED BY '123';
GRANT ALL PRIVILEGES ON * . * TO 'dev'@'%';
FLUSH PRIVILEGES;

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

insert into myuser (name,email,password,role,createdOn,updatedOn) values ('OASIP ADMIN','oasip.admin@kmutt.ac.th','$argon2id$v=19$m=4096,t=3,p=1$sYXzbUOqBoHY1NfhJ8cjnw$H6+adWySiFPgcUogJK3hEhcF6Y4fusy7tcXYEL+f0cQ','admin','2022-08-01 00:00:00+07:00','2022-08-01 00:00:00+07:00');
insert into myuser (name,email,password,role,createdOn,updatedOn) values ('Olarn Rojanapornpun','olarn.roj@kmutt.ac.th','$argon2id$v=19$m=4096,t=3,p=1$Sx7y2jxKZSjpWUV4srd8eg$AMH09iFiPQgAZ00cAdN3Gucqfhx2kRo3tQbHeLSR0RE','lecturer','2022-08-08 15:00:00+07:00','2022-08-08 15:00:00+07:00');
insert into myuser (name,email,password,role,createdOn,updatedOn) values ('Pichet Limvachiranan','pichet.limv@kmutt.ac.th','$argon2id$v=19$m=4096,t=3,p=1$46EB43gQ46Z1/EmdqxtKNA$7m6cWGO2iDlFl/ETDYuYf+ArnSjRnsNwXLIP18DTYQY','lecturer','2022-08-08 15:00:01+07:00','2022-08-08 15:00:01+07:00');
insert into myuser (name,email,password,role,createdOn,updatedOn) values ('Umaporn Supasitthimethee','umaporn.sup@kmutt.ac.th','$argon2id$v=19$m=4096,t=3,p=1$1Z2UK1zC76FIQeLH54GVAQ$qfXcHF31LnuWpt37QAcWyNp8PdbOQ+jjaV1xWXixS0M','lecturer','2022-08-08 15:00:02+07:00','2022-08-08 15:00:02+07:00');
insert into myuser (name,email,password,role,createdOn,updatedOn) values ('Siam Yamsaengsung','siam.yam@kmutt.ac.th','$argon2id$v=19$m=4096,t=3,p=1$C4pPaNWKTnZQX2mPs14jlg$rQ5W5NYKqGOu1B4GkUWq8cFbcg2peFWGjpUMr9Nkm8g','lecturer','2022-08-08 15:00:03+07:00','2022-08-08 15:00:03+07:00');
insert into myuser (name,email,password,role,createdOn,updatedOn) values ('Sunisa Sathapornvajana','sunisa.sat@kmutt.ac.th','$argon2id$v=19$m=4096,t=3,p=1$29/ffaszvjvi3CZO45bSCg$kKpfq5WEswoqa/LfyIZzQaQ6AFdjhyiYjXRCfMiTnwg','lecturer','2022-08-08 15:00:04+07:00','2022-08-08 15:00:04+07:00');
insert into myuser (name,email,password,role,createdOn,updatedOn) values ('Somchai Jaidee','somchai.jai@kmutt.ac.th','$argon2id$v=19$m=4096,t=3,p=1$dmsOy7LPTjmooPu+P2oTZA$NZFTFd3f0K1Sp19aaUwyn3jgiy15yFcXhp8E4/1yXoI','student','2022-08-08 16:00:00+07:00','2022-08-08 16:00:00+07:00');
insert into myuser (name,email,password,role,createdOn,updatedOn) values ('Komkrid Rakdee','komkrid.rak@mail.kmutt.ac.th','$argon2id$v=19$m=4096,t=3,p=1$8W61ZOC5RU7sJP5kKRbSqg$OLwZNPeMqxp+g0Vbn+odcA47XMClFN+IswTueVah7F0','student','2022-08-08 16:00:00+07:00','2022-08-08 16:00:00+07:00');
insert into myuser (name,email,password,role,createdOn,updatedOn) values ('สมเกียรติ ขยันเรียน','somkiat.kay@kmutt.ac.th','$argon2id$v=19$m=4096,t=3,p=1$gBqgjspF45FcIKQEw8GmaQ$alrOCZ0YrDqOu8/aZiLDMGZo4vFkSEAXA0YoHhY0BDQ','student','2022-08-16 09:00:00+07:00','2022-08-16 09:00:00+07:00');

insert into event_category_owner (user_id,event_category_id) values (1,16)
insert into event_category_owner (user_id,event_category_id) values (2,19)
insert into event_category_owner (user_id,event_category_id) values (3,20)
insert into event_category_owner (user_id,event_category_id) values (4,18)
insert into event_category_owner (user_id,event_category_id) values (5,17)
insert into event_category_owner (user_id,event_category_id) values (5,16)