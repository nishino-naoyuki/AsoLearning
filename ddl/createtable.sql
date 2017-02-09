SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Indexes */

DROP INDEX IDX_RESULT_TOTAL_SCORE ON RESULT_TBL;
DROP INDEX IDX_USER_USERID_TASKID ON RESULT_TBL;
DROP INDEX IDX_TASK_CREATE_USER_ID ON TASK_TBL;
DROP INDEX IDX_TESTCASE_TASK_TASKCASE ON TESTCASE_TABLE;
DROP INDEX IDX_USER_COURSE_ID ON USER_TBL;
DROP INDEX IDX_USER_ROLE_ID ON USER_TBL;



/* Drop Tables */

DROP TABLE IF EXISTS HISTORY_TBL;
DROP TABLE IF EXISTS ACTION_MASTER;
DROP TABLE IF EXISTS TASK_PUBLIC_TBL;
DROP TABLE IF EXISTS RESULT_METRICS_TBL;
DROP TABLE IF EXISTS RESULT_TESTCASE_TBL;
DROP TABLE IF EXISTS RESULT_TBL;
DROP TABLE IF EXISTS USER_TBL;
DROP TABLE IF EXISTS COURSE_MASTER;
DROP TABLE IF EXISTS PUBLIC_STATUS_MASTER;
DROP TABLE IF EXISTS ROLE_MASTER;
DROP TABLE IF EXISTS TESTCASE_TABLE;
DROP TABLE IF EXISTS TASK_TBL;




/* Create Tables */

-- 操作マスター
CREATE TABLE ACTION_MASTER
(
	ACTION_ID int NOT NULL,
	ACTION_NAME varchar(200),
	PRIMARY KEY (ACTION_ID)
) COMMENT = '操作マスター';


CREATE TABLE COURSE_MASTER
(
	COURSE_ID int NOT NULL,
	-- 学科名
	COURSE_NAME varchar(100) NOT NULL COMMENT '学科名',
	PRIMARY KEY (COURSE_ID)
);


-- 操作履歴テーブル
-- 操作履歴のテーブルです
CREATE TABLE HISTORY_TBL
(
	HISTORY_ID int NOT NULL AUTO_INCREMENT,
	-- ユーザーID
	USER_ID int NOT NULL COMMENT 'ユーザーID',
	ACTION_ID int NOT NULL,
	ACTION_DATE datetime NOT NULL,
	PRIMARY KEY (HISTORY_ID)
) COMMENT = '操作履歴テーブル
操作履歴のテーブルです';


-- 公開ステータスマスタ
CREATE TABLE PUBLIC_STATUS_MASTER
(
	STATUS_ID int NOT NULL,
	STATUS_NAME varchar(100) NOT NULL,
	PRIMARY KEY (STATUS_ID)
) COMMENT = '公開ステータスマスタ';


-- メトリクス計測結果格納クラス
CREATE TABLE RESULT_METRICS_TBL
(
	RESULT_ID int NOT NULL,
	MAX_MVG int NOT NULL,
	AVR_MVG float NOT NULL,
	-- 1メソッドの最大行数
	MAX_LOC int NOT NULL COMMENT '1メソッドの最大行数',
	AVR_LOC float NOT NULL,
	MAX_MVG_SCORE float NOT NULL,
	MAX_LOC_SCORE float NOT NULL,
	AVR_MVG_SCORE float NOT NULL,
	AVR_LOC_SCORE float NOT NULL
) COMMENT = 'メトリクス計測結果格納クラス';


-- 結果テーブル
-- 判定を行った結果を格納するテーブル
CREATE TABLE RESULT_TBL
(
	RESULT_ID int NOT NULL AUTO_INCREMENT,
	-- ユーザーID
	USER_ID int NOT NULL COMMENT 'ユーザーID',
	TASK_ID int NOT NULL,
	TOTAL_SCORE float NOT NULL,
	PRIMARY KEY (RESULT_ID)
) COMMENT = '結果テーブル
判定を行った結果を格納するテーブル';


-- テストケースの結果格納テーブル
CREATE TABLE RESULT_TESTCASE_TBL
(
	RESULT_ID int NOT NULL,
	TESTCASE_ID int NOT NULL,
	-- 得点は配点を超えない
	SCORE int NOT NULL COMMENT '得点は配点を超えない',
	-- コンパイルエラーの時などに表示するメッセージ
	MESSAGE varchar(2000) COMMENT 'コンパイルエラーの時などに表示するメッセージ'
) COMMENT = 'テストケースの結果格納テーブル';


-- 役割マスタ
-- ロールによる画面へのアクセス権限は、Webアプリの設定ファイルにて行う
-- （いちいちDBにアクセスするとパ
CREATE TABLE ROLE_MASTER
(
	ROLE_ID int NOT NULL,
	ROLE_NAME varchar(100) NOT NULL,
	PRIMARY KEY (ROLE_ID)
) COMMENT = '役割マスタ
ロールによる画面へのアクセス権限は、Webアプリの設定ファイルにて行う
（いちいちDBにアクセスするとパ';


-- 課題公開設定テーブル
CREATE TABLE TASK_PUBLIC_TBL
(
	TASK_ID int NOT NULL,
	-- 公開/非公開の対象ID
	COURSE_ID int NOT NULL COMMENT '公開/非公開の対象ID',
	-- 0:非公開
	-- 1:公開で必須
	-- 2:非公開で必須
	STATUS_ID int NOT NULL COMMENT '0:非公開
1:公開で必須
2:非公開で必須',
	-- 公開開始の日時
	PUBLIC_DATETIME datetime COMMENT '公開開始の日時'
) COMMENT = '課題公開設定テーブル';


-- 課題を格納するテーブル
CREATE TABLE TASK_TBL
(
	TASK_ID int NOT NULL AUTO_INCREMENT,
	-- 課題名
	NAME varchar(200) NOT NULL COMMENT '課題名',
	TASK_QUESTION varchar(3000) NOT NULL,
	-- 問題作成者
	-- USERテーブルのUSER_ID
	CREATE_USER_ID int NOT NULL COMMENT '問題作成者
USERテーブルのUSER_ID',
	ENTRY_DATE timestamp NOT NULL,
	UPDATE_TIM timestamp NOT NULL,
	-- NULLの場合は締め切りなし
	termination_date date COMMENT 'NULLの場合は締め切りなし',
	PRIMARY KEY (TASK_ID)
) COMMENT = '課題を格納するテーブル';


-- テストケースを格納するテーブル
CREATE TABLE TESTCASE_TABLE
(
	TASK_ID int NOT NULL,
	TESTCASE_ID int NOT NULL,
	-- 配点
	ALLMOST_OF_MARKS int NOT NULL COMMENT '配点',
	OUTPUT_FILE_NAME varchar(2000) NOT NULL,
	-- 入力ファイルのパス
	INPUT_FILE_NAME varchar(2000) COMMENT '入力ファイルのパス'
) COMMENT = 'テストケースを格納するテーブル';


-- 利用者テーブル
-- ログイン可能な利用者は全てこのテーブルに登録される
CREATE TABLE USER_TBL
(
	-- ユーザーID
	USER_ID int NOT NULL AUTO_INCREMENT COMMENT 'ユーザーID',
	-- メールアドレス（ログインID）
	MAILADRESS varchar(256) NOT NULL COMMENT 'メールアドレス（ログインID）',
	-- パスワードのハッシュ値
	PASSWORD varchar(256) NOT NULL COMMENT 'パスワードのハッシュ値',
	-- 学生は学籍番号
	-- 職員は職員ID
	NAME varchar(100) NOT NULL COMMENT '学生は学籍番号
職員は職員ID',
	-- ニックネーム
	-- ※暗号化する
	NICK_NAME varchar(100) NOT NULL COMMENT 'ニックネーム
※暗号化する',
	-- アカウントの有効期限（指定日まで有効）
	-- NULLの場合は無期限
	ACCOUNT_EXPRY_DATE date COMMENT 'アカウントの有効期限（指定日まで有効）
NULLの場合は無期限',
	-- NULLは無期限
	PASSWORD_EXPIRYDATE date COMMENT 'NULLは無期限',
	-- ユーザーの所属学科
	-- ROLEが「先生」の場合も必要
	-- どこにも所属していない場合は
	-- 学科「その他」のIDが入る
	COURSE_ID int NOT NULL COMMENT 'ユーザーの所属学科
ROLEが「先生」の場合も必要
どこにも所属していない場合は
学科「その他」のIDが入る',
	-- 役割ID
	ROLE_ID int NOT NULL COMMENT '役割ID',
	-- 初めてのログインかどうかを判定するフラグ
	IS_FIRST_FLG int NOT NULL COMMENT '初めてのログインかどうかを判定するフラグ',
	-- 認証失敗時カウントアップされる
	-- 何度失敗してもアカウントをロックしない場合は、カウントアップしない
	CERTIFY_ERR_CNT int DEFAULT 0 NOT NULL COMMENT '認証失敗時カウントアップされる
何度失敗してもアカウントをロックしない場合は、カウントアップしない',
	IS_LOCK_FLG int DEFAULT 0 NOT NULL,
	ENTRY_DATE datetime NOT NULL,
	UPDATE_DATE datetime NOT NULL,
	PRIMARY KEY (USER_ID)
) COMMENT = '利用者テーブル
ログイン可能な利用者は全てこのテーブルに登録される';



/* Create Foreign Keys */

ALTER TABLE HISTORY_TBL
	ADD FOREIGN KEY (ACTION_ID)
	REFERENCES ACTION_MASTER (ACTION_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TASK_PUBLIC_TBL
	ADD FOREIGN KEY (COURSE_ID)
	REFERENCES COURSE_MASTER (COURSE_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE USER_TBL
	ADD FOREIGN KEY (COURSE_ID)
	REFERENCES COURSE_MASTER (COURSE_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TASK_PUBLIC_TBL
	ADD FOREIGN KEY (STATUS_ID)
	REFERENCES PUBLIC_STATUS_MASTER (STATUS_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE RESULT_METRICS_TBL
	ADD FOREIGN KEY (RESULT_ID)
	REFERENCES RESULT_TBL (RESULT_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE RESULT_TESTCASE_TBL
	ADD FOREIGN KEY (RESULT_ID)
	REFERENCES RESULT_TBL (RESULT_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE USER_TBL
	ADD FOREIGN KEY (ROLE_ID)
	REFERENCES ROLE_MASTER (ROLE_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE RESULT_TBL
	ADD FOREIGN KEY (TASK_ID)
	REFERENCES TASK_TBL (TASK_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TASK_PUBLIC_TBL
	ADD FOREIGN KEY (TASK_ID)
	REFERENCES TASK_TBL (TASK_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TESTCASE_TABLE
	ADD FOREIGN KEY (TASK_ID)
	REFERENCES TASK_TBL (TASK_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HISTORY_TBL
	ADD FOREIGN KEY (USER_ID)
	REFERENCES USER_TBL (USER_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE RESULT_TBL
	ADD FOREIGN KEY (USER_ID)
	REFERENCES USER_TBL (USER_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



/* Create Indexes */

CREATE INDEX IDX_RESULT_TOTAL_SCORE ON RESULT_TBL (TOTAL_SCORE ASC);
CREATE INDEX IDX_USER_USERID_TASKID ON RESULT_TBL (USER_ID ASC, TASK_ID ASC);
CREATE INDEX IDX_TASK_CREATE_USER_ID ON TASK_TBL (CREATE_USER_ID ASC);
CREATE INDEX IDX_TESTCASE_TASK_TASKCASE ON TESTCASE_TABLE (TASK_ID ASC, TESTCASE_ID ASC);
CREATE INDEX IDX_USER_COURSE_ID ON USER_TBL (COURSE_ID ASC);
CREATE INDEX IDX_USER_ROLE_ID ON USER_TBL (ROLE_ID ASC);



