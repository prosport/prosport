--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.4
-- Dumped by pg_dump version 9.4.0
-- Started on 2015-07-12 21:16:09 MSK

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 186 (class 3079 OID 12123)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2359 (class 0 OID 0)
-- Dependencies: 186
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- TOC entry 172 (class 1259 OID 16388)
-- Name: banners_seq; Type: SEQUENCE; Schema: public; Owner: prosport_db
--

CREATE SEQUENCE banners_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE banners_seq OWNER TO prosport_db;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 173 (class 1259 OID 16390)
-- Name: banners; Type: TABLE; Schema: public; Owner: prosport_db; Tablespace: 
--

CREATE TABLE banners (
    id bigint DEFAULT nextval('banners_seq'::regclass) NOT NULL,
    name character varying(255) NOT NULL,
    url character varying(255) NOT NULL,
    image_id bigint NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    modified_at timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE banners OWNER TO prosport_db;

--
-- TOC entry 174 (class 1259 OID 16399)
-- Name: images; Type: TABLE; Schema: public; Owner: prosport_db; Tablespace: 
--

CREATE TABLE images (
    id bigint NOT NULL,
    name character varying(255),
    color character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    product_id bigint,
    _ebean_intercept bytea
);


ALTER TABLE images OWNER TO prosport_db;

--
-- TOC entry 175 (class 1259 OID 16405)
-- Name: images_seq; Type: SEQUENCE; Schema: public; Owner: prosport_db
--

CREATE SEQUENCE images_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE images_seq OWNER TO prosport_db;

--
-- TOC entry 176 (class 1259 OID 16407)
-- Name: pages; Type: TABLE; Schema: public; Owner: prosport_db; Tablespace: 
--

CREATE TABLE pages (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    url character varying(255),
    content text
);


ALTER TABLE pages OWNER TO prosport_db;

--
-- TOC entry 177 (class 1259 OID 16413)
-- Name: pages_seq; Type: SEQUENCE; Schema: public; Owner: prosport_db
--

CREATE SEQUENCE pages_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pages_seq OWNER TO prosport_db;

--
-- TOC entry 178 (class 1259 OID 16415)
-- Name: play_evolutions; Type: TABLE; Schema: public; Owner: prosport_db; Tablespace: 
--

CREATE TABLE play_evolutions (
    id integer NOT NULL,
    hash character varying(255) NOT NULL,
    applied_at timestamp without time zone NOT NULL,
    apply_script text,
    revert_script text,
    state character varying(255),
    last_problem text
);


ALTER TABLE play_evolutions OWNER TO prosport_db;

--
-- TOC entry 179 (class 1259 OID 16421)
-- Name: product_categories; Type: TABLE; Schema: public; Owner: prosport_db; Tablespace: 
--

CREATE TABLE product_categories (
    id bigint NOT NULL,
    name character varying(255),
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    modified_at timestamp without time zone DEFAULT now() NOT NULL,
    _ebean_intercept bytea,
    parent_id bigint,
    sort_order integer,
    url character varying(255)
);


ALTER TABLE product_categories OWNER TO prosport_db;

--
-- TOC entry 180 (class 1259 OID 16429)
-- Name: product_categories_seq; Type: SEQUENCE; Schema: public; Owner: prosport_db
--

CREATE SEQUENCE product_categories_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE product_categories_seq OWNER TO prosport_db;

--
-- TOC entry 181 (class 1259 OID 16431)
-- Name: products; Type: TABLE; Schema: public; Owner: prosport_db; Tablespace: 
--

CREATE TABLE products (
    id bigint NOT NULL,
    name character varying(255),
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    modified_at timestamp without time zone DEFAULT now() NOT NULL,
    articul character varying(255) NOT NULL,
    author character varying(255),
    description character varying(255) NOT NULL,
    rating double precision NOT NULL,
    semantic_url character varying(255) NOT NULL,
    short_description character varying(255) NOT NULL,
    views_count integer NOT NULL,
    category_id bigint NOT NULL,
    _ebean_intercept bytea
);


ALTER TABLE products OWNER TO prosport_db;

--
-- TOC entry 182 (class 1259 OID 16439)
-- Name: products_images; Type: TABLE; Schema: public; Owner: prosport_db; Tablespace: 
--

CREATE TABLE products_images (
    products_id bigint NOT NULL,
    images_id bigint NOT NULL
);


ALTER TABLE products_images OWNER TO prosport_db;

--
-- TOC entry 183 (class 1259 OID 16442)
-- Name: products_seq; Type: SEQUENCE; Schema: public; Owner: prosport_db
--

CREATE SEQUENCE products_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE products_seq OWNER TO prosport_db;

--
-- TOC entry 184 (class 1259 OID 16444)
-- Name: users; Type: TABLE; Schema: public; Owner: prosport_db; Tablespace: 
--

CREATE TABLE users (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    is_blocked boolean,
    password character varying(32),
    registed_at timestamp without time zone DEFAULT now() NOT NULL,
    role character varying(16),
    _ebean_intercept bytea
);


ALTER TABLE users OWNER TO prosport_db;

--
-- TOC entry 185 (class 1259 OID 16451)
-- Name: users_seq; Type: SEQUENCE; Schema: public; Owner: prosport_db
--

CREATE SEQUENCE users_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_seq OWNER TO prosport_db;

--
-- TOC entry 2339 (class 0 OID 16390)
-- Dependencies: 173
-- Data for Name: banners; Type: TABLE DATA; Schema: public; Owner: prosport_db
--



--
-- TOC entry 2360 (class 0 OID 0)
-- Dependencies: 172
-- Name: banners_seq; Type: SEQUENCE SET; Schema: public; Owner: prosport_db
--

SELECT pg_catalog.setval('banners_seq', 1, false);


--
-- TOC entry 2340 (class 0 OID 16399)
-- Dependencies: 174
-- Data for Name: images; Type: TABLE DATA; Schema: public; Owner: prosport_db
--

INSERT INTO images VALUES (214, 'тест', 'синий', '23/da/2977524a891feb765dff2d3bdc0c.jpg', 1, NULL);
INSERT INTO images VALUES (233, 'price_tshirts', '', '6c/37/2a3c030ad03cb3208ed7908d7fab.jpg', NULL, NULL);
INSERT INTO images VALUES (253, 'price_1_shirt_1', '', 'fd/e7/55b17c1fb2a78cdde6f7e8a1bf65.jpg', NULL, NULL);
INSERT INTO images VALUES (254, 'price_1_shirt_2', '', '48/e0/35030392a9df9872d6cff9b48da7.jpg', NULL, NULL);
INSERT INTO images VALUES (255, 'price_1_shirt_3', '', 'd8/c1/e657096ab9b96eab04e1a49120e9.jpg', NULL, NULL);
INSERT INTO images VALUES (256, 'price_1_shirt_4', '', '48/fc/c0ddb095ebc64d991dff700c895e.jpg', NULL, NULL);
INSERT INTO images VALUES (257, 'price_1_shirt_5', '', '70/b5/da378aa260aa4652d87407af09dd.jpg', NULL, NULL);
INSERT INTO images VALUES (258, 'price_1_shirt_6', '', '18/ba/9e043adeceb0fb8849327b8c059d.jpg', NULL, NULL);
INSERT INTO images VALUES (259, 'price_1_shirt_7', '', '5e/a4/1e9f4506436487bdae7b56389093.jpg', NULL, NULL);
INSERT INTO images VALUES (260, 'price_1_shirt_8', '', 'b3/6b/c53874ca30a76fb7565d9c3cdcc2.jpg', NULL, NULL);
INSERT INTO images VALUES (261, 'shvi', '', '77/a1/6b96dbfd744d77339e0a32fdf0f0.jpg', NULL, NULL);
INSERT INTO images VALUES (262, 'tolst1', '', 'a9/86/0e06e1a1a0838aedc609778c8ec4.jpg', NULL, NULL);
INSERT INTO images VALUES (263, 'legs', '', '52/2c/abb80dd53c8f3f62f44e49a2ee72.jpg', NULL, NULL);
INSERT INTO images VALUES (264, 'band', '', 'db/b9/18cda820525cf709b67a75080c3f.jpg', NULL, NULL);
INSERT INTO images VALUES (265, 'pirat', '', '71/65/18e633aa248a3f77130dab6246ec.jpg', NULL, NULL);
INSERT INTO images VALUES (266, 'shapka', '', '70/0a/a654bdb4d2606c488cb8b5d3b18c.jpg', NULL, NULL);
INSERT INTO images VALUES (267, 'kepki', '', '64/38/f5af5b09e031b518777be44bbc1e.jpg', NULL, NULL);
INSERT INTO images VALUES (268, 'mul', '', '16/38/a22b5b7ba216aab9c0d19fa5a969.jpg', NULL, NULL);
INSERT INTO images VALUES (293, 'nashivki', '', '1e/be/0cbc3cd0e4d3d99e4c0fa5603224.jpg', NULL, NULL);
INSERT INTO images VALUES (294, 'flag', '', '32/62/63934a14be0d6f6c3e50a3db688c.jpg', NULL, NULL);
INSERT INTO images VALUES (295, 'pro_sport_office', '', 'a8/6d/ebd51dd7fe5e09064c7fa5a5720a.jpg', NULL, NULL);
INSERT INTO images VALUES (313, 'pro_sport_print', '', '1b/02/2852e9fe056c4aed6dc69980bd55.jpg', NULL, NULL);
INSERT INTO images VALUES (314, 'pro_sport_poshiv', '', '45/d6/1eabc570d50d6f829afcdf139760.jpg', NULL, NULL);
INSERT INTO images VALUES (333, 'map', '', '8f/63/f0f8fcdfe85ee8808953b4402dca.jpg', NULL, NULL);


--
-- TOC entry 2361 (class 0 OID 0)
-- Dependencies: 175
-- Name: images_seq; Type: SEQUENCE SET; Schema: public; Owner: prosport_db
--

SELECT pg_catalog.setval('images_seq', 352, true);


--
-- TOC entry 2342 (class 0 OID 16407)
-- Dependencies: 176
-- Data for Name: pages; Type: TABLE DATA; Schema: public; Owner: prosport_db
--

INSERT INTO pages VALUES (81, 'О компании', '/about', '<p>Наша компания рада предложить Вам высококачественную одежду (футболки, толстовки, банданы, бейсболки) с нанесением логотипов, фирменных знаков и прочих изображений методом шелкографии (трафаретная печать), а также флаги, шевроны, вымпела и другую рекламную продукцию из текстиля.</p>

<p>Несомненным плюсом компании является наличие собственных цехов по пошиву футболок. Наше производство, размещенное в пределах г.Перми и удобное расположение офиса в центре города позволяет оперативно выполнять заказы клиентов.&nbsp;<br />
Так же мы осуществляем продажу футболок и реализуем их оптом.</p>

<p>Изготовление футболок осуществляется из высококачественных материалов, с соблюдением всех стандартов, что позволяет нам конкурировать с зарубежными производителями как по цене, так и по качеству. Нанесение трафаретов осуществляется на новейшем оборудовании импортного производства высококлассными специалистами с использованием качественных материалов. Футболка с логотипом сделает вашу компанию более узнаваемой, привлечет потенциальных клиентов и подчеркнет корпоративный стиль.</p>

<p>Одежда с фирменной символикой &ndash; это незаменимый инструмент любой промоакции и всегда приятный подарок клиенту. Получить консультацию опытных менеджеров, сделать заказ и купить футболки Вы сможете, связавшись с нами.&nbsp;</p>

<p><strong>Будем рады видеть Вас в числе наших клиентов!</strong></p>

<p><img alt="" src="http://localhost:9000/images/a8/6d/ebd51dd7fe5e09064c7fa5a5720a.jpg" style="float:left; height:202px; width:270px" /></p>

<p>Производственно-рекламное агентство &laquo;Про-спорт&raquo; основано в 1996 г.</p>

<p>Компания позиционирует себя производителем текстильной продукции высокого качества для рекламных целей.</p>

<p>Собственное швейное производство поставлено на поток и позволяет отшивать до 20.000 футболок в месяц.&nbsp;</p>

<p>Размеры варьируются с детских с 32 по 36 размер, подростковых с 38 по 42, взрослых с S по XXL, очень больших до XXXXL. Цветовая гамма: белый, василек (ярко-синий), темно-синий, черный, красный, оранжевый, желтый. По желанию заказчика, цвета могут комбинироваться. При изготовлении используется только качественный хлопок плотностью 160 гр., предназначенный специально для печати на нем.</p>

<p>&nbsp;</p>

<p>Основным направлением работы компании является трафаретная печать.&nbsp;</p>

<p>Шестикрасочные машины новейших модификаций фирмы M&amp;R в количестве 3 штук позволяют печатать полноцветные изображения высокого качества на текстильных изделиях.</p>

<p>Индивидуальный подход к каждому заказу, гарантия качества выпускаемой продукции - основные задачи компании.</p>

<p>&nbsp;</p>

<p>Производство состоит из:</p>

<table border="1" cellpadding="1" cellspacing="1" style="width:600px">
	<tbody>
		<tr>
			<td>
			<p><img alt="" src="http://localhost:9000/images/	1b/02/2852e9fe056c4aed6dc69980bd55.jpg" /></p>

			<p>печатного цеха, располагающего печатными машинами и термопрессами для термо-трансферной печати на футболках и бейсболках</p>
			</td>
			<td>
			<p><img alt="" src="http://localhost:9000/images/45/d6/1eabc570d50d6f829afcdf139760.jpg	" /></p>

			<p>трех цехов для пошива изделий из светлых и темных тканей, включающих 30 рабочих мест</p>
			</td>
		</tr>
	</tbody>
</table>

<p>&nbsp;</p>
');
INSERT INTO pages VALUES (61, 'Нанесение', '/price/application', '<h1>НАНЕСЕНИЕ</h1>

<h2>Шелкография, прямая печать</h2>

<p>Нанесение изображений на футболки, банданы и другие изделия из текстиля, предназначенные для ПРЯМОЙ печати на них.<br />
Тираж: от&nbsp;<strong>100</strong>&nbsp;шт. (цены указаны без стоимости носителя);</p>

<table border="0" cellpadding="4" cellspacing="3" style="width:100%">
	<tbody>
		<tr>
			<td><strong>Размер изображения, см<br />
			Количество цветов</strong></td>
			<td><strong>А6</strong><br />
			(10х15)</td>
			<td><strong>А5</strong><br />
			(20х15)</td>
			<td><strong>А4</strong><br />
			(20х28,5)</td>
			<td><strong>А3</strong><br />
			(28,5х40)</td>
			<td><strong>40х50</strong></td>
		</tr>
		<tr>
			<td><strong>1</strong></td>
			<td><strong>19</strong></td>
			<td><strong>22</strong></td>
			<td><strong>25</strong></td>
			<td><strong>30</strong></td>
			<td><strong>40</strong></td>
		</tr>
		<tr>
			<td><strong>2</strong></td>
			<td><strong>26</strong></td>
			<td><strong>32</strong></td>
			<td><strong>38</strong></td>
			<td><strong>44</strong></td>
			<td><strong>52</strong></td>
		</tr>
		<tr>
			<td><strong>3</strong></td>
			<td><strong>32</strong></td>
			<td><strong>38</strong></td>
			<td><strong>44</strong></td>
			<td><strong>52</strong></td>
			<td><strong>62</strong></td>
		</tr>
		<tr>
			<td><strong>4</strong></td>
			<td><strong>38</strong></td>
			<td><strong>44</strong></td>
			<td><strong>52</strong></td>
			<td><strong>62</strong></td>
			<td><strong>72</strong></td>
		</tr>
		<tr>
			<td><strong>5</strong></td>
			<td><strong>44</strong></td>
			<td><strong>50</strong></td>
			<td><strong>58</strong></td>
			<td><strong>68</strong></td>
			<td><strong>78</strong></td>
		</tr>
		<tr>
			<td><strong>6</strong></td>
			<td><strong>48</strong></td>
			<td><strong>54</strong></td>
			<td><strong>62</strong></td>
			<td><strong>72</strong></td>
			<td><strong>80</strong></td>
		</tr>
	</tbody>
</table>

<p>При печати на темных тканях учитывается дополнительный цвет - белая подложка.</p>

<p><strong>СКИДКИ на нанесение!</strong>&nbsp;100 футболок - 1%, 200 - 2%, 300 - 3% ... максимум до 10%.</p>

<p>&nbsp;</p>

<p>Шелкография, трансферы</p>

<table border="0" cellpadding="4" cellspacing="3" style="width:350px">
	<tbody>
		<tr>
			<td><strong>Кол-во цветов</strong></td>
			<td><strong>1 лист А4</strong><br />
			для БЕЛЫХ тканей</td>
			<td><strong>1 лист А4</strong><br />
			для любых тканей</td>
		</tr>
		<tr>
			<td><strong>1</strong></td>
			<td><strong>60</strong></td>
			<td><strong>70</strong></td>
		</tr>
		<tr>
			<td><strong>2</strong></td>
			<td><strong>80</strong></td>
			<td><strong>90</strong></td>
		</tr>
		<tr>
			<td><strong>3</strong></td>
			<td><strong>100</strong></td>
			<td><strong>110</strong></td>
		</tr>
		<tr>
			<td><strong>4</strong></td>
			<td><strong>120</strong></td>
			<td><strong>130</strong></td>
		</tr>
		<tr>
			<td><strong>5</strong></td>
			<td><strong>140</strong></td>
			<td><strong>150</strong></td>
		</tr>
		<tr>
			<td><strong>6</strong></td>
			<td><strong>150</strong></td>
			<td><strong>160</strong></td>
		</tr>
	</tbody>
</table>

<p>Изготовление трансферов для нанесения изображений на изделия, не предназначенные для прямой печати. Изображения наносятся на бумагу, с которой затем производится перенос непосредственно на изделие. На одном листе может располагаться несколько изображений. Количество цветов считается с листа, а не с каждого изображения на нем.&nbsp;</p>

<p>В макетах, имеющих более 1 цвета на лист, всегда учитывается дополнительный цвет - клеевой слой.</p>

<p>Тираж: от&nbsp;<strong>50</strong>&nbsp;листов.</p>

<p>&nbsp;</p>

<h1>ТОВАРЫ С НАНЕСЕНИЕМ</h1>

<table border="1" cellpadding="1" cellspacing="1" style="width:700px">
	<tbody>
		<tr>
			<td><img alt="" src="http://localhost:9000/images/1e/be/0cbc3cd0e4d3d99e4c0fa5603224.jpg" style="height:188px; width:200px" /></td>
			<td>
			<h2>Шевроны</h2>
			Нашивки с изображением, выполненным методом сублимации, с обшивкой по краям. Количество цветов и сложность рисунка не ограничены. Размеры, цвета обшивки и другие особенности - по желанию заказчика.<br />
			<br />
			Цена:&nbsp;<strong>от 60 руб.</strong></td>
		</tr>
		<tr>
			<td><img alt="" src="http://localhost:9000/images/32/62/63934a14be0d6f6c3e50a3db688c.jpg	" /></td>
			<td>
			<h2>Флажки</h2>
			Флажки на основе синтетической ткани (полиэстер) с окраской методом сублимации. Возможны как стандартные размеры (22,5х15 см), так и произвольные.&nbsp;<br />
			<br />
			Цена:&nbsp;<strong>от 120 руб.</strong><br />
			<br />
			В наличии есть флажки Российской Федерации по 40 руб.</td>
		</tr>
	</tbody>
</table>

<p>&nbsp;</p>
');
INSERT INTO pages VALUES (101, 'Контакты', '/contacts', '<h2>Схема размещения</h2>

<p><img alt="" src="http://localhost:9000/images/8f/63/f0f8fcdfe85ee8808953b4402dca.jpg" style="height:540px; width:540px" /></p>

<p><strong>Адрес:</strong>&nbsp;614000 г.Пермь, ул.Сибирская 9, (Гостиница &laquo;Центральная&raquo;), офис 725а&nbsp;<br />
<strong>Телефон:</strong>&nbsp;+7(342)2-125-490,&nbsp;&nbsp;<br />
<strong>Телефон-факс:</strong>&nbsp;+7(342)2-181-904<br />
<strong>Режим работы:</strong>&nbsp;с 9-00 до 18-00, без перерыва на обед.&nbsp;<br />
<strong>Выходные:</strong>&nbsp;суббота, воскресенье.</p>
');
INSERT INTO pages VALUES (41, 'Производство', '/price/manufacturing', '<h1>ФУТБОЛКИ</h1>

<p>Собственное швейное производство позволяет всегда иметь в наличии футболки любых размеров от 38 до 56 (до 38 - под заказ), белого, черного, темно-синего, василькового (ярко-синего), красного, оранжевого, желтого, серого цветов.<br />
Футболки изготавливаются из трикотажа плотностью 160-180 г/кв.см. (производитель - Турция) с усиленным воротом и плечевым швом.</p>

<h2><br />
<img alt="" src="http://localhost:9000/images/6c/37/2a3c030ad03cb3208ed7908d7fab.jpg" style="height:124px; width:550px" /></h2>

<table align="center" border="1" cellpadding="1" cellspacing="1" style="width:100%">
	<tbody>
		<tr>
			<td><img alt="" src="http://localhost:9000/images/fd/e7/55b17c1fb2a78cdde6f7e8a1bf65.jpg" style="height:174px; width:200px" /></td>
			<td>
			<h2>Классические футболки</h2>

			<p>Все стандартные цвета и размеры всегда в наличии. По желанию заказчика можно изготовить изделия из ткани любого цвета.</p>

			<p>детские (30-42):&nbsp;<strong>170 руб.</strong><br />
			взрослые (S-XXL):&nbsp;<strong>190 руб.</strong><br />
			большие (XXXL):&nbsp;<strong>210 руб.</strong><br />
			огромные (XXXXL):&nbsp;<strong>230 руб.</strong></p>
			</td>
		</tr>
		<tr>
			<td><img alt="" src="http://localhost:9000/images/48/e0/35030392a9df9872d6cff9b48da7.jpg" style="height:153px; width:200px" /></td>
			<td>
			<h2>Белые футболки с цветным рукавом</h2>

			<p>В наличии имеются белые футболки с синим рукавом, остальные цвета изготавливаются по заказу.</p>

			<p>взрослые (S-XXL):&nbsp;<strong>160 руб.</strong><br />
			большие (XXXL):&nbsp;<strong>180 руб.</strong><br />
			огромные (XXXXL):&nbsp;<strong>200 руб.</strong></p>
			</td>
		</tr>
		<tr>
			<td><img alt="" src="http://localhost:9000/images/d8/c1/e657096ab9b96eab04e1a49120e9.jpg	" style="height:225px; width:200px" /></td>
			<td>
			<h2>Футболки-безрукавки (стритболки)</h2>

			<p>Цвет и размер любой. Так же, по желанию заказчика, возможны двухцветные варианты.</p>

			<p>взрослые (S-XXL):&nbsp;<strong>130 руб.</strong><br />
			большие (XXXL):&nbsp;<strong>140 руб.</strong><br />
			огромные (XXXXL):&nbsp;<strong>150 руб.</strong></p>
			</td>
		</tr>
		<tr>
			<td><img alt="" src="http://localhost:9000/images/48/fc/c0ddb095ebc64d991dff700c895e.jpg" style="height:179px; width:200px" /></td>
			<td>
			<h2>Футболки с рукавом &quot;Реглан&quot;</h2>

			<p>Изготавливаются только под заказ.</p>

			<p>взрослые (S-XXL):&nbsp;<strong>180 руб.</strong><br />
			большие (XXXL):&nbsp;<strong>190 руб.</strong><br />
			огромные (XXXXL):&nbsp;<strong>200 руб.</strong></p>
			</td>
		</tr>
		<tr>
			<td><img alt="" src="http://localhost:9000/images/70/b5/da378aa260aa4652d87407af09dd.jpg" /></td>
			<td>
			<h2>Футболки с длинным рукавом</h2>

			<p>В наличии все стандартные цвета.</p>

			<p>взрослые (S-XXL):&nbsp;<strong>200 руб.</strong></p>
			</td>
		</tr>
		<tr>
			<td><img alt="" src="http://localhost:9000/images/18/ba/9e043adeceb0fb8849327b8c059d.jpg	" /></td>
			<td>
			<h2>Футболки &quot;Реглан&quot; с нанесением</h2>

			<p><strong>Пошив:</strong><br />
			взрослые (S-XXL):&nbsp;<strong>180 руб.</strong><br />
			большие (XXXL):&nbsp;<strong>190 руб.</strong><br />
			огромные (XXXXL):&nbsp;<strong>200 руб.</strong><br />
			<strong>Нанесение (за 1 рукав):</strong><br />
			1 цвет:&nbsp;<strong>70 руб.</strong><br />
			2 цвета:&nbsp;<strong>100 руб.</strong><br />
			3 цвета:&nbsp;<strong>120 руб.</strong><br />
			4 цвета:&nbsp;<strong>140 руб.</strong></p>

			<p>Минимальная партия одной модели -&nbsp;<strong>200 штук</strong>.</p>
			</td>
		</tr>
		<tr>
			<td><img alt="" src="http://localhost:9000/images/5e/a4/1e9f4506436487bdae7b56389093.jpg" style="height:166px; width:200px" /></td>
			<td>
			<h2>Футболки со вставками</h2>

			<p>Вставки выполненяются из синтетического трикотажа с нанесением изображения методом сублимации.</p>

			<p>Пошив:&nbsp;</p>

			<p><strong>от 150 руб.</strong><br />
			Нанесение (за 1 рукав):&nbsp;<strong>50 руб.</strong><br />
			<br />
			Минимальная партия одной модели -&nbsp;<strong>200 штук.</strong></p>
			</td>
		</tr>
		<tr>
			<td><img alt="" src="http://localhost:9000/images/b3/6b/c53874ca30a76fb7565d9c3cdcc2.jpg" style="height:201px; width:200px" /></td>
			<td>
			<h2>Детский ассортимент</h2>

			<p>Возможен пошив прямых и приталенных детских футболок с коротким, длинным рукавом и рукавом &frac34; для девочек.<br />
			По желанию заказчика можно нанести на футболки термотрансферные изображения американского производства.</p>

			<p>без изображения:&nbsp;<strong>90 руб.</strong><br />
			с изображением:&nbsp;<strong>140 руб.</strong></p>
			</td>
		</tr>
	</tbody>
</table>

<p><img alt="" src="http://localhost:9000/images/77/a1/6b96dbfd744d77339e0a32fdf0f0.jpg" style="height:307px; width:400px" /></p>

<p>Футболки изготавливаются из трикотажа плотностью 160-180 г/кв.см. (производитель - Турция) с усиленным воротом и плечевым швом.</p>

<h1>СПОРТИВНЫЕ КОСТЮМЫ</h1>

<table border="1" cellpadding="1" cellspacing="1" style="width:100%">
	<tbody>
		<tr>
			<td><img alt="" src="http://localhost:9000/images/a9/86/0e06e1a1a0838aedc609778c8ec4.jpg" /></td>
			<td>
			<h2>Толстовки</h2>
			Ткань:&nbsp;<strong>футер</strong><br />
			Цвета:&nbsp;<strong>серый (меланж), черный</strong><br />
			<br />
			детские (от 28 разм.):&nbsp;<strong>от 400 руб.</strong><br />
			детские (от 36 разм.):&nbsp;<strong>от 600 руб.</strong><br />
			взрослые (от 42 &nbsp;):&nbsp;<strong>от 700 руб.</strong><br />
			большие (от XXXL):&nbsp;<strong>от 750 руб.</strong><br />
			<br />
			Капюшон:&nbsp;<strong>по желанию* (+50 р.)</strong><br />
			Карман:&nbsp;<strong>&quot;Кенгуру&quot;, по желанию* (+100 р.)</strong><br />
			<br />
			*возможность реализации опций уточнять у менеджера.</td>
		</tr>
		<tr>
			<td><img alt="" src="http://localhost:9000/images/52/2c/abb80dd53c8f3f62f44e49a2ee72.jpg" /></td>
			<td>
			<h2>Брюки</h2>
			Ткань:&nbsp;<strong>футер</strong><br />
			Цвета:&nbsp;<strong>серый (меланж), черный</strong><br />
			<br />
			детские (от 28 разм.):&nbsp;<strong>от 450 руб.</strong><br />
			детские (от 36 разм.):&nbsp;<strong>от 600 руб.</strong><br />
			подростковые (от 40 разм.):&nbsp;<strong>от 700 руб.</strong><br />
			взрослые (от 42 и более):&nbsp;<strong>от 750 руб.</strong><br />
			<br />
			<strong>Карман:</strong><br />
			28-34 размер:&nbsp;<strong>нет</strong><br />
			36-42 размер:&nbsp;<strong>по желанию* (+50 р.)</strong><br />
			S и более:&nbsp;<strong>есть</strong><br />
			<br />
			*возможность реализации опций уточнять у менеджера.</td>
		</tr>
	</tbody>
</table>

<h1>ГОЛОВНЫЕ УБОРЫ</h1>

<table border="1" cellpadding="1" cellspacing="1" style="width:100%">
	<tbody>
		<tr>
			<td><img alt="" src="http://localhost:9000/images/db/b9/18cda820525cf709b67a75080c3f.jpg" /></td>
			<td>
			<h2>Банданы</h2>
			Ткань:&nbsp;<strong>ситец</strong><br />
			Размер:&nbsp;<strong>65х65 см</strong><br />
			Цвета:&nbsp;<strong>под заказ</strong><br />
			Наличие:&nbsp;<strong>под заказ</strong><br />
			<br />
			Нанесение изображения производится методом шелкографного трансфера или флексографии в лобную или височную область, либо прямой печатью с возможностью заполнения всей площади.</td>
		</tr>
		<tr>
			<td><img alt="" src="http://localhost:9000/images/71/65/18e633aa248a3f77130dab6246ec.jpg	" /></td>
			<td>
			<h2>Пиратки</h2>
			Ткань:&nbsp;<strong>х/б (как футболки)</strong><br />
			Цвета:&nbsp;<strong>стандартные*</strong><br />
			Наличие:&nbsp;<strong>есть</strong><br />
			<br />
			Нанесение изображения производится методом шелкографного трансфера, флексографии, или прямой печати в лобную или височную область.</td>
		</tr>
		<tr>
			<td><img alt="" src="http://localhost:9000/images/70/0a/a654bdb4d2606c488cb8b5d3b18c.jpg" style="height:197px; width:200px" /></td>
			<td>
			<h2>Зимние шапки</h2>
			Ткань:&nbsp;<strong>2 слоя (синтетика, х/б)</strong><br />
			Цвета:&nbsp;<strong>серый (меланж)</strong><br />
			Наличие:&nbsp;<strong>есть</strong><br />
			Размеры:&nbsp;<strong>S,M,L</strong><br />
			<br />
			Нанесение изображения производится методом шелкографного трансфера или флексографии по периметру канта.</td>
		</tr>
		<tr>
			<td><img alt="" src="http://localhost:9000/images/64/38/f5af5b09e031b518777be44bbc1e.jpg" /></td>
			<td>
			<h2>Бейсболки</h2>
			Цвета:&nbsp;<strong>стандартные*</strong><br />
			Наличие:&nbsp;<strong>под заказ</strong><br />
			<br />
			Производство:&nbsp;<strong>Китай</strong><br />
			<br />
			Нанесение изображения производится методом шелкографного трансфера или флексографии на лобную часть.<br />
			<br />
			Цена (без нанесения):&nbsp;<strong>80 руб.</strong></td>
		</tr>
	</tbody>
</table>

<h1>РАЗМЕРНИКИ</h1>

<p><img alt="" src="http://localhost:9000/images/16/38/a22b5b7ba216aab9c0d19fa5a969.jpg" /></p>

<p>Возможно изготовление размерников с Вашим логотипом, а так же вшивание их в чистые футболки.</p>

<p><br />
Минимальная партия &ndash; 1000 штук одного цвета футболок.<br />
Цена 1 размерника с вашим логотипом &ndash; от 5 руб.</p>

<p>&nbsp;</p>

<h1><strong>Предлагаем сотрудничество рекламным агентствам, оптовикам и производственным фирмам!</strong></h1>
');


--
-- TOC entry 2362 (class 0 OID 0)
-- Dependencies: 177
-- Name: pages_seq; Type: SEQUENCE SET; Schema: public; Owner: prosport_db
--

SELECT pg_catalog.setval('pages_seq', 120, true);


--
-- TOC entry 2344 (class 0 OID 16415)
-- Dependencies: 178
-- Data for Name: play_evolutions; Type: TABLE DATA; Schema: public; Owner: prosport_db
--

INSERT INTO play_evolutions VALUES (1, '943952e08b83d03342c739565bc45c302caa60df', '2015-07-09 00:00:00', 'create table IF NOT EXISTS banners (
id                        bigint not null,
name                      varchar(255),
created_at                TIMESTAMP DEFAULT now() not null,
modified_at               TIMESTAMP DEFAULT now() not null,
url                       varchar(255),
image_id                  bigint,
constraint uq_banners_name unique (name),
constraint pk_banners primary key (id))
;

create table IF NOT EXISTS images (
id                        bigint not null,
name                      varchar(255),
product_id                bigint,
color                     varchar(255) not null,
filename                  varchar(255) not null,
constraint uq_images_name unique (name),
constraint pk_images primary key (id))
;

create table IF NOT EXISTS products (
id                        bigint not null,
name                      varchar(255),
created_at                TIMESTAMP DEFAULT now() not null,
modified_at               TIMESTAMP DEFAULT now() not null,
category_id               bigint not null,
semantic_url              varchar(255) not null,
articul                   varchar(255) not null,
description               varchar(255) not null,
short_description         varchar(255) not null,
rating                    float not null,
views_count               integer not null,
author                    varchar(255),
constraint uq_products_name unique (name),
constraint pk_products primary key (id))
;

create table IF NOT EXISTS product_categories (
id                        bigint not null,
name                      varchar(255),
created_at                TIMESTAMP DEFAULT now() not null,
modified_at               TIMESTAMP DEFAULT now() not null,
sort_order                integer,
url                       varchar(255),
parent_id                 bigint,
constraint uq_product_categories_name unique (name),
constraint pk_product_categories primary key (id))
;

create table IF NOT EXISTS pages (
id                        bigint not null,
name                      varchar(255),
url                       varchar(255),
content                   varchar(255),
constraint uq_pages_name unique (name),
constraint pk_pages primary key (id))
;

create table IF NOT EXISTS users (
id                        bigint not null,
email                     varchar(255) not null,
password                  varchar(255),
role                      varchar(9),
registed_at               TIMESTAMP DEFAULT now() not null,
constraint ck_users_role check (role in (''blocked'',''manager'',''admin'',''moderator'',''n/a'')),
constraint uq_users_email unique (email),
constraint pk_users primary key (id))
;

create sequence IF NOT EXISTS banners_seq;

create sequence IF NOT EXISTS images_seq;

create sequence IF NOT EXISTS products_seq;

create sequence IF NOT EXISTS product_categories_seq;

create sequence IF NOT EXISTS pages_seq;

create sequence IF NOT EXISTS users_seq;

alter table banners add constraint fk_banners_image_1 foreign key (image_id) references images (id);
create index ix_banners_image_1 on banners (image_id);
alter table images add constraint fk_images_product_2 foreign key (product_id) references products (id);
create index ix_images_product_2 on images (product_id);
alter table products add constraint fk_products_category_3 foreign key (category_id) references product_categories (id);
create index ix_products_category_3 on products (category_id);
alter table product_categories add constraint fk_product_categories_parent_4 foreign key (parent_id) references product_categories (id);
create index ix_product_categories_parent_4 on product_categories (parent_id);', 'drop table if exists banners cascade;

drop table if exists images cascade;

drop table if exists products cascade;

drop table if exists product_categories cascade;

drop table if exists pages cascade;

drop table if exists users cascade;

drop sequence if exists banners_seq;

drop sequence if exists images_seq;

drop sequence if exists products_seq;

drop sequence if exists product_categories_seq;

drop sequence if exists pages_seq;

drop sequence if exists users_seq;', 'applying_up', 'ERROR: syntax error at or near "NOT"
  Position: 20 [ERROR:0, SQLSTATE:42601]');


--
-- TOC entry 2345 (class 0 OID 16421)
-- Dependencies: 179
-- Data for Name: product_categories; Type: TABLE DATA; Schema: public; Owner: prosport_db
--

INSERT INTO product_categories VALUES (214, 'Штаны', '2015-07-05 15:04:33.854', '2015-07-05 15:04:33.853', NULL, NULL, 5, 'trousers');
INSERT INTO product_categories VALUES (191, 'Футболки', '2015-07-05 14:35:20.644', '2015-07-05 14:35:20.586', NULL, NULL, -1, 't_shirts');
INSERT INTO product_categories VALUES (1, 'Дзюдо', '2015-04-13 19:23:46.003267', '2015-04-13 19:23:46.003267', NULL, 191, 3, 'dzudo');
INSERT INTO product_categories VALUES (2, 'Самбо', '2015-04-13 19:23:46.003267', '2015-04-13 19:23:46.003267', NULL, 191, 0, 'sambo');
INSERT INTO product_categories VALUES (3, 'Борьба', '2015-04-13 19:23:46.003267', '2015-04-13 19:23:46.003267', NULL, 191, 1, 'wrestling');
INSERT INTO product_categories VALUES (4, 'Карате', '2015-04-13 19:23:46.003267', '2015-04-13 19:23:46.003267', NULL, 191, 3, 'karate');
INSERT INTO product_categories VALUES (5, 'Бокс', '2015-04-13 19:23:46.003267', '2015-04-13 19:23:46.003267', NULL, 191, 1, 'boxing');
INSERT INTO product_categories VALUES (6, 'Кик-боксинг', '2015-04-13 19:23:46.003267', '2015-04-13 19:23:46.003267', NULL, 191, 1, 'kick_boxing');
INSERT INTO product_categories VALUES (7, 'Плавание', '2015-04-13 19:23:46.003267', '2015-04-13 19:23:46.003267', NULL, 191, 1, 'swimming');
INSERT INTO product_categories VALUES (8, 'Бои без правил', '2015-04-13 19:23:46.003267', '2015-04-13 19:23:46.003267', NULL, 191, 2, 'mma');
INSERT INTO product_categories VALUES (9, 'Россия', '2015-04-13 19:23:46.003267', '2015-04-13 19:23:46.003267', NULL, 191, 1, 'misc');
INSERT INTO product_categories VALUES (10, 'Разное', '2015-04-13 19:23:46.003267', '2015-04-13 19:23:46.003267', NULL, 191, 1, '1');
INSERT INTO product_categories VALUES (211, 'Длинный рукав', '2015-07-05 14:59:48.821', '2015-07-05 14:59:48.769', NULL, NULL, 2, 'long_sleeve');
INSERT INTO product_categories VALUES (212, 'Костюмы', '2015-07-05 15:02:04.882', '2015-07-05 15:02:04.881', NULL, NULL, 3, 'costumes');
INSERT INTO product_categories VALUES (213, 'Толстовки', '2015-07-05 15:03:07.337', '2015-07-05 15:03:07.335', NULL, NULL, 4, 'hoodies');


--
-- TOC entry 2363 (class 0 OID 0)
-- Dependencies: 180
-- Name: product_categories_seq; Type: SEQUENCE SET; Schema: public; Owner: prosport_db
--

SELECT pg_catalog.setval('product_categories_seq', 230, true);


--
-- TOC entry 2347 (class 0 OID 16431)
-- Dependencies: 181
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: prosport_db
--

INSERT INTO products VALUES (1, 'Футболка "Кот"', '2015-04-13 19:23:46.140736', '2015-04-13 19:23:46.140736', 'Р068', 'Юлия', 'Описание', 4, 'futbolka-kot', 'Краткое описание', 99, 9, NULL);


--
-- TOC entry 2348 (class 0 OID 16439)
-- Dependencies: 182
-- Data for Name: products_images; Type: TABLE DATA; Schema: public; Owner: prosport_db
--



--
-- TOC entry 2364 (class 0 OID 0)
-- Dependencies: 183
-- Name: products_seq; Type: SEQUENCE SET; Schema: public; Owner: prosport_db
--

SELECT pg_catalog.setval('products_seq', 41, true);


--
-- TOC entry 2350 (class 0 OID 16444)
-- Dependencies: 184
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: prosport_db
--

INSERT INTO users VALUES (7, 'valera.dt@gmail.com', NULL, '123456', '2015-04-13 19:23:45.498824', 'admin', NULL);
INSERT INTO users VALUES (8, 'lstghost@gmail.com', NULL, '123456', '2015-04-13 19:23:45.498824', 'admin', NULL);
INSERT INTO users VALUES (61, 'valera@lastochka-os.ru', NULL, '123456', '2015-04-19 16:42:32.107', 'admin', NULL);


--
-- TOC entry 2365 (class 0 OID 0)
-- Dependencies: 185
-- Name: users_seq; Type: SEQUENCE SET; Schema: public; Owner: prosport_db
--

SELECT pg_catalog.setval('users_seq', 62, false);


--
-- TOC entry 2201 (class 2606 OID 16457)
-- Name: images_pkey; Type: CONSTRAINT; Schema: public; Owner: prosport_db; Tablespace: 
--

ALTER TABLE ONLY images
    ADD CONSTRAINT images_pkey PRIMARY KEY (id);


--
-- TOC entry 2205 (class 2606 OID 16459)
-- Name: pk_pages; Type: CONSTRAINT; Schema: public; Owner: prosport_db; Tablespace: 
--

ALTER TABLE ONLY pages
    ADD CONSTRAINT pk_pages PRIMARY KEY (id);


--
-- TOC entry 2209 (class 2606 OID 16461)
-- Name: play_evolutions_pkey; Type: CONSTRAINT; Schema: public; Owner: prosport_db; Tablespace: 
--

ALTER TABLE ONLY play_evolutions
    ADD CONSTRAINT play_evolutions_pkey PRIMARY KEY (id);


--
-- TOC entry 2211 (class 2606 OID 16463)
-- Name: product_categories_pkey; Type: CONSTRAINT; Schema: public; Owner: prosport_db; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT product_categories_pkey PRIMARY KEY (id);


--
-- TOC entry 2215 (class 2606 OID 16465)
-- Name: products_pkey; Type: CONSTRAINT; Schema: public; Owner: prosport_db; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- TOC entry 2219 (class 2606 OID 16467)
-- Name: uk_68u3rm4tfmsixwa8nyfjg36xa; Type: CONSTRAINT; Schema: public; Owner: prosport_db; Tablespace: 
--

ALTER TABLE ONLY products_images
    ADD CONSTRAINT uk_68u3rm4tfmsixwa8nyfjg36xa UNIQUE (images_id);


--
-- TOC entry 2221 (class 2606 OID 16469)
-- Name: uk_6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: prosport_db; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- TOC entry 2213 (class 2606 OID 16471)
-- Name: uk_fl075bwasjwsxybk4x174befx; Type: CONSTRAINT; Schema: public; Owner: prosport_db; Tablespace: 
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT uk_fl075bwasjwsxybk4x174befx UNIQUE (name);


--
-- TOC entry 2217 (class 2606 OID 16473)
-- Name: uk_o61fmio5yukmmiqgnxf8pnavn; Type: CONSTRAINT; Schema: public; Owner: prosport_db; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT uk_o61fmio5yukmmiqgnxf8pnavn UNIQUE (name);


--
-- TOC entry 2203 (class 2606 OID 16475)
-- Name: uk_s1hn0flcjavvrkvbn1pd8dts2; Type: CONSTRAINT; Schema: public; Owner: prosport_db; Tablespace: 
--

ALTER TABLE ONLY images
    ADD CONSTRAINT uk_s1hn0flcjavvrkvbn1pd8dts2 UNIQUE (name);


--
-- TOC entry 2207 (class 2606 OID 16477)
-- Name: uq_pages_name; Type: CONSTRAINT; Schema: public; Owner: prosport_db; Tablespace: 
--

ALTER TABLE ONLY pages
    ADD CONSTRAINT uq_pages_name UNIQUE (name);


--
-- TOC entry 2223 (class 2606 OID 16479)
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: prosport_db; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 2225 (class 2606 OID 16480)
-- Name: fk_4yljd4n6x08xs6pc9xr3wqlu7; Type: FK CONSTRAINT; Schema: public; Owner: prosport_db
--

ALTER TABLE ONLY product_categories
    ADD CONSTRAINT fk_4yljd4n6x08xs6pc9xr3wqlu7 FOREIGN KEY (parent_id) REFERENCES product_categories(id);


--
-- TOC entry 2227 (class 2606 OID 16485)
-- Name: fk_68u3rm4tfmsixwa8nyfjg36xa; Type: FK CONSTRAINT; Schema: public; Owner: prosport_db
--

ALTER TABLE ONLY products_images
    ADD CONSTRAINT fk_68u3rm4tfmsixwa8nyfjg36xa FOREIGN KEY (images_id) REFERENCES images(id);


--
-- TOC entry 2228 (class 2606 OID 16490)
-- Name: fk_f6a0i56sqhypmj3bmshq7nr6c; Type: FK CONSTRAINT; Schema: public; Owner: prosport_db
--

ALTER TABLE ONLY products_images
    ADD CONSTRAINT fk_f6a0i56sqhypmj3bmshq7nr6c FOREIGN KEY (products_id) REFERENCES products(id);


--
-- TOC entry 2224 (class 2606 OID 16495)
-- Name: fk_jvinkc7xcd0x0pk49c1me6hb6; Type: FK CONSTRAINT; Schema: public; Owner: prosport_db
--

ALTER TABLE ONLY images
    ADD CONSTRAINT fk_jvinkc7xcd0x0pk49c1me6hb6 FOREIGN KEY (product_id) REFERENCES products(id);


--
-- TOC entry 2226 (class 2606 OID 16500)
-- Name: fk_of5oeawsy50x878ic9tyapdnv; Type: FK CONSTRAINT; Schema: public; Owner: prosport_db
--

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_of5oeawsy50x878ic9tyapdnv FOREIGN KEY (category_id) REFERENCES product_categories(id);


--
-- TOC entry 2358 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: pavelshvedov
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM pavelshvedov;
GRANT ALL ON SCHEMA public TO pavelshvedov;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-07-12 21:16:10 MSK

--
-- PostgreSQL database dump complete
--

