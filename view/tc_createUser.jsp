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
List<CourseDto> courseList = (List<CourseDto>)request.getAttribute(RequestConst.REQUEST_COURSE_LIST);
UserDto userDto = (UserDto)request.getAttribute(RequestConst.REQUEST_USER_DTO);

Integer courseId = -1;
Integer roleId = -1;
if( userDto != null){
	courseId = userDto.getUserId();
	roleId = userDto.getRoleId();
}


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
				<form action="tc_confirmUser"  method="post" >
                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            学生作成

                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-user"></i> 学生作成
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
                			ユーザー情報
                		</div>
	                	<div class="panel-body">

	                        <div class="table-responsive">
	                            <table class="table table-bordered table-hover" id="form">
	                                <tbody>
	                                	<tr>
	                                		<th>ロール[必須]</th>
	                                		<td>
	                                		<div class="form-group">

							                     <select id="couse" class="form-control" name="<%=RequestConst.REQUEST_ROLE_ID%>">
							                         <option value="<%=RoleId.STUDENT.getId() %>" <%= (roleId == RoleId.STUDENT.getId() ? "selected":"" ) %> ><%=RoleId.STUDENT.getMsg() %></option>
							                    	 <option value="<%=RoleId.TEACHER.getId() %>" <%= (roleId == RoleId.TEACHER.getId() ? "selected":"" ) %> ><%=RoleId.TEACHER.getMsg() %></option>
							                    	 <option value="<%=RoleId.MANAGER.getId() %>" <%= (roleId == RoleId.MANAGER.getId() ? "selected":"" ) %> ><%=RoleId.MANAGER.getMsg() %></option>
							                     </select>
	                                		</div>
	                                		</td>
	                                	</tr>
	                                	<tr>
	                                		<th>学籍番号/社員番号[必須]</th>
	                                		<td>
	                                		<div class="form-group">
												<input type="text" name="name" placeholder="学籍番号/社員番号を記入してください" value="<%=(userDto!=null? userDto.getName():"") %>">
	                                		</div>
	                                		</td>
	                                	</tr>
	                                	<tr>
	                                		<th>メールアドレス[必須]</th>
	                                		<td>
	                                		<div class="form-group">
	                                			<input type="text" name="mailadress" placeholder="メールアドレスを記入してください" value="<%=(userDto!=null? userDto.getMailAdress():"") %>">
	                                		</div>
	                                		</td>
	                                	</tr>
	                                	<tr>
	                                		<th>ニックネーム[必須]</th>
	                                		<td>
	                                		<div class="form-group">
	                                			<input type="text" name="nickname" placeholder="ニックネームを記入してください" value="<%=(userDto!=null? userDto.getNickName():"") %>">
	                                		</div>
	                                		</td>
	                                	</tr>
	                                	<tr>
	                                		<th>学科[必須]</th>
	                                		<td>
	                                		<div class="form-group">

							                     <select id="couse" class="form-control" name="<%=RequestConst.REQUEST_COURSE_ID%>">

							                     <% for(CourseDto course :courseList){ %>
							                         <option value="<%=course.getId() %>" <%= (courseId == course.getId() ? "selected":"" ) %>><%=course.getName() %></option>
							                     <%} %>
							                     </select>
	                                		</div>
	                                		</td>
	                                	</tr>
	                                	<tr>
	                                		<th>パスワード[必須]</th>
	                                		<td>
	                                		<div class="form-group">
	                                			<input type="password" name="password" placeholder="パスワードを記入してください">
	                                		</div>
	                                		</td>
	                                	</tr>
	                                	<tr>
	                                		<th>入学年度</th>
	                                		<td>
	                                		<div class="form-group">
	                                			<input type="text" name="admissionYear" placeholder="入学年度を記入してください" value="<%=(userDto!=null? userDto.getAdmissionYear():"") %>">
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
