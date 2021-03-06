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
                <jsp:include page="./header/title.jsp"/>
            </div>
            <!-- /.navbar-header -->
            <jsp:include page="./header/header.jsp"/>
            <!-- /.navbar-top-links -->
			<jsp:include page="./sidebar/sidebar.jsp"/>

            <!-- /.navbar-static-side -->
        </nav>

<%
List<CourseDto> courseList = (List<CourseDto>)request.getAttribute(RequestConst.REQUEST_COURSE_LIST);
List<RankingDto> rankingList = (List<RankingDto>)request.getAttribute(RequestConst.REQUEST_RANKING_LIST);
List<TaskDto> taskList = (List<TaskDto>)request.getAttribute(RequestConst.REQUEST_TASK_LIST);
List<TaskGroupDto> taskGrpList = (List<TaskGroupDto>)request.getAttribute(RequestConst.REQUEST_TASKGRP_LIST);
Integer courseId = (Integer)request.getAttribute(RequestConst.REQUEST_COURSE_ID);
Integer taskId = (Integer)request.getAttribute(RequestConst.REQUEST_TASK_ID);
Integer taskGrpId = (Integer)request.getAttribute(RequestConst.REQUEST_TASKGRP_ID);
Integer grade = (Integer)request.getAttribute(RequestConst.REQUEST_GRADE);
LogonInfoDTO loginInfo = (LogonInfoDTO)session.getAttribute(SessionConst.SESSION_LOGININFO);

if( courseId == null){
	courseId = -1;
}
if( taskId == null){
	taskId = -1;
}
if( taskGrpId == null){
	taskGrpId = -1;
}
if( grade == null){
	grade = -1;
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
			                   <div class="col-lg-2">
		                    		学年
				                     <select id="grade" class="form-control" name="<%=RequestConst.REQUEST_GRADE%>">
				                         <option value="" >すべて</option>
				                         <option value="1" <%= (grade == 1 ? "selected":"" ) %> >1</option>
				                         <option value="2" <%= (grade == 2 ? "selected":"" ) %> >2</option>
				                         <option value="3" <%= (grade == 3 ? "selected":"" ) %> >3</option>
				                         <option value="4" <%= (grade == 4 ? "selected":"" ) %> >4</option>
				                     </select>
			                	</div>
			                     <div class="col-lg-3">
			                    	課題グループ
				                     <select id="tgroup" class="form-control" name="<%=RequestConst.REQUEST_TASKGRP_LIST%>">
				                         <option value="" >すべて</option>
				                     <% for(TaskGroupDto taskGrp :taskGrpList){ %>
				                         <option value="<%=taskGrp.getId() %>" <%= (taskGrpId == taskGrp.getId() ? "selected":"" ) %>><%=taskGrp.getName() %></option>
				                     <%} %>
				                     </select>
			                	</div>
			                     <div class="col-lg-3">
			                    	課題
				                     <select id="task" class="form-control" name="<%=RequestConst.REQUEST_TASK_ID%>">
				                         <option value="" >すべて</option>
					                     <% for(TaskDto taskDto : taskList ){ %>
				                         	 <% if( taskDto != null){ %>
					                         <option value="<%=taskDto.getTaskId() %>" <%= (taskId == taskDto.getTaskId() ? "selected":"" ) %> ><%=taskDto.getTaskName() %></option>
					                         <%} %>
					                     <%} %>
				                     </select>
			                	</div>
			                     <div class="col-lg-3">
			                     	<button type="submit"  class="btn btn-default">表示</button>
		                        <% if( !RoleId.STUDENT.equals(loginInfo.getRoleId())){ %>
			                     	<button id="create_csv" class="btn btn-default">CSV出力</button>
			                     <% } %>
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
		                                        <td><%=HtmlUtil.nl2be( ranking.getCourseName()) %></td>
		                                        <td><%=ranking.getGrade() %></td>
		                                        <td>
		                                        <a href='userDetail?userId=<%=ranking.getUserId()%>' ><%=ranking.getName() %></a>
		                                        </td>
		                                        <td><%= HtmlUtil.nl2be( ranking.getNickName() ) %></td>
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
	<script type="text/javascript" src="view/js/thumbnail.js"></script>
<script>


$(function(){
	//学科の選択が変わった時
    $("#couse").change(function(){
        var value = $("#couse option:selected").val();
		var params = "<%=RequestConst.REQUEST_COURSE_ID%>="+value;

        //alert("?");
	    $.ajax({
	    	cache: false,
	        type : 'GET',
	        url : "rankingcousechange",
	        data : params,
	        dataType : 'json',
	        processData : false,
	        contentType : false,
	        timeout : 360000, // milliseconds

	    }).done(function(json) {
	    	//var obj = $.parseJSON(json);
            $("#tgroup").html("");
            $("#tgroup").append("<option value=>すべて</option>");
            for(var i=0;i<json.length;i++){
                $("#tgroup").append("<option value="+json[i].itemValue+">"+json[i].itemLabel+"</option>");
            }


	    }).fail(function(XMLHttpRequest, textStatus, errorThrown) {

	    	alert("err:"+textStatus);
	        console.log( textStatus  + errorThrown);
	    });

	    $.ajax({
	    	cache: false,
	        type : 'GET',
	        url : "rankingtgroupchange",
	        data : params,
	        dataType : 'json',
	        processData : false,
	        contentType : false,
	        timeout : 360000, // milliseconds

	    }).done(function(json) {
	    	//var obj = $.parseJSON(json);
            $("#task").html("");
            $("#task").append("<option value=>すべて</option>");
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
    });

	//課題グループの選択が変わった時
    $("#tgroup").change(function(){
        var value = $("#tgroup option:selected").val();
		var params = "<%=RequestConst.REQUEST_TASKGRP_LIST%>="+value;

        //alert("?");
	    $.ajax({
	    	cache: false,
	        type : 'GET',
	        url : "rankingtgroupchange",
	        data : params,
	        dataType : 'json',
	        processData : false,
	        contentType : false,
	        timeout : 360000, // milliseconds

	    }).done(function(json) {
	    	//var obj = $.parseJSON(json);
            $("#task").html("");
            $("#task").append("<option value=>すべて</option>");
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
    });
})

<% if( !RoleId.STUDENT.equals(loginInfo.getRoleId())){ %>
    //CSV出力ボタンをクリックした時
    $("#create_csv").on("click",function(){
        var couseId = $("#couse option:selected").val();
        var taskId = $("#task option:selected").val();
        var taskGrpId = $("#tgroup option:selected").val();

    	//alert("couseId:"+couseId+" taskId:"+taskId);
	    $.ajax({
	    	cache: false,
	        type : "GET",
	        url : "creRankCsv",
	        data : {"couseId":couseId, "taskId":taskId,"taskGrpId":taskGrpId },
	        dataType : 'text',
	        timeout : 360000, // milliseconds
	        beforeSend : function(xhr, settings) {
	            // disturb double submit
	            $("#create_csv").attr('disabled', true);
	        },
	        complete : function(xhr, textStatus) {
	            // allow resubmit
	            $("#create_csv").attr('disabled', false);
	        }

	    }).done(function(respText) {
	    	//alert(respText);
	    	if( respText == "error:nothing"){
	    		alert("出力対象がありません");
	    	}else{
	    		location.href = "dlRankingCSV?fname="+respText;
	    	}

	    }).fail(function(XMLHttpRequest, textStatus, errorThrown) {
	    	//alert(errorThrown);
	        console.log( textStatus  + errorThrown);
	    });

	    return false;
    });
<%}%>
    $(document).ready(function() {

        $('#dataTables-example').DataTable({
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
            // ソート機能 無効
            ordering: false,

        });



    });
</script>
</body>

</html>
