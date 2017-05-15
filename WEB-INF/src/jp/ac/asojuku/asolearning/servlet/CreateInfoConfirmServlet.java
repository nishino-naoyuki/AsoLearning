/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.CourseBo;
import jp.ac.asojuku.asolearning.bo.impl.CourseBoImpl;
import jp.ac.asojuku.asolearning.dto.CourseDto;
import jp.ac.asojuku.asolearning.dto.InfoPublicDto;
import jp.ac.asojuku.asolearning.dto.InfomationDto;
import jp.ac.asojuku.asolearning.err.ActionErrors;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * @author nishino
 *
 */
@WebServlet(name="CreateInfoConfirmServlet",urlPatterns={"/tc_confirmInfo"})
public class CreateInfoConfirmServlet extends BaseServlet{

	Logger logger = LoggerFactory.getLogger(CreateInfoConfirmServlet.class);
	private final String DISPNO = "01602";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}

	private ActionErrors errors;
	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.servlet.BaseServlet#doGetMain(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPostMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		errors = new ActionErrors();
		try{

			//学科データを取得
			CourseBo coursBo = new CourseBoImpl();

			List<CourseDto> courselist = coursBo.getCourseAllList();

			//パラメータの取得
			InfomationDto dto = getParams(req);

			//公開情報をセット
			List<InfoPublicDto> infoPublicList = getInfoPublicDtoList(req,courselist);

			//dtoにセット
			dto.setInfoPublicList(infoPublicList);
/*
			//エラーチェック
			checkError(dto);

			RequestDispatcher rd = null;
			if( errors.isHasErr() ){
				//エラーがある場合は、リクエストにセット
				setToRequest(req,dto,courselist);
				//画面遷移
				rd = req.getRequestDispatcher("view/tc_createTask.jsp");
			}else{
				//エラーが無い場合はセッションにセット
				setToSession(req,dto);
				rd = req.getRequestDispatcher("view/tc_creatTaskConfirm.jsp");
			}
			rd.forward(req, resp);
*/

		} catch (IllegalStateException e) {
			logger.error("IllegalStateException：",e);
		} catch (ServletException e) {
			logger.error("ServletException：",e);
		}
	}

	private InfomationDto getParams(HttpServletRequest req){
		InfomationDto dto = new InfomationDto();

		dto.setInfomationTitle( req.getParameter("title") );
		dto.setContents(req.getParameter("contents"));

		return dto;

	}


	/**
	 * 公開情報をセット
	 * @param req
	 * @return
	 * @throws AsoLearningSystemErrException
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws ServletException
	 */
	private List<InfoPublicDto> getInfoPublicDtoList(HttpServletRequest req,List<CourseDto> courselist) throws AsoLearningSystemErrException, IllegalStateException, IOException, ServletException{
		List<InfoPublicDto> infopublicList = new ArrayList<InfoPublicDto>();

		for(CourseDto course : courselist ){
			InfoPublicDto dto = new InfoPublicDto();

			int courseId = course.getId();
			dto.setCourseId(courseId);
			dto.setCourseName(course.getName());
			dto.setPublicDatetime(getStringParamFromPart(req,courseId+"-startterm"));
			dto.setEndDatetime(getStringParamFromPart(req,courseId+"-endterm"));

			infopublicList.add(dto);
		}

		return infopublicList;
	}
}
