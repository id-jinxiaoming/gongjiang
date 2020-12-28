<!DOCTYPE html>
<html>

<head>


<#include "/common/header.ftl">

    <link href="${basePath}/static/hplus/css/plugins/iCheck/custom.css" rel="stylesheet">


    <style type="text/css">
        #l-map{height:400px;width:500px;}
        #r-result{width:100px;}

        .layui-tab-title {border-bottom: 0px;}
        .thumbnail{border: 0px;}
        #fileList{width: 100%;}
        #filePicker{width:86px;height: 40px;}
        .thumbnail{float: left;margin-right: 10px;width: 150px;}
    </style>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=LUjQ4DMGSQjczKGUxCQ8EZOTxiwG6VMF"></script>

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
                    <form class="form-horizontal m-t" id="myForm" action="${basePath}/shop/shopCoupon/edit" method="post" >
                        <div class="form-group">
                            <label class="col-sm-3 control-label">名称：</label>
                            <div class="col-sm-3">
                                <input id="title" value="${item.title}" type="text" class="form-control" name="title" required="" placeholder="请输入名称" aria-required="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">金额：</label>
                            <div class="col-sm-3">
                                <input id="price" value="${item.price}" type="text" class="form-control" name="price" required="" placeholder="请输入金额" aria-required="true">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">数量：</label>
                            <div class="col-sm-3">
                                <input id="count" value="${item.count}" type="text" class="form-control" name="count" required="" placeholder="请输入数量" aria-required="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">开始时间：</label>
                            <div class="col-sm-3">
                                <input  id="startTime" name="startTime" class="form-control layer-date" placeholder="YYYY-MM-DD" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
                                <label class="laydate-icon"></label>


                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">结束时间：</label>
                            <div class="col-sm-3">
                                <input id="endTime" name="endTime" class="form-control layer-date" placeholder="YYYY-MM-DD" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
                                <label class="laydate-icon"></label>
                            </div>
                        </div>



                        <div class="form-group">
                            <label class="col-sm-3 control-label">状态：</label>
                            <div class="col-sm-9">
                                <div class="radio i-checks">
                                    <label>
                                        <input type="radio"
                                        <#if item.status=="1">
                                               checked=checked
                                        </#if>
                                               value="1" name="status"> <i></i>启用</label>
                                    <label>
                                        <input type="radio"
                                        <#if item.status=="0">
                                               checked=checked
                                        </#if>
                                               value="0" name="status"> <i></i>禁用</label>
                                </div>

                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <input type="hidden" name="shopId" value="${item.shopId}" />
                                <input type="hidden" name="couponId" value="${item.couponId}" />
                                <button class="btn btn-primary" type="submit">提交</button>
                                <a  class="btn btn-danger" type="button"  href="${basePath}/shop/shopCoupon/list/${item.shopId}"><i class="fa fa-close"></i> 返回</a>
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
<script src="${basePath}/static/hplus/js/plugins/iCheck/icheck.min.js"></script>
<script src="${basePath}/static/common/js/jquery.form.js"></script>
<script src="${basePath}/static/layer/layer.js"></script>
<script src="${basePath}/static/hplus/js/plugins/layer/laydate/laydate.js"></script>
<script >
    var id='${id}';
    $().ready(function() {

        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });

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
                            window.location.href="${basePath}/shop/shopCoupon/list/${item.shopId}";
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