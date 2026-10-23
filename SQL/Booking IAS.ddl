CREATE TABLE Booking (id uuid NOT NULL, status varchar(255) NOT NULL, created_at timestamp NOT NULL, cancelled_at timestamp, offer_id uuid NOT NULL, user_id uuid NOT NULL, PRIMARY KEY (id));
CREATE TABLE Offer (id uuid NOT NULL, quantity int4 NOT NULL, "date" timestamp NOT NULL, state bool NOT NULL, title varchar(255) NOT NULL, PRIMARY KEY (id));
CREATE TABLE Role (id uuid NOT NULL, name varchar(255) NOT NULL UNIQUE, PRIMARY KEY (id));
CREATE TABLE "User" (id uuid NOT NULL, name varchar(255) NOT NULL, email varchar(255) NOT NULL UNIQUE, password varchar(255) NOT NULL, PRIMARY KEY (id));
CREATE TABLE User_Role (user_role_id uuid NOT NULL, user_id uuid NOT NULL, role_id uuid NOT NULL, PRIMARY KEY (user_role_id));
ALTER TABLE Booking ADD CONSTRAINT FKBooking895610 FOREIGN KEY (offer_id) REFERENCES Offer (id);
ALTER TABLE Booking ADD CONSTRAINT FKBooking857470 FOREIGN KEY (user_id) REFERENCES "User" (id);
ALTER TABLE User_Role ADD CONSTRAINT FKUser_Role39262 FOREIGN KEY (role_id) REFERENCES Role (id);
ALTER TABLE User_Role ADD CONSTRAINT FKUser_Role964785 FOREIGN KEY (user_id) REFERENCES "User" (id);

