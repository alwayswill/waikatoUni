
use sl255;
DROP TABLE IF EXISTS `comp589_books`;
create table `comp589_books` (
 `book_id` int(8) unsigned NOT NULL auto_increment, 
 `title` varchar(255) NOT NULL default '',
 `author_id` int(5) unsigned NULL,
 `category` varchar(100) default NULL,  
 `price` decimal(8,2) unsigned NOT NULL default '0.0',
  primary KEY (`book_id`) 
);

DROP TABLE IF EXISTS `comp589_authors`;
create table `comp589_authors`(
  `author_id` int(5) unsigned NOT NULL auto_increment,  
  `first_name` varchar(100) NOT NULL default '',
  `last_name` varchar(100) NOT NULL default '',
  `country` char(2) default NULL,
  `website` varchar(100) default NULL,
   primary KEY (`author_id`)
);

insert into `comp589_authors` (`first_name`,`last_name`,`country`,`website`) VALUES ('Markus','Zusak','au','http://www.randomhouse.com/features/markuszusak'); 
insert into `comp589_authors` (`first_name`,`last_name`,`country`,`website`) VALUES ('Brandon','Sanderson','us','http://brandonsanderson.com');
insert into `comp589_authors` (`first_name`,`last_name`,`country`) VALUES ('Robert','Gates','us');
insert into `comp589_authors` (`first_name`,`last_name`,`country`,`website`) VALUES ('Catherine','Chidgey','nz','http://www.bookcouncil.org.nz/writers/chidgey.html');
 

insert into `comp589_books` (`title`,`author_id`,`category`,`price`) VALUES ('The Book Thief','1','Children\'s Book','7.50');  
insert into `comp589_books` (`title`,`author_id`,`category`,`price`) VALUES ('I Am the Messenger','1','Children\'s Book','6.20');
insert into `comp589_books` (`title`,`author_id`,`category`,`price`) VALUES ('Words of Radiance','2','Fiction','17.60');
insert into `comp589_books` (`title`,`author_id`,`category`,`price`) VALUES ('A Memory of Light','2','Fiction','6.29');
insert into `comp589_books` (`title`,`author_id`,`category`,`price`) VALUES ('Steelheart','2','Fiction','11.07');
insert into `comp589_books` (`title`,`author_id`,`category`,`price`) VALUES ('Duty: Memoirs of a Secretary at War','3','History','20.99');
insert into `comp589_books` (`title`,`author_id`,`category`,`price`) VALUES ('From the Shadows','3','History','13.70');
insert into `comp589_books` (`title`,`author_id`,`category`,`price`) VALUES ('In a Fishbone Church','4','Literary','9.17');
insert into `comp589_books` (`title`,`author_id`,`category`,`price`) VALUES ('The Transformation','4','World Literary','9.99');






