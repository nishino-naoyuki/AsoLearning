<!DOCTYPE html>
<html lang="en">

<head>

<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="jp.ac.asojuku.asolearning.param.RequestConst" %>
<%@ page import="java.util.List" %>
<%@ page import="jp.ac.asojuku.asolearning.param.*" %>
<%@ page import="jp.ac.asojuku.asolearning.dto.*" %>
<%@ page import="jp.ac.asojuku.asolearning.util.*" %>
<%@ page import="org.apache.commons.collections4.*" %>
	<LINK REL="SHORTCUT ICON" HREF="view/ico/favicon.ico">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>ランキング画面</title>

    <link href="view/css/main.css" rel="stylesheet">
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
                <jsp:include page="/view/header/title.jsp"/>
            </div>
            <!-- /.navbar-header -->
            <jsp:include page="/view/header/header.jsp"/>
            <!-- /.navbar-top-links -->
			<jsp:include page="/view/sidebar/sidebar.jsp"/>

            <!-- /.navbar-static-side -->
        </nav>

<%
List<CourseDto> courseList = (List<CourseDto>)request.getAttribute(RequestConst.REQUEST_COURSE_LIST);
List<RankingDto> rankingList = (List<RankingDto>)request.getAttribute(RequestConst.REQUEST_RANKING_LIST);
List<TaskDto> taskList = (List<TaskDto>)request.getAttribute(RequestConst.REQUEST_TASK_LIST);
Integer courseId = (Integer)request.getAttribute(RequestConst.REQUEST_COURSE_ID);
Integer taskId = (Integer)request.getAttribute(RequestConst.REQUEST_TASK_ID);
LogonInfoDTO loginInfo = (LogonInfoDTO)session.getAttribute(SessionConst.SESSION_LOGININFO);

if( courseId == null){
	courseId = -1;
}
if( taskId == null){
	taskId = -1;
}
%>
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            ランキング
                        </h1>
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-graduation-cap"></i> ランキング
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->

				<form action="ranking" method="GET">
                <div class="row">
                    <div class="col-lg-12">
	                	<div class="panel panel-primary">
	                		<div class="panel-heading">
	                			検索条件
	                		</div>
		                	<div class="panel-body">
			                   <div class="col-lg-4">
		                    		学科
				                     <select id="couse" class="form-control" name="<%=RequestConst.REQUEST_COURSE_ID%>">
				                         <option value="" >すべて</option>
				                     <% for(CourseDto course :courseList){ %>
				                         <option value="<%=course.getId() %>" <%= (courseId == course.getId() ? "selected":"" ) %>><%=course.getName() %></option>
				                     <%} %>
				                     </select>
			                	</div>
			                     <div class="col-lg-4">
			                    	課題
				                     <select id="task" class="form-control" name="<%=RequestConst.REQUEST_TASK_ID%>">
				                         <option value="" >全て</option>
					                     <% for(TaskDto taskDto : taskList ){ %>
				                         	 <% if( taskDto != null){ %>
					                         <option value="<%=taskDto.getTaskId() %>" <%= (taskId == taskDto.getTaskId() ? "selected":"" ) %> ><%=taskDto.getTaskName() %></option>
					                         <%} %>
					                     <%} %>
				                     </select>
			                	</div>
			                     <div class="col-lg-4">
			                     	<button type="submit"  class="btn btn-default">表示</button>
			                     </div>
		                     </div>
		                </div>
	            	</div>
                </div>
                </form>
                <div class="row">
                    <div class="col-lg-4">
                    <h1></h1>
                    </div>
                </div>
<% if( CollectionUtils.isEmpty(rankingList) ){ %>
                <div class="row">
                    <div class="col-lg-4">
                    <h1>検索結果０件です</h1>
                    </div>
                </div>
<% }else{ %>
                <div class="row">
                    <div class="col-lg-12">
	                	<div class="panel panel-info">
	                		<div class="panel-heading">
	                			検索結果
	                		</div>
		                	<div class="panel-body">
		                        <div class="table-responsive">
		                            <table class="table table-striped table-bordered table-hover" id="dataTables-example">
		                                <thead>
		                                    <tr class="info">
		                                        <th>
		                                        RANK
		                                        </th>
		                                        <th>
		                                        学科
		                                        </th>
		                                        <th>
		                                        学年
		                                        </th>
		                                        <th>
		                                        学籍番号
		                                        </th>
		                                        <th>
		                                        ニックネーム
		                                        </th>
		                                        <th>
		                                        点数
		                                        </th>
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                <% for(RankingDto ranking : rankingList){ %>
		                                    <tr>
		                                        <td><%=ranking.getRank() %></td>
		                                        <td><%=ranking.getCourseName() %></td>
		                                        <td><%=ranking.getGrade() %></td>
		                                        <td>
		                                        <% if( RoleId.STUDENT.equals(loginInfo.getRoleId())){ %>
		                                        <%=ranking.getName() %>
		                                        <% }else{ %>
		                                        <a href='userDetail?userId=<%=ranking.getUserId()%>'><%=ranking.getName() %></a>
		                                        <% } %>
		                                        </td>
		                                        <td><%=ranking.getNickName() %></td>
		                                        <td><%=ranking.getScore() %></td>
		                                    </tr>
		                                <% } %>
		                                </tbody>
		                            </table>
		                        </div>
		                     </div>
		                 </div>
	                 </div>
                </div>
                <!-- /.row -->
<% } %>
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
$(function(){
    $("#couse").change(function(){
        var value = $("#couse option:selected").val();
		var params = "<%=RequestConst.REQUEST_COURSE_ID%>="+value;

	    $.ajax({
	        type : 'GET',
	        url : "rankingcousechange",
	        data : params,
	        dataType : 'json',
	        processData : false,
	        contentType : false,
	        timeout : 360000, // milliseconds

	    }).done(function(json) {
	    	//var obj = $.parseJSON(json);
            $("#task").html("");
            $("#task").append("<option value=>全て</option>");
            for(var i=0;i<json.length;i++){
                $("#task").append("<option value="+json[i].itemValue+">"+json[i].itemLabel+"</option>");
            }


	    }).fail(function(XMLHttpRequest, textStatus, errorThrown) {

	    	alert("err:"+textStatus);
	        console.log( textStatus  + errorThrown);
	    });

       // $.get("rankingcousechange/" + value ,function(data){
       // 	alert(data);
       //     var obj = $.parseJSON(data);
       //     $("#task").html("");
       //     for(var i=0;i<obj.length;i++){
       //         $("#task").append("<option value="+obj[i].itemValue+">"+obj[i].itemLabel+"</option>");
       //     }
       // })
    })
})
    $(document).ready(function() {
    	// デフォルトの設定を変更
        $.extend( $.fn.dataTable.defaults, {
            language: {
                url: "http://cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Japanese.json"
            }
        });
        $('#dataTables-example').DataTable({
            responsive: true,
            // ソート機能 無効
            ordering: false,

        });
    });
</script>
</body>

</html>
