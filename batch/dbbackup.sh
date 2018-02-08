#!/bin/bash

#######################################
##
## データベースバックアップバッチ
##
#######################################

mysqldump -uroot -r /data/dbbk/aslearning`date "+%Y%m%d_%H%M%S"`.bakcup --single-transaction asolearning