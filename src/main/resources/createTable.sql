CREATE TABLE User(
                     id int auto_increment primary key,
                     userid varchar(255) not null unique key,
                     passwd varchar(255) not null,
                     email varchar(255) not null
);

DROP TABLE Board;

CREATE TABLE Board(
                      id bigint auto_increment primary key,
                      title varchar(255) not null,
                      content varchar(1000) not null,
                      writer varchar(255) not null,
                      category varchar(255) not null,
                      reg_date date not null
);

DROP TABLE ROOM;

CREATE TABLE Room (
                      id bigint auto_increment primary key,
                      name varchar(255) not null,
                      max_people int not null,
                      standard_people int not null,
                      count_of_rooms int not null,
                      description varchar(1000) not null,
                      amenity varchar(1000) not null
);

CREATE TABLE Amenity (
                         id bigint auto_increment primary key,
                         name varchar(255) not null
);

CREATE TABLE Amenity_In_Room(
                                room_id bigint,
                                amenity_id bigint,
                                FOREIGN KEY (room_id) REFERENCES Room (id),
                                FOREIGN KEY (amenity_id) REFERENCES Amenity (id)
);