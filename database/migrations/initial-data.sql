
/* Table: Users */
    INSERT INTO users(id,email,password,"role") VALUES
      (nextval('users_id_seq'),'valera.dt@gmail.com', '123456', 'admin'),
      (nextval('users_id_seq'), 'lstghost@gmail.com', '123456', 'admin');


/** Sample product categories
    WARNING: Don't remove ids because this hardcode need for linking its for other essences!
    */
INSERT INTO product_categories("id", "name") VALUES
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

SELECT setval('productcategories_id_seq', (SELECT max(id) + 1 from product_categories), false);

INSERT INTO products (id, category_id, semantic_url, name,articul,rating,views_count,author,description,short_description) VALUES
(1, 9, 'futbolka-kot', 'Футболка "Кот"', 'Р068', 4.0, 99, 'Юлия',  'Описание', 'Краткое описание');

INSERT INTO images (id, product_id, name, filename, color) VALUES
(nextval('images_id_seq'), 1, 'Футболка "Кот". Вариант 1', '11/1163s.jpg',   'Оранжевый'),
(nextval('images_id_seq'), 1, 'Футболка "Кот". Вариант 2', '11/1163s_1.jpg', 'Белый'),
(nextval('images_id_seq'), 1, 'Футболка "Кот". Вариант 3', '11/1163s_2.jpg', 'Голубой'),
(nextval('images_id_seq'), 1, 'Футболка "Кот". Вариант 4', '11/1163s_3.jpg', 'Желтый'),
(nextval('images_id_seq'), 1, 'Футболка "Кот". Вариант 5', '11/1163s_4.jpg', 'Красный'),
(nextval('images_id_seq'), 1, 'Футболка "Кот". Вариант 6', '11/1163s_5.jpg', 'Серый'),
(nextval('images_id_seq'), 1, 'Футболка "Кот". Вариант 7', '11/1163s_6.jpg', 'Синий'),
(nextval('images_id_seq'), 1, 'Футболка "Кот". Вариант 8', '11/1163s_7.jpg', 'Чёрный');

SELECT setval('images_seq', (SELECT max(id) + 1 FROM images), false);
SELECT setval('products_seq', (SELECT max(id) + 1 FROM products), false);
SELECT setval('product_categories_seq', (SELECT max(id) + 1 FROM product_categories), false);
SELECT setval('users_seq', (SELECT max(id) + 1 FROM users), false);
