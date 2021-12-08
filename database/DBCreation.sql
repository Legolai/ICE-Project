CREATE
DATABASE IF NOT EXISTS Favorite_Website_DB DEFAULT CHARSET = utf8mb4;
USE
Favorite_Website_DB;

SET
FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS Bookmark;
SET
FOREIGN_KEY_CHECKS = 1;

CREATE TABLE user
(
    user_id TINYINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
    #primary key (id)
);

CREATE TABLE Bookmark
(
    bookmark_id TINYINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    bookmark_name VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    status ENUM("Watching", "Completed", "On Hold", "Dropped", "Plan to Watch"),
    rating TINYINT NOT NULL,
);

CREATE TABLE Media
(
    media_id tinyint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    media_name varchar(255) not null
);
CREATE TABLE Genre
(
    genre_id tinyint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    genre_name varchar(255) not null
);
CREATE TABLE Tag
(
    tag_id tinyint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tag_name varchar(255) not null
);

CREATE TABLE Bookmark_Media
(
    bookmark_id tinyint NOT NULL,
    media_id tinyint NOT NULL,
    FOREIGN KEY (bookmark_id) REFERENCES Bookmark (bookmark_id),
    FOREIGN KEY (media_id) REFERENCES Media (media_id),
);
CREATE TABLE Bookmark_Genre
(
    bookmark_id tinyint NOT NULL,
    genre_id tinyint NOT NULL,
    FOREIGN KEY (bookmark_id) REFERENCES Bookmark (bookmark_id),
    FOREIGN KEY (genre_id) REFERENCES Media (genre_id),
);
CREATE TABLE Bookmark_Tag
(
    bookmark_id tinyint NOT NULL,
    tag_id tinyint NOT NULL,
    FOREIGN KEY (bookmark_id) REFERENCES Bookmark (bookmark_id),
    FOREIGN KEY (tag_id) REFERENCES Media (tag_id),
);




INSERT INTO user (email, username, password) VALUES ("Michael@email", "michael", "m123");
INSERT INTO user (email, username, password) VALUES ("Nicholai@email", "nicholai", "n123");

#INSERT INTO Bookmark (email, username, password) VALUES ("Nicholai@email", "nicholai", "n123");

#INSERT INTO user (email, username, password) VALUES ("Nicholai@email", "nicholai", "n123");



select * from user;
