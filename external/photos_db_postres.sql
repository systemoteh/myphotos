--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.14
-- Dumped by pg_dump version 9.5.14

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: update_rating(); Type: FUNCTION; Schema: public; Owner: myphotos
--

CREATE FUNCTION public.update_rating() RETURNS void
  LANGUAGE plpgsql
AS
$$

DECLARE

BEGIN

  UPDATE profile
  SET rating=stat.rating
  FROM (SELECT profile_id, sum(views * 1 + downloads * 100) as rating FROM photo GROUP BY profile_id) AS stat

  WHERE profile.id = stat.profile_id;

END;

$$;


ALTER FUNCTION public.update_rating() OWNER TO myphotos;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: access_token; Type: TABLE; Schema: public; Owner: myphotos
--

CREATE TABLE public.access_token
(
  token      character varying                            NOT NULL,
  profile_id bigint                                       NOT NULL,
  created    timestamp(0) without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.access_token
  OWNER TO myphotos;

--
-- Name: photo; Type: TABLE; Schema: public; Owner: myphotos
--

CREATE TABLE public.photo
(
  id           bigint                                       NOT NULL,
  profile_id   bigint                                       NOT NULL,
  small_url    character varying(255)                       NOT NULL,
  large_url    character varying(255)                       NOT NULL,
  original_url character varying(255)                       NOT NULL,
  views        bigint                         DEFAULT 0     NOT NULL,
  downloads    bigint                         DEFAULT 0     NOT NULL,
  created      timestamp(0) without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.photo
  OWNER TO myphotos;

--
-- Name: photo_seq; Type: SEQUENCE; Schema: public; Owner: myphotos
--

CREATE SEQUENCE public.photo_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;


ALTER TABLE public.photo_seq
  OWNER TO myphotos;

--
-- Name: profile; Type: TABLE; Schema: public; Owner: myphotos
--

CREATE TABLE public.profile
(
  id          bigint                                       NOT NULL,
  uid         character varying(255)                       NOT NULL,
  email       character varying(100)                       NOT NULL,
  first_name  character varying(50)                        NOT NULL,
  last_name   character varying(50)                        NOT NULL,
  avatar_url  character varying(255)                       NOT NULL,
  job_title   character varying(100)                       NOT NULL,
  location    character varying(100)                       NOT NULL,
  photo_count integer                        DEFAULT 0     NOT NULL,
  created     timestamp(0) without time zone DEFAULT now() NOT NULL,
  rating      integer                        DEFAULT 0     NOT NULL
);


ALTER TABLE public.profile
  OWNER TO myphotos;

--
-- Name: profile_seq; Type: SEQUENCE; Schema: public; Owner: myphotos
--

CREATE SEQUENCE public.profile_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;


ALTER TABLE public.profile_seq
  OWNER TO myphotos;

--
-- Data for Name: access_token; Type: TABLE DATA; Schema: public; Owner: myphotos
--


--
-- Data for Name: photo; Type: TABLE DATA; Schema: public; Owner: myphotos
--


--
-- Name: photo_seq; Type: SEQUENCE SET; Schema: public; Owner: myphotos
--

SELECT pg_catalog.setval('public.photo_seq', 1, false);


--
-- Data for Name: profile; Type: TABLE DATA; Schema: public; Owner: myphotos
--


--
-- Name: profile_seq; Type: SEQUENCE SET; Schema: public; Owner: myphotos
--

SELECT pg_catalog.setval('public.profile_seq', 1, false);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

