CREATE
DATABASE IF NOT EXISTS Favorite_Website_DB DEFAULT CHARSET = utf8mb4;
USE Favorite_Website_DB;

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Bookmark;
DROP TABLE IF EXISTS Media;
DROP TABLE IF EXISTS Genre;
DROP TABLE IF EXISTS Tag;
DROP TABLE IF EXISTS Bookmark_Media;
DROP TABLE IF EXISTS Bookmark_Genre;
DROP TABLE IF EXISTS Bookmark_Tag;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE User
(
    user_id TINYINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    firstname VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE Media
(
    media_id TINYINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    media_name varchar(255) not null UNIQUE
);

CREATE TABLE Bookmark
(
    bookmark_id TINYINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id TINYINT NOT NULL,
    bookmark_name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    media_name VARCHAR(255) NOT NULL,
    status ENUM("Watching", "Completed", "On Hold", "Dropped", "Plan to Watch"),
    rating TINYINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User (user_id),
    FOREIGN KEY (media_name) REFERENCES Media (media_name)
);

CREATE TABLE Genre
(
    genre_id TINYINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id TINYINT NOT NULL,
    genre_name varchar(255) not null,
    FOREIGN KEY (user_id) REFERENCES User (user_id)
);
CREATE TABLE Tag
(
    tag_id TINYINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id TINYINT NOT NULL,
    tag_name varchar(255) not null,
    FOREIGN KEY (user_id) REFERENCES User (user_id)
);

CREATE TABLE Bookmark_Genre
(
    bg_pk TINYINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    bookmark_id TINYINT NOT NULL,
    genre_id TINYINT NOT NULL,
    FOREIGN KEY (bookmark_id) REFERENCES Bookmark (bookmark_id),
    FOREIGN KEY (genre_id) REFERENCES Genre (genre_id)
);
CREATE TABLE Bookmark_Tag
(
    bt_pk TINYINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    bookmark_id TINYINT NOT NULL,
    tag_id TINYINT NOT NULL,
    FOREIGN KEY (bookmark_id) REFERENCES Bookmark (bookmark_id),
    FOREIGN KEY (tag_id) REFERENCES Tag (tag_id)
);


INSERT INTO User (email,firstname, surname, username, password) VALUES ("Michael@email","Mic","cahel", "michael", "m123");
INSERT INTO User (email,firstname, surname, username, password) VALUES ("Nicolai@email","Mic","cahel", "Nicolai", "n123");
INSERT INTO User (email,firstname, surname, username, password) VALUES ("test","test","test", "test", "test");

INSERT INTO Media (media_name) VALUES ("Movie");
INSERT INTO Media (media_name) VALUES ("Anime");
INSERT INTO Media (media_name) VALUES ("Song");
INSERT INTO Genre (user_id, genre_name) VALUES (2, "Sci-Fi");
INSERT INTO Tag (user_id, tag_name) VALUES (2, "Sick CGI");

INSERT INTO Bookmark (user_id, bookmark_name, description, url, media_name, status, rating) VALUES (2, "Avatar", "about blue people on another planet", "", "Movie", "Completed", 12);
INSERT INTO Bookmark (user_id, bookmark_name, description, url, media_name, status, rating) VALUES (2, "Fairy tail", "so much to complain about", "", "Anime", "Completed", 12);
INSERT INTO Bookmark (user_id, bookmark_name, description, url, media_name, status, rating) VALUES (2, "One piece", "awesome, the manga not anime", "", "Anime", "Watching", 12);
INSERT INTO Bookmark (user_id, bookmark_name, description, url, media_name, status, rating) VALUES (3, "Bleach", "eh can't remember", "", "Anime", "Completed", 12);
INSERT INTO Bookmark (user_id, bookmark_name, description, url, media_name, status, rating) VALUES (3, "Black Clover", "I, michael, haven't seen it yet", "", "Anime", "Completed", 12);
INSERT INTO Bookmark (user_id, bookmark_name, description, url, media_name, status, rating) VALUES (3, "Demon Slayer", "I, michael, haven't seen it yet", "", "Anime", "Watching", 12);


INSERT INTO Bookmark_Genre (bookmark_id, genre_id) VALUES (1, 1);
INSERT INTO Bookmark_Tag (bookmark_id, tag_id) VALUES (1, 1);


select * from User;
select * from Bookmark;
select * from Media;
select * from Genre;
select * from Tag;
select * from Bookmark_Genre;
select * from Bookmark_Tag;

