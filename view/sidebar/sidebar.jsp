 <%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="jp.ac.asojuku.asolearning.param.RoleId" %>
<%@ page import="jp.ac.asojuku.asolearning.dto.LogonInfoDTO" %>
<%@ page import="jp.ac.asojuku.asolearning.param.SessionConst" %>
<%@ page import="jp.ac.asojuku.asolearning.permit.*" %>

<%
LogonInfoDTO loginInfo = (LogonInfoDTO)session.getAttribute(SessionConst.SESSION_LOGININFO);
%>

 <div class="navbar-default sidebar" role="navigation">
     <div class="sidebar-nav navbar-collapse">
         <ul class="nav" id="side-menu">
             <li class="active">
                 <a href="index.html"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
             </li>
<% if( PermissionChecker.check("00101", loginInfo.getRoleId())){ %>
             <li >
                 <a href="tasklist"><i class="fa fa-pencil-square fa-fw"></i> 課題一覧<span class="fa arrow"></span></a>

             </li>
<%
}
if( PermissionChecker.check("00201", loginInfo.getRoleId())){
%>
             <li>
                 <a href="ranking"><i class="fa fa-graduation-cap fa-fw"></i>ランキング</a>
             </li>
<% }
  if( PermissionChecker.check("00501", loginInfo.getRoleId()) ){ %>
             <li>
                 <a href="tc_createTask"><i class="fa fa-graduation-cap fa-fw"></i>課題作成</a>
             </li>
<% }%>

         </ul>
     </div>
     <!-- /.sidebar-collapse -->
 </div>
