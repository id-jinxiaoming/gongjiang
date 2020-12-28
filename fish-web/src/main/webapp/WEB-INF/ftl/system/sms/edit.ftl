<!DOCTYPE html>
<html>

<head>


<#include "/common/header.ftl">
    <link href="${basePath}/static/hplus/css/plugins/iCheck/custom.css" rel="stylesheet">



</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">

    <div class="row">

        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>编辑</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>

                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="myForm" action="${basePath}/system/sms/edit" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="smsId" value="${item.smsId}" />
                        <div class="form-group">
                            <label class="col-sm-3 control-label">标签：</label>
                            <div class="col-sm-4">
                                <input id="smsKey" value="${item.smsKey!}" name="smsKey" class="form-control" type="text" readonly="readonly">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">模板ID：</label>
                            <div class="col-sm-4">
                                <input id="smsTemplateId" value="${item.smsTemplateId!}"  name="smsTemplateId" class="form-control" type="text" >
                                <span style="color:#ff0000;">*阿里大于短信平台的模板ID（注：必须和阿里大于平台上的一样）</span>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">说明：</label>
                            <div class="col-sm-4">
                                <textarea name="smsExplain" class="form-control">${(item.smsExplain)!''}</textarea>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">模板：</label>
                            <div class="col-sm-4">
                                <textarea name="smsTmpl" rows="5" class="form-control">${(item.smsTmpl)!''}</textarea>
                                <span style="color:#ff0000;">*阿里大于短信平台的模板（注：必须和阿里大于平台上的一样）</span>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">状态：</label>
                            <div class="col-sm-6">
                                <div class="radio i-checks">
                                    <label><input type="radio" value="1" name="isOpen" <#if item.isOpen == 1>checked</#if>>  开启</label>
                                    <label><input type="radio" value="0" name="isOpen" <#if item.isOpen == 0>checked</#if>>  关闭</label>
                                </div>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <button class="btn btn-primary" type="submit">提交</button>
                                <a  class="btn btn-danger" type="button"  href="${basePath}/system/sms/list"><i class="fa fa-close"></i> 返回</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>


<!-- 全局js -->
<script src="${basePath}/static/hplus/js/jquery.min.js?v=2.1.4"></script>
<script src="${basePath}/static/hplus/js/bootstrap.min.js?v=3.3.6"></script>

<!-- 自定义js -->
<script src="${basePath}/static/hplus/js/content.js?v=1.0.0"></script>

<!-- jQuery Validation plugin javascript-->
<script src="${basePath}/static/hplus/js/plugins/validate/jquery.validate.min.js"></script>
<script src="${basePath}/static/hplus/js/plugins/validate/messages_zh.min.js"></script>

<script src="${basePath}/static/common/js/jquery.form.js"></script>
<script src="${basePath}/static/layer/layer.js"></script>
<script src="${basePath}/static/hplus/js/plugins/iCheck/icheck.min.js"></script>
<script >
    $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});

    $().ready(function() {

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
                            window.location.href="${basePath}/system/sms/list";
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