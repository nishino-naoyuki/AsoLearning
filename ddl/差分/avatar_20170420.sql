
CREATE TABLE AVATAR_MASTER
(
	AVATAR_ID int NOT NULL,
	-- ０~１０
	-- レイヤの後ろから若い番号
	-- ０：後ろ髪
	-- １：体
	-- ２：輪郭
	-- ３：耳
	-- ４：眉
	-- ５：目
	-- ６：鼻
	-- ７：口
	-- ８：前髪
	-- ９：アクセサリ１
	-- １０：アクセサリ２
	KIND int NOT NULL COMMENT '０~１０
レイヤの後ろから若い番号
０：後ろ髪
１：体
２：輪郭
３：耳
４：眉
５：目
６：鼻
７：口
８：前髪
９：アクセサリ１
１０：アクセサリ２',
	-- このアバターパーツが出現するために必要な解答数
	ANS_COND int DEFAULT 0 NOT NULL COMMENT 'このアバターパーツが出現するために必要な解答数',
	-- このアバターパーツが出現するために必要な点数の条件
	TOTAL_CND int DEFAULT 0 NOT NULL COMMENT 'このアバターパーツが出現するために必要な点数の条件',
	FILE_NAME varchar(300) NOT NULL,
	PRIMARY KEY (AVATAR_ID)
);

alter table USER_TBL add AVATAR_ID_CSV varchar(100);