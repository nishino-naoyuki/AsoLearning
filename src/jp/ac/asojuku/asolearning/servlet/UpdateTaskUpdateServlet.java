/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import jp.ac.asojuku.asolearning.bo.TaskBo;
import jp.ac.asojuku.asolearning.bo.impl.TaskBoImpl;
import jp.ac.asojuku.asolearning.config.AppSettingProperty;
import jp.ac.asojuku.asolearning.dto.TaskDto;
import jp.ac.asojuku.asolearning.dto.TaskTestCaseDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;
import jp.ac.asojuku.asolearning.util.FileUtils;

/**
 * 更新処理
 * @author nishino
 *
 */
@WebServlet(name="UpdateTaskUpdateServlet",urlPatterns={"/tc_updateTaskUpdate"})
public class UpdateTaskUpdateServlet extends BaseServlet {

	private final String DISPNO = "00603";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}

	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.servlet.BaseServlet#doPostMain(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPostMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		////////////////////////////////////
		//セッションから情報取得
		HttpSession session = req.getSession(false);

		TaskDto dto = null;

		if( session != null ){
			dto = (TaskDto)session.getAttribute(RequestConst.REQUEST_TASK_DTO);
		}

		//	セッションに情報が無い場合はシステムエラー
		if( dto == null ){
			throw new AsoLearningSystemErrException("セッションから課題情報が取得できません");
		}

		////////////////////////////////////
		//DBへセット
		TaskBo task = new TaskBoImpl();

		task.update(getUserInfoDtoFromSession(req), dto);

		////////////////////////////////////
		//ファイルを本番の場所へ移動
		moveTestcaseFile(dto.getTaskTestCaseDtoList());

		////////////////////////////////////
		//完了画面減りダイレクト（セッション情報はリダイレクト先で消す）
		resp.sendRedirect("tc_updateTaskFin");
	}


	/**
	 * テストケースで設定したファイルをコピーし
	 * テンポラリのフォルダを削除する
	 *
	 * @param testCaseList
	 * @throws IOException
	 * @throws AsoLearningSystemErrException
	 */
	private void moveTestcaseFile(List<TaskTestCaseDto> testCaseList) throws IOException, AsoLearningSystemErrException{

		String tempDir = AppSettingProperty.getInstance().getTempDirectory();
		String answerDir = AppSettingProperty.getInstance().getAnswerDirectory();
		String inputDir = AppSettingProperty.getInstance().getInputDirectory();

		for(TaskTestCaseDto dto : testCaseList){

			if( dto.isUpdateInputFileFlag() && StringUtils.isNotEmpty(dto.getInputFileName()) ){
				FileUtils.copy(tempDir+"/"+dto.getInputFileName(),
						inputDir+"/"+dto.getInputFileName());
			}

			if( dto.isUpdateOutputFileFlag() && StringUtils.isNotEmpty(dto.getOutputFileName()) ){
				FileUtils.copy(tempDir+"/"+dto.getOutputFileName(),
						answerDir+"/"+dto.getOutputFileName());
			}

		}

		//テンポラリのフォルダを削除
		FileUtils.delete(tempDir);

	}
}
