INSERT INTO author(id, dateofbirth, name) VALUES (nextval('hibernate_sequence'), '1991-04-01', 'well known author');

INSERT INTO book(id, title, genre, price, releaseDate, author_id) VALUES (nextval('hibernate_sequence'), 'book-a', 0, 19.99, '2014-02-05', 1);
INSERT INTO book(id, title, genre, price, releaseDate) VALUES (nextval('hibernate_sequence'), 'book-b', 1, 9.99, '2018-03-04');