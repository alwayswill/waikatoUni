PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE "comp589_authors" (
  "author_id" INTEGER PRIMARY KEY   AUTOINCREMENT ,
  "first_name" varchar(100) NOT NULL DEFAULT '',
  "last_name" varchar(100) NOT NULL DEFAULT '',
  "country" char(2) DEFAULT NULL,
  "website" varchar(100) DEFAULT NULL
);
INSERT INTO "comp589_authors" VALUES(1,'Markus','Zusak','au','http://www.randomhouse.com/features/markuszusak');
INSERT INTO "comp589_authors" VALUES(2,'Brandon','Sanderson','us','http://brandonsanderson.com');
INSERT INTO "comp589_authors" VALUES(3,'Robert','Gates','us',NULL);
INSERT INTO "comp589_authors" VALUES(4,'Catherine','Chidgey','nz','http://www.bookcouncil.org.nz/writers/chidgey.html');
INSERT INTO "comp589_authors" VALUES(5,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(6,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(7,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(8,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(9,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(10,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(11,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(12,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(13,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(14,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(15,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(16,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(17,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(18,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(19,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(20,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(21,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(22,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(23,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(24,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(25,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(26,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(27,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(28,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(29,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(30,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(31,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(32,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(33,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(34,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(35,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(36,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(37,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(38,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(39,'William','Lee','cn','http://www.baidu.com');
INSERT INTO "comp589_authors" VALUES(99,'William','Lee','cn','http://www.baidu.com');
CREATE TABLE "comp589_books" (
  "book_id" INTEGER PRIMARY KEY   AUTOINCREMENT,
  "title" varchar(255) NOT NULL DEFAULT '',
  "author_id" int(5)  DEFAULT NULL,
  "category" varchar(100) DEFAULT NULL,
  "price" decimal(8,2)  NOT NULL DEFAULT '0.00',
  "publisher_id" int(8) DEFAULT NULL,
  "publish_date" date DEFAULT NULL
);
INSERT INTO "comp589_books" VALUES(1,'The Book Thief',1,'Children''s Book',7.13,1,'2007-09-11');
INSERT INTO "comp589_books" VALUES(2,'I Am the Messenger',1,'Children''s Book',5.89,1,'2006-05-09');
INSERT INTO "comp589_books" VALUES(3,'Words of Radiance',2,'Fiction',16.72,2,'2014-03-04');
INSERT INTO "comp589_books" VALUES(4,'A Memory of Light',2,'Fiction',5.98,2,'2013-12-31');
INSERT INTO "comp589_books" VALUES(5,'Steelheart',2,'Fiction',10.52,2,'2013-09-24');
INSERT INTO "comp589_books" VALUES(6,'Duty: Memoirs of a Secretary at War',3,'History',19.94,1,'2014-01-14');
INSERT INTO "comp589_books" VALUES(7,'From the Shadows',3,'History',13.02,1,'2007-01-09');
INSERT INTO "comp589_books" VALUES(8,'In a Fishbone Church',4,'Literary',8.71,3,'1918-05-01');
INSERT INTO "comp589_books" VALUES(9,'The Transformation',4,'World Literary',9.49,3,'2006-06-13');
INSERT INTO "comp589_books" VALUES(10,'William''s book',21,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(11,'William''s book',22,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(12,'William''s book',23,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(13,'William''s book',24,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(14,'William''s book',24,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(15,'William''s book',25,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(16,'William''s book',25,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(17,'William''s book',26,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(18,'William''s book',26,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(19,'William''s book',27,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(20,'William''s book',27,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(21,'William''s book',28,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(22,'William''s book',29,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(23,'William''s book',30,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(24,'William''s book',31,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(25,'William''s book',32,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(26,'William''s book',32,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(27,'William''s book',33,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(28,'William''s book',34,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(29,'William''s book',34,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(30,'William''s book',35,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(31,'William''s book',36,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(32,'William''s book',36,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(33,'William''s book',37,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(34,'William''s book',37,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(35,'William''s book',38,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(36,'William''s book',39,'Computers',101,3,'2015-03-27');
INSERT INTO "comp589_books" VALUES(37,'William''s book',39,'Computers',101,3,'2015-03-27');
CREATE TABLE "comp589_publishers" (
  "publisher_id" INTEGER PRIMARY KEY   AUTOINCREMENT ,
  "publisher_name" char(255) DEFAULT NULL,
  "publisher_country" char(255) DEFAULT NULL,
  "publisher_website" text
);
INSERT INTO "comp589_publishers" VALUES(1,'Alfred A.Knopf, Inc.','us','knopf.knopfdoubleday.com');
INSERT INTO "comp589_publishers" VALUES(2,'Victor Gollancz Ltd','uk','www.gollancz.co.uk');
INSERT INTO "comp589_publishers" VALUES(3,'Henry Holt and Company, LLC','us','us.macmillan.com/henryholt.aspx');
COMMIT;
