<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="jp.ac.asojuku.asolearning.dto.*" %>
<%@ page import="jp.ac.asojuku.asolearning.param.*" %>
 <link href="view/css/main.css" rel="stylesheet">

<%
LogonInfoDTO loginInfo = (LogonInfoDTO)session.getAttribute(SessionConst.SESSION_LOGININFO);
AvatarSettingDto avDto = loginInfo.getAvatar();
%>
	<% if( avDto.getAvatarId(AvatarKind.FACE) != -1 ){ %>
			<div class="avatar_relative_mini">
				<img  src="view/img/avatar/<%=AvatarKind.BACK_HAIR.getDir()%>/<%=avDto.getAvatarName(AvatarKind.BACK_HAIR) %>" alt="" class="avatar_absolute_mini" data-avatar-id="<%=avDto.getAvatarId(AvatarKind.BACK_HAIR) %>" />
				<img  src="view/img/avatar/<%=AvatarKind.BODY.getDir()%>/<%=avDto.getAvatarName(AvatarKind.BODY) %>" class="avatar_absolute_mini" data-avatar-id="<%=avDto.getAvatarId(AvatarKind.BODY) %>"/>
				<img  src="view/img/avatar/<%=AvatarKind.EAR.getDir()%>/<%=avDto.getAvatarName(AvatarKind.EAR) %>" alt="" class="avatar_absolute_mini" data-avatar-id="<%=avDto.getAvatarId(AvatarKind.EAR) %>" />
				<img  src="view/img/avatar/<%=AvatarKind.FACE.getDir()%>/<%=avDto.getAvatarName(AvatarKind.FACE) %>" alt="" class="avatar_absolute_mini" data-avatar-id="<%=avDto.getAvatarId(AvatarKind.FACE) %>" />
				<img  src="view/img/avatar/<%=AvatarKind.MAYU.getDir()%>/<%=avDto.getAvatarName(AvatarKind.MAYU) %>" alt="" class="avatar_absolute_mini" data-avatar-id="<%=avDto.getAvatarId(AvatarKind.MAYU) %>" />
				<img  src="view/img/avatar/<%=AvatarKind.EYE.getDir()%>/<%=avDto.getAvatarName(AvatarKind.EYE) %>" alt="" class="avatar_absolute_mini" data-avatar-id="<%=avDto.getAvatarId(AvatarKind.EYE) %>" />
				<img  src="view/img/avatar/<%=AvatarKind.NOSE.getDir()%>/<%=avDto.getAvatarName(AvatarKind.NOSE) %>" alt="" class="avatar_absolute_mini" data-avatar-id="<%=avDto.getAvatarId(AvatarKind.NOSE) %>" />
				<img  src="view/img/avatar/<%=AvatarKind.MOUTH.getDir()%>/<%=avDto.getAvatarName(AvatarKind.MOUTH) %>" alt="" class="avatar_absolute_mini" data-avatar-id="<%=avDto.getAvatarId(AvatarKind.MOUTH) %>" />
				<img  src="view/img/avatar/<%=AvatarKind.FRONT_HAIR.getDir()%>/<%=avDto.getAvatarName(AvatarKind.FRONT_HAIR) %>" alt="" class="avatar_absolute_mini" data-avatar-id="<%=avDto.getAvatarId(AvatarKind.FRONT_HAIR) %>" />
				<% if( avDto.getAvatarName(AvatarKind.ACC1) != null ){ %>
				<img id="ava_acc1" src="view/img/avatar/<%=AvatarKind.ACC1.getDir()%>/<%=avDto.getAvatarName(AvatarKind.ACC1) %>" alt="" class="avatar_absolute_mini" data-avatar-id="<%=avDto.getAvatarId(AvatarKind.ACC1) %>" />
				<% } %>
				<% if( avDto.getAvatarName(AvatarKind.ACC2) != null ){ %>
				<img id="ava_acc2" src="view/img/avatar/<%=AvatarKind.ACC2.getDir()%>/<%=avDto.getAvatarName(AvatarKind.ACC2) %>" alt="" class="avatar_absolute_mini" data-avatar-id="<%=avDto.getAvatarId(AvatarKind.ACC2) %>"  />
				<% } %>
			</div>
	<% } %>
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
            <li><a href="pchangeinput"><i class="fa fa-user fa-fw"></i> パスワード変更</a>
            </li>
            <li><a href="nicknamechangeinput"><i class="fa fa-gear fa-fw"></i> ニックネーム変更</a>
            </li>
            <li><a href="updateavatarinput"><i class="fa fa-heart fa-fw"></i> アバター設定</a>
            </li>
            <li class="divider"></li>
            <li><a href="logout"><i class="fa fa-sign-out fa-fw"></i> ログアウト</a>
            </li>
        </ul>
        <!-- /.dropdown-user -->
    </li>
    <!-- /.dropdown -->
</ul>

