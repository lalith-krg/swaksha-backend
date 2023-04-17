-- MySQL Script generated by MySQL Workbench
-- Fri Mar 24 19:16:33 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema swaksha_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `swaksha_db` ;

-- -----------------------------------------------------
-- Schema swaksha_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `swaksha_db` DEFAULT CHARACTER SET utf8 ;
USE `swaksha_db` ;

-- -----------------------------------------------------
-- Table `swaksha_db`.`Doctor_Details`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `swaksha_db`.`Doctor_Details` ;

CREATE TABLE IF NOT EXISTS `swaksha_db`.`Doctor_Details` (
  `ssid` CHAR(12) NOT NULL,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `phone_number` VARCHAR(45) NULL,
  `dob` DATETIME NULL,
  `gender` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(45) NULL,
  PRIMARY KEY (`ssid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `swaksha_db`.`Doctor_Hospital_Association`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `swaksha_db`.`Doctor_Hospital_Association` ;

CREATE TABLE IF NOT EXISTS `swaksha_db`.`Doctor_Hospital_Association` (
  `Hospital_Details_ssid` CHAR(12) NOT NULL,
  `Doctor_Details_ssid` CHAR(12) NOT NULL,
  PRIMARY KEY (`Hospital_Details_ssid`, `Doctor_Details_ssid`),
  INDEX `fk_Hospital_Details_has_Doctor_Details_Doctor_Details1_idx` (`Doctor_Details_ssid` ASC) VISIBLE,
  INDEX `fk_Hospital_Details_has_Doctor_Details_Hospital_Details1_idx` (`Hospital_Details_ssid` ASC) VISIBLE,
  CONSTRAINT `fk_Hospital_Details_has_Doctor_Details_Hospital_Details1`
    FOREIGN KEY (`Hospital_Details_ssid`)
    REFERENCES `swaksha_db`.`Hospital_Details` (`ssid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Hospital_Details_has_Doctor_Details_Doctor_Details1`
    FOREIGN KEY (`Doctor_Details_ssid`)
    REFERENCES `swaksha_db`.`Doctor_Details` (`ssid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `swaksha_db`.`Doctor_Waiting_List`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `swaksha_db`.`Doctor_Waiting_List` ;

CREATE TABLE IF NOT EXISTS `swaksha_db`.`Doctor_Waiting_List` (
  `regID` CHAR(12) NOT NULL,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `phone_number` VARCHAR(45) NULL,
  `dob` DATETIME NULL,
  `gender` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(45) NULL,
  PRIMARY KEY (`regID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `swaksha_db`.`Hospital_Credentials`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `swaksha_db`.`Hospital_Credentials` ;

CREATE TABLE IF NOT EXISTS `swaksha_db`.`Hospital_Credentials` (
  `ssid` CHAR(12) NOT NULL,
  `password` VARCHAR(30) CHARACTER SET 'utf8' NOT NULL,
  PRIMARY KEY (`ssid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `swaksha_db`.`Hospital_Details`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `swaksha_db`.`Hospital_Details` ;

CREATE TABLE IF NOT EXISTS `swaksha_db`.`Hospital_Details` (
  `ssid` CHAR(12) NOT NULL,
  `hospital_name` VARCHAR(45) NULL,
  `phone_num_2` VARCHAR(45) NULL,
  `phone_num_1` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(45) NULL,
  PRIMARY KEY (`ssid`),
  CONSTRAINT `hospital_cred_fk`
    FOREIGN KEY (`ssid`)
    REFERENCES `swaksha_db`.`Hospital_Credentials` (`ssid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `swaksha_db`.`Hospital_Waiting_List`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `swaksha_db`.`Hospital_Waiting_List` ;

CREATE TABLE IF NOT EXISTS `swaksha_db`.`Hospital_Waiting_List` (
  `regid` CHAR(12) NOT NULL,
  `hospital_name` VARCHAR(45) NULL,
  `phone_num_2` VARCHAR(45) NULL,
  `phone_num_1` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(45) NULL,
  PRIMARY KEY (`regid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `swaksha_db`.`Patient_Credentials`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `swaksha_db`.`Patient_Credentials` ;

CREATE TABLE IF NOT EXISTS `swaksha_db`.`Patient_Credentials` (
  `ssid` CHAR(12) NOT NULL,
  `password` VARCHAR(30) CHARACTER SET 'utf8' NOT NULL,
  PRIMARY KEY (`ssid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `swaksha_db`.`Patient_Details`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `swaksha_db`.`Patient_Details` ;

CREATE TABLE IF NOT EXISTS `swaksha_db`.`Patient_Details` (
  `ssid` CHAR(12) NOT NULL,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `phone_number` VARCHAR(45) NULL,
  `dob` DATETIME NULL,
  `gender` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(45) NULL,
  PRIMARY KEY (`ssid`),
  CONSTRAINT `patient_cred_fk`
    FOREIGN KEY (`ssid`)
    REFERENCES `swaksha_db`.`Patient_Credentials` (`ssid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `swaksha_db`.`Patient_Doctor_Association`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `swaksha_db`.`Patient_Doctor_Association` ;

CREATE TABLE IF NOT EXISTS `swaksha_db`.`Patient_Doctor_Association` (
  `Patient_Details_ssid` CHAR(12) NOT NULL,
  `Doctor_Details_ssid` CHAR(12) NOT NULL,
  PRIMARY KEY (`Patient_Details_ssid`, `Doctor_Details_ssid`),
  INDEX `fk_Patient_Details_has_Doctor_Details_Doctor_Details1_idx` (`Doctor_Details_ssid` ASC) VISIBLE,
  INDEX `fk_Patient_Details_has_Doctor_Details_Patient_Details1_idx` (`Patient_Details_ssid` ASC) VISIBLE,
  CONSTRAINT `fk_Patient_Details_has_Doctor_Details_Patient_Details1`
    FOREIGN KEY (`Patient_Details_ssid`)
    REFERENCES `swaksha_db`.`Patient_Details` (`ssid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Patient_Details_has_Doctor_Details_Doctor_Details1`
    FOREIGN KEY (`Doctor_Details_ssid`)
    REFERENCES `swaksha_db`.`Doctor_Details` (`ssid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;