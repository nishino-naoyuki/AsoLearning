<!DOCTYPE html>
<html lang="en">

<head>

<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="jp.ac.asojuku.asolearning.param.RequestConst" %>
<%@ page import="java.util.List" %>
<%@ page import="jp.ac.asojuku.asolearning.dto.*" %>
<%@ page import="jp.ac.asojuku.asolearning.util.*" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>課題画面</title>

    <link href="view/css/main.css" rel="stylesheet">
    <!-- Bootstrap Core CSS -->
    <link href="view/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="view/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="view/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="view/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <link href="view/css/main.css" rel="stylesheet">
    <link href="view/css/form.css" rel="stylesheet">
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

<%
TaskResultDetailDto resultDto = (TaskResultDetailDto)request.getAttribute(RequestConst.REQUEST_TASK_RESULT);
String dispName = (String)request.getAttribute(RequestConst.REQUEST_DISP_NO);
Integer userId = (Integer)request.getAttribute(RequestConst.REQUEST_USER_ID);
%>
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            <%=resultDto.getTaskName() %>-得点詳細
                        </h1>
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-pencil-square"></i> <a href="tasklist">課題一覧</a>
                            </li>
                            <% if( "detail".equals(dispName) ){ %>
                            <li>
                                <i class="fa fa-check-circle-o"></i>
                                <a href="task?taskid=<%=resultDto.getTaskId()%>">
                                        <%= resultDto.getTaskName() %></a>
                            </li>
                            <% }else if( "user".equals(dispName) ){ %>
                            <li>
                                <i class="fa fa-check-circle-o"></i>
                                <a href="userDetail?userId=<%=userId%>">
                                        ユーザー詳細</a>
                            </li>
                            <%} %>
                            <li class="active">
                                <i class="fa fa-check-circle-o"></i> 得点詳細
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->

<% if( resultDto == null ){ %>
                <div class="row">
                	得点結果がありません。
                </div>
<% }else{ %>
                <div class="row">
                	<div class="panel panel-default">
                		<div class="panel-heading">
                			総合得点
                		</div>
	                	<div class="panel-body">
	                        <div class="table-responsive">
	                            <table class="table table-bordered table-hover" id="form">
	                                <tbody>
	                                	<tr>
	                                		<th>得点</th>
	                                		<td>
	                                		<div class="form-group">
	                                			<%=resultDto.getTotalScore() %>
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
	                                <tbody>
	                                <%
	                                int cnt = 1;
	                                int total = 0;
	                                for(TaskResultTestCaseDto testCase : resultDto.getTestcase()){
	                                %>
	                                	<tr>
	                                		<th>パターン<%=cnt %></th>
	                                		<td>
	                                		<div class="form-group">
	                                			<%= (testCase.getScore() != 0 ? "正解":testCase.getMessage()) %>
	                                		</div>
	                                		</td>
	                                	</tr>
	                                <%
	                                	total += testCase.getScore();
	                                	cnt++;
	                                }
	                                %>
	                                	<tr>
	                                		<th>点数</th>
	                                		<td>
	                                		<div class="form-group">
	                                			<%= total %>点
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
                			コード品質
                		</div>
	                	<div class="panel-body">
	                        <div class="table-responsive">
	                        <% TaskResultMetricsDto metricsDto = resultDto.getMetrics(); %>
	                            <table class="table table-bordered table-hover" id="form">
	                                <tbody>
	                                	<tr>
	                                		<th>最高複雑度</th>
	                                		<td>
	                                		<div class="form-group">
	                                			<%= metricsDto.getMaxMvg() %>
	                                		</div>
	                                		</td>
	                                		<td>
	                                		<div class="form-group">
	                                			<%= metricsDto.getMaxMvgScore() %>点
	                                		</div>
	                                		</td>
	                                	</tr>

	                                	<tr>
	                                		<th>平均複雑度</th>
	                                		<td>
	                                		<div class="form-group">
	                                			<%= metricsDto.getAvrMvg() %>
	                                		</div>
	                                		</td>
	                                		<td>
	                                		<div class="form-group">
	                                			<%= metricsDto.getAvrMvgScore() %>点
	                                		</div>
	                                		</td>
	                                	</tr>

	                                	<tr>
	                                		<th>最高行数</th>
	                                		<td>
	                                		<div class="form-group">
	                                			<%= metricsDto.getMaxLoc() %>
	                                		</div>
	                                		</td>
	                                		<td>
	                                		<div class="form-group">
	                                			<%= metricsDto.getMaxLocScore() %>点
	                                		</div>
	                                		</td>
	                                	</tr>

	                                	<tr>
	                                		<th>平均行数</th>
	                                		<td>
	                                		<div class="form-group">
	                                			<%= metricsDto.getAvrLoc() %>
	                                		</div>
	                                		</td>
	                                		<td>
	                                		<div class="form-group">
	                                			<%= metricsDto.getAvrLocScore() %>点
	                                		</div>
	                                		</td>
	                                	</tr>
	                                	<tr>
	                                		<th>点数</th>
	                                		<td colspan="2">
	                                		<div class="form-group">
	                                			<%= (metricsDto.getMaxMvgScore()+metricsDto.getAvrMvgScore()+metricsDto.getMaxLocScore()+metricsDto.getAvrLocScore()) %>点
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
            </div>
            <!-- /.container-fluid -->
<% } %>
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
