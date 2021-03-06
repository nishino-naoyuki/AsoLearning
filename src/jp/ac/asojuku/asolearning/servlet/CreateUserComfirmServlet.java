/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.ac.asojuku.asolearning.bo.CourseBo;
import jp.ac.asojuku.asolearning.bo.impl.CourseBoImpl;
import jp.ac.asojuku.asolearning.dto.CourseDto;
import jp.ac.asojuku.asolearning.dto.UserDto;
import jp.ac.asojuku.asolearning.err.ActionErrors;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;
import jp.ac.asojuku.asolearning.param.RoleId;
import jp.ac.asojuku.asolearning.validator.UserValidator;

/**
 * @author nishino
 *
 */
@WebServlet(name="CreateUserComfirmServlet",urlPatterns={"/tc_confirmUser"})
public class CreateUserComfirmServlet extends BaseServlet {

	private final String DISPNO = "00803";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}
	private ActionErrors errors;

	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.servlet.BaseServlet#doPostMain(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPostMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		errors = new ActionErrors();

		///////////////////////////////////
		//学科データを取得
		CourseBo coursBo = new CourseBoImpl();

		List<CourseDto> list = coursBo.getCourseAllList();

		///////////////////////////////////
		//パラメータチェック
		checkError(req,list);

		UserDto userDto = null;
		///////////////////////////////////
		//パラメータ取得
		userDto = getParams(req,list);

		/////////////////////////////////
		//画面遷移
		RequestDispatcher rd = null;
		if( errors.isHasErr() ){
			//エラーがある場合は、リクエストにセット
			setToRequest(req,userDto,list);
			//画面遷移
			rd = req.getRequestDispatcher(getJspDir()+"tc_createUser.jsp");
		}else{
			////////////////////////////////////////
			//エラーが無い場合はセッションにセット
			setToSession(req,userDto);
			rd = req.getRequestDispatcher(getJspDir()+"tc_createUserConfirm.jsp");
		}
		rd.forward(req, resp);


	}

	private void setToRequest(HttpServletRequest req,UserDto dto,List<CourseDto> list) throws AsoLearningSystemErrException{

		req.setAttribute(RequestConst.REQUEST_USER_DTO, dto);
		req.setAttribute(RequestConst.REQUEST_ERRORS, errors);
		req.setAttribute(RequestConst.REQUEST_COURSE_LIST, list);
	}

	private void setToSession(HttpServletRequest req,UserDto dto){

		HttpSession session = req.getSession(false);

		if( session != null ){
			session.setAttribute(RequestConst.REQUEST_USER_DTO, dto);
		}
	}

	/**
	 * エラーチェック
	 * @param req
	 * @param list
	 * @throws AsoLearningSystemErrException
	 */
	private void checkError(HttpServletRequest req,List<CourseDto> list) throws AsoLearningSystemErrException{

		UserValidator.useName(req.getParameter("name"), errors);
		UserValidator.useNickName(req.getParameter("nickname"), errors);
		UserValidator.roleId(req.getParameter(RequestConst.REQUEST_ROLE_ID), errors);
		UserValidator.courseId(req.getParameter(RequestConst.REQUEST_COURSE_ID), list, errors);
		UserValidator.admissionYear(req.getParameter("admissionYear"), errors);
		UserValidator.mailAddress(req.getParameter("mailadress"), errors);
		UserValidator.password(req.getParameter("password"), errors);

	}

	/**
	 * パラメータのセット
	 * @param req
	 * @param list
	 * @return
	 */
	private UserDto getParams(HttpServletRequest req,List<CourseDto> list){
		UserDto userDto = new UserDto();

		userDto.setName(req.getParameter("name"));
		userDto.setMailAdress(req.getParameter("mailadress"));
		userDto.setNickName(req.getParameter("nickname"));
		userDto.setAdmissionYear(req.getParameter("admissionYear"));
		userDto.setPassword(req.getParameter("password"));
		userDto.setCourseId(getIntParam(RequestConst.REQUEST_COURSE_ID,req));
		userDto.setRoleId(getIntParam(RequestConst.REQUEST_ROLE_ID,req));

		//学科名を検索してセット
		for(CourseDto course : list ){
			if( course.getId() == userDto.getCourseId()){
				userDto.setCourseName(course.getName());
				break;
			}
		}
		//役割名をセット
		userDto.setRoleName(RoleId.search(userDto.getRoleId()).getMsg());


		return userDto;
	}
}
