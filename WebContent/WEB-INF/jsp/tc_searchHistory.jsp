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

    <title>履歴検索</title>

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
    <link rel="stylesheet" href="view/css/jquery-ui.css" >

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
                            履歴検索
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-search"></i> 履歴検索
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
<%
List<CourseDto> courseList = (List<CourseDto>)request.getAttribute(RequestConst.REQUEST_COURSE_LIST);
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
		                    		学科
		                    		<select id="couse" class="form-control" name="<%=RequestConst.REQUEST_COURSE_ID%>">
				                         <option value="" >指定なし</option>
				                     <% for(CourseDto course :courseList){ %>
				                         <option value="<%=course.getId() %>" ><%=course.getName() %></option>
				                     <%} %>
				                     </select>
				                </div>
	                        	<div class="form-group">
		                    		ロール
				                     <select id="role" class="form-control" name="<%=RequestConst.REQUEST_ROLE_ID%>">
				                         <option value="" >指定なし</option>
				                         <option value="<%=RoleId.STUDENT.getId() %>"  ><%=RoleId.STUDENT.getMsg() %></option>
				                    	 <option value="<%=RoleId.TEACHER.getId() %>" ><%=RoleId.TEACHER.getMsg() %></option>
				                    	 <option value="<%=RoleId.MANAGER.getId() %>" ><%=RoleId.MANAGER.getMsg() %></option>
				                     </select>
				                </div>
	                        	<div class="form-group">
		                    		メールアドレス（部分一致検索）
				                     <input type="text" name="mail" placeholder="メールアドレスを入力してください（部分一致）" value="">
				                </div>
	                        	<div class="form-group">
		                    		動作内容
				                     <select id="action" class="form-control" name="<%=RequestConst.REQUEST_ACTION_ID%>">
				                         <option value="" >指定なし</option>
				                         <% for(ActionId actionId : ActionId.values()){ %>
				                        	 <option value="<%=actionId.getId() %>"  ><%=actionId.getName() %></option>
				                         <% } %>
				                     </select>
				                </div>
	                        	<div class="form-group">
		                    		ログ時間
		                    		<input type="text" name="dispTermFrom" placeholder="" id="datepicker2-from" >
		                    		～
		                    		<input type="text" name="dispTermTo" placeholder="" id="datepicker2-to" >
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
		                                        <th>時間</th>
		                                        <th>学科</th>
		                                        <th>メールアドレス</th>
		                                        <th>操作</th>
		                                        <th>備考</th>
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
	<script src="view/js/jquery-ui.min.js"></script>
	<script src="view/js/jquery.ui.datepicker-ja.min.js"></script>
<script>

$(document).ready(function() {


    $('#dataTables-example').DataTable({
        responsive: true,
        columnDefs: [
                     // 2列目(0から始まるため1になっています)の幅を100pxにする
                     { targets: 1, width: 100 }
                 ]

    });
});

$('#create').on('click', function() {
	location.href = "tc_createInfo";
});

$('#search').on('click', function() {
	var params = "";


	if( $("select[name='<%=RequestConst.REQUEST_COURSE_ID%>']").val() != ""){
		params += "<%=RequestConst.REQUEST_COURSE_ID%>="+ $("select[name='<%=RequestConst.REQUEST_COURSE_ID%>']").val();
	}
	if( $("select[name='<%=RequestConst.REQUEST_ROLE_ID%>']").val() != ""){
		if(params.length>0){ params += "&";}
		params += "<%=RequestConst.REQUEST_ROLE_ID%>="+ $("select[name='<%=RequestConst.REQUEST_ROLE_ID%>']").val();
	}
	if( $("select[name='<%=RequestConst.REQUEST_ACTION_ID%>']").val() != ""){
		if(params.length>0){ params += "&";}
		params += "<%=RequestConst.REQUEST_ACTION_ID%>="+ $("select[name='<%=RequestConst.REQUEST_ACTION_ID%>']").val();
	}
	if( $("input[name='dispTermFrom']").val() != ""){
		if(params.length>0){ params += "&";}
		params += "dispTermFrom="+ $("input[name='dispTermFrom']").val();
	}
	if( $("input[name='dispTermTo']").val() != ""){
		if(params.length>0){ params += "&";}
		params += "dispTermTo="+ $("input[name='dispTermTo']").val();
	}
	if( $("input[name='mail']").val() != ""){
		if(params.length>0){ params += "&";}
		params += "mail="+ $("input[name='mail']").val();
	}

	if( $("select[name='<%=RequestConst.REQUEST_COURSE_ID%>']").val() != ""){
		if(params.length>0){ params += "&";}
		params += "<%=RequestConst.REQUEST_COURSE_ID%>="+ $("select[name='<%=RequestConst.REQUEST_COURSE_ID%>']").val();
	}


	//alert(params);
    $.ajax({
    	cache: false,
        type : 'GET',
        url : "searchHistoryAjax",
        data :params,
        dataType : 'json',
        processData : false,
        contentType: "text/html; charset=UTF-8",
        timeout : 360000, // milliseconds

    }).done(function(json) {

    	if( json.length > 1000 ){
    		if(!window.confirm("検索結果が1000件以上あり、表示に時間がかかります。\n表示しますか？")){
    			return;
    		}
    	}

    	if( json.length == 0 ){
    		$("#search_result_row").hide();
    		$("#search_nodata").show();
    	}else{
    		$("#search_result_row").show();
    		$("#search_nodata").hide();
    	}


    	$('#dataTables-example').DataTable().destroy();
    	$('#search_result').html("");
    	for( var i=0; i < json.length; i++){
    		var element;
    		var handedTask = "";
    		element = json[i];
 			for( var str in element.handedTask){
 				handedTask = str + " ";
 			}
 			if( handedTask.length ==0 ) handedTask = "&nbsp;";
    		var str  =
    			"<tr>"+
    			"  <td>"+element.logDate+"</td>"+
    			"  <td>"+element.courseName+"</td>"+
    			"  <td>"+element.mailAddress+"</td>"+
    			"  <td>"+element.actionName+"</td>"+
    			"  <td>"+element.message+"</td>"+
    			"</tr>";
    		//alert(str);
    		$('#search_result').append(str);
    	}
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
            columnDefs: [
                         // 2列目(0から始まるため1になっています)の幅を100pxにする
                         { targets: 0, width: 80 }
                     ]

        });

    }).fail(function(XMLHttpRequest, textStatus, errorThrown) {
    	alert("err:"+textStatus);
        console.log( textStatus  + errorThrown);
    });



});

$(function() {
	$("#datepicker2-from").datepicker();
});
$(function() {
    $("#datepicker2-to").datepicker();
});
</script>
</body>

</html>
