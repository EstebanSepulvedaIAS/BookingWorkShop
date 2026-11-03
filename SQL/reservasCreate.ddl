CREATE TABLE IF NOT EXISTS booking (id uuid NOT NULL, status varchar(255) NOT NULL, created_at timestamp NOT NULL, cancelled_at timestamp, offer_id uuid NOT NULL, user_id uuid NOT NULL, PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS offer (id uuid NOT NULL, quantity int4 NOT NULL, available_quantity int4 NOT NULL, "date" timestamp NOT NULL, state bool NOT NULL, title varchar(255) NOT NULL, PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS role (id uuid NOT NULL, name varchar(255) NOT NULL UNIQUE, PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS "User" (id uuid NOT NULL, name varchar(255) NOT NULL, email varchar(255) NOT NULL UNIQUE, password varchar(255) NOT NULL, PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS user_role (user_role_id uuid NOT NULL, user_id uuid NOT NULL, role_id uuid NOT NULL, PRIMARY KEY (user_role_id));
ALTER TABLE booking ADD CONSTRAINT FKBooking895610 FOREIGN KEY (offer_id) REFERENCES offer (id);
ALTER TABLE booking ADD CONSTRAINT FKBooking857470 FOREIGN KEY (user_id) REFERENCES "User" (id);
ALTER TABLE user_role ADD CONSTRAINT FKUser_Role39262 FOREIGN KEY (role_id) REFERENCES role (id);
ALTER TABLE user_role ADD CONSTRAINT FKUser_Role964785 FOREIGN KEY (user_id) REFERENCES "User" (id);

INSERT INTO role (id, name) VALUES ('6ab79acf-5090-4777-96bf-fa32a89537d6', 'ADMIN');
INSERT INTO role (id, name) VALUES ('d8aae8e2-5621-4c35-8999-3f66d011a657', 'GESTOR');
INSERT INTO role (id, name) VALUES ('c717547b-22e5-4f97-b837-cd930773ac3a', 'PARTICIPANTE');

INSERT INTO "User" (id, name, email, password) VALUES ('ed26a045-db4e-4243-984c-3c2f20883b96', 'Admin', 'admin@reserves.com', '$2a$12$lnnDujfTBmEsg7N5NvAP3es4BYwgflhJVhgcDDultLfd/9PNCyweC');

INSERT INTO user_role (user_role_id, user_id, role_id) SELECT '4f61a261-db51-42c1-ba33-39886ad5b22f', u.id, r.id FROM "User" u CROSS JOIN role r WHERE u.email = 'admin@reserves.com' and r.name = 'ADMIN';