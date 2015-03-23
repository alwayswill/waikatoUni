CREATE TABLE comp589_publishers(publisher_id INT(8) NOT NULL AUTO_INCREMENT, publisher_name CHAR(255), publisher_country CHAR(255), publisher_website TEXT, PRIMARY KEY(publisher_id));

INSERT INTO comp589_publishers VALUES("", "Alfred A.Knopf, Inc.", "us", "knopf.knopfdoubleday.com");
INSERT INTO comp589_publishers VALUES("", "Victor Gollancz Ltd", "uk", "www.gollancz.co.uk");
INSERT INTO comp589_publishers VALUES("", "Henry Holt and Company, LLC", "us", "us.macmillan.com/henryholt.aspx");
alter table comp589_books add publisher_id int(8);
update comp589_books set publisher_id="1" where author_id="1" or author_id="3";
update comp589_books set publisher_id="2" where author_id="2";
update comp589_books set publisher_id="3" where author_id="4";
alter table comp589_books add publish_date date;
update comp589_books set publish_date="2007-09-11" where book_id="1";
update comp589_books set publish_date="2006-05-9" where book_id="2";
update comp589_books set publish_date="2014-03-04" where book_id="3";
update comp589_books set publish_date="2013-12-31" where book_id="4";
update comp589_books set publish_date="2013-09-24" where book_id="5";
update comp589_books set publish_date="2014-01-14" where book_id="6";
update comp589_books set publish_date="2007-01-09" where book_id="7";
update comp589_books set publish_date="1918-05-01" where book_id="8";
update comp589_books set publish_date="2006-06-13" where book_id="9";
select *, DATE_FORMAT(publish_date, '%b %d, %Y') from comp589_books;
/*7*/
update  comp589_books set price =price*0.95;
/*8*/
select b.title, concat(a.first_name, " ", a.last_name) as author_name from comp589_books b left join comp589_authors a on b.author_id = a.author_id where b.book_id=2;
/*9*/
select b.title, concat(a.first_name, " ", a.last_name) as author_name, p.publisher_name from comp589_books b left join comp589_authors a on b.author_id = a.author_id left join comp589_publishers p on b.publisher_id=p.publisher_id;
/*10*/
select count(*) from comp589_books group by author_id ;
/*11*/
select title from comp589_books where author_id =3;
/*12*/
select title from comp589_books where author_id =3 or author_id=1;
/*13*/
select title from comp589_books b left join comp589_authors a on b.author_id=a.author_id where a.country != "nz";
/*14*/
select concat(a.first_name, " ", a.last_name) as author_name from comp589_books b left join comp589_authors a on b.author_id=a.author_id group by b.author_id having count(b.book_id) >2;
/*15*/
select title from comp589_books order by publish_date DESC;
/*16*/
select title from comp589_books where publish_date >= '2006-01-01' and publish_date <= '2007-01-01';
/*17*/
select title from comp589_books where title like "%Memo%";
/*18*/
select title from comp589_books where length(title) > 2;
/*19*/
select website from comp589_authors UNION ALL select publisher_website from comp589_publishers;
/*20*/
select b.title, concat(a.first_name, " ", a.last_name) as author_name, p.publisher_country from comp589_books b left join comp589_authors a on b.author_id = a.author_id left join comp589_publishers p on b.publisher_id=p.publisher_id where a.country=p.publisher_country;
