#!/bin/bash

#######################################
##
## 判定しょり実行シェル
##
#######################################

FNAME=$2


# 複雑度を判定
cccc $1/$2 --outdir=$3/cccc

if [ ${FNAME##*.} = "java" ]; then
  # コンパイルを行う
  javac -d $1/classes $1/$2 2>$3/error.txt

  # 実行する
  cd $1/classes
  java $4 $5 $6 $7 $8 $9 > $3/result.txt &
  
  # PIDを取得
  pid=$!
  
  # 100ms待つ
  usleep 100000
  time=0
  
  while true
  do
    #生存確認
    isAlive=`ps -ef | grep "$pid" | grep -v grep | grep -v srvchk | wc -l`
  
    echo $isAlive
    echo $time
  
    if [ $isAlive = 1 ]; then
      if [ $time = ${10} ]; then
        #タイムアウトに達したのでエラーにする
        kill $pid
        exit 1
      fi
    else
      exit 0
    fi
    # 1秒待つ
    usleep 1000000
    time=$(( time + 1 ))
  done
  
else

  #排他処理
  _LOCK_FILE=$0.lock
  exec {F_LOCK}>>$_LOCK_FILE

  #排他（タイムアウト10秒）
  flock -w 10 ${F_LOCK}

  # $?は特殊変数（直前の実行結果が入る）
  if [ $? -ne 1 ]; then
    gcc $1/$2 2>$3/error.txt

    cp a.out $1
    cd $1

    ./a.out $5 $6 $7 $8 $9 > $3/result.txt

echo $4.xml
    #結果ファイル名を変更する
    cd $3/cccc
    mv ./anonymous.xml ./$4.xml

	#flock -u ${F_LOCK}
  else
    echo "haita!"
  fi

fi