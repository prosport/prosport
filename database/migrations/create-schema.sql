CREATE DATABASE prosport;


-- Users
CREATE SEQUENCE users_id_seq INCREMENT BY 1;
CREATE TABLE users
(
	id BIGINT DEFAULT nextval('users_id_seq'::regclass) NOT NULL,
	email VARCHAR(255) NOT NULL,
	password VARCHAR(32) NOT NULL,
	"role" VARCHAR(16) NOT NULL
);
ALTER TABLE users ADD CONSTRAINT pkusers PRIMARY KEY (id);
CREATE UNIQUE INDEX uniq_email ON users USING btree (email);

/* Table: Products */
CREATE SEQUENCE products_id_seq INCREMENT BY 1;
CREATE TABLE products
(
	id BIGINT DEFAULT nextval('products_id_seq'::regclass) NOT NULL,
	title VARCHAR(255) NOT NULL,
	articul VARCHAR(255) NULL,
	description TEXT NULL,
	short_description TEXT NULL
);
ALTER TABLE products ADD CONSTRAINT pkproducts 	PRIMARY KEY (id);

/* Table: Users */
CREATE TABLE users
(
  id BIGINT DEFAULT nextval('users_id_seq'::regclass) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(32) NOT NULL,
  "role" VARCHAR(16) NOT NULL,
  registred_at TIMESTAMP DEFAULT now() NULL
) WITHOUT OIDS;
ALTER TABLE users ADD CONSTRAINT pkusers PRIMARY KEY (id);
CREATE UNIQUE INDEX uniq_email ON users USING btree (email);

/* Table: Products */
CREATE SEQUENCE products_id_seq INCREMENT BY 1;
CREATE TABLE products
(
  id BIGINT DEFAULT nextval('products_id_seq'::regclass) NOT NULL,
  title VARCHAR(255) NOT NULL,
  articul VARCHAR(255) NULL,
  description TEXT NULL,
  short_description TEXT NULL,
  created_at TIMESTAMP DEFAULT now() NOT NULL,
  modified_at TIMESTAMP DEFAULT now() NOT NULL,
  rating FLOAT DEFAULT 0 NOT NULL,
  count_views INTEGER DEFAULT 0 NOT NULL,
  author VARCHAR(255) NULL,
  hints TEXT NULL
) WITHOUT OIDS;
ALTER TABLE products ADD CONSTRAINT pkproducts PRIMARY KEY (id);

CREATE TABLE categories
(
  id BIGINT NOT NULL,
  title VARCHAR(255) NOT NULL,
  parent_id BIGINT NOT NULL
);
ALTER TABLE categories ADD CONSTRAINT pkcategories PRIMARY KEY (id);


/* Table: Images */
CREATE SEQUENCE images_id_seq INCREMENT BY 1;
CREATE TABLE images
(
  id BIGINT DEFAULT nextval('images_id_seq'::regclass) NOT NULL,
  title VARCHAR(255) NULL,
  filename VARCHAR(255) NOT NULL,
  product_id INTEGER NOT NULL,
  created_at TIMESTAMP DEFAULT now() NULL
) WITHOUT OIDS;
ALTER TABLE images ADD CONSTRAINT pkimages PRIMARY KEY (id);
CREATE TABLE products_images ( product_id BIGINT NOT NULL, image_id BIGINT NOT NULL ) WITHOUT OIDS;
CREATE UNIQUE INDEX mm_products_images ON products_images (product_id, image_id);

CREATE TABLE categories
(
  id BIGINT NOT NULL,
  title VARCHAR(255) NOT NULL,
  parent_id BIGINT NOT NULL
);
ALTER TABLE categories ADD CONSTRAINT pkcategories PRIMARY KEY (id);
CREATE TABLE products_categories (product_id BIGINT NOT NULL, category_id BIGINT NOT NULL);


ALTER TABLE images ADD CONSTRAINT fk_images_images FOREIGN KEY (product_id) REFERENCES products (id) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE products_images ADD CONSTRAINT fk_products_images_images FOREIGN KEY (image_id) REFERENCES images (id) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE products_images ADD CONSTRAINT fk_products_images_products FOREIGN KEY (product_id) REFERENCES products (id) ON UPDATE CASCADE ON DELETE CASCADE;

