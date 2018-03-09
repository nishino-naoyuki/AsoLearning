<!DOCTYPE html>
<html lang="en">

<head>

<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="jp.ac.asojuku.asolearning.param.RequestConst" %>
<%@ page import="java.util.List" %>
<%@ page import="jp.ac.asojuku.asolearning.dto.*" %>
<%@ page import="jp.ac.asojuku.asolearning.err.*" %>
<%@ page import="jp.ac.asojuku.asolearning.param.*" %>
<%@ page import="java.util.HashMap" %>
	<link rel="shortcut icon" href="view/ico/favicon.ico">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>ダッシュボード</title>

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
LogonInfoDTO loginInfo = (LogonInfoDTO)session.getAttribute(SessionConst.SESSION_LOGININFO);
%>
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            ダッシュボード <small>あなたへのお知らせ</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-pencil-square"></i> ダッシュボード
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
<%
InfomationListDto dto = (InfomationListDto)request.getAttribute(RequestConst.REQUEST_INFO_DTO);
List<String> infoList = dto.getInfoList();
%>
                <div class="row">
                    <div class="col-lg-12">
	                	<div class="panel panel-green">
	                		<div class="panel-heading">
	                			お知らせ一覧
	                		</div>
		                	<div class="panel-body">
<% if( infoList.size() == 0){ %>
お知らせはありません
<% }else{ %>
		                        <div class="table-responsive">
		                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
		                                <tbody id="table-body">
		                                <% for( String msg : infoList){ %>
		                                	<tr>
		                                		<td>
		                                		<%= msg %>
		                                		</td>
		                                	</tr>
		                                <% } %>
		                                </tbody>
		                            </table>
		                        </div>

<% } %>
							</div>
						</div>
					</div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
	                	<div class="panel panel-green">
	                		<div class="panel-heading">
	                			達成度
	                		</div>
		                	<div class="panel-body">
		                        <div class="table-responsive">
		                            <table class="table table-hover" id="form">
			                		<tr>
			                			<td>
			                	  <% if(dto.getTaskNum()>0){ %>
									<div id="container1" style="min-width: 200px; height: 400px; max-width: 100%; margin: 0 auto"></div>
								  <% } %>
			                			</td>
			                			<td>
			                	  <% if(dto.getMustTaskNum()>0){ %>
									<div id="container2" style="min-width: 200px; height: 400px; max-width: 100%; margin: 0 auto"></div>

								  <% } %>
			                			</td>
			                		</tr>
			                	</table>
		                	</div>
		                </div>
                	</div>
                </div>
            </div>

                <div class="row">
                    <div class="col-lg-12">
	                	<div class="panel panel-green">
	                		<div class="panel-heading">
	                			未解答の必須問題
	                		</div>
		                	<div class="panel-body">
<% List<String> unanswerList = dto.getUnAnswerList(); %>
<% if( unanswerList.size() == 0){ %>
未解答の必須問題はありません
<% }else{ %>
		                        <div class="table-responsive">
		                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
		                                <thead>
		                                    <tr class="info">
		                                        <th>未解答必須課題</th>
		                                    </tr>
		                                </thead>
		                                <tbody id="table-body">
		                                <% for( String msg : unanswerList){ %>
		                                	<tr>
		                                		<td>
		                                		<%= msg %>
		                                		</td>
		                                	</tr>
		                                <% } %>
		                                </tbody>
		                            </table>
			                	  </div>
<% } %>
			                </div>
	                	</div>
	                </div>
	            </div>
            </div>
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
	<!-- highcharts -->
	<script src="view/js/highcharts/highcharts.js"></script>
	<script src="view/js/highcharts/modules/exporting.js"></script>
	<script>

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
    });
});

Highcharts.chart('container1', {
    chart: {
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        type: 'pie'
    },
    title: {
        text: '課題の達成度'
    },
    tooltip: {
        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
    },
    plotOptions: {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
                enabled: true,
                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                style: {
                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                }
            }
        }
    },
    series: [{
        name: 'tasks',
        colorByPoint: true,
        data: [{
            name: '提出済み（<%=dto.getFinishTaskNum()%>）',
            y: <%= (double)dto.getFinishTaskNum()/(double)dto.getTaskNum()%>
        }, {
            name: '未提出（<%=dto.getTaskNum()-dto.getFinishTaskNum()%>）',
            y: <%=(1-  (double)dto.getFinishTaskNum()/(double)dto.getTaskNum())%>,
            sliced: true,
            selected: true
        }]
    }]
});

Highcharts.chart('container2', {
    chart: {
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        type: 'pie'
    },
    title: {
        text: '必須課題の達成度'
    },
    tooltip: {
        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
    },
    plotOptions: {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
                enabled: true,
                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                style: {
                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                }
            }
        }
    },
    series: [{
        name: 'tasks',
        colorByPoint: true,
        data: [{
            name: '提出済み(<%=dto.getFinishMustTaskNum()%>)',
            y: <%= (double)dto.getFinishMustTaskNum()/(double)dto.getMustTaskNum()%>
        }, {
            name: '未提出(<%=dto.getMustTaskNum()-dto.getFinishMustTaskNum()%>)',
            y: <%=(1-  (double)dto.getFinishMustTaskNum()/(double)dto.getMustTaskNum())%>,
            sliced: true,
            selected: true
        }]
    }]
});

		</script>
            <!-- /.container-fluid -->

</body>

</html>
