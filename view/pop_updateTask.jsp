<!DOCTYPE html>
<html lang="en">

<head>

<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="jp.ac.asojuku.asolearning.param.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
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
    <link rel="stylesheet" href="view/css/jquery-ui.css" >

<script>
var testcase_cnt = 0;	//テストケースの数。初期値は0
</script>
<%
	//エラー情報を取得する
	ActionErrors errors = (ActionErrors)request.getAttribute(RequestConst.REQUEST_ERRORS);
	TaskDto taskDto = (TaskDto)request.getAttribute(RequestConst.REQUEST_TASK_DTO);
	List<TaskPublicDto> taskPublicList = (taskDto == null ? null :taskDto.getTaskPublicList());
	List<TaskTestCaseDto> testCaseList = (taskDto == null ? null :taskDto.getTaskTestCaseDtoList());
%>

</head>

<body>
    <div id="wrapper">
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <!-- Page Heading -->
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
	                                 <% List<CourseDto> list = (List<CourseDto>)request.getAttribute(RequestConst.REQUEST_COURSE_LIST); %>
	                                 <% for(CourseDto dto : list ){ %>
	                                 <%
	                                 	//検索して一致するデータを採ってくる
	                                 	TaskPublicDto pubDto = null;
	                                 	if( taskPublicList != null ){
	                                 		for( TaskPublicDto pubDtoWk : taskPublicList){
		                                 		if( dto.getId() == pubDtoWk.getCourseId()){
		                                 			pubDto = pubDtoWk;
		                                 			break;
		                                 		}
	                                 		}
	                                 	}
	                                 %>
	                                	<tr>
	                                		<td>
	                                			<%=dto.getName()%>
	                                		</td>
	                                		<td>
	                                            <select class="form-control" name="<%=dto.getId()%>-course">
	                                                <option value="<%=TaskPublicStateId.PRIVATE.getId()%>" <%=(pubDto==null? "selected":(pubDto.getStatus()==TaskPublicStateId.PRIVATE? "selected":""))%>><%=TaskPublicStateId.PRIVATE.getMsg1()%></option>
	                                                <option value="<%=TaskPublicStateId.PUBLIC_MUST.getId()%>" <%=(pubDto==null? "":(pubDto.getStatus()==TaskPublicStateId.PUBLIC_MUST? "selected":""))%>><%=TaskPublicStateId.PUBLIC_MUST.getMsg1()%></option>
	                                                <option value="<%=TaskPublicStateId.PUBLIC.getId()%>" <%=(pubDto==null? "":(pubDto.getStatus()==TaskPublicStateId.PUBLIC? "selected":""))%>><%=TaskPublicStateId.PUBLIC.getMsg1()%></option>
	                                            </select>
	                                		</td>
	                                		<!--
	                                		<td>
	                                			<div class="form-group">
	                                			<input type="text" name="<%=dto.getId()%>-startterm" placeholder="" id="datepicker1-<%=dto.getId()%>" value="<%= (pubDto==null? "":pubDto.getPublicDatetime()) %>">
	                                			</div>
	                                		</td>
	                                		 -->
	                                		<td>
	                                			<div class="form-group">
	                                			<input type="text" name="<%=dto.getId()%>-endterm" placeholder="" id="datepicker2-<%=dto.getId()%>" value="<%= (pubDto==null? "":pubDto.getEndDatetime()) %>">
	                                			</div>
	                                		</td>
	                                	</tr>
	                                <% }%>
	                                </tbody>
	                            </table>
	                        </div>
						</div>
                	</div>
                	<input type="hidden" name="<%=RequestConst.REQUEST_TASKGRP_ID %>" value="<%=(String)request.getAttribute(RequestConst.REQUEST_TASKGRP_ID)%>">
                </div>
                <!-- /.row -->
                <div class="row">
                	 <button id="updateTaskGroup" class="btn"><span class="glyphicon glyphicon-circle-arrow-up" aria-hidden="true"></span> 更新</button>
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

    <!-- Custom Theme JavaScript -->
    <script src="view/js/sb-admin-2.js"></script>

<script src="view/js/jquery-ui.min.js"></script>
<script src="view/js/jquery.ui.datepicker-ja.min.js"></script>
	<script>

		$(function() {
			    $("[id^=datepicker]").datepicker();
		});

		$(function() {
			    $("#datepicker2").datepicker();
		});


		$('#updateTaskGroup').on('click', function() {


			var param = "";
			if( $("input[name='<%=RequestConst.REQUEST_TASKGRP_ID %>']").val() != ""){
				param += "<%=RequestConst.REQUEST_TASKGRP_ID %>="+ $("input[name='<%=RequestConst.REQUEST_TASKGRP_ID %>']").val();
			}
			<% for(CourseDto dto : list ){ %>
			if(param.length>0){ param += "&";}
			param += "<%=dto.getId()%>-course=" + $("[name='<%=dto.getId()%>-course']").val();
			param += "&<%=dto.getId()%>-endterm=" + $("input[name='<%=dto.getId()%>-endterm']").val();
			<%}%>

			//alert(param);
		    $.ajax({
		    	cache: false,
		        type : 'GET',
		        url : "updateTaskGrpUpd",
		        data :param,
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

	</script>
</body>

</html>
