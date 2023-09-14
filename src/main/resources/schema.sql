CREATE TABLE IF NOT EXISTS users (
id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
email varchar(320) NOT NULL,
user_name varchar(100) NOT NULL,
state varchar(50),
 CONSTRAINT unique_user_email UNIQUE (email));

CREATE TABLE IF NOT EXISTS items (
item_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
item_name VARCHAR(100) NOT NULL,
description VARCHAR(1000) NOT NULL,
available Boolean NOT NULL,
owner_id BIGINT REFERENCES users (id) ON DELETE CASCADE NOT NULL,
CONSTRAINT fk_items_to_users FOREIGN KEY(owner_id) REFERENCES users(id),
UNIQUE(item_id) );