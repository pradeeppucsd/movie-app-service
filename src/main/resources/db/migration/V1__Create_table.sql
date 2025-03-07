CREATE TABLE movie
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    release_date DATE,
    poster_url   VARCHAR(255),
    overview     TEXT,
    genres       VARCHAR(255),
    rating       DECIMAL(3, 1),
    runtime      INT,
    language     VARCHAR(50)
);

INSERT INTO movie (title, release_date, poster_url, overview, genres, rating, runtime, language)
VALUES ('Inception', '2010-07-16', 'inception.jpg', 'A mind-bending thriller', 'Sci-Fi, Action', 8.8, 148, 'English'),
       ('Interstellar', '2014-11-07', 'interstellar.jpg', 'A journey through space and time', 'Sci-Fi, Drama', 8.6, 169, 'English');
