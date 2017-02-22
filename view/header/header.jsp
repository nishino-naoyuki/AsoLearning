<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="jp.ac.asojuku.asolearning.dto.LogonInfoDTO" %>
<%@ page import="jp.ac.asojuku.asolearning.param.SessionConst" %>

<%
LogonInfoDTO loginInfo = (LogonInfoDTO)session.getAttribute(SessionConst.SESSION_LOGININFO);
%>
<ul class="nav navbar-top-links navbar-right">
    <li class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
        	<%if( loginInfo.getGrade() == null ){ %>
        	<i class="fa fa-user fa-fw"></i><%=loginInfo.getNickName()%>（<%=loginInfo.getRoleName()%>）<b class="caret"></b>
        	<%}else{ %>
            <i class="fa fa-user fa-fw"></i><%=loginInfo.getCourseName()%> <%=loginInfo.getGrade()%>年 <%=loginInfo.getNickName()%>（<%=loginInfo.getRoleName()%>）<b class="caret"></b>
        	<%} %>
        </a>
        <ul class="dropdown-menu dropdown-user">
            <li><a href="#"><i class="fa fa-user fa-fw"></i> パスワード変更</a>
            </li>
            <li><a href="#"><i class="fa fa-gear fa-fw"></i> ニックネーム変更</a>
            </li>
            <li class="divider"></li>
            <li><a href="logout"><i class="fa fa-sign-out fa-fw"></i> ログアウト</a>
            </li>
        </ul>
        <!-- /.dropdown-user -->
    </li>
    <!-- /.dropdown -->
</ul>