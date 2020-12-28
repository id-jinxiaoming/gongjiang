<!DOCTYPE html>
<html>

<head>

	
	<#include "/common/header.ftl">

	
  
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
                    <form class="form-horizontal m-t" id="myForm" action="${basePath}/config/payment/editAlipay" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="id" value="${item.id}" />
                        <div class="form-group">
                            <label class="col-sm-3 control-label">名称：  </label>
                            <div class="col-sm-3">
                               <img width="100px"  src="${basePath}/static/images/alipay.jpg">

                            </div>
                        </div>



                        <div class="form-group">
                            <label class="col-sm-3 control-label">AppId：</label>
                            <div class="col-sm-3">
                                <input id="AppId" type="text" value="${cfg.appId}" class="form-control" name="AppId" required="" aria-required="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">AlipayPublicKey(支付宝公钥)：</label>
                            <div class="col-sm-3">
                                <input id="MchId" type="text" value="${cfg.alipayPublicKey}" class="form-control" name="alipayPublicKey" required="" aria-required="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">PrivateKey(商家私钥)：</label>
                            <div class="col-sm-3">
                                <input id="PaternerKey" type="text" value="${cfg.privateKey}" class="form-control" name="privateKey" required="" aria-required="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">支付回调地址：</label>
                            <div class="col-sm-3">
                                <input id="url" type="text" value="${cfg.url}" class="form-control" name="url" required="" aria-required="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">排序号：</label>
                            <div class="col-sm-3">
                                <input id="seq" type="text" value="${item.seq}" class="form-control" name="seq" required="" aria-required="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">使用说明：</label>
                            <div class="col-sm-3">
                                <textarea id="instruction" name="instruction" class="form-control" required="" aria-required="true">${item.instruction}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label" >状态：</label>
                            <div class="col-sm-6">
                                <div class="radio i-checks">
                                    <label><input type="radio" value="1" name="enable" <#if item.enable == 1>checked</#if> > <i></i> 开启</label>
                                    <label><input type="radio" value="0" name="enable" <#if item.enable == 0>checked</#if> > <i></i> 关闭</label>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <button class="btn btn-primary" type="submit">提交</button>
                                <a  class="btn btn-danger" type="button"  href="${basePath}/config/payment/list"><i class="fa fa-close"></i> 返回</a>
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
<script >
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
                            window.location.href="${basePath}/config/payment/list";
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