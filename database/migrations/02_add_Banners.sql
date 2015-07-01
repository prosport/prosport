CREATE SEQUENCE banners_seq INCREMENT BY 1;
CREATE TABLE banners
(
	id BIGINT DEFAULT nextval('banners_seq'::regclass) NOT NULL,
	name VARCHAR(255) NOT NULL,
	url VARCHAR(255) NOT NULL,
	image_id BIGINT NOT NULL,
  created_at TIMESTAMP DEFAULT now() NOT NULL,
  modified_at TIMESTAMP DEFAULT now() NOT NULL
);
