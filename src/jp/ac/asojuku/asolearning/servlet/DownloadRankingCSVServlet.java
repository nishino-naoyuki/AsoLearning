/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.ac.asojuku.asolearning.config.AppSettingProperty;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * @author nishino
 *
 */
@WebServlet(name="DownloadRankingCSVServlet",urlPatterns={"/dlRankingCSV"})
public class DownloadRankingCSVServlet extends BaseServlet {

	private final String DISPNO = "01201";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}
	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.servlet.BaseServlet#doGetMain(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGetMain(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException, AsoLearningSystemErrException {


		//////////////////////////////////////
		//パラメータ取得
		String fname = req.getParameter("fname");
		String csvDir = AppSettingProperty.getInstance().getCsvDir();
		String fileEnc = AppSettingProperty.getInstance().getCsvFileEncode();
		/////////////////////////////////////
		//ファイルを読み込みクライアントへ帰す
		ServletOutputStream op = null;
		  DataInputStream in     = null;
		  try {
		   java.io.File file = new File(csvDir+"/"+fname);
		   if (file.exists()) {
			    //レスポンスヘッダーの作成
			    response.setContentType("application/octet-stream");
			    response.setContentLength((int) file.length());
			    //ファイル名の設定ISO_8859_1にエンコード
			    response.setHeader("Content-Disposition", "inline; filename=\""
			                       + new String(fname.getBytes(fileEnc), fileEnc) + "\"");
			    //ファイルの読み込み
			    int bytes = 0;
			    op = response.getOutputStream();
			    byte[] bbuf = new byte[1024];
			    in = new DataInputStream(new FileInputStream(file));
			    while ((in != null) && ((bytes = in.read(bbuf)) != -1)) {
			     op.write(bbuf, 0, bytes);
			    }
			    op.flush();
		   }
		  } catch (Exception e) {
			  e.printStackTrace();
		  } finally {
			   if(in != null) in.close();
			   if(op != null) op.close();
		  }

	}


}
