<!DOCTYPE html>
<html>

<head>


<#include "/common/header.ftl">



</head>

<body class="gray-bg">


    <div class="row">

        <div class="col-sm-12">
            <div class="ibox float-e-margins">

                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="myForm" action="${basePath}/order/aftersales/doCheck" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">状态：</label>
                            <div class="col-sm-3">
                                <select class="form-control m-b" name="status">

                                    <option value="2">正在处理中</option>
                                    <option value="3">已解决</option>
                                    <option value="4">未解决</option>

                                </select>

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">处理内容：</label>
                            <div class="col-sm-3">
                                <textarea id="reason" name="content" class="form-control" required="" aria-required="true"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <input type="hidden" name="id" value="${id}" />
                                <button class="btn btn-primary" type="submit">提交</button>
                            </div>
                        </div>
                    </form>
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
    var winIndex = parent.layer.getFrameIndex(window.name); //获取窗口索引
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
                        layer.msg(data.msg,{icon: 6,time:2000,shade: 0.1},function(index){
                            parent.layer.close(winIndex);
                            parent.update();
                        });
                    }else{
                        layer.msg(data.msg,{icon: 5,time:2000,shade: 0.1},function(index){
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