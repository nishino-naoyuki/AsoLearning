/**
 *
 */
package jp.ac.asojuku.asolearning.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * 圧縮・解凍クラス
 * @author nishino
 *
 */
public class CompressUtils {

	/**
	 * 文字列を圧縮し、Base64した文字列を返す
	 *
	 * @param srcString
	 * @return
	 * @throws IOException
	 */
	public static String encode(String srcString) throws IOException{

        byte[] dataByte = srcString.getBytes();

        Deflater def = new Deflater();
        def.setLevel(Deflater.BEST_COMPRESSION);
        def.setInput(dataByte);
        def.finish();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(dataByte.length);
        byte[]buf = new byte[1024];
        while(!def.finished()) {
            int compByte = def.deflate(buf);
            byteArrayOutputStream.write(buf, 0, compByte);
        }
		byteArrayOutputStream.close();

        byte[] compData = byteArrayOutputStream.toByteArray();

        Base64 base = new Base64();
        String encoded = base.encodeToString(compData);

        return encoded;
	}

	/**
	 * 圧縮しBase64された文字列を復号し、平文を取得する
	 * @param base64String
	 * @return
	 * @throws DataFormatException
	 * @throws IOException
	 */
	public static String decode(String base64String) throws DataFormatException, IOException{

		if( StringUtils.isEmpty(base64String) ){
			return "";
		}

        Base64 base = new Base64();
        byte[] decoded = base.decode(base64String);

        Inflater decompresser = new Inflater();
        decompresser.setInput(decoded);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[]buf = new byte[1024];
        while (!decompresser.finished()) {
        	int compByte = decompresser.inflate(buf);
        	byteArrayOutputStream.write(buf, 0, compByte);
        }
        byteArrayOutputStream.close();

        byte[] compData = byteArrayOutputStream.toByteArray();

        return new String(compData,"UTF-8");
	}
}
