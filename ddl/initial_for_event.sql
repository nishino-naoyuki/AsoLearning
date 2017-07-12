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

INSERT INTO COURSE_MASTER(COURSE_ID,COURSE_NAME)VALUES(0,"チーム1A");
INSERT INTO COURSE_MASTER(COURSE_ID,COURSE_NAME)VALUES(1,"チーム2A");
INSERT INTO COURSE_MASTER(COURSE_ID,COURSE_NAME)VALUES(2,"チーム3A");
INSERT INTO COURSE_MASTER(COURSE_ID,COURSE_NAME)VALUES(3,"チーム4A");
INSERT INTO COURSE_MASTER(COURSE_ID,COURSE_NAME)VALUES(4,"チーム5A");
INSERT INTO COURSE_MASTER(COURSE_ID,COURSE_NAME)VALUES(5,"チーム1B");
INSERT INTO COURSE_MASTER(COURSE_ID,COURSE_NAME)VALUES(6,"チーム2B");
INSERT INTO COURSE_MASTER(COURSE_ID,COURSE_NAME)VALUES(7,"チーム3B");
INSERT INTO COURSE_MASTER(COURSE_ID,COURSE_NAME)VALUES(8,"チーム4B");
INSERT INTO COURSE_MASTER(COURSE_ID,COURSE_NAME)VALUES(9,"チーム5B");

INSERT INTO PUBLIC_STATUS_MASTER(STATUS_ID,STATUS_NAME)VALUES(0,"非公開");
INSERT INTO PUBLIC_STATUS_MASTER(STATUS_ID,STATUS_NAME)VALUES(1,"必須");
INSERT INTO PUBLIC_STATUS_MASTER(STATUS_ID,STATUS_NAME)VALUES(2,"任意");

INSERT INTO ROLE_MASTER(ROLE_ID,ROLE_NAME)VALUES(0,"学生");
INSERT INTO ROLE_MASTER(ROLE_ID,ROLE_NAME)VALUES(1,"教員");
INSERT INTO ROLE_MASTER(ROLE_ID,ROLE_NAME)VALUES(2,"管理者");

INSERT INTO USER_TBL(USER_ID,MAILADRESS,PASSWORD,NAME,NICK_NAME,ACCOUNT_EXPRY_DATE,PASSWORD_EXPIRYDATE,COURSE_ID,ROLE_ID,IS_FIRST_FLG,CERTIFY_ERR_CNT,IS_LOCK_FLG,ENTRY_DATE,UPDATE_DATE)
VALUES(NULL,"nishino@asojuku.ac.jp","cbf9365f1b0e0c9b6984baf4bb7d9416","123456789","IC8sCCKBJVYdfMPLTzl7Gg==",null,null,1,2,1,0,0,CURRENT_DATE,CURRENT_DATE);

INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(1,0,0,0,0,0,0,0,"hair_back001.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(2,0,1,0,0,0,0,0,"hair_back002.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(3,0,0,0,1,0,0,0,"hair_back003.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(4,0,0,0,0,100,0,0,"hair_back004.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(5,0,0,0,0,0,0,100,"hair_back005.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(6,0,0,0,0,50,0,0,"hair_back006.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(7,0,0,0,0,0,0,50,"hair_back007.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(8,0,0,0,0,0,0,0,"hair_back008.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(9,0,0,0,0,0,0,0,"hair_back009.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(10,0,0,0,0,0,0,0,"hair_back010.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(11,0,0,0,0,0,0,0,"default.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(12,1,0,0,0,0,0,0,"body001.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(13,1,0,0,0,0,0,0,"body002.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(14,1,0,0,0,0,0,0,"body003.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(15,1,0,0,0,0,0,0,"body004.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(16,1,0,0,0,0,0,0,"body005.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(17,1,0,0,0,0,0,0,"body006.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(18,1,0,0,0,0,0,0,"body007.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(19,1,0,0,0,0,0,0,"body008.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(20,1,0,0,0,0,0,0,"body009.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(21,1,0,0,0,0,0,0,"body010.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(22,2,0,0,0,0,0,0,"faceline001.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(23,2,0,0,0,0,0,0,"faceline002.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(24,2,0,0,0,0,0,0,"faceline003.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(25,2,0,0,0,0,0,0,"faceline004.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(26,2,0,0,0,0,0,0,"faceline005.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(27,2,0,0,0,0,0,0,"faceline006.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(28,2,0,0,0,0,0,0,"faceline007.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(29,2,0,0,0,0,0,0,"faceline008.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(30,2,0,0,0,0,0,0,"faceline009.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(31,2,0,0,0,0,0,0,"faceline010.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(32,2,0,0,0,0,0,0,"faceline011.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(33,2,0,0,0,0,0,0,"faceline012.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(34,3,0,0,0,0,0,0,"ear001.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(35,3,0,0,0,0,0,0,"ear002.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(36,3,0,0,0,0,0,0,"ear003.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(37,3,0,0,0,0,0,0,"ear004.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(38,4,0,0,0,0,0,0,"eyebrows001.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(39,4,0,0,0,0,0,0,"eyebrows002.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(40,4,0,0,0,0,0,0,"eyebrows003.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(41,4,0,0,0,0,0,0,"eyebrows004.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(42,4,0,0,0,0,0,0,"eyebrows005.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(43,4,0,0,0,0,0,0,"eyebrows006.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(44,4,0,0,0,0,0,0,"eyebrows007.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(45,4,0,0,0,0,0,0,"eyebrows008.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(46,4,0,0,0,0,0,0,"eyebrows009.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(47,5,0,0,0,0,0,0,"eyes001.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(48,5,0,0,0,0,0,0,"eyes002.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(49,5,0,0,0,0,0,0,"eyes003.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(50,5,0,0,0,0,0,0,"eyes004.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(51,5,0,0,0,0,0,0,"eyes005.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(52,5,0,0,0,0,0,0,"eyes006.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(53,5,0,0,0,0,0,0,"eyes007.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(54,5,0,0,0,0,0,0,"eyes008.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(55,5,0,0,0,0,0,0,"eyes009.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(56,5,0,0,0,0,0,0,"eyes010.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(57,5,0,0,0,0,0,0,"eyes011.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(58,5,0,0,0,0,0,0,"eyes012.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(59,5,0,0,0,0,0,0,"eyes013.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(60,5,0,0,0,0,0,0,"eyes014.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(61,5,0,0,0,0,0,0,"eyes015.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(62,5,0,0,0,0,0,0,"eyes016.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(63,5,0,0,0,0,0,0,"eyes017.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(64,5,0,0,0,0,0,0,"eyes018.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(65,5,0,0,0,0,0,0,"eyes019.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(66,5,0,0,0,0,0,0,"eyes020.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(67,6,0,0,0,0,0,0,"nose001.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(68,6,0,0,0,0,0,0,"nose002.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(69,6,0,0,0,0,0,0,"nose003.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(70,6,0,0,0,0,0,0,"nose004.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(71,6,0,0,0,0,0,0,"nose005.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(72,6,0,0,0,0,0,0,"nose006.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(73,6,0,0,0,0,0,0,"nose007.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(74,6,0,0,0,0,0,0,"nose008.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(75,6,0,0,0,0,0,0,"nose009.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(76,7,0,0,0,0,0,0,"mouth001.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(77,7,0,0,0,0,0,0,"mouth002.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(78,7,0,0,0,0,0,0,"mouth003.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(79,7,0,0,0,0,0,0,"mouth004.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(80,7,0,0,0,0,0,0,"mouth005.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(81,7,0,0,0,0,0,0,"mouth006.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(82,7,0,0,0,0,0,0,"mouth007.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(83,7,0,0,0,0,0,0,"mouth008.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(84,7,0,0,0,0,0,0,"mouth009.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(85,7,0,0,0,0,0,0,"mouth010.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(86,7,0,0,0,0,0,0,"mouth011.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(87,7,0,0,0,0,0,0,"mouth012.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(88,7,0,0,0,0,0,0,"mouth013.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(89,7,0,0,0,0,0,0,"mouth014.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(90,7,0,0,0,0,0,0,"mouth015.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(91,7,0,0,0,0,0,0,"mouth016.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(92,8,0,0,0,0,0,0,"hair001.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(93,8,0,0,0,0,0,0,"hair002.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(94,8,0,0,0,0,0,0,"hair003.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(95,8,0,0,0,0,0,0,"hair004.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(96,8,0,0,0,0,0,0,"hair005.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(97,8,0,0,0,0,0,0,"hair006.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(98,8,0,0,0,0,0,0,"hair007.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(99,8,0,0,0,0,0,0,"hair008.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(100,8,0,0,0,0,0,0,"hair009.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(101,8,0,0,0,0,0,0,"hair010.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(102,8,0,0,0,0,0,0,"hair011.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(103,8,0,0,0,0,0,0,"default.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(104,9,0,0,0,0,0,0,"default.png");
INSERT INTO AVATAR_MASTER(AVATAR_ID,KIND,ANS_COND_EASY,ANS_COND_NORMAL,ANS_COND_HARD,TOTAL_CND_EASY,TOTAL_CND_NORMAL,TOTAL_CND_HARD,FILE_NAME)VALUES(105,10,0,0,0,0,0,0,"default.png");
