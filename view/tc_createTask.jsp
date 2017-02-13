<!DOCTYPE html>
<html lang="en">

<head>

<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="jp.ac.asojuku.asolearning.param.RequestConst" %>
<%@ page import="jp.ac.asojuku.asolearning.param.TaskPublicStateId" %>
<%@ page import="java.util.List" %>
<%@ page import="jp.ac.asojuku.asolearning.dto.TaskDto" %>
<%@ page import="jp.ac.asojuku.asolearning.dto.CourseDto" %>
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

    <!-- Custom Fonts -->
    <link href="view/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<script>
var testcase_cnt = 0;	//テストケースの数。初期値は0
</script>
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
                            課題作成</small>
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
                	<div class="panel panel-default">
                		<div class="panel-heading">
                			概要
                		</div>
	                	<div class="panel-body">
	                        <div class="table-responsive">
	                            <table class="table table-bordered table-hover" id="form">
	                                <tbody>
	                                	<tr>
	                                		<th>課題名</th>
	                                		<td>
	                                		<div class="form-group">
	                                			<input type="text" placeholder="課題名を記載してください">
	                                		</div>
	                                		</td>
	                                	</tr>
	                                	<tr>
	                                		<th>問題文</th>
	                                		<td>
	                                		<div class="form-group">
	                                			<textarea placeholder="問題文を記載してください"></textarea>
	                                		</div>
	                                		</td>
	                                	</tr>
	                                </tbody>
	                            </table>
	                        </div>
						</div>
                	</div>
                </div>
                <!-- /.row -->

                <div class="row">
                	<div class="panel panel-default">
                		<div class="panel-heading">
                			テストケース
                		</div>
	                	<div class="panel-body">
	                		<label>テストケースは最大10個までです。</label>
	                		<input type="button" value="+" id="addForm">

	                        <div class="table-responsive">
	                            <table class="table table-bordered table-hover" id="form">
	                                <thead>
	                                    <tr>
	                                        <th>&nbsp;</th>
	                                        <th>No.</th>
	                                        <th>入力ファイル</th>
	                                        <th>出力ファイル</th>
	                                        <th>配点</th>
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                	<tr id="testcase_table_tr[0]" class="testcase_table_tr">

	                                		<td>
	                                			<input class="close" type="button" value="－" id="close[0]" style="display: none;">
	                                		</td>
	                                		<td>
	                                			<div id="index[0]">1</div>
	                                		</td>
	                                		<td>
		                                        <input type="file" id="infile_select[0]" name="javafile" class="form-control" style="display:none;">
		                                        <div class="input-group">

										          <span class="input-group-btn">
										            <button type="button" id="infile_select_icon[0]" class="btn btn-sm"><span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span></button>
										          </span>
										          <input type="text" id="inputfile_name[0]" class="form-control" placeholder="Select file ..." readonly>

										        </div>
	                                		</td>
	                                		<td>
		                                        <input type="file" id="outfile_selec[0]" name="javafile" class="form-control" style="display:none;">
		                                        <div class="input-group">

										          <span class="input-group-btn">
										            <button type="button" id="outfile_select_icon[0]" class="btn btn-sm"><span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span></button>
										          </span>
										          <input type="text" id="outputfile_name[0]" class="form-control" placeholder="Select file ..." readonly>

										        </div>
	                                		</td>
	                                		<td>
	                                			<input type="text" id="haiten[0]" placeholder="配点を記入してください">

	                                		</td>
	                                	</tr>
	                                </tbody>
	                            </table>
	                        </div>
						</div>
                	</div>
                </div>
                <!-- /.row -->

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
	                                        <th>公開時間設定</th>
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                 <% List<CourseDto> list = (List<CourseDto>)request.getAttribute(RequestConst.REQUEST_COURSE_LIST); %>
	                                 <% for(CourseDto dto : list ){ %>
	                                	<tr>
	                                		<td>
	                                			<%=dto.getName()%>
	                                		</td>
	                                		<td>
	                                            <select class="form-control" name="course">
	                                                <option value="<%=dto.getId()%>-<%=TaskPublicStateId.PRIVATE.getId()%>"><%=TaskPublicStateId.PRIVATE.getMsg1()%></option>
	                                                <option value="<%=dto.getId()%>-<%=TaskPublicStateId.PUBLIC_MUST.getId()%>"><%=TaskPublicStateId.PUBLIC_MUST.getMsg1()%></option>
	                                                <option value="<%=dto.getId()%>-<%=TaskPublicStateId.PUBLIC.getId()%>"><%=TaskPublicStateId.PUBLIC.getMsg1()%></option>
	                                            </select>
	                                		</td>
	                                		<td>
	                                			<div class="form-group">
	                                			<input type="text" placeholder="">
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
	$('#infile_select_icon').on('click', function() {
	  $('#infile_select').click();
	});
	$('#outfile_select_icon').on('click', function() {
		  $('#outfile_select').click();
	});

	// ファイル選択時に表示用テキストボックスへ値を連動させる.
	// ファイル選択値のクリア機能の実装により、#file_select がDOMから消されることがあるので親要素からセレクタ指定でイベントを割り当てる.
	$('#infile_select').parent().on('change', '#infile_select', function() {
	  // $('#file_name').val($(this).val());
	  $('#inputfile_name').val($('#infile_select').prop('files')[0].name);
	});
	// ファイル選択時に表示用テキストボックスへ値を連動させる.
	// ファイル選択値のクリア機能の実装により、#file_select がDOMから消されることがあるので親要素からセレクタ指定でイベントを割り当てる.
	$('#outfile_select').parent().on('change', '#outfile_select', function() {
	  // $('#file_name').val($(this).val());
	  $('#outputfile_name').val($('#outfile_select').prop('files')[0].name);
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
					.find('input, button, div').each(function(idx, obj) {
						if($(obj).attr('id') != null ){
				              $(obj).attr({
				                  id: $(obj).attr('id').replace(/\[[0-9]\]+$/, '[' + testcase_cnt + ']'),
				                  //name: $(obj).attr('name').replace(/\[[0-9]\]+$/, '[' + testcase_cnt + ']')
				              });
				              $(obj).data("n",testcase_cnt);
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
		                        .find('input, button,div').each(function(idx, obj) {
		    						if($(obj).attr('id') != null ){
			                            $(obj).attr({
			                                id: $(obj).attr('id').replace(/\[[0-9]\]+$/, '[' + testcase_cnt + ']'),
			                                //name: $(obj).attr('name').replace(/\[[0-9]\]+$/, '[' + testcase_cnt + ']')
			                            });
							              $(obj).data("n",testcase_cnt);
										  var clone = $('#testcase_table_tr\\[' + testcase_cnt + '\\]');
										  $('#index\\['+testcase_cnt+'\\]').text( testcase_cnt+1 );
		    						}
		                        });
		                }
		            });
		        });

		    });

		});

	</script>
</body>

</html>
