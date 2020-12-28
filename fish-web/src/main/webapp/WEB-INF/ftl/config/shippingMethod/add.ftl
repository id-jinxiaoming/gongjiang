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
                    <form class="form-horizontal m-t" id="myForm"  action="${basePath}/config/shippingMethod/add" method="post">
                    <div class="form-group">
                            <label class="col-sm-3 control-label">名称：</label>
                            <div class="col-sm-3">
                                <input id="name" type="text" class="form-control" name="name" required="" aria-required="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">配送范围：</label>


                            <div class="col-sm-9" id="paramsTitle">
                                <button type="button" class="btn btn-primary"  onclick="openParams()">配送范围</button>
                                <button type="button" class="btn btn-danger"  onclick="clearItem()">清空</button>
                            </div>

                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">排序：</label>
                            <div class="col-sm-3">
                                <input id="seq" type="text" class="form-control" name="seq" required="" aria-required="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">使用说明：</label>
                            <div class="col-sm-3">
                                <textarea id="instruction" name="instruction" class="form-control" required="" aria-required="true"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label" >状态：</label>
                            <div class="col-sm-6">

                                <div class="radio i-checks">
                                    <label><input type="radio" value="1" name="enable" checked > <i></i> 开启</label>
                                    <label><input type="radio" value="0" name="enable"  > <i></i> 关闭</label>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="params" id="params" />
                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <button class="btn btn-primary">提交</button>
                                <a  class="btn btn-danger" type="button"  href="${basePath}/config/shippingMethod/list"><i class="fa fa-close"></i> 返回</a>
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
    var params=new Array();
    function addParams(param,content){
        params.push(param);
        $("#paramsTitle").append("<p class='paramTitle' style='background-color: #f6f6f6';width='80px;'>"+content+"</p>");
    }
    function clearItem(){
        params=new Array();
        $(".paramTitle").remove();


    }
    $().ready(function() {

        $("#myForm").validate({
            rules: {
                name: "required",
                type: "required"
            }
        });
        $('#myForm').on('submit', function() {
            if(params.length==0){
                layer.msg("请选择配送区域",{icon: 5,time:2000,shade: 0.1},function(index){

                });
                return false;
            }
            $("#params").val(JSON.stringify(params));
            $(this).ajaxSubmit({
                type: 'post',
                beforeSubmit:function(){
                    return $("#myForm").valid();
                },
                success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
                    // 此处可对 data 作相关处理

                    if(data.status == 200){

                        layer.msg(data.message,{icon: 6,time:2000,shade: 0.1},function(index){
                            window.location.href="${basePath}/config/shippingMethod/list";
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

    function openParams(){
        layer.open({
            title:'配送区域',
            type: 2,
            area: ['700px', '450px'],
            fixed: true, //不固定
            maxmin: false,
            content: '${basePath}/config/shippingMethod/params'
        });

    }



</script>




</body>

</html>