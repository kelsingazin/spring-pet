CREATE DATABASE IF NOT EXISTS photoapp;

-- create user with password
CREATE USER IF NOT EXISTS 'photoapp'@'%' IDENTIFIED BY 'photoapp@db';

-- grant access to user on database
GRANT TRIGGER ON photoapp.* TO 'photoapp'@'%';
GRANT ALTER ON photoapp.* TO 'photoapp'@'%';
GRANT ALTER ROUTINE ON photoapp.* TO 'photoapp'@'%';
GRANT CREATE ROUTINE ON photoapp.* TO 'photoapp'@'%';
GRANT DELETE ON photoapp.* TO 'photoapp'@'%';
GRANT SELECT ON photoapp.* TO 'photoapp'@'%';
GRANT CREATE ON photoapp.* TO 'photoapp'@'%';
GRANT EVENT ON photoapp.* TO 'photoapp'@'%';
GRANT CREATE TEMPORARY TABLES ON photoapp.* TO 'photoapp'@'%';
GRANT CREATE VIEW ON photoapp.* TO 'photoapp'@'%';
GRANT INDEX ON photoapp.* TO 'photoapp'@'%';
GRANT UPDATE ON photoapp.* TO 'photoapp'@'%';
GRANT SHOW VIEW ON photoapp.* TO 'photoapp'@'%';
GRANT REFERENCES ON photoapp.* TO 'photoapp'@'%';
GRANT INSERT ON photoapp.* TO 'photoapp'@'%';
GRANT LOCK TABLES ON photoapp.* TO 'photoapp'@'%';
GRANT DROP ON photoapp.* TO 'photoapp'@'%';