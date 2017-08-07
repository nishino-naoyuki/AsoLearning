<!DOCTYPE html>
<html lang="en">

<head>

<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="jp.ac.asojuku.asolearning.param.*" %>
<%@ page import="java.util.List" %>
<%@ page import="jp.ac.asojuku.asolearning.dto.TaskDto" %>
<%@ page import="jp.ac.asojuku.asolearning.util.*" %>
	<LINK REL="SHORTCUT ICON" HREF="view/ico/favicon.ico">
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
                <jsp:include page="./header/title.jsp"/>
            </div>
            <!-- /.navbar-header -->
            <jsp:include page="./header/header.jsp"/>
            <!-- /.navbar-top-links -->
			<jsp:include page="./sidebar/sidebar.jsp"/>

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
                            <li>
                                <i class="fa fa-pencil-square"></i> <a href="tasklist">課題一覧</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-check-circle-o"></i> <%=taskdto.getTaskName() %>
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->

                <div class="row">
                    <div class="col-lg-12">
                        <div class="table-responsive">
                            <table class="table table-bordered">
                                <thead>
                                    <tr class="info" >
                                        <th>
                                        問題
                                        <% if( taskdto.isRequiredFlg() ){ %>
                                        	（必須）
                                        <% }else{ %>
                                        	（任意）
                                        <% } %>
                                        :難易度 <%= Difficalty.search(taskdto.getDifficalty()).getMsg() %>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td><%=HtmlUtil.nl2be( taskdto.getQuestion() )%></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                     </div>
                </div>
                <!-- /.row -->

                <div class="row">
                    <div class="col-lg-12">
                        <div class="table-responsive">
                            <table class="table table-bordered">
                                <thead>
                                    <tr class="info" >
                                        <th>
                                        締め切り
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
	                                        <% if( taskdto.getTerminationDate() != null ){ %>
	                                        	<%=taskdto.getTerminationDate() %>
	                                        <% }else{ %>
	                                        	なし
	                                        <% } %>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                     </div>
                </div>
                <!-- /.row -->

                <div class="row">
                    <div class="col-lg-12">
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
                                        <p id="status">
                                        <% if( taskdto.getResult() != null ){ %>
                                        	<% if(  taskdto.getResult().isHanded()){ %>
                                        	提出済み
                                        	<% }else{ %>
                                        	不正解
                                        	<%} %>
                                        <% }else{ %>
                                        	未提出
                                        <% } %>
                                        </p>
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
                                        	<p id="score">
                                        <% if( taskdto.getResult() != null ){ %>
                                        	<a href="scoredetail?<%=RequestConst.REQUEST_DISP_NO%>=detail&<%=RequestConst.REQUEST_TASK_ID%>=<%=taskdto.getTaskId()%>"><%= taskdto.getResult().getTotal() %></a>
                                        <% }else{ %>
                                        	&nbsp;
                                        <% } %>
                                        	</p>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">
                                        <% if( taskdto.getRank() != null ){ %>
                                        この問題のあなたのランキングは<%=taskdto.getRank() %>位
                                        <% }else{ %>
                                        	&nbsp;
                                        <% } %>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
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
	  if ($("input[name='javafile']").val()!== '') {
		  $("#judge").attr('disabled', false);
	  }else{
		  $("#judge").attr('disabled', true);
	  }

	//アイコンをクリックした場合は、ファイル選択をクリックした挙動とする.
	$('#file_select_icon').on('click', function() {
	  $('#file_select').click();
	});

	// ファイル選択時に表示用テキストボックスへ値を連動させる.
	// ファイル選択値のクリア機能の実装により、#file_select がDOMから消されることがあるので親要素からセレクタ指定でイベントを割り当てる.
	$('#file_select').parent().on('change', '#file_select', function() {
	  // $('#file_name').val($(this).val());
	  $('#file_name').val($('#file_select').prop('files')[0].name);
	  $("#judge").attr('disabled', false);
	});

	$('#judge').on('click', function() {
		var fd = new FormData();
		  if ($("input[name='javafile']").val()!== '') {
		    fd.append( "file", $("input[name='javafile']").prop("files")[0] );
		    fd.append( "taskid", <%=taskdto.getTaskId()%> );
		 }

		  //一旦エラーメッセージをクリア
		  $("#uploadErrorMsg").text("");
		submit_action("judgetask",fd,null);
	});
	function submit_action(url, input_data, mode) {


	    $.ajax({
	    	cache: false,
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
    		if( json.errorMsg != null && json.errorMsg != ""){
	    		$("#uploadErrorMsg").text(json.errorMsg);
    		}else if(json.score > 0){
    			if( json.allOK ){
	    			//得点がついた場合は、提出済みにし得点を表示する
		    		$("#status").text("提出済み");
    			}else{
		    		$("#status").text("不正解");
    			}
	    		$("#score").html("<a href='scoredetail?<%=RequestConst.REQUEST_DISP_NO%>=detail&<%=RequestConst.REQUEST_TASK_ID%>=<%=taskdto.getTaskId()%>'>"+json.score+"</a>");
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
