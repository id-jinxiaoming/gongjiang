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
                    <h5>创建</h5>
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
                    <form class="form-horizontal m-t" id="myForm" action="${basePath}/users/usersGroup/add" method="post">


                        <div class="form-group">
                            <label class="col-sm-3 control-label">用户组名称：</label>
                            <div class="col-sm-3">
                                <input id="groupName" type="text" class="form-control" name="groupName" required="" placeholder="请输入名称" aria-required="true"><span class="error" id="err_groupName"></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">经验值下限：</label>
                            <div class="col-sm-3">
                                <input id="minExp" type="text" class="form-control" name="minExp" required="" placeholder="请输入经验值下限" aria-required="true"><span class="error" id="err_minExp">必须为纯数字且为非负整数</span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">折扣率：</label>
                            <div class="col-sm-3">
                                <input id="discountRate" type="text" class="form-control" name="discountRate" required="" placeholder="请输入折扣率" aria-required="true"><span class="error" id="err_discountRate">必须为0-100之间的整数, 例如为88, 则表示享受8.8折的商品折扣率</span>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <button class="btn btn-primary" type="submit">提交</button>
                                <a  class="btn btn-danger" type="button"  href="${basePath}/users/usersGroup/list"><i class="fa fa-close"></i> 返回</a>
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
            $(".error").html("");
            $(this).ajaxSubmit({
                type: 'post',
                beforeSubmit:function(){

                    return $("#myForm").valid();

                },
                success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
                    // 此处可对 data 作相关处理

                    if(data.status == 200){

                        layer.msg(data.message,{icon: 6,time:2000,shade: 0.1},function(index){
                            window.location.href="${basePath}/users/usersGroup/list";
                        });
                    }else if(data.status == 400){

                        for(var i=0;i<data.data.length;i++)
                        {
                            $("#err_"+data.data[i].name).html(data.data[i].value);

                        }


                    }else {
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