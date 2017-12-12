BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `TESTS` (
	`_ID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`TEST_NAME`	INTEGER NOT NULL
);
CREATE TABLE IF NOT EXISTS `QUESTIONS` (
	`_ID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`QUESTION`	TEXT NOT NULL,
	`TEST_ID`	INTEGER NOT NULL,
	FOREIGN KEY(`TEST_ID`) REFERENCES `TESTS`(`_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
CREATE TABLE IF NOT EXISTS `ANSWERS` (
	`_ID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`ANSWER`	TEXT NOT NULL,
	`IS_RIGHT`	INTEGER NOT NULL,
	`QUESTION_ID`	INTEGER NOT NULL,
	FOREIGN KEY(`QUESTION_ID`) REFERENCES `QUESTIONS`(`_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
CREATE TABLE IF NOT EXISTS `android_metadata` (
	`locale` TEXT DEFAULT "en_US"
);

DROP TRIGGER IF EXISTS QUESTION_DELETE;
CREATE TRIGGER QUESTION_DELETE BEFORE DELETE ON QUESTIONS 
FOR EACH ROW
BEGIN
	DELETE FROM ANSWERS WHERE QUESTION_ID = OLD._ID;
END;

DROP TRIGGER IF EXISTS TEST_DELETE;
CREATE TRIGGER TEST_DELETE BEFORE DELETE ON TESTS
FOR EACH ROW
BEGIN
	DELETE FROM QUESTIONS WHERE TEST_ID = OLD._ID;
END;

COMMIT;


insert into TESTS(test_name) values ("test1");

insert into QUESTIONS(question, test_id) values ("question1.1", 1);

insert into ANSWERS(answer, is_right, question_id) values ("answer1", 1, 1); 

insert into android_metadata values ("en_US");