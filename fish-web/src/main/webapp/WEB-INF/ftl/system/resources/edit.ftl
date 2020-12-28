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
                    <h5>编辑权限</h5>
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
                    <form class="form-horizontal m-t" id="myForm" action="${basePath}/system/resources/edit" method="post">
                        <input type="hidden" name="parentId" value="${item.parentId!}" />
                        <input type="hidden" name="id" value="${item.id!}" />
                        <div class="form-group">
                            <label class="col-sm-3 control-label">权限名称：</label>
                            <div class="col-sm-4">
                                <input  name="name" value="${item.name!}" class="form-control" placeholder="权限名称" type="text">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">URL：</label>
                            <div class="col-sm-4">
                                <input id="url" name="url" value="${item.url!}" placeholder="模块/控制器/方法" class="form-control" type="text">
                                <span class="help-block m-b-none">如：/system/usered/list (一级url填"/"即可)</span>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">权限：</label>
                            <div class="col-sm-4">
                                <input id="permission" name="permission" value="${item.permission!}" placeholder="模块/控制器/方法" class="form-control" type="text">
                                <span class="help-block m-b-none">如：system:usered:list </span>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">图标：</label>
                            <div class="col-sm-4">
                                <i id="iconIcon" class="icon- hide"></i>&nbsp;<span id="iconIconLabel">无</span>&nbsp;
                                <input id="icon" name="icon" value="${item.icon!}" type="hidden" value=""/><a id="iconButton" href="javascript:" class="btn btn-primary">选择</a>&nbsp;&nbsp;
                                <input id="iconclear" class="btn btn-default" type="button" value="清除" onclick="clear()"/>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">排序：</label>
                            <div class="col-sm-4">
                                <input id="sort" name="sort" value="${item.sort!}" placeholder="输入排序" value="0" class="form-control" type="text">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">功能菜单：</label>
                            <div class="col-sm-6">
                                <div class="radio i-checks">

                                <#if item.type == 1>

                                    <label><input type="radio" value="1" name="type" checked> <i></i> 菜单</label>
                                    <label><input type="radio" value="2" name="type" > <i></i> 功能</label>
                                </#if>

                                <#if item.type == 2>
                                    <label><input type="radio" value="1" name="type" > <i></i> 菜单</label>
                                    <label><input type="radio" value="2" name="type" checked> <i></i> 功能</label>
                                </#if>

                                </div>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <button id="btnSubmit" class="btn btn-primary" type="submit"><i class="fa fa-save"></i> 提交</button>
                                <button  class="btn btn-danger" type="button" onclick="javascript:history.go(-1);"><i class="fa fa-close"></i> 返回</button>
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
<script src="${basePath}/static/webupload/webuploader.min.js"></script>
<script src="${basePath}/static/hplus/js/plugins/iCheck/icheck.min.js"></script>
<script>
    //选择图片的弹出框
    $("#iconButton").click(function(){

        top.layer.open({
            type: 2,
            title:"选择图标",
            area: ['700px',  $(top.document).height()-180+"px"],
            content: '${basePath}/common/icon',
            btn: ['确定', '关闭'],
            yes: function(index, layero){ //或者使用btn1
                var icon = layero.find("iframe")[0].contentWindow.$("#icon").val();
                $("#iconIcon").attr("class", "fa "+icon);
                $("#iconIconLabel").text(icon);
                $("#icon").val(icon);
                top.layer.close(index);
            },cancel: function(index){ //或者使用btn2

            }
        });
    });
    $("#iconclear").click(function(){
        $("#iconIcon").attr("class", "icon- hide");
        $("#iconIconLabel").text("无");
        $("#icon").val("");

    });

</script>
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
                            window.location.href="${basePath}/system/resources/list";
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