<!DOCTYPE html>
<html lang="en">

<head>

<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="jp.ac.asojuku.asolearning.param.RequestConst" %>
<%@ page import="java.util.List" %>
<%@ page import="jp.ac.asojuku.asolearning.dto.*" %>
<%@ page import="jp.ac.asojuku.asolearning.param.*" %>
	<link rel="shortcut icon" href="view/ico/favicon.ico">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>アバター設定</title>
    <!-- Bootstrap Core CSS -->
    <link href="view/css/main.css" rel="stylesheet">

    <!-- Bootstrap Core CSS -->
    <link href="view/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="view/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="view/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="view/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <jsp:include page="/view/header/title.jsp"/>
            </div>
            <!-- /.navbar-header -->
            <jsp:include page="/view/header/header.jsp"/>
            <!-- /.navbar-top-links -->
			<jsp:include page="/view/sidebar/sidebar.jsp"/>

            <!-- /.navbar-static-side -->
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            アバター設定
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-pencil-square"></i> アバター設定
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
<%
AvatarPartsDto dto = (AvatarPartsDto)request.getAttribute(RequestConst.REQUEST_AVATAR_DTO);
%>
                <div class="row">
                    <div class="col-lg-12">
                    <p>アバターを選択して、OKをクリックしてください。</p>
					</div>
                </div>
            	<div class="row">
					<div class="col-lg-3">
								<div class="avatar_relative">
									<img id="ava_backhair" src="view/img/avatar/<%=AvatarKind.BACK_HAIR.getDir()%>/hair_back001.png" alt="" class="avatar_absolute" data-avatar-id="" />
									<img id="ava_body" src="view/img/avatar/<%=AvatarKind.BODY.getDir()%>/body001.png" class="avatar_absolute" data-avatar-id=""/>
									<img id="ava_ear" src="view/img/avatar/<%=AvatarKind.EAR.getDir()%>/ear001.png" alt="" class="avatar_absolute" data-avatar-id="" />
									<img id="ava_face" src="view/img/avatar/<%=AvatarKind.FACE.getDir()%>/faceline001.png" alt="" class="avatar_absolute" data-avatar-id="" />
									<img id="ava_mayu" src="view/img/avatar/<%=AvatarKind.MAYU.getDir()%>/eyebrows001.png" alt="" class="avatar_absolute" data-avatar-id="" />
									<img id="ava_eye" src="view/img/avatar/<%=AvatarKind.EYE.getDir()%>/eyes001.png" alt="" class="avatar_absolute" data-avatar-id="" />
									<img id="ava_nose" src="view/img/avatar/<%=AvatarKind.NOSE.getDir()%>/nose001.png" alt="" class="avatar_absolute" data-avatar-id="" />
									<img id="ava_mouth" src="view/img/avatar/<%=AvatarKind.MOUTH.getDir()%>/mouth001.png" alt="" class="avatar_absolute" data-avatar-id="" />
									<img id="ava_fronthair" src="view/img/avatar/<%=AvatarKind.FRONT_HAIR.getDir()%>/hair001.png" alt="" class="avatar_absolute" data-avatar-id="" />
									<img id="ava_acc1" src="view/img/avatar/<%=AvatarKind.ACC1.getDir()%>/mouth001.png" alt="" class="avatar_absolute" data-avatar-id="" />
									<img id="ava_acc2" src="view/img/avatar/<%=AvatarKind.ACC2.getDir()%>/mouth001.png" alt="" class="avatar_absolute" data-avatar-id=""  />
								</div>

					</div>
					<!-- アバターパーツ -->
                <div class="col-lg-8">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            アバターパーツ
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <!-- Nav tabs -->
                            <ul class="nav nav-tabs">
                                <li class="active"><a href="#backhair" data-toggle="tab">後ろ髪</a>
                                </li>
                                <li><a href="#body" data-toggle="tab">体</a>
                                </li>
                                <li><a href="#face" data-toggle="tab">輪郭</a>
                                </li>
                                <li><a href="#ear" data-toggle="tab">耳</a>
                                </li>
                                <li><a href="#mayu" data-toggle="tab">眉</a>
                                </li>
                                <li><a href="#eye" data-toggle="tab">目</a>
                                </li>
                                <li><a href="#nose" data-toggle="tab">鼻</a>
                                </li>
                                <li><a href="#mouth" data-toggle="tab">口</a>
                                </li>
                                <li><a href="#fronthair" data-toggle="tab">前髪</a>
                                </li>
                                <li><a href="#acc1" data-toggle="tab">アクセ1</a>
                                </li>
                                <li><a href="#acc2" data-toggle="tab">アクセ2</a>
                                </li>
                            </ul>

                            <!-- Tab panes -->
                            <div class="tab-content">
                                <div class="tab-pane fade in active" id="backhair">
                                <%
                                AvatarDto bh = dto.getAvatar(AvatarKind.BACK_HAIR);
                                List<AvatarDto.Element> bhlist = bh.getAvatarList();
                                %>
                                <% for( AvatarDto.Element element : bhlist ){ %>
                                <img src="view/img/avatar/<%=AvatarKind.BACK_HAIR.getDir()%>/<%=element.getName()%>" width="10%" height="10%" data-avatar-id="<%= element.getId()%>" data-avatar-name="<%=element.getName()%>" class="<%=AvatarKind.BACK_HAIR.getDir()%>" />
                                <% } %>

                                </div>
                                <div class="tab-pane fade" id="body">
                                <%
                                AvatarDto bdy = dto.getAvatar(AvatarKind.BODY);
                                List<AvatarDto.Element> bdylist = bdy.getAvatarList();
                                %>
                                <% for( AvatarDto.Element element : bdylist ){ %>
                                <img src="view/img/avatar/<%=AvatarKind.BODY.getDir()%>/<%=element.getName()%>" width="10%" height="10%" data-avatar-id="<%= element.getId()%>" data-avatar-name="<%=element.getName()%>" class="<%=AvatarKind.BODY.getDir()%>" />
                                <% } %>
                                </div>
                                <div class="tab-pane fade" id="face">
                                <%
                                AvatarDto fc = dto.getAvatar(AvatarKind.FACE);
                                List<AvatarDto.Element> fclist = fc.getAvatarList();
                                %>
                                <% for( AvatarDto.Element element : fclist ){ %>
                                <img src="view/img/avatar/<%=AvatarKind.FACE.getDir()%>/<%=element.getName()%>" width="10%" height="10%" data-avatar-id="<%= element.getId()%>" data-avatar-name="<%=element.getName()%>" class="<%=AvatarKind.FACE.getDir()%>" />
                                <% } %>
                                </div>
                                <div class="tab-pane fade" id="ear">
                                <%
                                AvatarDto ear = dto.getAvatar(AvatarKind.EAR);
                                List<AvatarDto.Element> earlist = ear.getAvatarList();
                                %>
                                <% for( AvatarDto.Element element : earlist ){ %>
                                <img src="view/img/avatar/<%=AvatarKind.EAR.getDir()%>/<%=element.getName()%>" width="10%" height="10%" data-avatar-id="<%= element.getId()%>" data-avatar-name="<%=element.getName()%>" class="<%=AvatarKind.EAR.getDir()%>" />
                                <% } %>
                                </div>
                                <div class="tab-pane fade" id="mayu">
                                <%
                                AvatarDto my = dto.getAvatar(AvatarKind.MAYU);
                                List<AvatarDto.Element> mylist = my.getAvatarList();
                                %>
                                <% for( AvatarDto.Element element : mylist ){ %>
                                <img src="view/img/avatar/<%=AvatarKind.MAYU.getDir()%>/<%=element.getName()%>" width="10%" height="10%" data-avatar-id="<%= element.getId()%>" data-avatar-name="<%=element.getName()%>" class="<%=AvatarKind.MAYU.getDir()%>" />
                                <% } %>
                                </div>
                                <div class="tab-pane fade" id="eye">
                                <%
                                AvatarDto eye = dto.getAvatar(AvatarKind.EYE);
                                List<AvatarDto.Element> eyelist = eye.getAvatarList();
                                %>
                                <% for( AvatarDto.Element element : eyelist ){ %>
                                <img src="view/img/avatar/<%=AvatarKind.EYE.getDir()%>/<%=element.getName()%>" width="10%" height="10%" data-avatar-id="<%= element.getId()%>" data-avatar-name="<%=element.getName()%>" class="<%=AvatarKind.EYE.getDir()%>" />
                                <% } %>
                                </div>
                                <div class="tab-pane fade" id="nose">
                                <%
                                AvatarDto ns = dto.getAvatar(AvatarKind.NOSE);
                                List<AvatarDto.Element> nslist = ns.getAvatarList();
                                %>
                                <% for( AvatarDto.Element element : nslist ){ %>
                                <img src="view/img/avatar/<%=AvatarKind.NOSE.getDir()%>/<%=element.getName()%>" width="10%" height="10%" data-avatar-id="<%= element.getId()%>" data-avatar-name="<%=element.getName()%>" class="<%=AvatarKind.NOSE.getDir()%>" />
                                <% } %>
                                </div>
                                <div class="tab-pane fade" id="mouth">
                                <%
                                AvatarDto mt = dto.getAvatar(AvatarKind.MOUTH);
                                List<AvatarDto.Element> mtlist = mt.getAvatarList();
                                %>
                                <% for( AvatarDto.Element element : mtlist ){ %>
                                <img src="view/img/avatar/<%=AvatarKind.MOUTH.getDir()%>/<%=element.getName()%>" width="10%" height="10%" data-avatar-id="<%= element.getId()%>" data-avatar-name="<%=element.getName()%>" class="<%=AvatarKind.MOUTH.getDir()%>" />
                                <% } %>
                                </div>
                                <div class="tab-pane fade" id="fronthair">
                                <%
                                AvatarDto fh = dto.getAvatar(AvatarKind.FRONT_HAIR);
                                List<AvatarDto.Element> fhlist = fh.getAvatarList();
                                %>
                                <% for( AvatarDto.Element element : fhlist ){ %>
                                <img src="view/img/avatar/<%=AvatarKind.FRONT_HAIR.getDir()%>/<%=element.getName()%>" width="10%" height="10%" data-avatar-id="<%= element.getId()%>" data-avatar-name="<%=element.getName()%>" class="<%=AvatarKind.FRONT_HAIR.getDir()%>" />
                                <% } %>
                                </div>
                                <div class="tab-pane fade" id="acc1">
                                <%
                                AvatarDto acc1 = dto.getAvatar(AvatarKind.ACC1);
                                List<AvatarDto.Element> acc1list = acc1.getAvatarList();
                                %>
                                <% for( AvatarDto.Element element : acc1list ){ %>
                                <img src="view/img/avatar/<%=AvatarKind.ACC1.getDir()%>/<%=element.getName()%>" width="10%" height="10%" data-avatar-id="<%= element.getId()%>" data-avatar-name="<%=element.getName()%>" class="<%=AvatarKind.ACC1.getDir()%>" />
                                <% } %>
                                </div>
                                <div class="tab-pane fade" id="acc2">
                                <%
                                AvatarDto acc2 = dto.getAvatar(AvatarKind.ACC2);
                                List<AvatarDto.Element> acc2list = acc2.getAvatarList();
                                %>
                                <% for( AvatarDto.Element element : acc2list ){ %>
                                <img src="view/img/avatar/<%=AvatarKind.ACC2.getDir()%>/<%=element.getName()%>" width="10%" height="10%" data-avatar-id="<%= element.getId()%>" data-avatar-name="<%=element.getName()%>" class="<%=AvatarKind.ACC2.getDir()%>" />
                                <% } %>
                                </div>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>

                </div>
                <div class="row">
                    <div class="col-lg-12">
                    <button id="updateButton" class="btn"><span class="glyphicon glyphicon-circle-arrow-up" aria-hidden="true"></span> 決定</button>
					</div>
                </div>
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="view/js/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="view/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="view/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="view/js/sb-admin-2.js"></script>

<script>
//後ろ髪
$('.backhair').on('click', function() {
	$('#ava_backhair').attr("src","view/img/avatar/<%=AvatarKind.BACK_HAIR.getDir()%>/"+$(this).attr("data-avatar-name"));
	$('#ava_backhair').attr("data-avatar-id",$(this).attr("data-avatar-id"));
});
$('.body').on('click', function() {
	$('#ava_body').attr("src","view/img/avatar/<%=AvatarKind.BODY.getDir()%>/"+$(this).attr("data-avatar-name"));
	$('#ava_body').attr("data-avatar-id",$(this).attr("data-avatar-id"));
});
$('.face').on('click', function() {
	$('#ava_face').attr("src","view/img/avatar/<%=AvatarKind.FACE.getDir()%>/"+$(this).attr("data-avatar-name"));
	$('#ava_face').attr("data-avatar-id",$(this).attr("data-avatar-id"));
});
$('.ear').on('click', function() {
	$('#ava_ear').attr("src","view/img/avatar/<%=AvatarKind.EAR.getDir()%>/"+$(this).attr("data-avatar-name"));
	$('#ava_ear').attr("data-avatar-id",$(this).attr("data-avatar-id"));
});
$('.mayu').on('click', function() {
	$('#ava_mayu').attr("src","view/img/avatar/<%=AvatarKind.MAYU.getDir()%>/"+$(this).attr("data-avatar-name"));
	$('#ava_mayu').attr("data-avatar-id",$(this).attr("data-avatar-id"));
});
$('.eye').on('click', function() {
	$('#ava_eye').attr("src","view/img/avatar/<%=AvatarKind.EYE.getDir()%>/"+$(this).attr("data-avatar-name"));
	$('#ava_eye').attr("data-avatar-id",$(this).attr("data-avatar-id"));
});
$('.nose').on('click', function() {
	$('#ava_nose').attr("src","view/img/avatar/<%=AvatarKind.NOSE.getDir()%>/"+$(this).attr("data-avatar-name"));
	$('#ava_nose').attr("data-avatar-id",$(this).attr("data-avatar-id"));
});
$('.mouth').on('click', function() {
	$('#ava_mouth').attr("src","view/img/avatar/<%=AvatarKind.MOUTH.getDir()%>/"+$(this).attr("data-avatar-name"));
	$('#ava_mouth').attr("data-avatar-id",$(this).attr("data-avatar-id"));
});
$('.fronthair').on('click', function() {
	$('#ava_fronthair').attr("src","view/img/avatar/<%=AvatarKind.FRONT_HAIR.getDir()%>/"+$(this).attr("data-avatar-name"));
	$('#ava_fronthair').attr("data-avatar-id",$(this).attr("data-avatar-id"));
});
$('.acc1').on('click', function() {
	$('#ava_acc1').attr("src","view/img/avatar/<%=AvatarKind.ACC1.getDir()%>/"+$(this).attr("data-avatar-name"));
	$('#ava_acc1').attr("data-avatar-id",$(this).attr("data-avatar-id"));
});
$('.acc2').on('click', function() {
	$('#ava_acc2').attr("src","view/img/avatar/<%=AvatarKind.ACC2.getDir()%>/"+$(this).attr("data-avatar-name"));
	$('#ava_acc2').attr("data-avatar-id",$(this).attr("data-avatar-id"));
});


$('#updateButton').on('click', function() {


	var ava_backhair = $('#ava_backhair').attr("data-avatar-id");
	var ava_body = $('#ava_body').attr("data-avatar-id");
	var ava_face = $('#ava_face').attr("data-avatar-id");
	var ava_ear = $('#ava_ear').attr("data-avatar-id");
	var ava_mayu = $('#ava_mayu').attr("data-avatar-id");
	var ava_eye = $('#ava_eye').attr("data-avatar-id");
	var ava_nose = $('#ava_nose').attr("data-avatar-id");
	var ava_mouth = $('#ava_mouth').attr("data-avatar-id");
	var ava_fronthair = $('#ava_fronthair').attr("data-avatar-id");
	var ava_acc1 = $('#ava_acc1').attr("data-avatar-id");
	var ava_acc2 = $('#ava_acc2').attr("data-avatar-id");

	var params=	"ava_backhair="+ava_backhair+
				"&ava_body="+ava_body+
				"&ava_face="+ava_face+
				"&ava_ear="+ava_ear+
				"&ava_mayu="+ava_mayu+
				"&ava_eye="+ava_eye+
				"&ava_nose="+ava_nose+
				"&ava_mouth="+ava_mouth+
				"&ava_fronthair="+ava_fronthair+
				"&ava_acc1="+ava_acc1+
				"&ava_acc2="+ava_acc2;
alert(params);
    $.ajax({
    	cache: false,
        type : 'GET',
        url : "updateavatar",
        data :params,
        dataType : 'text',
        processData : false,
        timeout : 360000, // milliseconds

    }).done(function(message) {
    	alert(message);
    }).fail(function(XMLHttpRequest, textStatus, errorThrown) {
    	alert("err:"+textStatus);
        console.log( textStatus  + errorThrown);
    });
});
</script>
</body>
</html>
