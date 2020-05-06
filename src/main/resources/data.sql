INSERT INTO users (id, name, email, password)
VALUES (1, 'User', 'user@yandex.ru', '$2a$10$AQ/mfRr3KuZx1vX4bruzquumXgLaZul4OKzoSZOr4Zd8cbiq4idRK'),
       (2, 'Admin', 'admin@gmail.com', '$2a$10$lcXKWK.Wq9UpRWDhea3V2uVr2loBbSUCZogp/MIQFrDbKZYWNuK.C'),
       (3, 'Second User', 'seconduser@yandex.ru', '$2a$10$AQ/mfRr3KuZx1vX4bruzquumXgLaZul4OKzoSZOr4Zd8cbiq4idRK');

insert into user_role(role, user_id)
values ('ROLE_USER', 1),
       ('ROLE_ADMIN', 2),
       ('ROLE_USER', 3);

INSERT INTO restaurant (id, name, telephone, address)
VALUES (1, 'La Maisie', '(416) 901-1050', '211 King St W, Toronto'),
       (2, 'Courage Couture', '(416) 203-1121', '2281 Lake Shore Blvd W, Etobicoke, ON. M8V1C5'),
       (3, 'Royal Palace', '+14169162099', '150 Eglinton Avenue East, Toronto M5H 3H1');

INSERT INTO dish (id, name, price, date, restaurant_id)
VALUES (1, 'Falafel plate', 11.99, CURRENT_DATE - 3, 1),
       (2, 'Green Salad', 4.99, CURRENT_DATE - 3, 1),

       (3, 'Chicken Shawarma Plate', 12.99, CURRENT_DATE - 3, 2),
       (4, 'Coleslaw Salad', 6.99, CURRENT_DATE - 3, 2),

       (5, 'Tartar de Carne', 22, CURRENT_DATE - 3, 3),
       (6, 'Vieira Cruda', 23, CURRENT_DATE - 3, 3),
       (7, 'Terrapura, Pinot Noir, Itata Valley, Chile  2018', 14, CURRENT_DATE - 3, 3),


       (8, 'Enchiladas', 16, CURRENT_DATE - 2, 1),
       (9, 'Green Salad', 4.99, CURRENT_DATE - 2, 1),
       (10, 'tea', 2.99, CURRENT_DATE - 2, 1),

       (11, 'Prime Rib with Cheddar', 49.99, CURRENT_DATE - 2, 2),
       (12, 'Turkish coffee', 4.99, CURRENT_DATE - 2, 2),

       (13, 'Rib-eye', 33.99, CURRENT_DATE - 2, 3),
       (14, 'Apple Tart', 10, CURRENT_DATE - 2, 3),


       (15, 'Stuffed Grape Leaves', 7.99, CURRENT_DATE - 1, 1),
       (16, 'Flank Steak', 22.99, CURRENT_DATE - 1, 1),

       (17, 'Parmesan Chicken', 16.49, CURRENT_DATE - 1, 2),
       (18, 'Caesar salad', 9.99, CURRENT_DATE - 1, 2),

       (19, 'Chicken Shawarma on the Sticks', 11.99, CURRENT_DATE - 1, 3),
       (20, 'Greek salad', 4.99, CURRENT_DATE - 1, 3),


       (21, 'Green Salad', 4.99, CURRENT_DATE, 1),
       (22, 'Ginger Chicken', 22.99, CURRENT_DATE, 1),

       (23, 'Rib-eye', 49.99, CURRENT_DATE, 2),
       (24, 'Coleslaw Salad', 5.99, CURRENT_DATE, 2);

INSERT INTO vote (id, date, time, user_id, user_email_history, restaurant_id, restaurant_name_history)
VALUES (1, CURRENT_DATE - 3, '10:00:00', 1, 'user@yandex.ru', 1, 'La Maisie'),
       (2, CURRENT_DATE - 3, '10:00:00', 3, 'seconduser@yandex.ru', 2, 'Courage Couture'),

       (3, CURRENT_DATE - 2, '10:00:00', 1, 'user@yandex.ru', 3, 'Royal Palace'),
       (4, CURRENT_DATE - 2, '10:00:00', 3, 'seconduser@yandex.ru', 1, 'La Maisie'),

       (5, CURRENT_DATE - 1, '10:00:00', 1, 'user@yandex.ru', 1, 'La Maisie'),
       (6, CURRENT_DATE - 1, '10:00:00', 3, 'seconduser@yandex.ru', 2, 'Courage Couture');
