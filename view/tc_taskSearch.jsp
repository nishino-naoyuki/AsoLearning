<!DOCTYPE html>
<html lang="en">

<head>

<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="jp.ac.asojuku.asolearning.param.RequestConst" %>
<%@ page import="java.util.List" %>
<%@ page import="jp.ac.asojuku.asolearning.dto.*" %>
<%@ page import="jp.ac.asojuku.asolearning.param.*" %>
<%@ page import="org.apache.commons.collections4.*" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>課題検索</title>

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

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            課題検索
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-pencil-square"></i> 課題検索
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
<%
List<CourseDto> courseList = (List<CourseDto>)request.getAttribute(RequestConst.REQUEST_COURSE_LIST);
List<TaskDto> taskList = (List<TaskDto>)request.getAttribute(RequestConst.REQUEST_TASK_LIST);
LogonInfoDTO loginInfo = (LogonInfoDTO)session.getAttribute(SessionConst.SESSION_LOGININFO);
%>
                <div class="row">
                    <div class="col-lg-12">
	                	<div class="panel panel-primary">
	                		<div class="panel-heading">
	                			検索条件
	                		</div>
		                	<div class="panel-body">
	                        	<div class="form-group">
			                    	課題名
				                    <input type="text" name="taskname" >
				                </div>
	                        	<div class="form-group">
			                    	作者名
				                    <input type="text" name="creator" >
				                </div>
	                        	<div class="form-group">
		                    		学科
		                    		<select id="couse" class="form-control" name="<%=RequestConst.REQUEST_COURSE_ID%>">
				                         <option value="" >すべて</option>
				                     <% for(CourseDto course :courseList){ %>
				                         <option value="<%=course.getId() %>" ><%=course.getName() %></option>
				                     <%} %>
				                     </select>
				                </div>
			                     <div class="col-lg-12">
			                     	<button id="search"  class="btn btn-default">検索</button>
			                     </div>
		                     </div>
		                </div>
	            	</div>
                </div>

                <div class="row" id="search_nodata" style="display:none">
                検索結果０件
                </div>

                <div class="row" id="search_result_row" style="display:none">
	                <div class="col-lg-12">
	                	<div class="panel panel-info">
	                		<div class="panel-heading">
	                			検索結果
	                		</div>
		                	<div class="panel-body">
		                        <div class="table-responsive">
		                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">

		                                <thead>
		                                    <tr class="info">
		                                        <th>課題名</th>
		                                        <th>作者</th>
		                                        <th>対象学科</th>
		                                        <th>公開開始日</th>
		                                        <th>締切</th>
		                                    </tr>
		                                </thead>
		                                <tbody id="search_result">
		                                </tbody>
		                            </table>
		                        </div>


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
	// デフォルトの設定を変更
    $.extend( $.fn.dataTable.defaults, {
        language: {
            url: "http://cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Japanese.json"
        }
    });

    $('#dataTables-example').DataTable({
        responsive: true,
        columnDefs: [
                     // 2列目(0から始まるため1になっています)の幅を100pxにする
                     { targets: 1, width: 100 }
                 ]

    });
});


$('#search').on('click', function() {
	var fd = new FormData();

	fd.append( "taskname", $("input[name='taskname']").val() );
	fd.append( "creator", $("input[name='creator']").val() );
	fd.append( "<%=RequestConst.REQUEST_COURSE_ID%>", $("input[name='<%=RequestConst.REQUEST_COURSE_ID%>']").val() );

	submit_action("searchTask",fd,null);
});
function submit_action(url, input_data, mode) {
	var params = "";

	if( $("input[name='taskname']").val() != ""){
		params += "taskname="+ $("input[name='taskname']").val();
	}
	if( $("input[name='creator']").val() != ""){
		if(params.length>0){ params += "&";}
		params += "creator="+ $("input[name='creator']").val();
	}
	if( $("select[name='<%=RequestConst.REQUEST_COURSE_ID%>']").val() != 0){
		if(params.length>0){ params += "&";}
		params += "<%=RequestConst.REQUEST_COURSE_ID%>="+ $("select[name='<%=RequestConst.REQUEST_COURSE_ID%>']").val();
	}

	alert(params);
    $.ajax({
        type : 'GET',
        url : url,
        data :params,
        dataType : 'json',
        processData : false,
        contentType: "text/html; charset=UTF-8",
        timeout : 360000, // milliseconds

    }).done(function(json) {

    	if( json.length == 0 ){
    		$("#search_result_row").hide();
    		$("#search_nodata").show();
    	}else{
    		$("#search_result_row").show();
    	}

    	$('#dataTables-example').DataTable().destroy();
    	for( var i=0; i < json.length; i++){
    		var element;
    		element = json[i];
    		var str  = "<tr><td><a href='tc_updateTask?taskId="+element.taskId+"'>"+element.taskName+"</a></td><td>"+element.creator+"</td><td>"+element.targetCourseList+"</td><td>"+element.limit+"</td></tr>";
    		//alert(str);
    		$('#search_result').append(str);
    	}
        $('#dataTables-example').DataTable({
            responsive: true,
            columnDefs: [
                         // 2列目(0から始まるため1になっています)の幅を100pxにする
                         { targets: 1, width: 100 }
                     ]

        });

    }).fail(function(XMLHttpRequest, textStatus, errorThrown) {
    	alert("err:"+textStatus);
        console.log( textStatus  + errorThrown);
    });
};

</script>
</body>

</html>
