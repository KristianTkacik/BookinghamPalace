INSERT INTO author(id, dateofbirth, name) VALUES (nextval('hibernate_sequence'), '1991-04-01', 'author-a');
INSERT INTO author(id, dateofbirth, name) VALUES (nextval('hibernate_sequence'), '2000-05-07', 'author-b');

INSERT INTO book(id, title, genre, price, releaseDate, author_id) VALUES (nextval('hibernate_sequence'), 'book-a', 0, 19.99, '2014-02-05', 1);
INSERT INTO book(id, title, genre, price, releaseDate) VALUES (nextval('hibernate_sequence'), 'book-b', 1, 9.99, '2018-03-04');
INSERT INTO book(id, title, genre, price, releaseDate) VALUES (nextval('hibernate_sequence'), 'book-c', 1, 9.99, '2018-03-04');

INSERT INTO author(id, dateofbirth, name) VALUES (nextval('hibernate_sequence'), '2000-05-07', 'author-c');
INSERT INTO book(id, title, genre, price, releaseDate) VALUES (nextval('hibernate_sequence'), 'book-c', 1, 9.99, '2018-03-04');

INSERT INTO author(id, dateofbirth, name) VALUES (nextval('hibernate_sequence'), '2000-05-07', 'author-f');
INSERT INTO book(id, title, genre, price, releaseDate, author_id) VALUES (nextval('hibernate_sequence'), 'book-f', 1, 9.99, '2018-03-04', 8);



