DROP TABLE USER_ACCESS_T;
DROP TABLE USER_T;

-- 회원
CREATE TABLE USER_T (
    USER_NO        NUMBER             NOT NULL,
    ID             VARCHAR2(40 BYTE)  NOT NULL UNIQUE,  -- ID 정규식에 반영. 40자 넘었는지 안 넘었는지
    PW             VARCHAR2(64 BYTE)  NOT NULL,         -- SHA-256 암호화 방식 사용 글자수에 상관없이 64자로 암호화된다.그래서 64BYTE
    NAME           VARCHAR2(40 BYTE),                  -- 이름
    GENDER         VARCHAR2(2 BYTE),                   -- M, F, NO
    EMAIL          VARCHAR2(100 BYTE) NOT NULL UNIQUE,        -- 이메일
    MOBILE         VARCHAR2(15 BYTE),                  -- 하이픈 제외(-) 후 저장
    BIRTHYEAR      VARCHAR2(4 BYTE),                   -- 출생년도(YYYY)
    BIRTHDATE      VARCHAR2(4 BYTE),                   -- 출생월일(MMDD)
    POSTCODE       VARCHAR2(5 BYTE),                   -- 우편번호
    ROAD_ADDRESS   VARCHAR2(100 BYTE),                 -- 도로명주소
    JIBUN_ADDRESS  VARCHAR2(100 BYTE),                 -- 지번주소
    DETAIL_ADDRESS VARCHAR2(100 BYTE),                 -- 상세주소
    EXTRA_ADDRESS  VARCHAR2(100 BYTE),                 -- 참고항목
    AGREE_CODE     NUMBER             NOT NULL,        -- 동의여부(0:필수, 1:위치, 2:이벤트, 3:위치+이벤트)
    JOINED_AT      DATE,                               -- 가입일자
    PW_MODIFIED_AT DATE,                               -- 비밀번호변경일
    AUTOLOGIN_ID   VARCHAR2(32 BYTE),                  -- 자동로그인할 때 사용하는 ID(SESSION_ID를 사용함)   -- 1. 자동로그인할 아이디 2. 자동로그인 만료일
    AUTOLOGIN_EXPIRED_AT DATE                          -- 자동로그인 ID
    
);

-- 회원 접속 기록(회원마다 마지막 로그인 날짜 1개만 기록)
CREATE TABLE USER_ACCESS_T (
    ID            VARCHAR2(40 BYTE) NOT NULL UNIQUE,   -- 로그인한 사용자 ID. UNIQUE달아준 이유는 성능때문에,속도 향상
    LAST_LOGIN_AT DATE                                 -- 마지막 로그인 날짜
);

ALTER TABLE USER_T
    ADD CONSTRAINT PK_USER
        PRIMARY KEY(USER_NO);
        
ALTER TABLE USER_ACCESS_T
    ADD CONSTRAINT FK_USER_ACCESS
        FOREIGN KEY(ID) REFERENCES USER_T(ID)
            ON DELETE CASCADE;
            
DROP SEQUENCE USER_SEQ;
CREATE SEQUENCE USER_SEQ NOCACHE;
    
-- 탈퇴 (탈퇴한 아이디로 재가입이 불가능)
DROP TABLE LEAVE_USER_T;
CREATE TABLE LEAVE_USER_T(
    ID        VARCHAR2(40 BYTE) NOT NULL UNIQUE,
    EMAIL          VARCHAR2(100 BYTE) NOT NULL UNIQUE,
    JOINED_AT DATE,     -- 가입일
    LEAVED_AT DATE      -- 탈퇴일
);

-- 하루에 한번씩 돌아가는 스케줄러를 짜서 오늘날짜하고 로그인한 날짜하고 비교해서 시간이 오래됐다 싶으면 이메일을 보내주는 서비스 구현 가능(카톡으로 보내주는 것도 API로 가능)
DROP TABLE SLEEP_USER_T;
CREATE TABLE SLEEP_USER_T(
    USER_NO        NUMBER             NOT NULL,
    ID             VARCHAR2(40 BYTE)  NOT NULL UNIQUE,  -- ID 정규식에 반영. 40자 넘었는지 안 넘었는지
    PW             VARCHAR2(64 BYTE)  NOT NULL,         -- SHA-256 암호화 방식 사용 글자수에 상관없이 64자로 암호화된다.그래서 64BYTE
    NAME           VARCHAR2(40 BYTE),                  -- 이름
    GENDER         VARCHAR2(2 BYTE),                   -- M, F, NO
    EMAIL          VARCHAR2(100 BYTE) NOT NULL,        -- 이메일
    MOBILE         VARCHAR2(15 BYTE),                  -- 하이픈 제외(-) 후 저장
    BIRTHYEAR      VARCHAR2(4 BYTE),                   -- 출생년도(YYYY)
    BIRTHDATE      VARCHAR2(4 BYTE),                   -- 출생월일(MMDD)
    POSTCODE       VARCHAR2(5 BYTE),                   -- 우편번호
    ROAD_ADDRESS   VARCHAR2(100 BYTE),                 -- 도로명주소
    JIBUN_ADDRESS  VARCHAR2(100 BYTE),                 -- 지번주소
    DETAIL_ADDRESS VARCHAR2(100 BYTE),                 -- 상세주소
    EXTRA_ADDRESS  VARCHAR2(100 BYTE),                 -- 참고항목
    AGREE_CODE     NUMBER             NOT NULL,        -- 동의여부(0:필수, 1:위치, 2:이벤트, 3:위치+이벤트)
    JOINED_AT      DATE,                               -- 가입일자
    PW_MODIFIED_AT DATE,                               -- 비밀번호변경일
    SLEPT_AT       DATE                                -- 휴면일
);

-- 간편 가입 서비스를 하고 싶은 사람들은 일단 서비스API문서를 먼저 봐야한다. 그래야 뭐가 더 필요한 지 알 수 있다. 지금 있는 정보로 네이버 로그인은 가능할 듯?

-- user1/1111, user2/2222, user3/3333
INSERT INTO USER_T VALUES(USER_SEQ.NEXTVAL, 'user1', ' FFE1ABD1A 8215353C233D6E0 9613E95EEC4253832A761AF28FF37AC5A15 C', '고길동', 'M', 'gildong@naver.com', '01011111111', '2000', '0101', '12345', '서울시', '서울시', '가산동', '가산동', 0, TO_DATE('20220101', 'YYYYMMDD'), NULL, NULL, NULL);
INSERT INTO USER_T VALUES(USER_SEQ.NEXTVAL, 'user2', 'EDEE29F882543B956620B26D EE0E7E950399B1C4222F5DE 5E06425B4C995E9', '홍길순', 'F', 'gilsoon@naver.com', '01022222222', '2000', '0101', '12345', '서울시', '서울시', '가산동', '가산동', 0, TO_DATE('20220101', 'YYYYMMDD'), NULL, NULL, NULL);
INSERT INTO USER_T VALUES(USER_SEQ.NEXTVAL, 'user3', '318AEE3FED8C9D 4 D35A7FC1FA776FB31303833AA2DE885354DDF3D44D8FB69', '최자두', 'F', 'jadoo@naver.com', '01033333333', '2000', '0101', '12345', '서울시', '서울시', '가산동', '가산동', 0, TO_DATE('20220101', 'YYYYMMDD'), NULL, NULL, NULL);

INSERT INTO USER_ACCESS_T VALUES('user1', TO_DATE('20230501', 'YYYYMMDD'));  -- user1 정상 회원
INSERT INTO USER_ACCESS_T VALUES('user2', TO_DATE('20220501', 'YYYYMMDD'));  -- user2 휴면 대상(12개월 이상 로그인 이력 없음)
-- user3 휴면 대상(12개월 이상 로그인 이력이 아예 없음)

COMMIT;




