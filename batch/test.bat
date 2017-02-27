@echo on
set INPUT_DIR = %1
set INPUT_FILE = %2
set OUTPUT_DIR = %3

cd %1

rem CCCC�ŕi������
"C:\Program Files\CCCC\cccc" %1/%2 --outdir=%3\cccc

rem �R���p�C��
javac -d %1\classes %1\%2 2>%3\error.txt

rem ���s����
cd %1\classes
java %4 %5 %6 %7 %8 %9 > %3\result.txt