INSERT INTO eshopOrder(id, customerId, customerName, street, city, country, date)
VALUES ( nextval('hibernate_sequence'),
        5,
        'Adam',
        'Mostova 1',
        'Moskva',
        'Rusko',
        '2017-07-23'
        );

INSERT INTO eshopOrder(id, customerId, customerName, street, city, country, date)
VALUES ( nextval('hibernate_sequence'),
         5,
         'Anna',
         'Mostova 2',
         'Praha',
         'Cesko',
         '2027-07-23'
       );

INSERT INTO eshopOrder(id, customerId, customerName, street, city, country, date)
VALUES ( nextval('hibernate_sequence'),
         5,
         'Jozka',
         'Peklo 2',
         'Brno',
         'Cesko',
         '2027-07-2'
       );

INSERT INTO orderItem(id, orderId, bookId, bookTitle, unitprice, quantity)
VALUES ( nextval('hibernate_sequence'),
         1,
         1,
         'Book1',
         20,
         15
       );

INSERT INTO orderItem(id, orderId, bookId, bookTitle, unitprice, quantity)
VALUES ( nextval('hibernate_sequence'),
         2,
         2,
         'Book2',
         2,
         1
       );




