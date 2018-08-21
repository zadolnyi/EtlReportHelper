
CREATE DATABASE source_db;
CREATE SCHEMA source_schema;
CREATE TABLE source_schema.source_table (
  id serial PRIMARY KEY NOT NULL,
  column_2 varchar,
  column_3 varchar,
  column_4 varchar,
  column_5 int
);
INSERT INTO "source_schema"."source_table" ("id", "column_2", "column_3", "column_4", "column_5")
VALUES (DEFAULT, 'строка 1 колонка 2', 'строка 1 колонка 3', 'строка 1 колонка 4', 15);
INSERT INTO "source_schema"."source_table" ("id", "column_2", "column_3", "column_4", "column_5")
VALUES (DEFAULT, 'строка 2 колонка 2', 'строка 2 колонка 3', 'строка 2 колонка 4', 25);
INSERT INTO "source_schema"."source_table" ("id", "column_2", "column_3", "column_4", "column_5")
  VALUES (DEFAULT, 'строка 3 колонка 2', 'строка 3 колонка 3', 'строка 3 колонка 4', 35);
INSERT INTO "source_schema"."source_table" ("id", "column_2", "column_3", "column_4", "column_5")
  VALUES (DEFAULT, 'строка 4 колонка 2', 'строка 4 колонка 3', 'строка 4 колонка 4', 45);
INSERT INTO "source_schema"."source_table" ("id", "column_2", "column_3", "column_4", "column_5")
  VALUES (DEFAULT, 'строка 5 колонка 2', 'строка 5 колонка 3', 'строка 5 колонка 4', 55);
INSERT INTO "source_schema"."source_table" ("id", "column_2", "column_3", "column_4", "column_5")
  VALUES (DEFAULT, 'строка 6 колонка 2', 'строка 6 колонка 3', 'строка 6 колонка 4', 65);
INSERT INTO "source_schema"."source_table" ("id", "column_2", "column_3", "column_4", "column_5")
  VALUES (DEFAULT, 'строка 7 колонка 2', 'строка 7 колонка 3', 'строка 7 колонка 4', 75);
INSERT INTO "source_schema"."source_table" ("id", "column_2", "column_3", "column_4", "column_5")
  VALUES (DEFAULT, 'строка 8 колонка 2', 'строка 8 колонка 3', 'строка 8 колонка 4', 85);
INSERT INTO "source_schema"."source_table" ("id", "column_2", "column_3", "column_4", "column_5")
  VALUES (DEFAULT, 'строка 9 колонка 2', 'строка 9 колонка 3', 'строка 9 колонка 4', 95);
commit;


CREATE DATABASE target_db;
CREATE SCHEMA target_schema;
CREATE TABLE target_schema.reports (
  id serial NOT NULL
    constraint reports_pkey
    PRIMARY KEY,
  name varchar not null,
  description text,
  class_name varchar not null,
  report_table_name varchar not null,
  sql_source_query text,
  sql_insert text,
  sql_create_table text not null,
  period integer,
  reset_rebuild_counter_date varchar,
  rebuild_count integer,
  recreate boolean
);
INSERT INTO target_schema.reports (id, name, description, class_name, report_table_name, sql_source_query, sql_insert, sql_create_table, period, reset_rebuild_counter_date, rebuild_count, recreate) VALUES (1, 'Отчёт1', 'Отчёт1', 'Report1', 'report1', 'SELECT id, column_2, column_3, column_4 from source_schema.source_table WHERE id < 5', '', 'CREATE TABLE target_schema.report1 (id serial not null constraint report1_pkey primary key, column_2 varchar, column_3 varchar, column_4 varchar)', '130', '',  0,  false);
INSERT INTO target_schema.reports (id, name, description, class_name, report_table_name, sql_source_query, sql_insert, sql_create_table, period, reset_rebuild_counter_date, rebuild_count, recreate) VALUES (2, 'Отчёт2', 'Отчёт2', 'Report2', 'report2', 'SELECT id, column_3, column_4, column_5 from source_schema.source_table WHERE id > 5', '', 'CREATE TABLE target_schema.report2 (id serial not null constraint report2_pkey primary key, column_3 varchar, column_4 varchar, column_5 int)', '30', '',  0, false);
INSERT INTO target_schema.reports (
    id, name, description, class_name, report_table_name, sql_source_query, sql_insert, sql_create_table, period, reset_rebuild_counter_date, rebuild_count, recreate)
VALUES (3,
        'Отчёт3', 'Отчёт3', 'Report3', 'report3',
        'SELECT column_2, column_5 from source_schema.source_table WHERE id > 5',
        'INSERT INTO target_schema.report3(column_2, column_5) VALUES (?, ?)',
        'CREATE TABLE target_schema.report3 (id serial not null constraint report3_pkey primary key, column_2 varchar, column_5 int)',
        '30', '',  0, true);
commit;
