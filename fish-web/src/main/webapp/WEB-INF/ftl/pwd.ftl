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
                        <h5>修改密码</h5>                      
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="myForm" action="${basePath}/index/doPwd" method="get">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">当前密码：</label>
                                <div class="col-sm-4">
                                    <input id="old_password" name="old_password" class="form-control" type="password">
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">新密码：</label>
                                <div class="col-sm-4">
                                    <input id="password" name="password" class="form-control" type="password">
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">确认密码：</label>
                                <div class="col-sm-4">
                                    <input id="confirm_password" name="confirm_password" class="form-control" type="password">
                                    <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> 请再次输入您的密码</span>
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
    <script src="${basePath}/static/hplus/js/jquery.min.js?v=2.1.4"></script>
    <script src="${basePath}/static/layer/layer.js"></script>
    <script src="${basePath}/static/hplus/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="${basePath}/static/hplus/js/content.js?v=1.0.0"></script>
    <script src="${basePath}/static/hplus/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${basePath}/static/hplus/js/plugins/validate/messages_zh.min.js"></script>
    <script src="${basePath}/static/common/js/jquery.form.js"></script>
    <script src="${basePath}/static/common/js/pwd.js"></script>

</body>

</html>

