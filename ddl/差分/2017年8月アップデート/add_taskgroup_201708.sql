CREATE TABLE TASK_GROUP_TBL
(
	-- タスクグループのID
	TASK_GROUP_ID int NOT NULL AUTO_INCREMENT COMMENT 'タスクグループのID',
	-- タスクグループ名
	TASK_GROUP_NAME varchar(300) NOT NULL COMMENT 'タスクグループ名',
	ENTRY_DATE datetime NOT NULL,
	UPDATE_DATE datetime NOT NULL,
	PRIMARY KEY (TASK_GROUP_ID)
);

ALTER TABLE TASK_TBL ADD TASK_GROUP_ID int;

CREATE INDEX IDX_TASK_GROUP_ID ON TASK_TBL (TASK_GROUP_ID ASC);

--ココより下は、データに合わせてUPDATEしたもの
INSERT INTO TASK_GROUP_TBL(TASK_GROUP_NAME,ENTRY_DATE,UPDATE_DATE)VALUES("グループ無し",NOW(),NOW());
INSERT INTO TASK_GROUP_TBL(TASK_GROUP_NAME,ENTRY_DATE,UPDATE_DATE)VALUES("JSS-JK01",NOW(),NOW());
INSERT INTO TASK_GROUP_TBL(TASK_GROUP_NAME,ENTRY_DATE,UPDATE_DATE)VALUES("JSS-JK02",NOW(),NOW());
INSERT INTO TASK_GROUP_TBL(TASK_GROUP_NAME,ENTRY_DATE,UPDATE_DATE)VALUES("JSS-JK03",NOW(),NOW());
INSERT INTO TASK_GROUP_TBL(TASK_GROUP_NAME,ENTRY_DATE,UPDATE_DATE)VALUES("JSS-JK04",NOW(),NOW());
INSERT INTO TASK_GROUP_TBL(TASK_GROUP_NAME,ENTRY_DATE,UPDATE_DATE)VALUES("JSS-JK05",NOW(),NOW());
INSERT INTO TASK_GROUP_TBL(TASK_GROUP_NAME,ENTRY_DATE,UPDATE_DATE)VALUES("JSS-JK06",NOW(),NOW());
INSERT INTO TASK_GROUP_TBL(TASK_GROUP_NAME,ENTRY_DATE,UPDATE_DATE)VALUES("JSS-JK07",NOW(),NOW());
INSERT INTO TASK_GROUP_TBL(TASK_GROUP_NAME,ENTRY_DATE,UPDATE_DATE)VALUES("JSS-JK08",NOW(),NOW());
INSERT INTO TASK_GROUP_TBL(TASK_GROUP_NAME,ENTRY_DATE,UPDATE_DATE)VALUES("JSS-JK09",NOW(),NOW());
INSERT INTO TASK_GROUP_TBL(TASK_GROUP_NAME,ENTRY_DATE,UPDATE_DATE)VALUES("JSS-JK10",NOW(),NOW());
INSERT INTO TASK_GROUP_TBL(TASK_GROUP_NAME,ENTRY_DATE,UPDATE_DATE)VALUES("JSS-JK11",NOW(),NOW());
INSERT INTO TASK_GROUP_TBL(TASK_GROUP_NAME,ENTRY_DATE,UPDATE_DATE)VALUES("JSS-ST01",NOW(),NOW());
INSERT INTO TASK_GROUP_TBL(TASK_GROUP_NAME,ENTRY_DATE,UPDATE_DATE)VALUES("JSS-ST02",NOW(),NOW());
INSERT INTO TASK_GROUP_TBL(TASK_GROUP_NAME,ENTRY_DATE,UPDATE_DATE)VALUES("JSS-ST03",NOW(),NOW());
INSERT INTO TASK_GROUP_TBL(TASK_GROUP_NAME,ENTRY_DATE,UPDATE_DATE)VALUES("JSS-ST04",NOW(),NOW());
INSERT INTO TASK_GROUP_TBL(TASK_GROUP_NAME,ENTRY_DATE,UPDATE_DATE)VALUES("JSS-ST05",NOW(),NOW());
INSERT INTO TASK_GROUP_TBL(TASK_GROUP_NAME,ENTRY_DATE,UPDATE_DATE)VALUES("JSS-ST06",NOW(),NOW());
INSERT INTO TASK_GROUP_TBL(TASK_GROUP_NAME,ENTRY_DATE,UPDATE_DATE)VALUES("JSS-ST07",NOW(),NOW());
INSERT INTO TASK_GROUP_TBL(TASK_GROUP_NAME,ENTRY_DATE,UPDATE_DATE)VALUES("JSS-ST08",NOW(),NOW());

UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK01") WHERE MID(NAME,2,8) = "JSS-JK01";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK02") WHERE MID(NAME,2,8) = "JSS-JK02";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK03") WHERE MID(NAME,2,8) = "JSS-JK03";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK04") WHERE MID(NAME,2,8) = "JSS-JK04";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK05") WHERE MID(NAME,2,8) = "JSS-JK05";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK06") WHERE MID(NAME,2,8) = "JSS-JK06";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK07") WHERE MID(NAME,2,8) = "JSS-JK07";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK08") WHERE MID(NAME,2,8) = "JSS-JK08";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK09") WHERE MID(NAME,2,8) = "JSS-JK09";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK10") WHERE MID(NAME,2,8) = "JSS-JK10";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK11") WHERE MID(NAME,2,8) = "JSS-JK11";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-ST01") WHERE MID(NAME,2,8) = "JSS-ST01";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-ST02") WHERE MID(NAME,2,8) = "JSS-ST02";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-ST03") WHERE MID(NAME,2,8) = "JSS-ST03";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-ST04") WHERE MID(NAME,2,8) = "JSS-ST04";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-ST05") WHERE MID(NAME,2,8) = "JSS-ST05";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-ST06") WHERE MID(NAME,2,8) = "JSS-ST06";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-ST07") WHERE MID(NAME,2,8) = "JSS-ST07";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-ST08") WHERE MID(NAME,2,8) = "JSS-ST08";

UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK01") WHERE MID(NAME,3,8) = "JSS-JK01";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK02") WHERE MID(NAME,3,8) = "JSS-JK02";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK03") WHERE MID(NAME,3,8) = "JSS-JK03";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK04") WHERE MID(NAME,3,8) = "JSS-JK04";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK05") WHERE MID(NAME,3,8) = "JSS-JK05";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK06") WHERE MID(NAME,3,8) = "JSS-JK06";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK07") WHERE MID(NAME,3,8) = "JSS-JK07";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK08") WHERE MID(NAME,3,8) = "JSS-JK08";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK09") WHERE MID(NAME,3,8) = "JSS-JK09";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK10") WHERE MID(NAME,3,8) = "JSS-JK10";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-JK11") WHERE MID(NAME,3,8) = "JSS-JK11";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-ST01") WHERE MID(NAME,3,8) = "JSS-ST01";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-ST02") WHERE MID(NAME,3,8) = "JSS-ST02";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-ST03") WHERE MID(NAME,3,8) = "JSS-ST03";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-ST04") WHERE MID(NAME,3,8) = "JSS-ST04";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-ST05") WHERE MID(NAME,3,8) = "JSS-ST05";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-ST06") WHERE MID(NAME,3,8) = "JSS-ST06";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-ST07") WHERE MID(NAME,3,8) = "JSS-ST07";
UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "JSS-ST08") WHERE MID(NAME,3,8) = "JSS-ST08";

UPDATE TASK_TBL SET TASK_GROUP_ID = (select TASK_GROUP_ID from TASK_GROUP_TBL where TASK_GROUP_NAME = "グループ無し") WHERE TASK_GROUP_ID is null;

