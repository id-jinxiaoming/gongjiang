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
                    <h5>订单信息</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                    </div>
                </div>
                <div class="ibox-content">




                        <div  class="wrapper wrapper-content" id="printDiv">

                            <table class="table table-bordered table-hover">
                                <tr class="long-td">
                                    <td style="text-align: left;">订单号:</td>
                                    <td>${item.order_id}</td>
                                    <td style="text-align: left;">用户:</td>
                                    <td>${item.username}</td>
                                </tr>
                                <tr class="long-td">
                                    <td style="text-align: left;">订单总额:</td>
                                    <td>${item.order_amount?string ("currency")}</td>
                                    <td style="text-align: left;">下单日期:</td>
                                    <td>${item.created_date}</td>
                                </tr>
                                <tr class="long-td">
                                    <td style="text-align: left;">运费:</td>
                                    <td>${item.shipping_amount?string ("currency")}</td>
                                    <td style="text-align: left;">支付方式:</td>
                                    <td>${item.payment}</td>
                                </tr>
                                <tr class="long-td">
                                    <td style="text-align: left;">订单状态:</td>
                                    <td>${item.order_status_name}</td>
                                    <td style="text-align: left;">配送方式:</td>
                                    <td>${item.shipping}</td>
                                </tr>
                                <tr class="long-td">
                                    <td style="text-align: left;">付款时间:</td>
                                    <td>${item.payment_date}</td>
                                    <td style="text-align: left;">第三方流水号:</td>
                                    <td>${item.thirdparty_trade_id}</td>
                                </tr>
                                <tr class="long-td">
                                    <td style="text-align: left;">收件人信息:</td>
                                    <td>${item.receiver}&nbsp;${item.mobile}&nbsp;${item.province}&nbsp;${item.city}&nbsp;${item.area}&nbsp;${item.address}</td>
                                    <td style="text-align: left;">备注:</td>
                                    <td>${item.memor}</td>
                                </tr>


                            </table>

                        <div class="hr-line-dashed"></div>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>

                            <th style="text-align:center;">订单商品</th>
                            <th style="text-align:center;">单价</th>
                            <th style="text-align:center;">数量</th>
                            </tr>
                            </thead>
                        <#list goods as g>
                            <tr class="long-td">

                                    <td style="text-align: left;">
                                        <img src="${g.goodsImage}" width="60px" height="60px" />
                                    ${g.goodsName}
                                    </td>
                                    <td>${g.goodsPrice?string ("currency")}</td>
                                    <td>${g.goodsQty}</td>

                            </tr>
                        </#list>

                        </table>

                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-8">

                            <#if item.order_status==1>
                                <@shiro.hasPermission name="order:order:setAddress">
                                    <button class="btn btn-primary" onclick="openWin('修改地址','${basePath}/order/order/setAddress/${item.order_id}',450)" type="button">修改收件人信息</button>
                                </@shiro.hasPermission>
                                <@shiro.hasPermission name="order:order:setMoney">
                                    <button class="btn btn-primary" onclick="openWin('更改订单金额','${basePath}/order/order/setMoney/${item.order_id}',300)" type="button">更改订单金额</button>
                                </@shiro.hasPermission>
                                <@shiro.hasPermission name="order:order:setCancel">
                                    <button class="btn btn-primary" onclick="openWin('取消交易','${basePath}/order/order/setCancel/${item.order_id}',200)" type="button">取消交易</button>
                                </@shiro.hasPermission>
                            </#if>
                            <#if item.order_status==2>
                                <@shiro.hasPermission name="order:order:setShipping">
                                    <button class="btn btn-primary" onclick="openWin('发货配送','${basePath}/order/order/setShipping/${item.order_id}',400)" type="button">发货配送</button>
                                </@shiro.hasPermission>
                            </#if>
                            <#if item.order_status==0>
                                <@shiro.hasPermission name="order:order:setRecover">
                                    <button class="btn btn-primary" onclick="openWin('恢复交易','${basePath}/order/order/setRecover/${item.order_id}',200)" type="button">恢复交易</button>
                                </@shiro.hasPermission>
                            </#if>
                                <button class="btn btn-primary" onclick="printOrder()" type="button">打印订单</button>



                                <a  class="btn btn-danger" type="button"  href="${basePath}/order/order/list"><i class="fa fa-close"></i> 返回</a>
                            </div>
                        </div>

                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                            <th style="text-align:center;">物流承运商</th>
                            <th style="text-align:center;">运单号</th>
                            <th style="text-align:center;">发货日期</th>
                            <th style="text-align:center;">备注</th>
                            </tr>
                            </thead>

                        <#list shipping as s>
                            <tr class="long-td">

                                <td>
                                ${s.name}
                                </td>
                                <td> ${s.trackingNo}</td>
                                <td> ${s.memos}</td>
                                <td>${s.createTime?string('yyyy.MM.dd. HH:mm:ss')}</td>

                            </tr>
                        </#list>



                        </table>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th style="text-align:center;">操作记录</th>
                                <th style="text-align:center;">原因备注</th>

                            </tr>
                            </thead>
                        <#list logs as log>
                            <tr class="long-td">

                                <td style="text-align: left;">

                                    管理员:${log.account}在${log.createTime?string('yyyy.MM.dd. HH:mm:ss')}${log.operate}
                                </td>
                                <td>${log.cause}</td>
                            </tr>
                        </#list>

                        </table>

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

<script >

    $().ready(function() {

    });

    var indexWin = '';
    //openWin
    function openWin(title,url,height){

        //页面层
        indexWin = layer.open({
            type: 2,
            area: ['500px', height+'px'],
            title:title,
            content:url

        });

    }


    function update(){
        window.location.reload();

    }

    function printOrder() {
        var headStr = "<html><head><title>商城订单</title></head><body>";
        var footStr = "</body>";
        var bodyStr = document.all.item("printDiv").innerHTML;
        var oldStr = document.body.innerHTML;
        document.body.innerHTML = headStr+bodyStr+footStr;
        window.print();
        document.body.innerHTML=oldStr;

    }






</script>




</body>

</html>