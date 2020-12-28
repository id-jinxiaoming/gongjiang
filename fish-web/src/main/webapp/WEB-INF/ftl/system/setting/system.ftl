<!DOCTYPE html>
<html>
<head>

<#include "/common/header.ftl">
    <script charset="utf-8" src="${basePath}/static/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${basePath}/static/kindeditor/lang/zh-CN.js"></script>

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
    <div class="wrapper wrapper-content animated fadeInRight">    
        <div class="row">          
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
				<#include "/system/setting/menu.ftl">
                    <div class="ibox-content" style="border:none;">
                        <form class="form-horizontal" id="myForm" action="${basePath}/system/setting/systemEdit" method="post">
                            <div class="form-group">
	                            <label class="col-sm-3 control-label">图片上传地址：</label>
	                            <div class="col-sm-6">
	                                <div class="radio i-checks">
	                                    <label><input type="radio" value="0" name="upload" <#if config.upload== 0>checked</#if>> <i></i> 本地</label>
	                                    <label><input type="radio" value="1" name="upload" <#if config.upload== 1>checked</#if>> <i></i> 七牛云</label>

									</div>
	                            </div>
	                        </div>
	                        <div class="hr-line-dashed"></div>
		                    <div class="form-group">
		                        <label class="col-sm-3 control-label">是否开启积分抵扣运费：</label>
		                        <div class="col-sm-4">
                                    <div class="radio i-checks">
                                    <label><input type="radio" value="0" name="isIntegralToFreight" <#if config.isIntegralToFreight== 0>checked</#if>> <i></i> 关闭</label>
                                    <label><input type="radio" value="1" name="isIntegralToFreight" <#if config.isIntegralToFreight== 1>checked</#if>> <i></i> 开启</label>
                                    </div>
		                        </div>
	                        </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">快递100 KEY：</label>
                                <div class="col-sm-4">
										<input id="money" name="kuaidi100Key" value="${config.kuaidi100Key!}"  class="form-control" type="text" >
                                    <span style="color:#ff0000;">*快递100接口KEY</span>
                                </div>
                            </div>

	                        <div class="hr-line-dashed"></div>
                            <div class="form-group">
                            <label class="col-sm-3 control-label">android下载地址</label>
                            <div class="col-sm-4">
                                <input id="android" name="android" value="${config.android!}"  class="form-control" type="text" >
                                <span style="color:#ff0000;">*android下载地址</span>
                            </div>
                        </div>

                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">ios下载地址</label>
                                <div class="col-sm-4">
                                    <input id="ios" name="ios" value="${config.ios!}"  class="form-control" type="text" >
                                    <span style="color:#ff0000;">*ios下载地址</span>
                                </div>
                            </div>

                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">管家服务：</label>
                                <div class="col-sm-4">
                                    <textarea id="" name="serviceInfo" style="width:700px;height:300px;">
									${config.serviceInfo!}
										</textarea>
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
    <script src="${basePath}/static/hplus/js/content.js?v=1.0.0"></script>
    <script src="${basePath}/static/hplus/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${basePath}/static/hplus/js/plugins/validate/messages_zh.min.js"></script>
    <script src="${basePath}/static/common/js/jquery.form.js"></script>

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
        var editor = KindEditor.create('textarea[name="serviceInfo"]', {
            uploadJson : '${basePath}/common/editUpload',
            allowFileManager : false,
            allowPreviewEmoticons : false,
            items : ['source', '|', 'undo', 'redo', '|', 'preview', 'template', 'cut', 'copy', 'paste',
                'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
                'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image','multiimage',
                'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
                'anchor', 'link', 'unlink']
        });
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
		            		window.location.href="${basePath}/system/setting/system";
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


    
    </script>
</body>
</html>