<!DOCTYPE html>
<html lang="en">

<head>

<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="jp.ac.asojuku.asolearning.param.RequestConst" %>
<%@ page import="java.util.List" %>
<%@ page import="jp.ac.asojuku.asolearning.dto.TaskDto" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>課題画面</title>

    <link href="view/css/main.css" rel="stylesheet">
    <!-- Bootstrap Core CSS -->
    <link href="view/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="view/metisMenu/metisMenu.min.css" rel="stylesheet">

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
TaskDto taskdto = (TaskDto)request.getAttribute(RequestConst.REQUEST_TASK);
%>
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            課題
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-pencil-square"></i> <a href="tasklist">課題一覧</a>
                            </li>
                            <li>
                                <i class="fa fa-check-circle-o"></i> <%=taskdto.getTaskName() %>
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->

                <div class="row">
                        <div class="table-responsive">
                            <table class="table table-bordered">
                                <thead>
                                    <tr class="info">
                                        <th>
                                        問題
                                        <% if( taskdto.isRequiredFlg() ){ %>
                                        	（必須）
                                        <% }else{ %>
                                        	（任意）
                                        <% } %>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td><%=taskdto.getQuestion() %></td>

                                    </tr>
                                </tbody>
                            </table>
                        </div>
                </div>
                <!-- /.row -->

                <div class="row">
                	<div id="error">
                		<p id="uploadErrorMsg" ></p>
                	</div>

                        <div class="table-responsive">
                            <table class="table table-bordered">
                                <thead>
                                    <tr class="info">
                                        <th>状態</th>
                                        <th>ファイル</th>
                                        <th>得点</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                        <% if( taskdto.getResult() != null ){ %>
                                        	<p id="status">提出済み</p>
                                        <% }else{ %>
                                        	<p id="status">未提出</p>
                                        <% } %>
								            <button type="submit" id="judge" class="btn"><span class="glyphicon glyphicon-circle-arrow-up" aria-hidden="true"></span> 判定</button>
                                        </td>
                                        <td>
                                        <input type="file" id="file_select" name="javafile" class="form-control" style="display:none;">
                                        <div class="input-group">

								          <span class="input-group-btn">
								            <button type="button" id="file_select_icon" class="btn btn-sm"><span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span></button>
								          </span>
								          <input type="text" id="file_name" class="form-control" placeholder="Select file ..." readonly>

								        </div>

                                        </td>
                                        <td>
                                        <% if( taskdto.getResult() != null ){ %>
                                        	<p id="score"><%= taskdto.getResult().getTotal() %></p>
                                        <% }else{ %>
                                        	&nbsp;
                                        <% } %>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">
                                        この問題のあなたのランキングはXX位
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                </div>
                <!-- /.row -->
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

	<script>
	//アイコンをクリックした場合は、ファイル選択をクリックした挙動とする.
	$('#file_select_icon').on('click', function() {
	  $('#file_select').click();
	});

	// ファイル選択時に表示用テキストボックスへ値を連動させる.
	// ファイル選択値のクリア機能の実装により、#file_select がDOMから消されることがあるので親要素からセレクタ指定でイベントを割り当てる.
	$('#file_select').parent().on('change', '#file_select', function() {
	  // $('#file_name').val($(this).val());
	  $('#file_name').val($('#file_select').prop('files')[0].name);
	});

	$('#judge').on('click', function() {
		var fd = new FormData();
		  if ($("input[name='javafile']").val()!== '') {
		    fd.append( "file", $("input[name='javafile']").prop("files")[0] );
		    fd.append( "taskid", <%=taskdto.getTaskId()%> );
		 }

		submit_action("judgetask",fd,null);
	});
	function submit_action(url, input_data, mode) {


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
	            $("#judge").attr('disabled', true);
	    		dispLoading("判定処理中...");
	        },
	        complete : function(xhr, textStatus) {
	            // allow resubmit
	            $("#judge").attr('disabled', false);
	            removeLoading();
	        }
	    }).done(function(json) {
			//エラーメッセージがある場合はエラーを表示する
    		if( json.errorMsg.length != 0){
	    		$("#uploadErrorMsg").text(json.errorMsg);
    		}else if(json.score > 0){
    			//得点がついた場合は、提出済みにし得点を表示する
	    		$("#status").text("提出済み");
	    		$("#score").text(json.score);
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

	</script>

</body>

</html>
