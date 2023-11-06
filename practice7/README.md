# practice7

spring security


### MySQL 設定
* 建立資料表格(Identity)
    ```
	DROP TABLE If Exists role_permission;
	DROP TABLE If Exists user_role;
	DROP TABLE If Exists `role`;
	DROP TABLE If Exists permission;
	DROP TABLE If Exists `user`;

	CREATE TABLE `role` (
	 id                 INT AUTO_INCREMENT NOT NULL,
	 role_name          VARCHAR(30),
	 role_desc          VARCHAR(60),
	 CONSTRAINT role_primary_key PRIMARY KEY (id));
 
	CREATE TABLE permission (
	 id                 INT AUTO_INCREMENT NOT NULL,
	 permission_name    VARCHAR(30),
	 permission_desc    VARCHAR(60),
	 CONSTRAINT permission_primary_key PRIMARY KEY (id));

	CREATE TABLE role_permission (
	 rid                INT NOT NULL,
	 pid                INT NOT NULL,
	 CONSTRAINT role_permission_primary_key PRIMARY KEY (rid, pid),
	 CONSTRAINT FOREIGN KEY (rid) REFERENCES `role` (id),
	 CONSTRAINT FOREIGN KEY (pid) REFERENCES `permission` (id));
    
	CREATE TABLE `user` (
	 id                 INT AUTO_INCREMENT NOT NULL,
	 username           VARCHAR(30),
	 `password`         VARCHAR(120),
	 status              INT default '1',
	 CONSTRAINT user_primary_key PRIMARY KEY (id));
 
	CREATE TABLE user_role (
	 uid                INT NOT NULL,
	 rid                INT NOT NULL,
	 CONSTRAINT user_role_primary_key PRIMARY KEY (uid, rid),
	 CONSTRAINT FOREIGN KEY (uid) REFERENCES `user` (id),
	 CONSTRAINT FOREIGN KEY (rid) REFERENCES `role` (id));
	 
	CREATE TABLE persistent_logins (
      username VARCHAR(64) NOT NULL,
      series VARCHAR(64) PRIMARY KEY,
      token VARCHAR(64) NOT NULL,
      last_used TIMESTAMP NOT NULL);
    ```