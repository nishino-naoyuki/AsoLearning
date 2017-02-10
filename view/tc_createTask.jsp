<!DOCTYPE html>
<html lang="en">

<head>

<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="jp.ac.asojuku.asolearning.param.RequestConst" %>
<%@ page import="java.util.List" %>
<%@ page import="jp.ac.asojuku.asolearning.dto.TaskDto" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>課題一覧</title>

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
                            課題一覧 <small>あなたへの課題の一覧です</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-pencil-square"></i> 課題一覧
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
<%
List<TaskDto> taskList = (List<TaskDto>)request.getAttribute(RequestConst.REQUEST_TASK_LIST);
%>
                <div class="row">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover table-striped">
                                <thead>
                                    <tr class="info">
                                        <th>No.</th>
                                        <th>ソース</th>
                                        <th>締め切り</th>
                                        <th>必須</th>
                                        <th>得点</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <% for( TaskDto taskDto : taskList ){ %>
                                    <tr>

                                        <td>
                                        <a href="task?taskid=<%=taskDto.getTaskId()%>">
                                        <%= taskDto.getTaskName() %>
                                        </a>
                                        </td>

                                        <% if( taskDto.getResult() != null ){ %>
                                        	<td>提出済み</td>
                                        <% }else{ %>
                                        	<td>未提出</td>
                                        <% } %>

                                        <% if( taskDto.getTerminationDate() != null ){ %>
                                        	<td>あり</td>
                                        <% }else{ %>
                                        	<td>なし</td>
                                        <% } %>

                                        <% if( taskDto.isRequiredFlg() ){ %>
                                        	<td>必須</td>
                                        <% }else{ %>
                                        	<td>任意</td>
                                        <% } %>

                                        <% if( taskDto.getResult() != null ){ %>
                                        	<td><%= taskDto.getResult().getTotal() %></td>
                                        <% }else{ %>
                                        	<td>&nbsp;</td>
                                        <% } %>
                                    </tr>
                                <% } %>
                                </tbody>
                            </table>
                        </div>

                </div>
                <!-- /.row -->

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
