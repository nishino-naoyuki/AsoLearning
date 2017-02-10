 <%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="jp.ac.asojuku.asolearning.param.RoleId" %>
<%@ page import="jp.ac.asojuku.asolearning.dto.LogonInfoDTO" %>
<%@ page import="jp.ac.asojuku.asolearning.param.SessionConst" %>

<%
LogonInfoDTO loginInfo = (LogonInfoDTO)session.getAttribute(SessionConst.SESSION_LOGININFO);
%>

 <div class="navbar-default sidebar" role="navigation">
     <div class="sidebar-nav navbar-collapse">
         <ul class="nav" id="side-menu">
             <li class="active">
                 <a href="index.html"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
             </li>
<% if( RoleId.STUDENT.equals(loginInfo.getRoleId()) ){ %>
             <li >
                 <a href="#"><i class="fa fa-pencil-square fa-fw"></i> 課題一覧<span class="fa arrow"></span></a>
                 <ul class="nav nav-second-level">
                     <li>
                         <a href="flot.html"><i class="fa fa-check-circle-o fa-fw"></i>課題１</a>
                     </li>
                     <li>
                         <a href="morris.html"><i class="fa fa-check-circle-o fa-fw"></i>課題２</a>
                     </li>
                 </ul>
                 <!-- /.nav-second-level -->
             </li>
             <li>
                 <a href="#"><i class="fa fa-graduation-cap fa-fw"></i>ランキング</a>
             </li>
<% }else if( RoleId.TEACHER.equals(loginInfo.getRoleId()) ){ %>
             <li>
                 <a href="#"><i class="fa fa-graduation-cap fa-fw"></i>課題作成</a>
             </li>
<% }else{ %>
             <li>
                 <a href="#"><i class="fa fa-graduation-cap fa-fw"></i>課題作成</a>
             </li>
<%}%>
         </ul>
     </div>
     <!-- /.sidebar-collapse -->
 </div>
