<!DOCTYPE html>
<html lang="en">

<head>

<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="jp.ac.asojuku.asolearning.param.RequestConst" %>
<%@ page import="jp.ac.asojuku.asolearning.param.TaskPublicStateId" %>
<%@ page import="java.util.List" %>
<%@ page import="jp.ac.asojuku.asolearning.dto.*" %>
<%@ page import="jp.ac.asojuku.asolearning.err.*" %>
<%@ page import="jp.ac.asojuku.asolearning.param.*" %>
	<LINK REL="SHORTCUT ICON" HREF="view/ico/favicon.ico">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>ユーザー作成</title>

    <!-- Bootstrap Core CSS -->
    <link href="view/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="view/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="view/css/sb-admin-2.css" rel="stylesheet">
    <link href="view/css/form.css" rel="stylesheet">
    <link href="view/css/main.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="view/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/redmond/jquery-ui.css" >

<script>
var testcase_cnt = 0;	//テストケースの数。初期値は0
</script>
<%
//エラー情報を取得する
UserDto userDto = (UserDto)session.getAttribute(RequestConst.REQUEST_USER_DTO);

Integer courseId = -1;
Integer roleId = -1;
if( userDto != null){
	courseId = userDto.getUserId();
	roleId = userDto.getRoleId();
}


%>
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
				<form action="tc_insertUser"  method="post" >
                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            ユーザー作成

                        </h1>
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-user"></i> ユーザー作成
                            </li>
                            <li class="active">
                                <i class="fa fa-user"></i> ユーザー作成確認
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                <div class="row">

                	<div class="panel panel-default">
                		<div class="panel-heading">
                			ユーザー情報
                		</div>
	                	<div class="panel-body">
	                        <div class="table-responsive">
	                            <table class="table table-bordered table-hover" id="form">
	                                <tbody>
	                                	<tr>
	                                		<th>ロール</th>
	                                		<td>
	                                		<div class="form-group">
												<%=(userDto!=null? userDto.getRoleName():"") %>
	                                		</div>
	                                		</td>
	                                	</tr>
	                                	<tr>
	                                		<th>学籍番号/社員番号[必須]</th>
	                                		<td>
	                                		<div class="form-group">
												<%=(userDto!=null? userDto.getName():"") %>
	                                		</div>
	                                		</td>
	                                	</tr>
	                                	<tr>
	                                		<th>メールアドレス[必須]</th>
	                                		<td>
	                                		<div class="form-group">
	                                			<%=(userDto!=null? userDto.getMailAdress():"") %>
	                                		</div>
	                                		</td>
	                                	</tr>
	                                	<tr>
	                                		<th>ニックネーム[必須]</th>
	                                		<td>
	                                		<div class="form-group">
	                                			<%=(userDto!=null? userDto.getNickName():"") %>
	                                		</div>
	                                		</td>
	                                	</tr>
	                                	<tr>
	                                		<th>学科[必須]</th>
	                                		<td>
	                                		<div class="form-group">
												<%=(userDto!=null? userDto.getCourseName():"") %>
	                                		</div>
	                                		</td>
	                                	</tr>
	                                	<tr>
	                                		<th>パスワード[必須]</th>
	                                		<td>
	                                		<div class="form-group">
	                                			*****
	                                		</div>
	                                		</td>
	                                	</tr>
	                                	<tr>
	                                		<th>入学年度</th>
	                                		<td>
	                                		<div class="form-group">
	                                			<%=(userDto!=null? userDto.getAdmissionYear():"") %>
	                                		</div>
	                                		</td>
	                                	</tr>
	                                </tbody>
	                            </table>
	                        </div>
						</div>
                	</div>
                </div>
                <!-- /.row -->

                <!-- /.row -->
                <div class="row">
                	 <button type="submit" class="btn"><span class="glyphicon glyphicon-circle-arrow-up" aria-hidden="true"></span> 登録</button>
                </div>
                </form>
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

<script src="view/js/jquery-ui.min.js"></script>
<script src="view/js/jquery.ui.datepicker-ja.min.js"></script>

</body>

</html>
