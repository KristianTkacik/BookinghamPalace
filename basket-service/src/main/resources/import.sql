INSERT INTO basket(id, customerId) VALUES (nextval('hibernate_sequence'), 1);
INSERT INTO basket(id, customerId) VALUES (nextval('hibernate_sequence'), 2);
INSERT INTO basket(id, customerId) VALUES (nextval('hibernate_sequence'), 3);
INSERT INTO basket(id, customerId) VALUES (nextval('hibernate_sequence'), 4);

INSERT INTO basketitem(id, basketId, bookId, bookTitle, quantity, unitPrice)
VALUES (nextval('hibernate_sequence'), 1, 2, 'book-a', 1, 19.99);