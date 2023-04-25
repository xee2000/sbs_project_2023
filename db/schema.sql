CREATE TABLE `member` (
    id  INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(20) NOT NULL,
    authLevel SMALLINT(2) UNSIGNED DEFAULT 3 COMMENT '(3=일반, 7=관리자)',
    loginPw CHAR(60) NOT NULL,
    `name` CHAR(20) NOT NULL,
    nickname CHAR(20) NOT NULL,
    cellphoneNo CHAR(20) NOT NULL,
    email CHAR(50) NOT NULL,
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT  0 COMMENT  '탈퇴여부',
    delDate DATETIME COMMENT '탈퇴날짜'
);

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user1',
loginPw = 'user1',
`name` = '사용자1',
nickname = '사용자1',
cellphoneNo = '01011111111',
email = 'user1@gmail.com';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user2',
loginPw = 'user2',
`name` = '사용자2',
nickname = '사용자2',
cellphoneNo = '01011111111',
email = 'user2@gmail.com';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
authLevel = '7',
loginPw = 'admin',
`name` = '관리자',
nickname = '관리자',
cellphoneNo = '01011111111',
email = 'admin@gmail.com';

SELECT * FROM `member`;

#article테이블에 컬럼추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER `updateDate`;

UPDATE article
SET memberId = 2
WHERE memberId = 0;

SELECT * FROM article;

#게시판 테이블 생성
CREATE TABLE board(
id INT(10) UNSIGNED NOT NULL KEY AUTO_INCREMENT PRIMARY KEY,
regDate DATETIME NOT NULL,
updateDate DATETIME NOT NULL,
`code` CHAR(50) NOT NULL UNIQUE COMMENT 'notice(공지사항), free1(자유게시판1), free2(자유게시판2,...',
`name` CHAR(50) NOT NULL UNIQUE COMMENT '게시판 이름',
delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT  '삭제여부(0=탈퇴전, 1=탈퇴)',
delDate DATETIME COMMENT '삭제날짜'
);


INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'notice',
`name` = '공지사항';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'free1',
`name` = '자유';

SHOW TABLES

SELECT * FROM article
SELECT * FROM board WHERE id =1;
SELECT * FROM board WHERE id =2;

#게시판 테이블에 boardId 컬럼 추가
ALTER TABLE article ADD COLUMN boardId INT(10) UNSIGNED NOT NULL AFTER `memberId`;

#1, 2번 게시물을 공지사항 게시물로 지정
UPDATE article
SET boardId =1
WHERE id IN(1,2);
#3번 게시물을 자유게시판 게시물로 지정
UPDATE article
SET boardId =2
WHERE id IN(3);

#게시물 개수 늘리기
INSERT INTO article
(
    regDate, updateDate, memberId, boardId, title, `body`
)
SELECT NOW(), NOW(), FLOOR(RAND() *2) + 1, FLOOR(RAND() *2) + 1, CONCAT('제목_', RAND()), CONCAT('내용_', RAND())
FROM article

#게시물 컬럼추가
ALTER TABLE article
ADD COLUMN hitcount INT(10) UNSIGNED NOT NULL DEFAULT 0;

# like 테이블 생성		      
CREATE TABLE reactionPoint(
id INT(10) UNSIGNED NOT NULL KEY AUTO_INCREMENT PRIMARY KEY,
regDate DATETIME NOT NULL,
updateDate DATETIME NOT NULL,
memberId INT(10) UNSIGNED NOT NULL,
relTypeCode CHAR(30) NOT NULL COMMENT '관련데이터타입코드',
relId INT(10) UNSIGNED NOT NULL COMMENT '관련데이터번호',
`point`SMALLINT(2) NOT NULL 
);

#리액션포인트 테스트 데이터
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 1,
`point` = -1;
	
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 2,
`point` = 1;

INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 2,
`point` = -1;

INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 3,
relTypeCode = 'article',
relId = 1,
`point` = 1;
	