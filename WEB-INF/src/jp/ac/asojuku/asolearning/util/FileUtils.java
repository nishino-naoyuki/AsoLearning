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

}
