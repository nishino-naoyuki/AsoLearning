<!DOCTYPE html>
<html lang="en">

<head>

<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="jp.ac.asojuku.asolearning.param.RequestConst" %>
<%@ page import="jp.ac.asojuku.asolearning.param.TaskPublicStateId" %>
<%@ page import="java.util.List" %>
<%@ page import="jp.ac.asojuku.asolearning.dto.*" %>
<%@ page import="jp.ac.asojuku.asolearning.err.*" %>
<%@ page import="jp.ac.asojuku.asolearning.param.*" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>学生作成</title>

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
    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/redmond/jquery-ui.css" >

<script>
var testcase_cnt = 0;	//テストケースの数。初期値は0
</script>
<%
//エラー情報を取得する
ActionErrors errors = (ActionErrors)request.getAttribute(RequestConst.REQUEST_ERRORS);

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
				<form action="tc_confirmTask" enctype="multipart/form-data" method="post" >
                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            学生作成

                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="glyphicon glyphicon-file"></i> CSV登録
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
                <div class="row">
                <%if( errors != null && errors.isHasErr() ){%>
                	<% for( ActionError err : errors.getList() ){ %>
							<div id="error"><%= err.getMessage() %></div>
                	<% } %>
                <% } %>

                	<div class="panel panel-default">
                		<div class="panel-heading">
                			CSV登録
                		</div>
	                	<div class="panel-body">
	                        <div class="table-responsive">
	                            <table class="table table-bordered table-hover" id="form">
	                                <tbody>
	                                	<tr>
	                                		<th>CSVファイル名</th>
	                                		<td>
	                                		<div class="form-group">

		                                        <input type="file" id="csvfile" name="csvfile" class="form-control" style="display:none;">
		                                        <div class="input-group">
										          <span class="input-group-btn">
										            <button type="button" id="csvfile_select_icon" class="btn btn-sm"><span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span></button>
										          </span>
										          <input type="text" id="csvfile_name" name="csvfile_name" class="form-control" placeholder="Select file ..." readonly>

										        </div>
	                                		</div>
	                                		</td>
	                                	</tr>
	                                	<tr>
	                                		<td colspan="2">
	                                        <div class="form-group">
	                                            <label>種類</label>
	                                            <div class="radio">
	                                                <label>
	                                                    <input type="radio" name="rdoType" id="rdoInsertUpdate" value="rdoInsertUpdate" checked>追加・更新
	                                                </label>
	                                            </div>
	                                            <div class="radio">
	                                                <label>
	                                                    <input type="radio" name="rdoType" id="rdoDelete" value="rdoDelete">削除
	                                                </label>
	                                            </div>
	                                            <div class="radio">
	                                                <label>
	                                                    <input type="radio" name="rdoType" id="rdoGraduate" value="rdoGraduate">卒業処理
	                                                </label>
	                                            </div>
	                                            <div class="radio">
	                                                <label>
	                                                    <input type="radio" name="rdoType" id="rdoBye" value="rdoBye">退学処理
	                                                </label>
	                                            </div>
	                                        </div>

	                                		</td>
	                                	</tr>
	                                </tbody>
	                            </table>
	                        </div>
	                		<p>
	                		<button type="button" id="startCSV" class="btn btn-default"><span class="glyphicon glyphicon-file" aria-hidden="true"></span> CSV登録 </button>
	                        </p>
						</div>
                	</div>
                </div>
                <!-- /.row -->

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

<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/i18n/jquery.ui.datepicker-ja.min.js"></script>
	<script>
	var uuid = getUniqueStr();

	//アイコンをクリックした場合は、ファイル選択をクリックした挙動とする.
	$('#csvfile_select_icon').on('click', function() {
	  $('#csvfile').click();
	});


	// ファイル選択時に表示用テキストボックスへ値を連動させる.
	// ファイル選択値のクリア機能の実装により、#file_select がDOMから消されることがあるので親要素からセレクタ指定でイベントを割り当てる.
	$('#csvfile').parent().on('change', '#csvfile', function() {

	  $('#csvfile_name').val($('#csvfile').prop('files')[0].name);
	});

	$('#startCSV').on('click', function() {

		var fd = new FormData();
		  if ($("input[name='csvfile']").val()!== '') {
		    fd.append( "file", $("input[name='csvfile']").prop("files")[0] );
		    fd.append( "uuid", uuid );
		    fd.append( "rdoType", $("input[name='rdoType']").val() );
		 }

		submit_action("userCSVProcess",fd,null);
	});
	function submit_action(url, input_data, mode) {

		if( !confirm("処理を開始します。途中で中断はできません。\nよろしいですか？")){
			return;
		}

	    $.ajax({
	        type : 'POST',
	        url : url,
	        data : input_data,
	        dataType : 'json',
	        processData : false,
	        contentType : false,
	        timeout : 360000, // milliseconds

	        beforeSend : function(xhr, settings) {
	            // disturb double submit
	            $("#startCSV").attr('disabled', true);
	    		dispLoading("CSV登録処理中...");
	        },
	        complete : function(xhr, textStatus) {
	            // allow resubmit
	            $("#startCSV").attr('disabled', false);
	            removeLoading();
	        }
	    }).done(function(json) {
			//エラーメッセージがある場合はエラーを表示する
    		if( json.errorMsg.length != 0){
	    		$("#uploadErrorMsg").text(json.errorMsg);
    		}else if(json.score > 0){
    			//得点がついた場合は、提出済みにし得点を表示する
	    		$("#status").text("提出済み");
	    		$("#score").text(json.score+"点");
    		}

	    }).fail(function(XMLHttpRequest, textStatus, errorThrown) {
	    	removeLoading();
	    	alert("err:"+textStatus);
	        console.log( textStatus  + errorThrown);
	    });
	};

	// Loadingイメージ表示関数
	function dispLoading(msg){
	    // 画面表示メッセージ
	    var dispMsg = "";

	    // 引数が空の場合は画像のみ
	    if( msg != "" ){
	        dispMsg = "<div class='loadingMsg'>" + msg + "</div>";
	    }
	    // ローディング画像が表示されていない場合のみ表示
	    //if($("#loading").size() == 0){
	        $("body").append("<div id='loading'>" + dispMsg + "</div>");
	    //}
	}

	// Loadingイメージ削除関数
	function removeLoading(){
	 $("#loading").remove();
	}

	//ユニークなIDを取得する
	function getUniqueStr(){
		 var strong = 1000;
		 return new Date().getTime().toString(16)  + Math.floor(strong*Math.random()).toString(16)
	}

	</script>
</body>

</html>
