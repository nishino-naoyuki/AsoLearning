<!DOCTYPE html>
<html lang="en">

<head>

<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="jp.ac.asojuku.asolearning.param.*" %>
<%@ page import="java.util.List" %>
<%@ page import="jp.ac.asojuku.asolearning.dto.*" %>
<%@ page import="jp.ac.asojuku.asolearning.err.*" %>
	<LINK REL="SHORTCUT ICON" HREF="view/ico/favicon.ico">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>お知らせ作成</title>

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
	InfomationDto infoDto = (InfomationDto)request.getAttribute(RequestConst.REQUEST_INFO_DTO);
	List<InfoPublicDto> infoPublicList = (infoDto == null ? null :infoDto.getInfoPublicList());
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
				<form action="tc_confirmInfo" enctype="multipart/form-data" method="post" >
                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            お知らせ作成</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-search"> <a href="searchInfo">お知らせ検索</a>
                                </i>
                            </li>
                            <li class="active">
                                <i class="fa fa-pencil-square"></i> お知らせ作成
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                <div class="row">
                    <div class="col-lg-12">
                <%if( errors != null && errors.isHasErr() ){%>
                	<% for( ActionError err : errors.getList() ){ %>
							<div id="error"><%= err.getMessage() %></div>
                	<% } %>
                <% } %>
	                	<div class="panel panel-default">
	                		<div class="panel-heading">
	                			お知らせ内容
	                		</div>
		                	<div class="panel-body">
		                        <div class="table-responsive">
		                            <table class="table table-bordered table-hover" id="form">
		                                <tbody>
		                                	<tr>
		                                		<th>タイトル[必須]</th>
		                                		<td>
		                                		<div class="form-group">
		                                			<input type="text" name="title" placeholder="タイトルを記載してください" value="<%= (infoDto!=null ? infoDto.getInfomationTitle():"") %>" class="form-control">
		                                		</div>
		                                		</td>
		                                	</tr>
		                                	<tr>
		                                		<th>内容[必須]</th>
		                                		<td>
		                                		<div class="form-group">
		                                			<input type="text" name="contents" placeholder="内容を記載してください" value="<%= (infoDto!=null ? infoDto.getContents():"") %>" class="form-control">
		                                		</div>
		                                		</td>
		                                	</tr>
		                                </tbody>
		                            </table>
		                        </div>
							</div>
	                	</div>
                	</div>
                </div>
                <!-- /.row -->

                <div class="row">
                    <div class="col-lg-12">
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
		                                        <th>公開開始</th>
		                                        <th>公開終了</th>
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                 <% List<CourseDto> list = (List<CourseDto>)request.getAttribute(RequestConst.REQUEST_COURSE_LIST); %>
		                                 <% for(CourseDto dto : list ){ %>
		                                 <%
		                                 	//検索して一致するデータを採ってくる
		                                 	InfoPublicDto pubDto = null;
		                                 	if( infoPublicList != null ){
		                                 		for( InfoPublicDto pubDtoWk : infoPublicList){
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
		                                                <option value="<%=InfoPublicStateId.PRIVATE.getId()%>" <%=(pubDto==null? "":(pubDto.getStatus()==InfoPublicStateId.PRIVATE? "selected":""))%>><%=InfoPublicStateId.PRIVATE.getMsg1()%></option>
		                                                <option value="<%=InfoPublicStateId.PUBLIC.getId()%>" <%=(pubDto==null? "":(pubDto.getStatus()==InfoPublicStateId.PUBLIC? "selected":""))%>><%=InfoPublicStateId.PUBLIC.getMsg1()%></option>
		                                            </select>
		                                		</td>
		                                		<td>
		                                			<div class="form-group">
		                                			<input type="text" name="<%=dto.getId()%>-startterm" placeholder="" id="datepicker1-<%=dto.getId()%>" value="<%= (pubDto==null? "":pubDto.getPublicDatetime()) %>">
		                                			</div>
		                                		</td>
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
                	</div>
                </div>
                <!-- /.row -->
                <div class="row">
                	 <button type="submit" class="btn"><span class="glyphicon glyphicon-circle-arrow-up" aria-hidden="true"></span> 確認</button>
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

<script src="view/js/jquery-ui.min.js"></script>
<script src="view/js/jquery.ui.datepicker-ja.min.js"></script>
	<script>


		$(function() {
			    $("[id^=datepicker]").datepicker();
		});

		$(function() {
			    $("#datepicker1").datepicker();
		});
		$(function() {
			    $("#datepicker2").datepicker();
		});

	</script>
</body>

</html>
