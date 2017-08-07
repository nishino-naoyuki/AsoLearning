<!DOCTYPE html>
<html lang="en">

<head>

<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="jp.ac.asojuku.asolearning.param.RequestConst" %>
<%@ page import="java.util.List" %>
<%@ page import="jp.ac.asojuku.asolearning.dto.*" %>
<%@ page import="jp.ac.asojuku.asolearning.param.*" %>
<%@ page import="org.apache.commons.collections4.*" %>
	<LINK REL="SHORTCUT ICON" HREF="view/ico/favicon.ico">
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

    <!-- DataTables CSS -->
    <link href="view/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="view/datatables-responsive/dataTables.responsive.css" rel="stylesheet">

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

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            課題一覧 <small>あなたへの課題の一覧です</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-pencil-square" ></i> 課題一覧
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
<%
List<TaskDto> taskList = (List<TaskDto>)request.getAttribute(RequestConst.REQUEST_TASK_LIST);
LogonInfoDTO loginInfo = (LogonInfoDTO)session.getAttribute(SessionConst.SESSION_LOGININFO);
%>
                <div class="row">
	                <div class="col-lg-12">
	                	<div class="panel panel-info">
	                		<div class="panel-heading">
	                			課題一覧
	                		</div>
		                	<div class="panel-body">
		 <%
		 if( CollectionUtils.isEmpty(taskList) ){
		 %>
			 課題はありません
		 <%
		 }else{
		 %>
		                        <div class="table-responsive">
		                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">

		                                <thead>
		                                    <tr class="info">
		                                        <th>課題名</th>
		                                        <th>難易度</th>
		                                        <th>状態</th>
		                                        <th>締め切り</th>
		                                        <th>必須</th>
		                                        <th>得点</th>
		                                    </tr>
		                                </thead>
		                                <tbody id="table-body">
		                                <% for( TaskDto taskDto : taskList ){ %>
		                                    <tr>

		                                        <td>
		                                        <a href="task?taskid=<%=taskDto.getTaskId()%>">
		                                        <%= taskDto.getTaskName() %>
		                                        </a>
		                                        </td>
												<td>
		                                        	<%= Difficalty.search(taskDto.getDifficalty()).getMsg() %>
												</td>
		                                        <% if( taskDto.getResult() != null ){ %>
		                                        	<% if(  taskDto.getResult().isHanded()){ %>
		                                        	<td>提出済み<br>（<%=taskDto.getResult().getHandedDate() %> 提出）</td>
		                                        	<% }else{ %>
		                                        	<td>不正解</td>
		                                        	<%} %>
		                                        <% }else{ %>
		                                        	<td>未提出</td>
		                                        <% } %>

		                                        <% if( taskDto.getTerminationDate() != null ){ %>
		                                        	<td><%=taskDto.getTerminationDate() %></td>
		                                        <% }else{ %>
		                                        	<td>なし</td>
		                                        <% } %>

		                                        <% if( taskDto.isRequiredFlg() ){ %>
		                                        	<td>必須</td>
		                                        <% }else{ %>
		                                        	<td>任意</td>
		                                        <% } %>

		                                        <% if( taskDto.getResult() != null ){ %>
		                                        	<td>
		                                        	<a href="scoredetail?<%=RequestConst.REQUEST_DISP_NO%>=list&<%=RequestConst.REQUEST_TASK_ID%>=<%=taskDto.getTaskId()%>"><%= taskDto.getResult().getTotal() %></a>
		                                        	</td>
		                                        <% }else{ %>
		                                        	<td>&nbsp;</td>
		                                        <% } %>
		                                    </tr>
		                                <% } %>
		                                </tbody>
		                            </table>
		                        </div>

		 <%
		 }
		 %>
		                </div>
	                </div><!-- panel body -->
				  </div><!-- panel -->
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

    <!-- DataTables JavaScript -->
    <script src="view/datatables/js/jquery.dataTables.min.js"></script>
    <script src="view/datatables-plugins/dataTables.bootstrap.min.js"></script>
    <script src="view/datatables-responsive/dataTables.responsive.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="view/js/sb-admin-2.js"></script>
<script>


$(document).ready(function() {

    ddd = $('#dataTables-example').DataTable({
    	"oLanguage": {
	        "sLengthMenu": "表示行数 _MENU_ 件",
	        "oPaginate": {
	            "sNext": "次のページ",
	            "sPrevious": "前のページ"
	        },
	        "sInfo": "全_TOTAL_件中 _START_件から_END_件を表示",
	        "sSearch": "検索："
	    },
        responsive: true,
        columnDefs: [
                     // 2列目(0から始まるため1になっています)の幅を100pxにする
                     { targets: 1, width: 120 },
                     { targets: 2, width: 100 }
                 ]

    });
});
</script>
</body>

</html>
