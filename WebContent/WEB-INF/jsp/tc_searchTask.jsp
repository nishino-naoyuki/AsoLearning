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
    <link href="view/css/typeaheadjs.css" rel="stylesheet">

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
			                    	<div id="scrollable-dropdown-menu">
			                    	課題グループ
				                    	<input type="text" name="groupname" class="typeahead">
				                    </div>
				                </div>
	                        	<div class="form-group">
			                    	課題名
				                    <input type="text" name="taskname" >
				                </div>
	                        	<div class="form-group">
			                    	作者メールアドレス
				                    <input type="text" name="creator" >
				                </div>
	                        	<div class="form-group">
		                    		対象学科
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
		                        	<p><button id="delete_result"  class="btn btn-default"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span> チェックしたデータの解答情報を削除</button></p>
		                        	<p><button id="update_public"  class="btn btn-default"><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span> チェックした課題の公開情報をセット</button></p>
		                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">

		                                <thead>
		                                    <tr class="info">
		                                        <th><input name="allcheck" id="allcheck" type="checkbox"   ></th>
		                                        <th>課題名</th>
		                                        <th>作者</th>
		                                        <th>対象学科</th>
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

    <!-- autocomplete jquery -->
    <script src="view/js/typeahead.bundle.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="view/js/sb-admin-2.js"></script>
<script>


var states = new Bloodhound({
	  datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
	  queryTokenizer: Bloodhound.tokenizers.whitespace,
	  remote: {
	    url: 'searchTaskGrp?groupname=%QUERY',
	    wildcard: '%QUERY'
	  }
	});


$(document).ready(function() {

    $('#dataTables-example').DataTable({
        responsive: true,
        columnDefs: [
                     // 2列目(0から始まるため1になっています)の幅を100pxにする
                     { targets: 1, width: 100 }
                 ]

    });

    $('#scrollable-dropdown-menu .typeahead').typeahead(
    	null,
    	{
    	  name: 'states',
    	  limit: 30,
    	  display: 'value',
    	  source: states
    	});

	});


$('#search').on('click', function() {
	var params = "";

	if( $("input[name='groupname']").val() != ""){
		params += "groupname="+ $("input[name='groupname']").val();
	}
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

	//alert(params);
    $.ajax({
    	cache: false,
        type : 'GET',
        url : "searchTask",
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
    	$('#search_result').html("");
    	for( var i=0; i < json.length; i++){
    		var element;
    		element = json[i];
    		var str  = "<tr>"+
    		           "  <td>"+
    		           "    <div class=\"checkbox\">"+
    		           "      <label>"+
    		           "        <input name=\"chk-"+i+"\" class=\"area\" type=\"checkbox\" value=\""+element.taskId+"\">"+
    		           "      </label>"+
    		           "    </div>"+
    		           "  </td>"+
    		           "  <td>"+
    		           "    <a href='tc_updateTask?taskId="+element.taskId+"'>"+element.taskName+"</a>"+
    		           "  </td>"+
    		           "  <td>"+element.creator+"</td>"+
    		           "  <td>"+element.targetCourseList+"</td>"+
    		           "  <td>"+element.limit+"</td>"+
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
                         { targets: 0, width: 50 }
                     ]

        });

    }).fail(function(XMLHttpRequest, textStatus, errorThrown) {
    	alert("err:"+textStatus);
        console.log( textStatus  + errorThrown);
    });
});


$('#delete_result').on('click', function() {

	if( !confirm("解答情報を削除すると元に戻せません。\n実行してよろしいでしょうか？") ){
		return;
	}
	var taskIds = $('.area:checked').map(function() {
		  return $(this).val();
		}).get();

	if( taskIds.length == 0 ){
		alert("課題を選択してください");
		return;
	}

	var params="taskIds="+taskIds;

    $.ajax({
    	cache: false,
        type : 'GET',
        url : "delResult",
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


$('#update_public').on('click', function() {

	var taskIds = $('.area:checked').map(function() {
		  return $(this).val();
		}).get();

	if( taskIds.length == 0 ){
		alert("課題を選択してください");
		return;
	}

	var params="taskIds="+taskIds;

	window.open("popupUpdatePublicTask?"+params,"WindowName","width=600,height=700,resizable=yes,scrollbars=yes");
});


$('#allcheck').on('click', function() {

	var items = $('#search_result').find('input');
	if($(this).is(':checked')) {
		$(items).prop('checked', true);
	}else{
		$(items).prop('checked', false);
	}
});


</script>
</body>

</html>
