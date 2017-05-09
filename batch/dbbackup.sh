#!/bin/bash

#######################################
##
## データベースバックアップバッチ
##
#######################################

mysqldump -uroot -r /home/ec2-user/dbbk/aslearning`date "+%Y%m%d_%H%M%S"`.bakcup --single-transaction asolearning