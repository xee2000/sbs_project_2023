#DB생성

DROP DATABASE IF EXISTS sbs_proj_2023;
CREATE DATABASE sbs_proj_2023;
USE sbs_proj_2023;

CREATE TABLE article(
id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
regDate DATETIME NOT NULL,
updateDate DATETIME NOT NULL,
title CHAR(100) NOT NULL,
`body` TEXT NOT NULL
);

DROP TABLE article;

INSERT INTO article
SET regDate = NOW(),
updateDate  = NOW(),
title = '제목',
`body` = '내용'; 

INSERT INTO article
SET regDate = NOW(),
updateDate  = NOW(),
title = '제목1',
`body` = '내용1'; 
INSERT INTO article
SET regDate = NOW(),
updateDate  = NOW(),
title = '제목2',
`body` = '내용2'; 

SELECT *
FROM article;