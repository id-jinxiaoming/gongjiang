<!DOCTYPE html>
<html>

<head>


<#include "/common/header.ftl">
    <script src="${basePath}/static/js/date.js"></script>


</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">

    <div class="row">

        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>用户信息</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                    </div>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="myForm" action="" method="post" >

                    <input type="hidden" name="userId" value="${item.userId}" />

                        <div class="form-group">
                            <label class="col-sm-3 control-label">用户名：</label>
                            <div class="col-sm-4">
                                <p class="form-control-static">${item.username}&nbsp;&nbsp;<#if item.status == 1>[正常]<#else >[禁用]</#if></p>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">头像：</label>
                            <div class="col-sm-4">
                                <img src="${item.avatar}" class="img-circle" style="width:50px;height:50px" onerror="this.src='${basePath}/static/webupload/head_default.gif'"/>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">手机号：</label>
                            <div class="col-sm-4">
                                <p class="form-control-static"><#if item.mobile !="">${item.mobile}&nbsp;&nbsp;<#if item.mobileStatus == 1>[已认证]<#else>[未认证]</#if><#else>未设置</#if></p>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">邮箱：</label>
                            <div class="col-sm-4">
                                <p class="form-control-static"><#if item.email !="">${item.email}&nbsp;&nbsp;<#if item.emailStatus == 1>[已认证]<#else>[未认证]</#if><#else>未设置</#if></p>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">账户金额：</label>
                            <div class="col-sm-9">
                                <p class="form-control-static">${account.balance}</p>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">账户积分：</label>
                            <div class="col-sm-9">
                                <p class="form-control-static">${account.points}</p>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">账户经验：</label>
                            <div class="col-sm-9">
                                <p class="form-control-static">${account.exp}</p>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">注册日期：</label>
                            <div class="col-sm-9">
                                <p class="form-control-static">
                                    <script type="text/javascript">
                                        var createdDate=
                                        document.write(getLocalTime("${record.createdDate}")); //内容用""括起来，""里的内容直接输出。
                                    </script>
                                     &nbsp;&nbsp;IP:${record.createdIp}</p>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">最后一次登录日期：</label>
                            <div class="col-sm-9">
                                <p class="form-control-static">
                                    <script type="text/javascript">
                                        document.write(getLocalTime("${record.lastDate}")); //内容用""括起来，""里的内容直接输出。
                                    </script>
                                    &nbsp;&nbsp;IP:${record.lastIp}</p>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <@shiro.hasPermission name="users:users:pwd">
                                <button class="btn btn-primary" type="button" onclick="openWin('重设密码','pwd')">重设密码</button>
                                </@shiro.hasPermission>
                                <@shiro.hasPermission name="users:users:account">
                                    <button class="btn btn-primary" type="button" onclick="openWin('调整帐号','account')">调整帐号数据</button>
                                </@shiro.hasPermission>
                                <a  class="btn btn-danger" type="button"  href="${basePath}/users/users/list"><i class="fa fa-close"></i> 返回</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>

<!-- 重设密码 -->
<div  id="pwdWin" style="display: none;">
    <div class="ibox-content">
        <form class="form-horizontal m-t"  id="pwdForm" action="${basePath}/users/users/pwd"  method="post" >

            <input type="hidden" NAME="userId" value="${item.userId}">
            <div class="form-group">
            <label class="col-sm-4 control-label">新密码：</label>
            <div class="col-sm-8">
                <input type="password" id="pwd" name="pwd" required="" placeholder="请输入新密码" class="form-control" ></input>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-4 control-label">确认密码：</label>
            <div class="col-sm-8">
                <input type="password" id="pwd1" name="pwd1" required="" placeholder="请输入确认新密码" class="form-control" ></input>
            </div>
        </div>
        <div class="hr-line-dashed"></div>
        <div class="form-group">
            <div class="col-sm-4 col-sm-offset-4" style="margin-bottom: 15px">
                <button class="btn btn-primary" type="submit">提交</button>
            </div>
        </div>
        </form>
    </div>
</div>
<!-- 调整账户 -->
<div  id="accountWin" style="display: none;">
    <div class="ibox-content">
        <form class="form-horizontal m-t" id="accountForm"  action="${basePath}/users/users/account"  method="post" >

            <input type="hidden"  NAME="userId" value="${item.userId}">
            <div class="form-group">
                <label class="col-sm-4 control-label">余额：</label>
                <div class="col-sm-8">
                    <input type="text" id="" name="balance" required="" placeholder="请输入余额" class="form-control" ></input>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">积分：</label>
                <div class="col-sm-8">
                    <input type="text" id="" name="points" required="" placeholder="请输入积分" class="form-control" ></input>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-4 control-label">经验：</label>
                <div class="col-sm-8">
                    <input type="text" id="" name="exp" required="" placeholder="请输入经验" class="form-control" ></input>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-4 control-label">原因：</label>
                <div class="col-sm-8">
                    <textarea id="" name="cause" class="form-control" ></textarea>

                </div>
            </div>

            <div class="hr-line-dashed"></div>
            <div class="form-group">
                <div class="col-sm-4 col-sm-offset-4" style="margin-bottom: 15px">
                    <button class="btn btn-primary" type="submit">提交</button>
                </div>
            </div>
        </form>
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

    $().ready(function() {

        $("#pwdForm").validate({
            rules: {
                name: "required",
                type: "required"

            }
        });

        $('#pwdForm').on('submit', function() {
           ;
            $(this).ajaxSubmit({
                type: 'post',
                beforeSubmit:function(){

                    return $("#pwdForm").valid();

                },
                success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
                    // 此处可对 data 作相关处理

                    if(data.status == 200){

                        layer.msg(data.message,{icon: 6,time:2000,shade: 0.1},function(index){
                            layer.close(index);

                        });
                        layer.close(indexWin);
                    }else{
                        layer.msg(data.message,{icon: 5,time:2000,shade: 0.1},function(index){
                            layer.close(index);
                        });
                    }


                }
            });
            return false; // 阻止表单自动提交事件
        });
        $("#accountForm").validate({
            rules: {
                name: "required",
                type: "required"

            }
        });

        $('#accountForm').on('submit', function() {

            $(this).ajaxSubmit({
                type: 'post',
                beforeSubmit:function(){

                    return $("#accountForm").valid();

                },
                success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
                    // 此处可对 data 作相关处理

                    if(data.status == 200){

                        layer.msg(data.message,{icon: 6,time:2000,shade: 0.1},function(index){
                            layer.close(index);
                            window.location.reload();

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

    var indexWin = '';
    //openWin
    function openWin(title,win){

        //页面层
        indexWin = layer.open({
            type: 1,
            area: ['40%', '68%'],
            title:title,
            shadeClose: true,
            content:$("#"+win+"Win")

        });

    }






</script>




</body>

</html>