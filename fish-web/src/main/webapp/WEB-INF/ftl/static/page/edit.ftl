<!DOCTYPE html>
<html>

<head>


<#include "/common/header.ftl">
    <link rel="stylesheet" href="${basePath}/static/webupload/webuploader.css">
    <link rel="stylesheet" href="${basePath}/static/webupload/style.css">
    <link href="${basePath}/static/hplus/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link rel="stylesheet" href="${basePath}/static/layui/css/layui.css">

    <script charset="utf-8" src="${basePath}/static/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${basePath}/static/kindeditor/lang/zh-CN.js"></script>

    <link rel="stylesheet" type="text/css" href="${basePath}/static/webuploader-0.1.5/webuploader.css">




    <style type="text/css">
        .layui-tab-title {border-bottom: 0px;}
        .thumbnail{border: 0px;}
        #fileList{width: 100%;}
        #filePicker{width:86px;height: 40px;}
        .thumbnail{float: left;margin-right: 10px;width: 150px;}

    </style>

</head>

<body class="gray-bg" >

<form class="form-horizontal m-t" id="myForm" action="${basePath}/static/page/edit" method="post" enctype="multipart/form-data">

    <div class="wrapper wrapper-content animated fadeInRight">

        <div class="row">

            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <div class="builder-tabs builder-form-tabs">
                            <ul class="nav nav-tabs">
                                <li class="active"><a href="${basePath}/static/page/edit">编辑</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="ibox-content" style="border:none;">
                        <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
                            <ul class="layui-tab-title">
                                <li class="layui-this">基本信息</li>
                            </ul>
                            <div class="layui-tab-content" style="height: 100%;">

                                <div class="layui-tab-item layui-show">

                                    <div class="ibox-content">

                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">信用评价(750px*2561px)：</label>
                                            <div class="col-sm-9" >
                                                <div >
                                                    <!--用来存放item-->
                                                        <img id="imgPhoto1" src="${staticPage.creditEvaluation}" width="150px" height="510px"/>
                                                    <input id="photo1"  value="${staticPage.creditEvaluation}"  type="hidden" name="creditEvaluation" value=""/>
                                                    <div id="filePicker1">选择图片</div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">咨询服务(750px*776px)：</label>
                                            <div class="col-sm-9" >
                                                <div >
                                                    <!--用来存放item-->
                                                    <img id="imgPhoto2" src="${staticPage.consultingService}" width="150px" height="150px" style="background-color: rgb(0,0,0,0.4)"/>
                                                    <input id="photo2"  value="${staticPage.consultingService}"  type="hidden" name="consultingService" value=""/>
                                                    <div id="filePicker3">选择图片</div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">关于我们(750px*5960px)：</label>
                                            <div class="col-sm-9" >
                                                <div >
                                                    <!--用来存放item-->
                                                    <img id="imgPhoto4" src="${staticPage.aboutUs}" width="150px" height="1191px"/>
                                                    <input id="photo4"  value="${staticPage.aboutUs}"  type="hidden" name="aboutUs" value=""/>
                                                    <div id="filePicker4">选择图片</div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">客服二维码(144px*144px)：</label>
                                            <div class="col-sm-9" >
                                                <div >
                                                    <!--用来存放item-->
                                                    <img id="imgPhoto5" src="${staticPage.customerService}" width="150px" height="150px"/>
                                                    <input id="photo5"  value="${staticPage.customerService}"  type="hidden" name="customerService" value=""/>
                                                    <div id="filePicker5">选择图片</div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">工友群(144px*144px)：</label>
                                            <div class="col-sm-9" >
                                                <div >
                                                    <!--用来存放item-->
                                                    <img id="imgPhoto6" src="${staticPage.workmates}" width="150px" height="150px"/>
                                                    <input id="photo6"  value="${staticPage.workmates}"  type="hidden" name="workmates" value=""/>
                                                    <div id="filePicker6">选择图片</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <input type="hidden" name="id" value=" ${staticPage.id}"/>
                                <button class="btn btn-primary" type="submit">提交</button>
                                <a  class="btn btn-danger" type="button"  href="${basePath}/static/page/list"><i class="fa fa-close"></i> 返回</a>
                            </div>
                        </div>

                    </div>

                </div>
            </div>
        </div>

    </div>
</form>



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
<script src="${basePath}/static/layui/layui.js"></script>
<!-- iCheck -->
<script src="${basePath}/static/hplus/js/plugins/iCheck/icheck.min.js"></script>
<script src="${basePath}/static/webuploader-0.1.5/webuploader.min.js"></script>


<script >


    var flag=false;
    layui.use(['element', 'layer'], function(){
        var element = layui.element()
                ,layer = layui.layer;

        element.on('tab(docDemoTabBrief)', function(data){
            if(data.index==1&&!flag)
            {
                flag=true;

                initUpload();
            }
            //layer.msg('切到到了'+ data.index + '：' + this.innerHTML);
        });
    });
    initUploadSingle();
    function initUploadSingle(){
        ratio = window.devicePixelRatio || 1,
         // 缩略图大小
        thumbnailWidth = 150 * ratio;
        thumbnailHeight = 150 * ratio;
        // Web Uploader实例
        var uploader1;
        // 初始化Web Uploader
        uploader1 = WebUploader.create({
            // 自动上传。
            auto: true,
            // swf文件路径
            swf:  '${basePath}/static/webuploader-0.1.5/Uploader.swf',
            // 文件接收服务端。
            server: '${basePath}/common/uploadOss',
            // [默认值：'file']  设置文件上传域的name。
            fileVal:'upfile',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#filePicker1',
            // 只允许选择文件，可选。
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });
        // 当有文件添加进来的时候
        uploader1.on( 'fileQueued', function( file ) {
            // 创建缩略图
            uploader1.makeThumb( file, function( error, src ) {
                if ( error ) {
                    $("#imgPhoto1").replaceWith('<span>不能预览</span>');
                    return;
                }
                $("#imgPhoto1").attr( 'src', src );
            }, thumbnailWidth, thumbnailHeight );
        });
        // 文件上传过程中创建进度条实时显示。
        uploader1.on( 'uploadProgress', function( file, percentage ) {
            var $li = $( '#'+file.id ),
                    $percent = $li.find('.progress span');
            // 避免重复创建
            if ( !$percent.length ) {
                $percent = $('<p class="progress"><span></span></p>')
                        .appendTo( $li )
                        .find('span');
            }
            $percent.css( 'width', percentage * 100 + '%' );
        });
        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader1.on( 'uploadSuccess', function( file,data ) {
            $('#photo1').val(data);
            $( '#'+file.id ).addClass('upload-state-done');
        });
        // 文件上传失败，现实上传出错。
        uploader1.on( 'uploadError', function( file ) {
            var $li = $( '#'+file.id ),
                    $error = $li.find('div.error');
            // 避免重复创建
            if ( !$error.length ) {
                $error = $('<div class="error"></div>').appendTo( $li );
            }
            $error.text('上传失败!!!');
        });
        // 完成上传完了，成功或者失败，先删除进度条。
        uploader1.on( 'uploadComplete', function( file ) {
            $( '#'+file.id ).find('.progress').remove();
        });
        uploader1.on('error', function(handler) {
            if (handler == "Q_EXCEED_NUM_LIMIT") {
                alert("只能上传一张图片");
            }
        });
    }


    initUploadSingles();
    function initUploadSingles(){
        ratio = window.devicePixelRatio || 1,
                thumbnailWidth = 150 * ratio;
        thumbnailHeight = 150 * ratio;
        var uploader1;
        uploader1 = WebUploader.create({
            auto: true,
            swf:  '${basePath}/static/webuploader-0.1.5/Uploader.swf',
            server: '${basePath}/common/uploadOss',
            fileVal:'upfile',
            pick: '#filePicker3',
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });
        uploader1.on( 'fileQueued', function( file ) {
            uploader1.makeThumb( file, function( error, src ) {
                if ( error ) {
                    $("#imgPhoto2").replaceWith('<span>不能预览</span>');
                    return;
                }
                $("#imgPhoto2").attr( 'src', src );
            }, thumbnailWidth, thumbnailHeight );
        });
        uploader1.on( 'uploadProgress', function( file, percentage ) {
            var $li = $( '#'+file.id ),
                    $percent = $li.find('.progress span');
            if ( !$percent.length ) {
                $percent = $('<p class="progress"><span></span></p>')
                        .appendTo( $li )
                        .find('span');
            }
            $percent.css( 'width', percentage * 100 + '%' );
        });
        uploader1.on( 'uploadSuccess', function( file,data ) {
            $('#photo2').val(data);
            $( '#'+file.id ).addClass('upload-state-done');
        });
        uploader1.on( 'uploadError', function( file ) {
            var $li = $( '#'+file.id ),
                    $error = $li.find('div.error');
            if ( !$error.length ) {
                $error = $('<div class="error"></div>').appendTo( $li );
            }
            $error.text('上传失败!!!');
        });
        uploader1.on( 'uploadComplete', function( file ) {
            $( '#'+file.id ).find('.progress').remove();
        });
        uploader1.on('error', function(handler) {
            if (handler == "Q_EXCEED_NUM_LIMIT") {
                alert("只能上传一张图片");
            }
        });
    }



    initUploadSingle1();
    function initUploadSingle1(){
        ratio = window.devicePixelRatio || 1,
                thumbnailWidth = 150 * ratio;
        thumbnailHeight = 150 * ratio;
        var uploader1;
        uploader1 = WebUploader.create({
            compress:false,
            auto: true,
            swf:  '${basePath}/static/webuploader-0.1.5/Uploader.swf',
            server: '${basePath}/common/uploadOss',
            fileVal:'upfile',
            pick: '#filePicker4',
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });
        uploader1.on( 'fileQueued', function( file ) {
            uploader1.makeThumb( file, function( error, src ) {
                if ( error ) {
                    $("#imgPhoto4").replaceWith('<span>不能预览</span>');
                    return;
                }
                $("#imgPhoto4").attr( 'src', src );
            }, thumbnailWidth, thumbnailHeight );
        });
        uploader1.on( 'uploadProgress', function( file, percentage ) {
            var $li = $( '#'+file.id ),
                    $percent = $li.find('.progress span');
            if ( !$percent.length ) {
                $percent = $('<p class="progress"><span></span></p>')
                        .appendTo( $li )
                        .find('span');
            }
            $percent.css( 'width', percentage * 100 + '%' );
        });
        uploader1.on( 'uploadSuccess', function( file,data ) {
            $('#photo4').val(data);
            $( '#'+file.id ).addClass('upload-state-done');
        });
        uploader1.on( 'uploadError', function( file ) {
            var $li = $( '#'+file.id ),
                    $error = $li.find('div.error');
            if ( !$error.length ) {
                $error = $('<div class="error"></div>').appendTo( $li );
            }
            $error.text('上传失败!!!');
        });
        uploader1.on( 'uploadComplete', function( file ) {
            $( '#'+file.id ).find('.progress').remove();
        });
        uploader1.on('error', function(handler) {
            if (handler == "Q_EXCEED_NUM_LIMIT") {
                alert("只能上传一张图片");
            }
        });
    }




    initUploadSingle2();
    function initUploadSingle2(){
        ratio = window.devicePixelRatio || 1,
                thumbnailWidth = 150 * ratio;
        thumbnailHeight = 150 * ratio;
        var uploader1;
        uploader1 = WebUploader.create({
            auto: true,
            swf:  '${basePath}/static/webuploader-0.1.5/Uploader.swf',
            server: '${basePath}/common/uploadOss',
            fileVal:'upfile',
            pick: '#filePicker5',
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });
        uploader1.on( 'fileQueued', function( file ) {
            uploader1.makeThumb( file, function( error, src ) {
                if ( error ) {
                    $("#imgPhoto5").replaceWith('<span>不能预览</span>');
                    return;
                }
                $("#imgPhoto5").attr( 'src', src );
            }, thumbnailWidth, thumbnailHeight );
        });
        uploader1.on( 'uploadProgress', function( file, percentage ) {
            var $li = $( '#'+file.id ),
                    $percent = $li.find('.progress span');
            if ( !$percent.length ) {
                $percent = $('<p class="progress"><span></span></p>')
                        .appendTo( $li )
                        .find('span');
            }
            $percent.css( 'width', percentage * 100 + '%' );
        });
        uploader1.on( 'uploadSuccess', function( file,data ) {
            $('#photo5').val(data);
            $( '#'+file.id ).addClass('upload-state-done');
        });
        uploader1.on( 'uploadError', function( file ) {
            var $li = $( '#'+file.id ),
                    $error = $li.find('div.error');
            if ( !$error.length ) {
                $error = $('<div class="error"></div>').appendTo( $li );
            }
            $error.text('上传失败!!!');
        });
        uploader1.on( 'uploadComplete', function( file ) {
            $( '#'+file.id ).find('.progress').remove();
        });
        uploader1.on('error', function(handler) {
            if (handler == "Q_EXCEED_NUM_LIMIT") {
                alert("只能上传一张图片");
            }
        });
    }


    initUploadSingle3();
    function initUploadSingle3(){
        ratio = window.devicePixelRatio || 1,
                thumbnailWidth = 150 * ratio;
        thumbnailHeight = 150 * ratio;
        var uploader1;
        uploader1 = WebUploader.create({
            auto: true,
            swf:  '${basePath}/static/webuploader-0.1.5/Uploader.swf',
            server: '${basePath}/common/uploadOss',
            fileVal:'upfile',
            pick: '#filePicker6',
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });
        uploader1.on( 'fileQueued', function( file ) {
            uploader1.makeThumb( file, function( error, src ) {
                if ( error ) {
                    $("#imgPhoto6").replaceWith('<span>不能预览</span>');
                    return;
                }
                $("#imgPhoto6").attr( 'src', src );
            }, thumbnailWidth, thumbnailHeight );
        });
        uploader1.on( 'uploadProgress', function( file, percentage ) {
            var $li = $( '#'+file.id ),
                    $percent = $li.find('.progress span');
            if ( !$percent.length ) {
                $percent = $('<p class="progress"><span></span></p>')
                        .appendTo( $li )
                        .find('span');
            }
            $percent.css( 'width', percentage * 100 + '%' );
        });
        uploader1.on( 'uploadSuccess', function( file,data ) {
            $('#photo6').val(data);
            $( '#'+file.id ).addClass('upload-state-done');
        });
        uploader1.on( 'uploadError', function( file ) {
            var $li = $( '#'+file.id ),
                    $error = $li.find('div.error');
            if ( !$error.length ) {
                $error = $('<div class="error"></div>').appendTo( $li );
            }
            $error.text('上传失败!!!');
        });
        uploader1.on( 'uploadComplete', function( file ) {
            $( '#'+file.id ).find('.progress').remove();
        });
        uploader1.on('error', function(handler) {
            if (handler == "Q_EXCEED_NUM_LIMIT") {
                alert("只能上传一张图片");
            }
        });
    }




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
                            window.location.href="${basePath}/static/page/list";
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