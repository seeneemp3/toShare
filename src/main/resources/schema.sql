CREATE TABLE IF NOT EXISTS users (
id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
email varchar(320) NOT NULL,
user_name varchar(100) NOT NULL,
state varchar(50),
 CONSTRAINT unique_user_email UNIQUE (email));

CREATE TABLE IF NOT EXISTS requests (
request_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
description VARCHAR(300) NOT NULL,
requester_id BIGINT REFERENCES users (id) ON DELETE CASCADE,
created TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS items (
item_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
item_name VARCHAR(100) NOT NULL,
description VARCHAR(1000) NOT NULL,
available Boolean NOT NULL,
owner_id BIGINT REFERENCES users (id) ON DELETE CASCADE NOT NULL,
request_id BIGINT REFERENCES requests (request_id) ON DELETE CASCADE,
CONSTRAINT fk_items_to_users FOREIGN KEY(owner_id) REFERENCES users(id),
UNIQUE(item_id) );

CREATE TABLE IF NOT EXISTS bookings (
booking_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
start_date TIMESTAMP WITHOUT TIME ZONE,
end_date TIMESTAMP WITHOUT TIME ZONE,
item_id BIGINT REFERENCES items (item_id) ON DELETE CASCADE NOT NULL,
booker_id BIGINT REFERENCES users (id) ON DELETE CASCADE NOT NULL,
status VARCHAR (25)
);
CREATE TABLE IF NOT EXISTS comments (
id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
text VARCHAR(1024) NOT NULL,
item_id BIGINT NOT NULL,
author_id BIGINT NOT NULL,
created TIMESTAMP NOT NULL,
CONSTRAINT PK_COMMENTS PRIMARY KEY (id),
CONSTRAINT FK_COMMENT_FOR_ITEM FOREIGN KEY (item_id) REFERENCES items (item_id),
CONSTRAINT FK_COMMENT_FOR_USER FOREIGN KEY (author_id) REFERENCES users (id)
);
CREATE TABLE IF NOT EXISTS requests (
request_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
description VARCHAR(300) NOT NULL,
requester_id BIGINT REFERENCES users (id) ON DELETE CASCADE,
created TIMESTAMP WITHOUT TIME ZONE
);