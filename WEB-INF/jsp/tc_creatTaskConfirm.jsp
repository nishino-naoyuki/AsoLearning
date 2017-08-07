<!DOCTYPE html>
<html lang="en">

<head>

<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="jp.ac.asojuku.asolearning.param.*" %>
<%@ page import="java.util.List" %>
<%@ page import="jp.ac.asojuku.asolearning.dto.*" %>
<%@ page import="jp.ac.asojuku.asolearning.err.*" %>
<%@ page import="jp.ac.asojuku.asolearning.util.*" %>
	<LINK REL="SHORTCUT ICON" HREF="view/ico/favicon.ico">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>課題作成</title>

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
	TaskDto dto = (TaskDto)session.getAttribute(SessionConst.SESSION_TASK_DTO);
	List<TaskPublicDto> taskPublicList = dto.getTaskPublicList();
	List<TaskTestCaseDto> testCaseList = dto.getTaskTestCaseDtoList();
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
                <jsp:include page="./header/title.jsp"/>
            </div>
            <!-- /.navbar-header -->
            <jsp:include page="./header/header.jsp"/>
            <!-- /.navbar-top-links -->
			<jsp:include page="./sidebar/sidebar.jsp"/>

            <!-- /.navbar-static-side -->
        </nav>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
				<form action="tc_insertTask" method="post" >
                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            課題作成確認
                        </h1>
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-pencil-square"></i> 課題作成
                            </li>
                            <li class="active">
                                <i class="fa fa-check-circle-o"></i> 確認
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                <div class="row">
                	<div class="panel panel-default">
                		<div class="panel-heading">
                			概要
                		</div>
	                	<div class="panel-body">
	                        <div class="table-responsive">
	                            <table class="table table-bordered table-hover" id="form">
	                                <tbody>
	                                	<tr>
	                                		<th>課題名</th>
	                                		<td>
	                                		<div class="form-group">
	                                			<%= dto.getTaskName() %>
	                                		</div>
	                                		</td>
	                                	</tr>
	                                	<tr>
	                                		<th>難易度</th>
	                                		<td>
	                                		<div class="form-group">
	                                			<%= Difficalty.search(dto.getDifficalty()).getMsg() %>
	                                		</div>
	                                		</td>
	                                	</tr>
	                                	<tr>
	                                		<th>問題文</th>
	                                		<td>
	                                		<div class="form-group">
	                                			<%= HtmlUtil.nl2be(dto.getQuestion()) %>
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

                <div class="row">
                	<div class="panel panel-default">
                		<div class="panel-heading">
                			テストケース
                		</div>
	                	<div class="panel-body">
	                        <div class="table-responsive">
	                            <table class="table table-bordered table-hover" id="form">
	                                <thead>
	                                    <tr>
	                                        <th>No.</th>
	                                        <th>入力ファイル</th>
	                                        <th>出力ファイル</th>
	                                        <th>配点[必須]</th>
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                <%
	                                int index = 1;
	                                for( TaskTestCaseDto testcasse : testCaseList ){
	                                %>
	                                <% if( testcasse.getAllmostOfMarks() != null ){ %>
	                                	<tr  class="testcase_table_tr">
	                                		<td>
	                                		<%=index %>
	                                		</td>
	                                		<td>
		                                        <div class="input-group">
		                                        <% if( testcasse.getInputFileName()!=null ){%>
													<%= FileUtils.getFileNameFromPath( testcasse.getInputFileName()) %>
												<% }else{ %>
													&nbsp;
												<% } %>
										        </div>
	                                		</td>
	                                		<td>
		                                        <div class="input-group">
												<%= FileUtils.getFileNameFromPath( testcasse.getOutputFileName()) %>
										        </div>
	                                		</td>
	                                		<td>
	                                			<%= testcasse.getAllmostOfMarks() %>
	                                		</td>
	                                	</tr>
	                                		<% } %>
	                                	<% index++; %>
	                                	<% } %>
	                                </tbody>
	                            </table>
	                        </div>
						</div>
                	</div>
                </div>
                <!-- /.row -->

                <div class="row">
                	<div class="panel panel-default">
                		<div class="panel-heading">
                			公開設定
                		</div>
	                	<div class="panel-body">
	                        <div class="table-responsive">
	                            <table class="table table-bordered table-hover" id="form">
	                                <thead>
	                                    <tr>
	                                        <th>学科</th>
	                                        <th>公開設定</th>
	                                        <!--
	                                        <th>公開時間設定</th>
		                                     -->
	                                        <th>締め切り設定</th>
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                 <% for(TaskPublicDto pdto : taskPublicList ){ %>
	                                	<tr>
	                                		<td>
	                                			<%=pdto.getCourseName()%>
	                                		</td>
	                                		<td>
	                                           <%=pdto.getStatus().getMsg1()%>
	                                		</td>
	                                        <!--
	                                		<td>
	                                			<div class="form-group">
	                                			<%=pdto.getPublicDatetime()%>
	                                			</div>
	                                		</td>
		                                     -->
	                                		<td>
	                                			<div class="form-group">
	                                			<%=pdto.getEndDatetime()%>
	                                			</div>
	                                		</td>
	                                	</tr>
	                                <% }%>
	                                </tbody>
	                            </table>
	                        </div>
						</div>
                	</div>
                </div>
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

</body>

</html>
