<!DOCTYPE html>
<html>
<head>

<#include "/common/header.ftl">
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
                    <form class="form-horizontal" id="myForm" action="${basePath}/system/setting/jpush" method="post">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">App Key：</label>
                            <div class="col-sm-4">
                                <input id="appKey" name="appKey" value="${config.appKey!}"  class="form-control" type="text" >
                                <span style="color:#ff0000;">*极光App Key</span>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Master Secret：</label>
                            <div class="col-sm-4">
                                <input id="masterSecret" name="masterSecret" value="${config.masterSecret!}"  class="form-control" type="text" >
                                <span style="color:#ff0000;">*Master Secret</span>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <button id="btnSubmit" class="btn btn-primary" type="submit"><i class="fa fa-save"></i> 提交</button>
                                <button id="btnSubmit" class="btn btn-primary" type="button" onclick="sendJpush()"> 发送测试</button>

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
                            window.location.href="${basePath}/system/setting/jpush";
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
    function sendJpush(){
        layer.prompt({title: '输入ID并确认', formType: 3}, function(text, index){
            layer.msg(text);
            $.ajax({
                url: "${basePath}/common/sendPushById",
                type: "POST",
                data: {id:text},
                dataType:'json',
                success:function(res){
                    layer.msg("发送成功",{icon:1,time:1500,shade: 0.1});

                },
                error:function(er){

                }
            });
            layer.close(index);

        });
        return;






    }

</script>
</body>
</html>