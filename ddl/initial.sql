INSERT INTO ACTION_MASTER(ACTION_ID,ACTION_NAME)VALUES(0,"ログイン");
INSERT INTO ACTION_MASTER(ACTION_ID,ACTION_NAME)VALUES(1,"ログアウト");
INSERT INTO ACTION_MASTER(ACTION_ID,ACTION_NAME)VALUES(2,"課題判定");
INSERT INTO ACTION_MASTER(ACTION_ID,ACTION_NAME)VALUES(3,"課題作成");
INSERT INTO ACTION_MASTER(ACTION_ID,ACTION_NAME)VALUES(4,"課題編集");
INSERT INTO ACTION_MASTER(ACTION_ID,ACTION_NAME)VALUES(5,"ユーザー作成");
INSERT INTO ACTION_MASTER(ACTION_ID,ACTION_NAME)VALUES(6,"ユーザー編集");
INSERT INTO ACTION_MASTER(ACTION_ID,ACTION_NAME)VALUES(7,"CSV登録(作成)");
INSERT INTO ACTION_MASTER(ACTION_ID,ACTION_NAME)VALUES(8,"パスワード変更");
INSERT INTO ACTION_MASTER(ACTION_ID,ACTION_NAME)VALUES(9,"ニックネーム変更");
INSERT INTO ACTION_MASTER(ACTION_ID,ACTION_NAME)VALUES(10,"卒業処理");
INSERT INTO ACTION_MASTER(ACTION_ID,ACTION_NAME)VALUES(11,"留年処理");
INSERT INTO ACTION_MASTER(ACTION_ID,ACTION_NAME)VALUES(12,"退学処理");

INSERT INTO COURSE_MASTER(COURSE_ID,COURSE_NAME)VALUES(0,"情報システム科");
INSERT INTO COURSE_MASTER(COURSE_ID,COURSE_NAME)VALUES(1,"情報システム専攻科");
INSERT INTO COURSE_MASTER(COURSE_ID,COURSE_NAME)VALUES(2,"情報工学科");
INSERT INTO COURSE_MASTER(COURSE_ID,COURSE_NAME)VALUES(3,"組込みシステム科");
INSERT INTO COURSE_MASTER(COURSE_ID,COURSE_NAME)VALUES(4,"ネットワーク科");
INSERT INTO COURSE_MASTER(COURSE_ID,COURSE_NAME)VALUES(999,"その他");

INSERT INTO PUBLIC_STATUS_MASTER(STATUS_ID,STATUS_NAME)VALUES(0,"非公開");
INSERT INTO PUBLIC_STATUS_MASTER(STATUS_ID,STATUS_NAME)VALUES(1,"必須");
INSERT INTO PUBLIC_STATUS_MASTER(STATUS_ID,STATUS_NAME)VALUES(2,"任意");

INSERT INTO ROLE_MASTER(ROLE_ID,ROLE_NAME)VALUES(0,"学生");
INSERT INTO ROLE_MASTER(ROLE_ID,ROLE_NAME)VALUES(1,"教員");
INSERT INTO ROLE_MASTER(ROLE_ID,ROLE_NAME)VALUES(2,"管理者");

INSERT INTO USER_TBL(USER_ID,MAILADRESS,PASSWORD,NAME,NICK_NAME,ACCOUNT_EXPRY_DATE,PASSWORD_EXPIRYDATE,COURSE_ID,ROLE_ID,IS_FIRST_FLG,CERTIFY_ERR_CNT,IS_LOCK_FLG,ENTRY_DATE,UPDATE_DATE)
VALUES(NULL,"nishino@asojuku.ac.jp","cbf9365f1b0e0c9b6984baf4bb7d9416","123456789","IC8sCCKBJVYdfMPLTzl7Gg==",null,null,1,2,1,0,0,CURRENT_DATE,CURRENT_DATE);



