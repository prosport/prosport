CREATE DATABASE prosport;

-- Users
CREATE SEQUENCE users_id_seq INCREMENT BY 1;
CREATE TABLE users
(
	id INTEGER DEFAULT nextval('users_id_seq'::regclass) NOT NULL,
	email VARCHAR(255) NOT NULL,
	password VARCHAR(32) NOT NULL,
	"role" VARCHAR(16) NOT NULL
);
ALTER TABLE users ADD CONSTRAINT pkusers PRIMARY KEY (id);
CREATE UNIQUE INDEX uniq_email ON users USING btree (email);

/* Table: Users */
INSERT INTO users(email,password,"role") VALUES
('valera.dt@gmail.com', '123456', 'admin'),
('i.dont.know.your.email@gmail.com', '123456', 'admin');

/* Table: Products */
CREATE SEQUENCE products_id_seq INCREMENT BY 1;
CREATE TABLE products
(
	id INTEGER DEFAULT nextval('products_id_seq'::regclass) NOT NULL,
	title VARCHAR(255) NOT NULL,
	articul VARCHAR(255) NULL,
	description TEXT NULL,
	short_description TEXT NULL
);
ALTER TABLE products ADD CONSTRAINT pkproducts 	PRIMARY KEY (id);

/* Table: Images */
-- Users
CREATE SEQUENCE users_id_seq INCREMENT BY 1;
CREATE TABLE users
(
	id INTEGER DEFAULT nextval('users_id_seq'::regclass) NOT NULL,
	email VARCHAR(255) NOT NULL,
	password VARCHAR(32) NOT NULL,
	"role" VARCHAR(16) NOT NULL
);
ALTER TABLE users ADD CONSTRAINT pkusers PRIMARY KEY (id);
CREATE UNIQUE INDEX uniq_email ON users USING btree (email);

/* Table: Users */
INSERT INTO users(email,password,"role") VALUES
('valera.dt@gmail.com', '123456', 'admin'),
('i.dont.know.your.email@gmail.com', '123456', 'admin');

/* Table: Products */
CREATE SEQUENCE products_id_seq INCREMENT BY 1;
CREATE TABLE products
(
	id INTEGER DEFAULT nextval('products_id_seq'::regclass) NOT NULL,
	title VARCHAR(255) NOT NULL,
	articul VARCHAR(255) NULL,
	description TEXT NULL,
	short_description TEXT NULL
);
ALTER TABLE products ADD CONSTRAINT pkproducts 	PRIMARY KEY (id);



/* Table: Images */
CREATE TABLE images
(
	id INTEGER DEFAULT nextval('images_id_seq'::regclass) NOT NULL,
	title VARCHAR(255) NULL,
	filename VARCHAR(255) NOT NULL,
	product_id INTEGER NOT NULL
);

CREATE SEQUENCE images_id_seq INCREMENT BY 1;
CREATE TABLE images
(
	id INTEGER DEFAULT nextval('images_id_seq'::regclass) NOT NULL,
	title VARCHAR(255) NULL,
	filename VARCHAR(255) NOT NULL,
	product_id INTEGER NOT NULL
);

ALTER TABLE images ADD CONSTRAINT pkimages PRIMARY KEY (id);
CREATE TABLE products_images (product_id INTEGER NOT NULL, image_id INTEGER NOT NULL);


ALTER TABLE images ADD CONSTRAINT fk_images_images FOREIGN KEY (product_id) REFERENCES products (id) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE products_images ADD CONSTRAINT fk_products_images_images FOREIGN KEY (image_id) REFERENCES images (id) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE products_images ADD CONSTRAINT fk_products_images_products FOREIGN KEY (product_id) REFERENCES products (id) ON UPDATE CASCADE ON DELETE CASCADE;


/* Add date information for all tables sort by adding */
ALTER TABLE products ADD COLUMN created_at TIMESTAMP;
ALTER TABLE products ALTER COLUMN created_at SET NOT NULL;
ALTER TABLE products ADD COLUMN modified_at TIMESTAMP;
ALTER TABLE products ALTER COLUMN modified_at SET NOT NULL;


