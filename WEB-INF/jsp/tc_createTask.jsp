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

    <title>課題作成</title>

    <!-- Bootstrap Core CSS -->
    <link href="view/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="view/metisMenu/metisMenu.min.css" rel="stylesheet">
    <link href="view/css/typeaheadjs.css" rel="stylesheet">

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
				<form action="tc_confirmTask" enctype="multipart/form-data" method="post" >
                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            <small>課題作成</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-pencil-square"></i> 課題作成
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
	                			概要
	                		</div>
		                	<div class="panel-body">
		                        <div class="table-responsive">
		                            <table class="table table-bordered table-hover" id="form">
		                                <tbody>
		                                	<tr>
		                                		<th>課題グループ[必須]</th>
		                                		<td>
		                                		<div class="form-group">
							                    	<div id="scrollable-dropdown-menu">
								                    	<input type="text" name="groupname" class="typeahead" placeholder="課題グループを選択するか新しく入力してください">
								                    </div>
		                                		</div>
		                                		</td>
		                                	</tr>
		                                	<tr>
		                                		<th>課題名[必須]</th>
		                                		<td>
		                                		<div class="form-group">
		                                			<input type="text" name="taskname" placeholder="課題名を記載してください" value="<%= (taskDto!=null ? taskDto.getTaskName():"") %>" class="form-control" >
		                                		</div>
		                                		</td>
		                                	</tr>
		                                	<tr>
		                                		<th>難易度[必須]</th>
		                                		<td>
		                                		<div class="form-group">
		                                            <select class="form-control" name="difficalty">
		                                                <option value="<%=Difficalty.EASY.getId()%>" <%=(taskDto==null? "":( Difficalty.EASY.equals(taskDto.getDifficalty()) ? "selected":""))%>><%=Difficalty.EASY.getMsg()%></option>
		                                                <option value="<%=Difficalty.NORMAL.getId()%>" <%=(taskDto==null? "":(Difficalty.NORMAL.equals(taskDto.getDifficalty()) ? "selected":""))%>><%=Difficalty.EASY.NORMAL.getMsg()%></option>
		                                                <option value="<%=Difficalty.DIFFCAL.getId()%>" <%=(taskDto==null? "":(Difficalty.DIFFCAL.equals(taskDto.getDifficalty()) ? "selected":""))%>><%=Difficalty.EASY.DIFFCAL.getMsg()%></option>
		                                            </select>
		                                		</div>
		                                		</td>
		                                	</tr>
		                                	<tr>
		                                		<th>問題文[必須]</th>
		                                		<td>
		                                		<div class="form-group">
		                                			<textarea name="question" placeholder="問題文を記載してください" class="form-control"><%= (taskDto!=null ? taskDto.getQuestion():"") %></textarea>
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
	                			テストケース
	                		</div>
		                	<div class="panel-body">

		                		<label>
		                		<p>テストケースは最大10個までです。配点は合計50点になるようにしてください。</p>
		                		<p>入力ファイルには実行時に渡したい引数を1行ずつ記載してください（最大５つ）</p>
		                		</label>
		                		<input type="button" value="行追加" id="addForm">

		                        <div class="table-responsive">
		                            <table class="table table-bordered table-hover" id="form">
		                                <thead>
		                                    <tr>
		                                        <th>&nbsp;</th>
		                                        <th>No.</th>
		                                        <th>入力ファイル</th>
		                                        <th>出力ファイル[必須]</th>
		                                        <th>配点[必須]</th>
		                                    </tr>
		                                </thead>
		                                <tbody>
		                                	<tr id="testcase_table_tr[0]" class="testcase_table_tr">

		                                		<td>
		                                			<input class="close" type="button" value="削除" id="close[0]" style="display: none;">
		                                		</td>
		                                		<td>
		                                			<div id="index[0]">1</div>
		                                		</td>
		                                		<td>
			                                        <input type="file" id="infile_select[0]" name="inputfile[0]" class="form-control" style="display:none;">
			                                        <div class="input-group">

											          <span class="input-group-btn">
											            <button type="button" id="infile_select_icon[0]" class="btn btn-sm"><span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span></button>
											          </span>
											          <input type="text" id="inputfile_name[0]" name="inputfile_name[0]" class="form-control" placeholder="Select file ..." readonly>

											        </div>
		                                		</td>
		                                		<td>
			                                        <input type="file" id="outfile_select[0]" name="outputfile[0]" class="form-control" style="display:none;">
			                                        <div class="input-group">

											          <span class="input-group-btn">
											            <button type="button" id="outfile_select_icon[0]" class="btn btn-sm"><span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span></button>
											          </span>
											          <input type="text" id="outputfile_name[0]" name="outputfile_name[0]" class="form-control" placeholder="Select file ..." readonly>

											        </div>
		                                		</td>
		                                		<td>
		                                			<input type="text" id="haiten[0]" name="haiten[0]" placeholder="配点を記入してください">

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
		                                        <!-- 未対応
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
		                                                <option value="<%=TaskPublicStateId.PRIVATE.getId()%>" <%=(pubDto==null? "":(pubDto.getStatus()==TaskPublicStateId.PRIVATE? "selected":""))%>><%=TaskPublicStateId.PRIVATE.getMsg1()%></option>
		                                                <option value="<%=TaskPublicStateId.PUBLIC_MUST.getId()%>" <%=(pubDto==null? "":(pubDto.getStatus()==TaskPublicStateId.PUBLIC_MUST? "selected":""))%>><%=TaskPublicStateId.PUBLIC_MUST.getMsg1()%></option>
		                                                <option value="<%=TaskPublicStateId.PUBLIC.getId()%>" <%=(pubDto==null? "":(pubDto.getStatus()==TaskPublicStateId.PUBLIC? "selected":""))%>><%=TaskPublicStateId.PUBLIC.getMsg1()%></option>
		                                            </select>
		                                		</td>
		                                        <!-- 未対応
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
    <!-- autocomplete jquery -->
    <script src="view/js/typeahead.bundle.js"></script>

<script src="view/js/jquery-ui.min.js"></script>
<script src="view/js/jquery.ui.datepicker-ja.min.js"></script>
	<script>

	var states = new Bloodhound({
		  datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
		  queryTokenizer: Bloodhound.tokenizers.whitespace,
		  remote: {
		    url: 'searchTaskGrp?groupname=%QUERY',
		    wildcard: '%QUERY'
		  }
		});

	//アイコンをクリックした場合は、ファイル選択をクリックした挙動とする.
	$('[id^=infile_select_icon]').on('click', function() {
		var idx = 0;
		if( $(this).data("n") != null ){
			idx = $(this).data("n");
		}
	  $('[id=infile_select\\['+idx+'\\]]').click();
	});

	$('[id^=outfile_select_icon]').on('click', function() {
		var idx = 0;
		if( $(this).data("n") != null ){
			idx = $(this).data("n");
		}
		$('[id=outfile_select\\['+idx+'\\]]').click();
	});

	// ファイル選択時に表示用テキストボックスへ値を連動させる.
	// ファイル選択値のクリア機能の実装により、#file_select がDOMから消されることがあるので親要素からセレクタ指定でイベントを割り当てる.
	$('[id^=infile_select]').parent().on('change', '[id^=infile_select]', function() {
	  // $('#file_name').val($(this).val());
		var idx = 0;
		if( $(this).data("n") != null ){
			idx = $(this).data("n");
		}
	  $('[id=inputfile_name\\['+idx+'\\]]').val($('[id=infile_select\\['+idx+'\\]]').prop('files')[0].name);
	});

	// ファイル選択時に表示用テキストボックスへ値を連動させる.
	// ファイル選択値のクリア機能の実装により、#file_select がDOMから消されることがあるので親要素からセレクタ指定でイベントを割り当てる.
	$('[id^=outfile_select]').parent().on('change', '[id^=outfile_select]', function() {
	  // $('#file_name').val($(this).val());
		var idx = 0;
		if( $(this).data("n") != null ){
			idx = $(this).data("n");
		}
	  $('[id=outputfile_name\\['+idx+'\\]]').val($('[id=outfile_select\\['+idx+'\\]]').prop('files')[0].name);
	});


	$(function(){
		//追加
		  $('#addForm').click(function(){
			  var original = $('#testcase_table_tr\\[' + testcase_cnt + '\\]');
			  var originCnt = testcase_cnt;

			  if( testcase_cnt < 9 ){
				  testcase_cnt++;
				  original
				  	.clone(original)
					.insertAfter(original)
					.attr('id', 'testcase_table_tr[' + testcase_cnt + ']')
					.end()
					.find('input, button, div, button').each(function(idx, obj) {
							//id属性変更
							if($(obj).attr('id') != null ){
					              $(obj).attr({
					                  id: $(obj).attr('id').replace(/\[[0-9]\]+$/, '[' + testcase_cnt + ']'),
					              });
					              $(obj).data("n",testcase_cnt);
				             }
							//名前属性変更
							if($(obj).attr('name') != null ){
	                            $(obj).attr({
	                                name: $(obj).attr('name').replace(/\[[0-9]\]+$/, '[' + testcase_cnt + ']'),
	                            });
							}
							if ($(obj).attr('type') == 'text') {
				                $(obj).val('');
				            }
			          });

					  var clone = $('#testcase_table_tr\\[' + testcase_cnt + '\\]');
					  $('#index\\['+testcase_cnt+'\\]').text( testcase_cnt+1 );
				      clone.children('td').children('input.close').show();
				      clone.slideDown('slow');
		  		}

		  });

		  //削除
		  $("[id^=close]").on("click",function(){
		        console.log($(this).data("n"));
				//削除対象を取得
		        var removeObj = $('#testcase_table_tr\\[' + $(this).data("n") + '\\]');
		        //var removeObj = $(this).parent();
		        removeObj.fadeOut('fast', function() {
		            removeObj.remove();
		            // 番号振り直し
		            testcase_cnt = 0;
		            $('[id^=testcase_table_tr]').each(function(index, formObj) {
		            	//alert($(formObj).attr('id'));
		                if ($(formObj).attr('id') != 'testcase_table_tr[0]') {
		                	testcase_cnt++;
		                    $(formObj)
		                        .attr('id', 'testcase_table_tr[' + testcase_cnt + ']') // id属性を変更。
		                        .find('input, button,div, button').each(function(idx, obj) {
		    						if($(obj).attr('id') != null ){
			                            $(obj).attr({
			                                id: $(obj).attr('id').replace(/\[[0-9]\]+$/, '[' + testcase_cnt + ']'),
			                            });
							              $(obj).data("n",testcase_cnt);
										  var clone = $('#testcase_table_tr\\[' + testcase_cnt + '\\]');
										  $('#index\\['+testcase_cnt+'\\]').text( testcase_cnt+1 );
		    						}
		    						if($(obj).attr('name') != null ){
			                            $(obj).attr({
			                                name: $(obj).attr('name').replace(/\[[0-9]\]+$/, '[' + testcase_cnt + ']'),
			                            });
		    						}
		                        });
		                }
		            });
		        });

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


		$(function() {
			    $("[id^=datepicker]").datepicker();
		});

		$(function() {
			    $("#datepicker2").datepicker();
		});

	</script>
</body>

</html>
