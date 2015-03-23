use sl255;
show tables;
describe comp589_books;
select * from comp589_books;
select title, price from comp589_books where book_id=1;
select cb.title, ca.first_name , ca.last_name from comp589_books as cb, comp589_authors as ca where ca.country="us";
select cb.title, ca.first_name , ca.last_name from comp589_books as cb, comp589_authors as ca where cb.author_id = ca.author_id and ca.country="us";
