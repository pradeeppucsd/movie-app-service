ALTER TABLE movie ADD popularity INT DEFAULT(1);

UPDATE movie
SET popularity = FLOOR(1 + (RAND() * 100));
