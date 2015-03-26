
DROP TABLE IF EXISTS `comp589_authors`;
CREATE TABLE `comp589_authors` (
  `author_id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) NOT NULL DEFAULT '',
  `last_name` varchar(100) NOT NULL DEFAULT '',
  `country` char(2) DEFAULT NULL,
  `website` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`author_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comp589_authors`
--

LOCK TABLES `comp589_authors` WRITE;
/*!40000 ALTER TABLE `comp589_authors` DISABLE KEYS */;
INSERT INTO `comp589_authors` VALUES (1,'Markus','Zusak','au','http://www.randomhouse.com/features/markuszusak'),(2,'Brandon','Sanderson','us','http://brandonsanderson.com'),(3,'Robert','Gates','us',NULL),(4,'Catherine','Chidgey','nz','http://www.bookcouncil.org.nz/writers/chidgey.html');
/*!40000 ALTER TABLE `comp589_authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comp589_books`
--

DROP TABLE IF EXISTS `comp589_books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comp589_books` (
  `book_id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL DEFAULT '',
  `author_id` int(5) unsigned DEFAULT NULL,
  `category` varchar(100) DEFAULT NULL,
  `price` decimal(8,2) unsigned NOT NULL DEFAULT '0.00',
  `publisher_id` int(8) DEFAULT NULL,
  `publish_date` date DEFAULT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comp589_books`
--

LOCK TABLES `comp589_books` WRITE;
/*!40000 ALTER TABLE `comp589_books` DISABLE KEYS */;
INSERT INTO `comp589_books` VALUES (1,'The Book Thief',1,'Children\'s Book',7.50,1,'2007-09-11'),(2,'I Am the Messenger',1,'Children\'s Book',6.20,1,'2006-05-09'),(3,'Words of Radiance',2,'Fiction',17.60,2,'2014-03-04'),(4,'A Memory of Light',2,'Fiction',6.29,2,'2013-12-31'),(5,'Steelheart',2,'Fiction',11.07,2,'2013-09-24'),(6,'Duty: Memoirs of a Secretary at War',3,'History',20.99,1,'2014-01-14'),(7,'From the Shadows',3,'History',13.70,1,'2007-01-09'),(8,'In a Fishbone Church',4,'Literary',9.17,3,'1918-05-01'),(9,'The Transformation',4,'World Literary',9.99,3,'2006-06-13');
/*!40000 ALTER TABLE `comp589_books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comp589_publishers`
--

DROP TABLE IF EXISTS `comp589_publishers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comp589_publishers` (
  `publisher_id` int(8) NOT NULL AUTO_INCREMENT,
  `publisher_name` char(255) DEFAULT NULL,
  `publisher_country` char(255) DEFAULT NULL,
  `publisher_website` text,
  PRIMARY KEY (`publisher_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comp589_publishers`
--

LOCK TABLES `comp589_publishers` WRITE;
/*!40000 ALTER TABLE `comp589_publishers` DISABLE KEYS */;
INSERT INTO `comp589_publishers` VALUES (1,'Alfred A.Knopf, Inc.','us','knopf.knopfdoubleday.com'),(2,'Victor Gollancz Ltd','uk','www.gollancz.co.uk'),(3,'Henry Holt and Company, LLC','us','us.macmillan.com/henryholt.aspx');
