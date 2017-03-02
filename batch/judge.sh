#!/bin/bash

#######################################
##
## 判定しょり実行シェル
##
#######################################

FNAME=$2


# 複雑度を判定
cccc $1/$2 --outdir=$3/cccc

if [${FNAME##*.}="java"] then
# コンパイルを行う
javac -d $1/classes $1/$2 2>$3/error.txt

# 実行する
cd $1/classes
java $4 $5 $6 $7 $8 $9 > $3/result.txt
else
gcc $1/$2 2>$3/error.txt

cd $1

./a.out $5 $6 $7 $8 $9 > $3/result.txt
fi