@echo on
set INPUT_DIR = %1
set INPUT_FILE = %2
set OUTPUT_DIR = %3

cd %1

rem CCCCで品質検査
"C:\Program Files\CCCC\cccc" %1/%2 --outdir=%3\cccc

rem コンパイル
javac -d %1\classes %1\%2 2>%3\error.txt

rem 実行処理
cd %1\classes
java %4 > %3\result.txt