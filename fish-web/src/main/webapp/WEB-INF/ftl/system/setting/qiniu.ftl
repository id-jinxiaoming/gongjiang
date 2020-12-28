<!DOCTYPE html>
<html>
<head>
<#include "/common/header.ftl">
    <link rel="stylesheet" href="${basePath}/static/webupload/webuploader.css">
    <link rel="stylesheet" href="${basePath}/static/webupload/style.css">
    <style type="text/css">
    .long-tr th{
        text-align: center
    }
    .long-td td{
        text-align: center
    }
    /* TAB */
	.nav-tabs.nav>li>a {
	    padding: 10px 25px;
	    margin-right: 0;
	}
	.nav-tabs.nav>li>a:hover,
	.nav-tabs.nav>li.active>a {
	    border-top: 3px solid #1ab394;
	    padding-top: 8px;
	}
	.nav-tabs>li>a {
	    color: #A7B1C2;
	    font-weight: 500;
	    margin-right: 2px;
	    line-height: 1.42857143;
	    border: 1px solid transparent;
	    border-radius: 0;
	}
    </style>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated">    
        <div class="row">          
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
					<#include "/system/setting/menu.ftl">
                    <div class="ibox-content" style="border:none;">
                        <form class="form-horizontal" id="myForm" action="${basePath}/system/setting/qiniuEdit" method="post">
	                        <div class="form-group">
		                        <label class="col-sm-3 control-label">accesskey：</label>
		                        <div class="col-sm-4">
		                            <input id="appKey" name="accesskey" value="${config.accesskey!}"  class="form-control" type="text" >
		                            <span style="color:#ff0000;">*七牛云上传的app key</span>
		                        </div>
		                    </div>
		                    <div class="hr-line-dashed"></div>
		                    <div class="form-group">
		                        <label class="col-sm-3 control-label">secretkey：</label>
		                        <div class="col-sm-4">
		                            <input id="secretkey" name="secretkey" value="${config.secretkey!}"  class="form-control" type="text" >
		                            <span style="color:#ff0000;">*七牛云上传secretkey</span>
		                        </div>
	                        </div>
	                        <div class="hr-line-dashed"></div>
		                    <div class="form-group">
		                        <label class="col-sm-3 control-label">外链默认域名：</label>
		                        <div class="col-sm-4">
		                            <input id="domain" name="domain" value="${config.domain!}"  class="form-control" type="text" >
		                            <span style="color:#ff0000;">*七牛云的外链默认域名</span>
		                        </div>
	                        </div>
	                        <div class="hr-line-dashed"></div>
		                    <div class="form-group">
		                        <label class="col-sm-3 control-label">空间名：</label>
		                        <div class="col-sm-4">
		                            <input id="bucketname" name="bucketname" value="${config.bucketname!}"  class="form-control" type="text" >
		                            <span style="color:#ff0000;">*七牛云要上传的空间  </span>
		                        </div>
	                        </div>

                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">上传测试：</label>
                                <div class="col-sm-4">
                                    <input type="hidden" id="data_photo" name="imgUrl" >
                                    <div id="fileList" class="uploader-list" style="float:right"></div>
                                    <div id="imgPicker" style="margin-left: 15px;" >选择图片</div>
                                    <div style="margin-top: 10px;"><img id="img_data" src="${basePath}/static/webupload/empty_mid.png"  height="80px" width="80px" style="float:left;margin-left: 50px;margin-top: -10px;" onerror="this.src='${basePath}/static/webupload/empty_mid.png'"/></div>

								</div>
                            </div>








                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-8 col-sm-offset-3">
                                    <button id="btnSubmit" class="btn btn-primary" type="submit"><i class="fa fa-save"></i> 提交</button>

                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="${basePath}/static/hplus/js/jquery.min.js?v=2.1.4"></script>
    <script src="${basePath}/static/layer/layer.js"></script>
    <script src="${basePath}/static/hplus/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="${basePath}/static/hplus/js/content.min.js?v=1.0.0"></script>
    <script src="${basePath}/static/hplus/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${basePath}/static/hplus/js/plugins/validate/messages_zh.min.js"></script>
    <script src="${basePath}/static/common/js/jquery.form.js"></script>
    <script src="${basePath}/static/webupload/webuploader.min.js"></script>

    <script src="${basePath}/static/hplus/js/plugins/iCheck/icheck.min.js"></script>
    <script>
		$(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
	</script>
    <script type="text/javascript" >
    $.validator.setDefaults({
	    highlight: function(e) {
	        $(e).closest(".form-group").removeClass("has-success").addClass("has-error")
	    },
	    success: function(e) {
	        e.closest(".form-group").removeClass("has-error").addClass("has-success")
	    },
	    errorElement: "span",
	    errorPlacement: function(e, r) {
	        e.appendTo(r.is(":radio") || r.is(":checkbox") ? r.parent().parent().parent() : r.parent())
	    },
	    errorClass: "help-block m-b-none",
	    validClass: "help-block m-b-none"
	});
	$().ready(function() {
	    var e = "<i class='fa fa-times-circle'></i> ";
	    $("#myForm").validate({
	        rules: {
	            name: "required",
	            type: "required"	           
	        }
	    });
  
	    $('#myForm').on('submit', function() {
	    
	        $(this).ajaxSubmit({
	            type: 'post',
		    	beforeSubmit:function(){		    		
		    		return $("#myForm").valid();		    		
		    	},
	            success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
	                // 此处可对 data 作相关处理
	            	if(data.status == 200){
	            		layer.msg(data.message,{icon: 6,time:2000,shade: 0.1},function(index){
		            		window.location.href="${basePath}/system/setting/qiniu";
		            	});
	            	}else{
	            		layer.msg(data.message,{icon: 5,time:2000,shade: 0.1},function(index){
		            		layer.close(index);
		            	});
	            	}	            		            	
	            }
	        });
	        return false; // 阻止表单自动提交事件
	    });
    
	});
    var $list = $('#fileList');
    //上传图片,初始化WebUploader
    var uploader = WebUploader.create({

        auto: true,// 选完文件后，是否自动上传。
        swf: '${basePath}/static/webupload/Uploader.swf',// swf文件路径
        server: "${basePath}/common/uploadOss",// 文件接收服务端。
        duplicate :true,// 重复上传图片，true为可重复false为不可重复
        pick: '#imgPicker',// 选择文件的按钮。可选。
        fileVal:'upfile',
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/jpg,image/jpeg,image/png'
        },
        'onUploadSuccess': function(file, data, response) {
            $("#data_photo").val(data);
            $("#img_data").attr('src', data).show();
        }
    });

    uploader.on( 'fileQueued', function( file ) {
        $list.html( '<div id="' + file.id + '" class="item">' +
                '<h4 class="info">' + file.name + '</h4>' +
                '<p class="state">正在上传...</p>' +
                '</div>' );
    });

    // 文件上传成功
    uploader.on( 'uploadSuccess', function( file ) {
        $( '#'+file.id ).find('p.state').text('上传成功！');
    });

    // 文件上传失败，显示上传出错。
    uploader.on( 'uploadError', function( file ) {
        $( '#'+file.id ).find('p.state').text('上传出错!');
    });



    
    </script>
</body>
</html>