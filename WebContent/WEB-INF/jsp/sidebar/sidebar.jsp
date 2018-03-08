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
                 <a href="st_dashboad"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
             </li>
<% if( PermissionChecker.check("00101", loginInfo.getRoleId())){ %>
             <li >
                 <a href="tasklist"><i class="fa fa-pencil-square fa-fw"></i> 課題一覧</a>

             </li>
<%
}
if( PermissionChecker.check("00201", loginInfo.getRoleId())){
%>
             <li>
                 <a href="ranking"><i class="fa fa-graduation-cap fa-fw"></i> ランキング</a>
             </li>
<% }
  if( PermissionChecker.check("00501", loginInfo.getRoleId()) ){ %>
             <li>
                 <a href="tc_createTask"><i class="fa fa-graduation-cap fa-fw"></i> 課題作成</a>
             </li>
<% }
  if( PermissionChecker.check("00604", loginInfo.getRoleId()) ){ %>
             <li>
                 <a href="tasksearch"><i class="fa fa-search"></i> 課題検索（編集）</a>
             </li>
<% }
  if( PermissionChecker.check("00802", loginInfo.getRoleId()) ){ %>
             <li>
                 <a href="tc_createUser"><i class="fa fa-user fa-fw"></i> ユーザー作成</a>
             </li>
<% }
  if( PermissionChecker.check("00801", loginInfo.getRoleId()) ){ %>
             <li>
                 <a href="usersearch"><i class="fa fa-search"></i> ユーザー検索</a>
             </li>
<% }
  if( PermissionChecker.check("01601", loginInfo.getRoleId()) ){ %>
  <!--
             <li>
                 <a href="searchInfo"><i class="fa fa-desktop"></i> お知らせ情報</a>
             </li>
    -->
<% }
  if( PermissionChecker.check("01001", loginInfo.getRoleId()) ){ %>
             <li>
                 <a href="csvEntry"><i class="fa fa-file fa-fw"></i>CSV処理</a>
             </li>
<% }%>
<% if( PermissionChecker.check("01401", loginInfo.getRoleId()) ){ %>
             <li>
                 <a href="historysearch"><i class="fa fa-search"></i>履歴検索</a>
             </li>
<% }%>

         </ul>
     </div>
     <!-- /.sidebar-collapse -->
 </div>
