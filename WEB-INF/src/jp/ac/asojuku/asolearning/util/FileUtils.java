/**
 *
 */
package jp.ac.asojuku.asolearning.util;

import java.io.File;

/**
 * ファイルのユーティリティ
 * @author nishino
 *
 */
public class FileUtils {

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

}
