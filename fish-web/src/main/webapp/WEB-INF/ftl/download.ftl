<!DOCTYPE html>
<html  xmlns="http://www.w3.org/1999/xhtml"
       xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,usered-scalable=0,width=device-width,initial-scale=1.0"/>
    <meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
    <title>天天聚乐补App下载</title>

    <link rel="stylesheet" href="${basePath}/static/downloadApp/css/reset.css" />
    <link rel="stylesheet" href="${basePath}/static/downloadApp/css/index.css" />


</head>
<body>
<div class="logo">
    <img src="${basePath}/static/downloadApp/img/logo.png" width="100"/>

</div>
<div class="btns">
    <a class="ios" href="${ios}">
        <i class="ios-icon"></i>天天聚乐补IOS下载
    </a>
    <a class="ios" href="${android}">
        <i class="android-icon"></i>天天聚乐补Android下载
    </a>
    <div style="color: rgba(22,22,22,0.57); font-size: 16px; padding-top: 50px;">
        注：如在微信内下载，请点击右上角按钮，在浏览器打开下载
    </div>
</div>

</body>
</html>
