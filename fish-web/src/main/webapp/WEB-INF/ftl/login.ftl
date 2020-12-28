<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

    <title>工匠后台管理 - 登录</title>
    <meta name="keywords" content="工匠后台管理">
    <meta name="description" content="工匠后台管理">
    <link href="${basePath}/static/hplus/css/bootstrap.min.css" rel="stylesheet">
    <link href="${basePath}/static/hplus/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${basePath}/static/hplus/css/animate.css" rel="stylesheet">
    <link href="${basePath}/static/hplus/css/style.css" rel="stylesheet">
    <link href="${basePath}/static/hplus/css/login.css" rel="stylesheet">

    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <script>
        if (window.top !== window.self) {
            window.top.location = window.location;
        }
    </script>

</head>

<body class="signin">
<div class="signinpanel">
    <div class="row">
        <div class="col-sm-7">
            <div class="signin-info">
                <div class="logopanel m-b">
                    <h1 style="color:#050000 ">[工匠管理平台]</h1>
                </div>
                <div class="m-b"></div>
                <h4 style="color: #050000">欢迎使用 <strong>工匠管理平台</strong></h4>
                <ul class="m-b">
                    <li style="color: #050000"><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 分布式</li>
                    <li style="color: #050000"><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 高并发</li>
                    <li style="color: #050000"><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 集群部署</li>

                </ul>

            </div>
        </div>

        <div class="col-sm-5">
            <form method="post" action="index.html">
                <h4 class="no-margins" style="color: #ffff00">登录：</h4>
                <p class="m-t-md" style="color: #ffff00">登录到后台管理系统</p>
                <input type="text" id="username" class="form-control uname" placeholder="用户名" />
                <input type="password" id="password" class="form-control pword m-b" placeholder="密码" />
                <button id="login_btn" class="btn btn-success btn-block">登录</button>
            </form>
        </div>
    </div>
    <div class="signup-footer">
        <div class="pull-left">
            &copy; 2019 All Rights Reserved.
        </div>
    </div>
</div>


</body>
<script src="${basePath}/static/hplus/js/jquery.min.js?v=2.1.4"></script>
<script src="${basePath}/static/hplus/js/bootstrap.min.js?v=3.3.6"></script>
<script type="text/javascript" src="${basePath}/static/layer/layer.js"></script>

<script type="text/javascript">
    var lock = false;


    $(function () {


        $('#login_btn').click(function(){

            var username = $('#username').val();
            var password = $('#password').val();

            if(username == '') {
                $('#username').focus();
                return false;
            }
            if(password == '') {
                $('#password').focus();
                return false;
            }

            if(lock){
                return false;
            }
            lock = true;
            $('#login_btn').removeClass('btn-primary').addClass('btn-danger').val('登录中...');

            $.post("${basePath}/login",{'username':username, 'password':password},function(data){


                $('#login_btn').removeClass('btn-danger').addClass('btn-primary').val('登录');

                if(data.msg!=null){

                    layer.msg(data.msg);
                    lock = false;

                }else{
                    layer.msg('登录成功,正在跳转首页..', function(index){
                        window.location.href="${basePath}/";

                    });


                }
                return false;
            });
            return false;
        });
    });

</script>
</html>



