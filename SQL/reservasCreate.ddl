CREATE TABLE booking (id uuid NOT NULL, status varchar(255) NOT NULL, created_at timestamp NOT NULL, cancelled_at timestamp, offer_id uuid NOT NULL, user_id uuid NOT NULL, PRIMARY KEY (id));
CREATE TABLE offer (id uuid NOT NULL, quantity int4 NOT NULL, available_quantity int4 NOT NULL, "date" timestamp NOT NULL, state bool NOT NULL, title varchar(255) NOT NULL, PRIMARY KEY (id));
CREATE TABLE role (id uuid NOT NULL, name varchar(255) NOT NULL UNIQUE, PRIMARY KEY (id));
CREATE TABLE "User" (id uuid NOT NULL, name varchar(255) NOT NULL, email varchar(255) NOT NULL UNIQUE, password varchar(255) NOT NULL, PRIMARY KEY (id));
CREATE TABLE user_role (user_role_id uuid NOT NULL, user_id uuid NOT NULL, role_id uuid NOT NULL, PRIMARY KEY (user_role_id));
ALTER TABLE booking ADD CONSTRAINT FKBooking895610 FOREIGN KEY (offer_id) REFERENCES offer (id);
ALTER TABLE booking ADD CONSTRAINT FKBooking857470 FOREIGN KEY (user_id) REFERENCES "User" (id);
ALTER TABLE user_role ADD CONSTRAINT FKUser_Role39262 FOREIGN KEY (role_id) REFERENCES role (id);
ALTER TABLE user_role ADD CONSTRAINT FKUser_Role964785 FOREIGN KEY (user_id) REFERENCES "User" (id);

