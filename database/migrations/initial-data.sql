
/* Table: Users */
INSERT INTO users(email,password,"role") VALUES
  ('valera.dt@gmail.com', '123456', 'admin'),
  ('i.dont.know.your.email@gmail.com', '123456', 'admin');


/** */
INSERT INTO categories (id, title) VALUES
(1, 'Дзюдо'),
(2, 'Самбо'),
(3, 'Борьба'),
(4, 'Карате'),
(5, 'Бокс'),
(6, 'Кик-боксинг'),
(7, 'Плавание'),
(8, 'Бои без правил'),
(9, 'Россия'),
(10, 'Разное');

SELECT setval('categories_id_seq', (SELECT max(id) + 1 from categories), false);