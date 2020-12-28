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
                    <form class="form-horizontal m-t" id="myForm" action="${basePath}/shop/goodsCateAttr/add" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">属性名称：</label>
                            <div class="col-sm-3">
                                <input id="name" type="text" class="form-control" name="name" required="" placeholder="请输入属性名称" aria-required="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">属性可选项：</label>
                            <div class="col-sm-3">
                                <input id="opts" type="text" class="form-control" name="opts" required="" placeholder="" aria-required="true"><span>多个中间用,号分隔</span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">计量单位：</label>
                            <div class="col-sm-3">
                                <input id="uom" type="text" value="" class="form-control" name="uom" required="" aria-required="true"><span>例如"英寸"、"像素"、"Kg"等等，如该属性不使用计量单位，请留空</span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">作为筛选：</label>
                            <div class="col-sm-6">
                                <div class="radio i-checks">
                                    <label><input type="radio" value="1" name="filtrate" checked> <i></i> 是</label>
                                    <label><input type="radio" value="0" name="filtrate" > <i></i> 否</label>
                                </div>
                            </div>

                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">排序：</label>
                            <div class="col-sm-3">
                                <input id="seq" type="text" value="0" class="form-control" name="seq" required="" aria-required="true">
                            </div>
                        </div>


                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <input type="hidden" name="cateId" value="${id}" />
                                <button class="btn btn-primary" type="submit">提交</button>
                                <a  class="btn btn-danger" type="button"  href="${basePath}/shop/goodsCateAttr/list/${id}"><i class="fa fa-close"></i> 返回</a>
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
                            window.location.href="${basePath}/shop/goodsCateAttr/list/${id}";
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