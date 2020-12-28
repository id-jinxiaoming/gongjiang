<!DOCTYPE html>
<html>

<head>


	<#include "/common/header.ftl">
        <link rel="stylesheet" href="${basePath}/static/webupload/webuploader.css">
        <link rel="stylesheet" href="${basePath}/static/webupload/style.css">

        <link href="${basePath}/static/hplus/css/plugins/iCheck/custom.css" rel="stylesheet">
        <script charset="utf-8" src="${basePath}/static/kindeditor/kindeditor-all-min.js"></script>
        <script charset="utf-8" src="${basePath}/static/kindeditor/lang/zh-CN.js"></script>
        <link rel="stylesheet" href="${basePath}/static/layui/css/layui.css">
        <link rel="stylesheet" type="text/css" href="${basePath}/static/webuploader-0.1.5/webuploader.css">

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
                    <form class="form-horizontal m-t" id="myForm" action="${basePath}/shop/shop/add" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">商家名称：</label>
                            <div class="col-sm-3">
                                <input id="title" type="text" class="form-control" name="title" required="" placeholder="请输入商家名称" aria-required="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">所属用户：</label>
                            <div class="col-sm-3">
                                <input id="username" type="text" class="form-control" name="username" required="" placeholder="请输入用户帐号" aria-required="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">商家分类：</label>
                            <div class="col-sm-3">
                                <select class="form-control m-b" name="cateId">
                                <#list cate as item>
                                    <option value="${item.cateId}">${item.name}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">描述：</label>
                            <div class="col-sm-3">
                                <input id="subTitle" type="text" class="form-control" name="subTitle" required="" placeholder="请输入描述" aria-required="true">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">消费返利%：</label>
                            <div class="col-sm-3">
                                <input id="rate" type="text" class="form-control" name="rate" required="" placeholder="请输入消费返利%" aria-required="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">快捷支付可用积分%：</label>
                            <div class="col-sm-3">
                                <input id="rate" type="text" class="form-control" name="integral" required="" placeholder="请输入快捷支付可用积分%" aria-required="true">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">消费满：</label>
                            <div class="col-sm-3">
                                <input id="minMoney" type="text" class="form-control" name="minMoney" required="" placeholder="请输入消费满多少元" aria-required="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">减：</label>
                            <div class="col-sm-3">
                                <input id="jianMoney" type="text" class="form-control" name="jianMoney" required="" placeholder="请输入减多少元" aria-required="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">联系电话：</label>
                            <div class="col-sm-3">
                                <input id="tel" type="text"  class="form-control" name="tel" required="" aria-required="true">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">商家位置：</label>
                            <div class="col-sm-3">
                                <div>

                                    经度：
                                    <input id="lng" type="text"   class="form-control" name="lng" readonly=readonly>
                                    纬度：
                                    <input id="lat" type="text"  class="form-control" name="lat" readonly=readonly>
                                </div>

                                请输入地址搜索:<input type="text" id="suggestId" size="20" value="百度" style="width:150px;" />
                                <div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
                                <div id="l-map"></div>
                            </div>
                        </div>



                        <div class="form-group">
                            <label class="col-sm-3 control-label">地址：</label>
                            <div class="col-sm-3">
                                <input id="address" type="text"  class="form-control" name="address" required="" aria-required="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">LOGO图(200px*200px)：</label>
                            <div class="col-sm-9">
                                <!--用来存放item-->
                                <img id="imgPhoto1" src="" />
                                <input id="photo1"  type="hidden" name="imgUrl" value=""/>
                                <div id="filePicker1">选择图片</div>

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">商家组图(800px*600px)：</label>
                            <div class="col-sm-9" >
                                <div id="uploader-demo">
                                    <!--用来存放item-->

                                    <div id="fileList" class="uploader-list">

                                    </div>

                                    <div id="filePicker" style="float:left">选择图片</div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">商家简介：</label>
                            <div class="col-sm-9" >
                                            <textarea id="editor_id" name="description" style="width:700px;height:300px;">

                                            </textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">购买须知：</label>
                            <div class="col-sm-9" >
                                            <textarea id="editor_id1" name="note" style="width:700px;height:300px;">

                                            </textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否推荐：</label>
                            <div class="col-sm-9">
                                <div class="radio i-checks">
                                    <label>
                                        <input type="radio" checked=checked value="1" name="isTui"> <i></i>是</label>
                                    <label>
                                        <input type="radio"  value="0" name="isTui"> <i></i>否</label>
                                </div>

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">状态：</label>
                            <div class="col-sm-9">
                                <div class="radio i-checks">
                                    <label>
                                        <input type="radio" checked=checked value="1" name="status"> <i></i>启用</label>
                                    <label>
                                        <input type="radio"  value="0" name="status"> <i></i>禁用</label>
                                </div>

                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <button class="btn btn-primary" type="submit">提交</button>
                                <a  class="btn btn-danger" type="button"  href="${basePath}/shop/shop/list/${id}"><i class="fa fa-close"></i> 返回</a>
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
<script src="${basePath}/static/layui/layui.js"></script>
<script >
    // 百度地图API功能
    function G(id) {
        return document.getElementById(id);
    }

    var map = new BMap.Map("l-map");
    map.centerAndZoom("兰州",12);                   // 初始化地图,设置城市和地图级别。
    map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
    var geoc = new BMap.Geocoder();
    function showInfo(e){
        map.clearOverlays();
        addMarker(e.point);

        $("#lng").val(e.point.lng);
        $("#lat").val(e.point.lat);

        geoc.getLocation(e.point, function(rs){
            var addComp = rs.addressComponents;
            $("#address").val(addComp.province  + addComp.city   + addComp.district  + addComp.street  + addComp.streetNumber);

        });

    }
    map.addEventListener("click", showInfo);
    // 编写自定义函数,创建标注
    function addMarker(point){
        var marker = new BMap.Marker(point);
        map.addOverlay(marker);
    }
    var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
            {"input" : "suggestId"
                ,"location" : map
            });

    ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
        var str = "";
        var _value = e.fromitem.value;
        var value = "";
        if (e.fromitem.index > -1) {
            value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
        }
        str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;

        value = "";
        if (e.toitem.index > -1) {
            _value = e.toitem.value;
            value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
        }
        str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
        G("searchResultPanel").innerHTML = str;
    });

    var myValue;
    ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
        var _value = e.item.value;
        myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
        G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;

        setPlace();
    });

    function setPlace(){
        map.clearOverlays();    //清除地图上所有覆盖物
        function myFun(){
            var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
            map.centerAndZoom(pp, 18);
            map.addOverlay(new BMap.Marker(pp));    //添加标注
        }
        var local = new BMap.LocalSearch(map, { //智能搜索
            onSearchComplete: myFun
        });
        local.search(myValue);
    }

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
    initUploadSingle();
    initUpload();
    $().ready(function() {
        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });
        var editor = KindEditor.create('textarea[name="description"]', {
            uploadJson : '${basePath}/common/editUpload',
            allowFileManager : true,
            items : ['source', '|', 'undo', 'redo', '|', 'preview', 'template', 'cut', 'copy', 'paste',
                'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
                'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image','multiimage',
                'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
                'anchor', 'link', 'unlink']
        });
        var editor1 = KindEditor.create('textarea[name="note"]', {
            uploadJson : '${basePath}/common/editUpload',
            allowFileManager : true,
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
                            window.location.href="${basePath}/shop/shop/list";
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