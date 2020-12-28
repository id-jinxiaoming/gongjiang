<!DOCTYPE html>
<html>

<head>


<#include "/common/header.ftl">
    <link rel="stylesheet" href="${basePath}/static/webupload/webuploader.css">
    <link rel="stylesheet" href="${basePath}/static/webupload/style.css">

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
                    <form class="form-horizontal m-t" id="myForm" action="${basePath}/rotation/chart/edit" method="post" enctype="multipart/form-data">

                        <div class="form-group">
                            <label class="col-sm-3 control-label">名称：</label>
                            <div class="col-sm-3">
                                <input id="title" type="text" value="${item.name}" class="form-control" name="name" required="" placeholder="请输入名称" aria-required="true">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">轮播图：</label>
                            <div class="input-group col-sm-4">
                                <input type="hidden" id="data_photo" value="${item.images}" name="images" >
                                <div id="fileList" class="uploader-list" style="float:right"></div>
                                <div id="imgPicker" style="margin-left: 15px;" >选择图片</div>
                                <div style="margin-top: 10px;"><img id="img_data" src="${item.images}" height="120px"  style="float:left;margin-left: 50px;margin-top: -10px;" src="${basePath}/static/webupload/empty_mid.png"/></div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">类型：</label>
                            <div class="col-sm-9">
                                <div class="radio i-checks">
                                    <label>
                                        <input type="radio"
                                        <#if item.type=="1">
                                               checked="checked"
                                        </#if>
                                               value="1" name="type"> <i></i>通知
                                    </label>
                                    <label>
                                        <input type="radio"
                                        <#if item.type=="2">
                                               checked="checked"
                                        </#if>
                                               value="2" name="type"> <i></i>公告
                                    </label>
                                    <label>
                                        <input type="radio"
                                        <#if item.type=="3">
                                               checked="checked"
                                        </#if>
                                               value="3" name="type"> <i></i>市场
                                    </label>
                                    <label>
                                        <input type="radio"
                                        <#if item.type=="4">
                                               checked="checked"
                                        </#if>
                                               value="4" name="type"> <i></i>政策
                                    </label>
                                    <label>
                                        <input type="radio"
                                        <#if item.type=="5">
                                               checked="checked"
                                        </#if>
                                               value="5" name="type"> <i></i>技术
                                    </label>
                                    <label>
                                        <input type="radio"
                                        <#if item.type=="6">
                                               checked="checked"
                                        </#if>
                                               value="6" name="type"> <i></i>管理
                                    </label>

                                </div>

                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">状态：</label>
                            <div class="col-sm-9">
                                <div class="radio i-checks">
                                    <label>
                                        <input type="radio"
                                        <#if item.status=="1">
                                               checked=""
                                        </#if>
                                               value="1" name="status"> <i></i>启用</label>
                                    <label>
                                        <input type="radio"
                                        <#if item.status=="0">
                                               checked=""
                                        </#if>
                                               value="0" name="status"> <i></i>禁用</label>
                                </div>

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">跳转ID：</label>
                            <div class="col-sm-3">
                                <input id="linkId" type="text" value="${item.linkId}" class="form-control" name="linkId" required="" aria-required="true">请输入图文列表id
                            </div>
                        </div>
                        <input type="hidden" name="id" value=" ${item.id}"/>
                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <button class="btn btn-primary" type="submit">提交</button>
                                <a  class="btn btn-danger" type="button"  href="${basePath}/rotation/chart/list"><i class="fa fa-close"></i> 返回</a>
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
<script src="${basePath}/static/hplus/js/plugins/iCheck/icheck.min.js"></script>
<script src="${basePath}/static/webupload/webuploader.min.js"></script>
<script src="${basePath}/static/layer/layer.js"></script>
<script >
    var $list = $('#fileList');
    //上传图片,初始化WebUploader
    var uploader = WebUploader.create({

        auto: true,// 选完文件后，是否自动上传。
        swf: '${basePath}/static/webupload/Uploader.swf',// swf文件路径
        server: "${basePath}/common/uploadOss",// 文件接收服务端。
        duplicate :true,// 重复上传图片，true为可重复false为不可重复
        pick: '#imgPicker',// 选择文件的按钮。可选。
        fileVal:'upfile',
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/jpg,image/jpeg,image/png'
        },
        'onUploadSuccess': function(file, data, response) {
            $("#data_photo").val(data);
            $("#img_data").attr('src', data).show();
        }
    });

    uploader.on( 'fileQueued', function( file ) {
        $list.html( '<div id="' + file.id + '" class="item">' +
                '<h4 class="info">' + file.name + '</h4>' +
                '<p class="state">正在上传...</p>' +
                '</div>' );
    });

    // 文件上传成功
    uploader.on( 'uploadSuccess', function( file ) {
        $( '#'+file.id ).find('p.state').text('上传成功！');
    });

    // 文件上传失败，显示上传出错。
    uploader.on( 'uploadError', function( file ) {
        $( '#'+file.id ).find('p.state').text('上传出错!');
    });
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
                            window.location.href="${basePath}/rotation/chart/list";
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