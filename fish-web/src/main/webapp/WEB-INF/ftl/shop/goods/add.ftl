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

<form class="form-horizontal m-t" id="myForm" action="${basePath}/shop/goods/add" method="post" enctype="multipart/form-data">

    <div class="wrapper wrapper-content animated fadeInRight">

        <div class="row">

            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <div class="builder-tabs builder-form-tabs">
                            <ul class="nav nav-tabs">
                                <li class="active"><a href="${basePath}/shop/goods/add">创建</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="ibox-content" style="border:none;">
                        <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
                            <ul class="layui-tab-title">
                                <li class="layui-this">基本信息</li>
                                <li>商品图片</li>
                                <li>商品详情</li>
                                <li>其他信息</li>
                            </ul>
                            <div class="layui-tab-content" style="height: 100%;">

                                <div class="layui-tab-item layui-show">

                                    <div class="ibox-content">

                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">商品名称：</label>
                                            <div class="col-sm-3">

                                                <input id="goodsName" type="text" class="form-control" name="goodsName" required="" placeholder="请输入商品名称" aria-required="true">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">商品分类：</label>
                                            <div class="col-sm-3">
                                                <select class="form-control m-b" name="cateId">
                                                    <option value="0">无分类</option>
                                                    <#list cate as item>
                                                        <option value="${item.cateId}">${item.cateName}</option>
                                                    </#list>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">所属品牌：</label>
                                            <div class="col-sm-3">
                                                <select class="form-control m-b" name="brandId">
                                                    <option value="0">无品牌</option>
                                                    <#list brand as item>
                                                        <option value="${item.brandId}">${item.brandName}</option>
                                                    </#list>

                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">商品货号：</label>
                                            <div class="col-sm-3">
                                                <input id="goodsSn" type="text" class="form-control" name="goodsSn" required="" placeholder="请输入商品货号" aria-required="true"><span class="help-block m-b-none">长度不应超过20个字符, 留空则系统会按约定规则自动生成货号</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">当前售价：</label>
                                            <div class="col-sm-3">
                                                <input id="nowPrice" type="text" class="form-control" name="nowPrice" required="" placeholder="请输入当前售价" aria-required="true"><span class="help-block m-b-none">售价请按此格式填写, 如: "188.00" 或 "8.00"</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">原售价：</label>
                                            <div class="col-sm-3">
                                                <input id="originalPrice" type="text" class="form-control" name="originalPrice" required="" placeholder="请输入原售价" aria-required="true"><span class="help-block m-b-none">留空或为0时，前台不显示原售价</span>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">商品简介：</label>
                                            <div class="col-sm-9" >
                                            <textarea id="editor_id" name="goodsBrief" style="width:700px;height:300px;">

                                            </textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">标记：</label>
                                            <div class="col-sm-6">
                                                <div class="checkbox i-checks">
                                                    <label>
                                                        <input type="checkbox" name="newarrival" value="1"> <i></i> 新品</label>
                                                    <label>
                                                        <input type="checkbox" name="recommend" value="1"> <i></i> 推荐</label>
                                                    <label>
                                                        <input type="checkbox" name="bargain" value="1"> <i></i> 特价</label>

                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">状态：</label>
                                            <div class="col-sm-9">
                                                <div class="radio i-checks">
                                                    <label>
                                                        <input type="radio" checked=checked value="1" name="status"> <i></i>上架</label>
                                                    <label>
                                                        <input type="radio"  value="0" name="status"> <i></i>下架</label>
                                                </div>

                                            </div>
                                        </div>

                                    </div>


                                </div>
                                <div class="layui-tab-item">
                                    <div class="layui-tab-item layui-show">
                                        <div class="ibox-content">

                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">商品图片(200px*200px)：</label>
                                                <div class="col-sm-9" >
                                                    <div >
                                                        <!--用来存放item-->
                                                        <img id="imgPhoto1" src="" />
                                                        <input id="photo1"  type="hidden" name="goodsImage" value=""/>
                                                        <div id="filePicker1">选择图片</div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">商品组图(800px*600px)：</label>
                                                <div class="col-sm-9" >
                                                    <div id="uploader-demo">
                                                        <!--用来存放item-->

                                                        <div id="fileList" class="uploader-list">

                                                        </div>

                                                        <div id="filePicker" style="float:left">选择图片</div>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>



                                </div>
                                <div class="layui-tab-item">
                                    <div class="layui-tab-item layui-show">
                                        <div class="ibox-content">

                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">商品详情：</label>
                                                <div class="col-sm-9" >
                                                <textarea id="editor_id1" name="goodsContent" style="width:700px;height:300px;">

                                                </textarea>
                                                </div>
                                            </div>




                                        </div>
                                    </div>
                                </div>
                                <div class="layui-tab-item">
                                    <div class="layui-tab-item layui-show">
                                        <div class="ibox-content">
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">可用积分：</label>
                                                <div class="col-sm-3">
                                                    <input id="integral" type="text" class="form-control" name="integral" required="" placeholder="请输入可用积分" aria-required="true"><span class="help-block m-b-none"></span>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">返利积分：</label>
                                                <div class="col-sm-3">
                                                    <input id="rebateIntegral" type="text" class="form-control" name="rebateIntegral" required="" placeholder="请输入返利积分" aria-required="true"><span class="help-block m-b-none"></span>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">库存数量：</label>
                                                <div class="col-sm-3">
                                                    <input id="stockQty" type="text" class="form-control" name="stockQty" required="" placeholder="请输入库存数量" aria-required="true"><span class="help-block m-b-none"></span>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">重量：</label>
                                                <div class="col-sm-3">
                                                    <input id="goodsWeight" type="text" class="form-control" name="goodsWeight" required="" placeholder="请输入重量" aria-required="true"><span class="help-block m-b-none">Kg (千克)</span>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">Meta 关键词：</label>
                                                <div class="col-sm-9" >

                                                     <textarea id="metaKeywords" name="metaKeywords" class="form-control" ></textarea>

                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">Meta 描述：</label>
                                                <div class="col-sm-9" >
                                                <textarea id="metaDescription" name="metaDescription" class="form-control" ></textarea>
                                                </textarea>
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
                                <button class="btn btn-primary" type="submit">提交</button>
                                <a  class="btn btn-danger" type="button"  href="${basePath}/shop/goods/list"><i class="fa fa-close"></i> 返回</a>
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
                initUploadSingle();
                initUpload();
            }
            //layer.msg('切到到了'+ data.index + '：' + this.innerHTML);
        });
    });
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
            var $li = $( '#'+file.id ), $percent = $li.find('.progress span');
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
            var $li = $( '#'+file.id ), $error = $li.find('div.error');
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
    var  uploader;
    function initUpload(){
        var $ = jQuery, $list = $('#fileList'),
                // 优化retina, 在retina下这个值是2
                ratio = window.devicePixelRatio || 1,
                // 缩略图大小
                thumbnailWidth = 150 * ratio,
                thumbnailHeight = 150 * ratio;

        // 初始化Web Uploader
        uploader = WebUploader.create({
            fileNumLimit: 5,
            // 自动上传。
            auto: true,
            duplicate :true,
            // swf文件路径
            swf:  '${basePath}/static/webuploader-0.1.5/Uploader.swf',
            // 文件接收服务端。
            server: '${basePath}/common/uploadOss',
            // [默认值：'file']  设置文件上传域的name。
            fileVal:'upfile',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#filePicker',
            // 只允许选择文件，可选。
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });

        // 当有文件添加进来的时候
        uploader.on( 'fileQueued', function( file ) {
            var $li = $(

                            '<div  id="' + file.id + '" class="file-item thumbnail">' +
                            '<img>' +
                            '<div class="info" style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap; width:150px;">' + file.name + '</div>' +
                            '<div style="text-align: right;"><button class="info"  onclick=delImg(\''+file.id+'\') >删除</button></div>' +
                            '</div>'
                    ),
                    $img = $li.find('img');
            $list.append( $li );

            // 创建缩略图
            uploader.makeThumb( file, function( error, src ) {
                if ( error ) {
                    $img.replaceWith('<span>不能预览</span>');
                    return;
                }

                $img.attr( 'src', src );
            }, 150, 150 );
        });
        uploader.on('error', function(handler) {
            if (handler == "Q_EXCEED_NUM_LIMIT") {
                alert("只能上传五张图片");
            }
        });
        // 文件上传过程中创建进度条实时显示。
        uploader.on( 'uploadProgress', function( file, percentage ) {

            var $li = $( '#'+file.id ), $percent = $li.find('.progress span');
            // 避免重复创建
            if ( !$percent.length ) {
                $percent = $('<p class="progress"><span></span></p>')
                        .appendTo( $li )
                        .find('span');
            }
            $percent.css( 'width', percentage * 100 + '%' );
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader.on( 'uploadSuccess', function( file,data ) {
            $('#uploader-demo').append('<input id="photoId_'+file.id+'"  type="hidden" name="photo" value="'+data+'"/>');
            $( '#'+file.id ).addClass('upload-state-done');
        });

        // 文件上传失败，现实上传出错。
        uploader.on( 'uploadError', function( file ) {
            var $li = $( '#'+file.id ), $error = $li.find('div.error');
            // 避免重复创建
            if ( !$error.length ) {
                $error = $('<div class="error"></div>').appendTo( $li );
            }
            $error.text('上传失败!!!');
        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploader.on( 'uploadComplete', function( file ) {
            $( '#'+file.id ).find('.progress').remove();
        });

    }
    function delImg(id){
        $( '#'+id ).remove();
        uploader.removeFile( id,true );
        $('#uploader-demo').find("#photoId_"+id).remove();

    }


    $().ready(function() {
        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });

        var editor = KindEditor.create('textarea[name="goodsBrief"]', {
            uploadJson : '${basePath}/common/editUpload',
            allowFileManager : false,
            allowPreviewEmoticons : false,
            items : ['source', '|', 'undo', 'redo', '|', 'preview', 'template', 'cut', 'copy', 'paste',
                'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
                'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image','multiimage',
                'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
                'anchor', 'link', 'unlink']
        });
        var editor1 = KindEditor.create('textarea[name="goodsContent"]', {
            uploadJson : '${basePath}/common/editUpload',
            allowFileManager : false,
            allowPreviewEmoticons : false,
            items : ['source', '|', 'undo', 'redo', '|', 'preview', 'template', 'cut', 'copy', 'paste',
                'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
                'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image','multiimage',
                'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
                'anchor', 'link', 'unlink']
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
                            window.location.href="${basePath}/shop/goods/list";
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