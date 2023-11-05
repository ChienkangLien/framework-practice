# practice0

原始專案：動態 web 專案

### MySQL 設定
    
* 資料庫連線設定
    ```
    使用者名稱: sa
    密碼: 123456
    
    主機名稱: localhost
    連接埠: 3306
    資料庫: practice
    
    連線字串: jdbc:mysql://localhost:3306/practice
    ```
* 建立資料庫
    ```
    CREATE DATABASE practice;
    CREATE USER sa@localhost IDENTIFIED BY '123456';
    GRANT ALL PRIVILEGES ON practice.* TO sa;
    GRANT ALL PRIVILEGES ON practice.* TO 'sa'@'localhost';
    ```

* 建立資料表格(Identity)
    ```
    DROP TABLE If Exists emp2;
    DROP TABLE If Exists dept2;

    CREATE TABLE dept2 (
     deptno              INT AUTO_INCREMENT NOT NULL,
     dname               VARCHAR(14),
     loc                 VARCHAR(13),
     CONSTRAINT dept2_primary_key PRIMARY KEY (deptno));

    INSERT INTO dept2(dname,loc,deptno) VALUES('財務部','臺灣台北',10);
    INSERT INTO dept2(dname,loc,deptno) VALUES('研發部','臺灣新竹',20);
    INSERT INTO dept2(dname,loc,deptno) VALUES('業務部','美國紐約',30);
    INSERT INTO dept2(dname,loc,deptno) VALUES('生管部','日本東京',40);

    CREATE TABLE emp2 (
     empno               INT AUTO_INCREMENT NOT NULL,
     ename               VARCHAR(10),
     job                 VARCHAR(9),
     hiredate            DATE,
     sal                 FLOAT,
     comm                FLOAT,
     deptno              INT NOT NULL,
     CONSTRAINT emp2_deptno_fk FOREIGN KEY (deptno) REFERENCES dept2 (deptno),
     CONSTRAINT emp2_empno_pk PRIMARY KEY (empno));

    INSERT INTO emp2(ename,job,hiredate,sal,comm,deptno) VALUES('king','president','1981-11-17',5000.5,0.0,10);
    INSERT INTO emp2(ename,job,hiredate,sal,comm,deptno) VALUES('blake','manager','1981-05-01',2850,0.0,30);
    INSERT INTO emp2(ename,job,hiredate,sal,comm,deptno) VALUES('clark','manager','1981-01-09',2450,0.0,10);
    INSERT INTO emp2(ename,job,hiredate,sal,comm,deptno) VALUES('jones','manager','1981-04-02',2975,0.0,20);
    INSERT INTO emp2(ename,job,hiredate,sal,comm,deptno) VALUES('martin','salesman','1981-09-28',1250,1400,30);
    INSERT INTO emp2(ename,job,hiredate,sal,comm,deptno) VALUES('allen','salesman','1981-02-2',1600,300,30);
    INSERT INTO emp2(ename,job,hiredate,sal,comm,deptno) VALUES('turner','salesman','1981-09-28',1500,0,30);
    INSERT INTO emp2(ename,job,hiredate,sal,comm,deptno) VALUES('james','clerk','1981-12-03',950,0.0,30);
    INSERT INTO emp2(ename,job,hiredate,sal,comm,deptno) VALUES('ward','salesman','1981-02-22',1250,500,30);
    INSERT INTO emp2(ename,job,hiredate,sal,comm,deptno) VALUES('ford','analyst','1981-12-03',3000,0.0,20);
    INSERT INTO emp2(ename,job,hiredate,sal,comm,deptno) VALUES('smith','clerk','1980-12-17',800,0.0,20);
    INSERT INTO emp2(ename,job,hiredate,sal,comm,deptno) VALUES('scott','analyst','1981-12-09',3000,0.0,20);
    INSERT INTO emp2(ename,job,hiredate,sal,comm,deptno) VALUES('adams','clerk','1983-01-12',1100,0.0,20);
    INSERT INTO emp2(ename,job,hiredate,sal,comm,deptno) VALUES('miller','clerk','1982-01-23',1300,0.0,10);
    ```
