<!DOCTYPE html>
<html>

<head>


<#include "/common/header.ftl">

    <link rel="stylesheet" href="${basePath}/static/webupload/webuploader.css">
    <link rel="stylesheet" href="${basePath}/static/webupload/style.css">

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
                    <form class="form-horizontal m-t" id="myForm" action="${basePath}/shop/goodsSpec/edit" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">选项类型：</label>
                            <div class="col-sm-9">
                                <div class="col-sm-10">
                                <#list typeList as item>
                                    <div class="radio i-checks">
                                    ${item.name}:
                                        <#list goodsOptionals as opt>
                                            <#if item.typeId==opt.typeId>
                                                <label>
                                                    <input type="radio" checked="" value="${opt.id}" name="${item.typeId}"> <i></i> ${opt.optText}</label>
                                            </#if>
                                        </#list>
                                    </div>
                                </#list>

                                </div>
                            </div>

                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">价格：</label>
                            <div class="col-sm-3">
                                <input id="price" type="text" value="${goodsSpec.price}" class="form-control" name="price" required="" aria-required="true">
                            </div>
                        </div>


                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <input type="hidden" name="goodsId" value="${goodsId}" />
                                <input type="hidden" name="id" value="${goodsSpec.id}" />
                                <input type="hidden" name="key" id="key" value="" />
                                <button class="btn btn-primary" type="submit">提交</button>
                                <a  class="btn btn-danger" type="button"  href="${basePath}/shop/goodsSpec/list/${goodsId}"><i class="fa fa-close"></i> 返回</a>
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
<script src="${basePath}/static/hplus/js/plugins/iCheck/icheck.min.js"></script>

<!-- jQuery Validation plugin javascript-->
<script src="${basePath}/static/hplus/js/plugins/validate/jquery.validate.min.js"></script>
<script src="${basePath}/static/hplus/js/plugins/validate/messages_zh.min.js"></script>

<script src="${basePath}/static/common/js/jquery.form.js"></script>

<script src="${basePath}/static/layer/layer.js"></script>
<script>
    $(document).ready(function(){$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})});
</script>
<script >

    var optType = new Array();
    <#list typeList as item>
    optType.push("${item.typeId?js_string}");//必须这么写
    </#list>

    $().ready(function() {

        $("#myForm").validate({
            rules: {
                name: "required",
                type: "required"

            }
        });

        $('#myForm').on('submit', function() {

            //判断选项是否选定
            var ids="";
            for(var i=0;i<optType.length;i++){

                var val=$('input:radio[name='+optType[i]+']:checked').val();
                ids+=val+":";

            }
            $("#key").val(ids.substr(0,ids.length-1));
            $(this).ajaxSubmit({
                type: 'post',
                beforeSubmit:function(){
                    return $("#myForm").valid();
                },
                success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
                    // 此处可对 data 作相关处理
                    if(data.status == 200){
                        layer.msg(data.message,{icon: 6,time:2000,shade: 0.1},function(index){
                            window.location.href="${basePath}/shop/goodsSpec/list/${goodsId}";
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