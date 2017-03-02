/**
 *
 */
package jp.ac.asojuku.asolearning.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * ファイルのユーティリティ
 * @author nishino
 *
 */
public class FileUtils {

	/**
	 * ファイルから1行ごとのデータをリストとして読み込む
	 * @param filePath
	 * @return
	 */
	public static List<String> readLine(String filePath) {
		List<String> lineList = new ArrayList<>();

		if( StringUtils.isEmpty(filePath) ){
			return lineList;
		}
	    FileReader fr = null;
	    BufferedReader br = null;
	    try {
	        fr = new FileReader(filePath);
	        br = new BufferedReader(fr);

	        String line;
	        while ((line = br.readLine()) != null) {
	        	lineList.add(line);
	        }
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if(br != null )
	            	br.close();
	            if(fr != null)
	            	fr.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    return lineList;
	}

	/**
	 * ファイル名が指定した拡張子かどうかを判断する
	 * @param file
	 * @param ext
	 * @return
	 */
	public static boolean checkFileExt(File file,String ext) {
		if( file == null ){
			return false;
		}

		return file.isFile() && file.canRead() && file.getPath().endsWith("."+ext);
	}

	/**
	 * 拡張子を取得する
	 * @param fileName
	 * @return
	 */
	public static String getExt(String fileName){

	    if (fileName == null)
	        return null;
	    int point = fileName.lastIndexOf(".");
	    if(point == -1){
	    	return "";
	    }

	    return fileName.substring(point+1);
	}
	/**
	 * ファイル名が指定した拡張子かどうかを判断する
	 * @param file
	 * @param ext
	 * @return
	 */
	public static boolean checkFileExt(String filename,String ext) {

		File file = new File(filename);

		return file.isFile() && file.canRead() && file.getPath().endsWith("."+ext);
	}

	/**
	 * ディレクトリ作成
	 * @param dir
	 */
	public static void makeDir(String dir){
		//Fileオブジェクトを生成する
        File f = new File(dir);

        if (!f.exists()) {
            //フォルダ作成実行
            f.mkdirs();
        }
	}
	/**
	 * ファイル名から拡張子を取り除いた名前を返します。
	 * @param fileName ファイル名
	 * @return ファイル名
	 */
	public static String getPreffix(String fileName) {
	    if (fileName == null)
	        return null;
	    int point = fileName.lastIndexOf(".");
	    if (point != -1) {
	        return fileName.substring(0, point);
	    }
	    return fileName;
	}

	/**
	 * ファイルを比較する
	 * @param fileA
	 * @param fileB
	 * @return
	 */
	public static boolean fileCompare(String fileA, String fileB) {
	    boolean bRet = false;

        List<String> listA = readLine(fileA);
        List<String> listB = readLine(fileB);
        String[] arrayA = listA.toArray(new String[0]);
        String[] arrayB = listB.toArray(new String[0]);

        bRet = Arrays.equals(arrayA, arrayB);

	    return bRet;
	}

	/**
	 * 削除
	 * @param path
	 */
	public static void delete(String path){
		File file = new File(path);

		delete(file);
	}
	public static void delete(File f){

		/*
         * ファイルまたはディレクトリが存在しない場合は何もしない
         */
        if(f.exists() == false) {
            return;
        }

        if(f.isFile()) {
            /*
             * ファイルの場合は削除する
             */
            f.delete();

        } else if(f.isDirectory()){
            /*
             * ディレクトリの場合は、すべてのファイルを削除する
             */

            /*
             * 対象ディレクトリ内のファイルおよびディレクトリの一覧を取得
             */
            File[] files = f.listFiles();

            /*
             * ファイルおよびディレクトリをすべて削除
             */
            for(int i=0; i<files.length; i++) {
                /*
                 * 自身をコールし、再帰的に削除する
                 */
                delete( files[i] );
            }
            /*
             * 自ディレクトリを削除する
             */
            f.delete();
        }

	}

	/**
	 * コピーする
	 *
	 * @param srcPath
	 * @param dstPath
	 * @throws IOException
	 */
	public static void copy(String srcPath,String dstPath) throws IOException{

		File f = new File(dstPath);

		f.getParentFile().mkdirs();

		Files.copy(
				new File(srcPath).toPath(),
				new File(dstPath).toPath());
	}

	/**
	 * パスからファイル名を取得する
	 * @param path
	 * @return
	 */
	public static String getFileNameFromPath(String path){

		File f = new File(path);

		return f.getName();
	}
}
