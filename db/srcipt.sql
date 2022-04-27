-- MySQL Script generated by MySQL Workbench
-- Fri Apr 22 22:29:57 2022
-- Model: New Model    Version: 1.0
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
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`eventCategory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`eventCategory` (
  `event_category_id` INT NOT NULL,
  `event_category_name` VARCHAR(100) NULL character set utf8 ,
  `event_category_description` VARCHAR(500) NULL character set utf8,
  `event_duration` INT NULL,
  constraint `eventCategory` Check (event_duration <= 480),
  PRIMARY KEY (`event_category_id`),
  UNIQUE INDEX `eventCategoryName_UNIQUE` (`event_category_name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`event` (
  `booking_id` INT NOT NULL ,
  `booking_name` VARCHAR(100) NULL character set utf8 ,
  `booking_email` VARCHAR(255) ,
  `event_notes` VARCHAR(500) NULL character set utf8 ,
  `event_start_time` DATETIME NULL,
  `event_category_id` INT NOT NULL ,
  `event_duration` INT NULL ,
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
insert into eventCategory values(1,"Project Management Clinic","ตารางนัดหมายนี้ใช้สำหรับนัดหมาย project management clinic ในวิชา INT221 integrated project I ให้นักศึกษาเตรียมเอกสารที่เกี่ยวข้องเพื่อแสดงระหว่างขอคำปรึกษา",30);
insert into eventCategory values(2,"DevOps/Infra Clinic","Use this event category for DevOps/Infra clinic.",20);
insert into eventCategory values(3,"Database Clinic","ตารางนัดหมายนี้ใช้สำหรับนัดหมาย database clinic ในวิชา INT221 integrated project I",15);
insert into eventCategory values(4,"Client-side Clinic","ตารางนัดหมายนี้ใช้สำหรับนัดหมาย client-side clinic ในวิชา INT221 integrated project I",30);
insert into eventCategory values(5,"Server-side Clinic","",30);

insert into event values(1, "Somchai Jaidee (OR-7)", "somchai.jai@mail.kmutt.ac.th", "", '2022-05-23 13:30:00', 2, 30);
insert into event values(2,"Somsri Rakdee (SJ-3)","somsri.rak@mail.kmutt.ac.th","ขอปรึกษาปัญหาเพื่อนไม่ช่วยงาน", '2565-12-02 23:00:10', 1, 30);
insert into event values(3, "สมเกียรติ ขยันเรียน กลุ่ม TT-4", "somkiat.kay@kmutt.ac.th","", '2565-12-20 06:30:41', 3, 15);


CREATE USER 'test'@'%' IDENTIFIED BY '123';
GRANT ALL PRIVILEGES ON * . * TO 'test'@'%';
FLUSH PRIVILEGES;