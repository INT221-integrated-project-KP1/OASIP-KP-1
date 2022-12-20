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
  `password` VARCHAR(90) NOT NULL,
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
