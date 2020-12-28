<!DOCTYPE html>
<html>

<head>


<#include "/common/header.ftl">


</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <div class="builder-tabs builder-form-tabs">
                <ul class="nav nav-tabs">
                    <li class="active"><a href="${basePath}/config/payment/list">支付方式列表</a></li>
                </ul>
            </div>
        </div>
        <div class="ibox-content" style="border-color: transparent">


            <div class="example-wrap">
                <div class="example">

                    <table class="table table-bordered table-hover">
                        <thead>

                        <th style="text-align:center;">ID</th>
                        <th style="text-align:center;">名称</th>
                        <th style="text-align:center;">说明</th>
                        <th style="text-align:center;">排序</th>
                        <th style="text-align:center;">状态</th>
                        <th style="text-align:center;">操作</th>

                        </tr>
                        </thead>
                        <script id="datalist" type="text/html">
                            {{# for(var i=0;i<d.length;i++){  }}
                            <tr class="long-td">

                                <td>{{d[i].id}}</td>

                                <td style="text-align: center;">
                                    {{# if(d[i].pcode=="weixinpay"){ }}
                                    <img width="100px"  src="${basePath}/static/images/weixinpay.jpg">
                                    {{# }else if(d[i].pcode=="alipay"){ }}
                                    <img width="100px"  src="${basePath}/static/images/alipay.jpg">
                                    {{# } }}</td>
                                <td>{{d[i].instruction}}</td>
                                <td>{{d[i].seq}}</td>

                                <td>
                                    {{# if(d[i].enable==1){ }}
                                    <span class="badge badge-success">开启</span>

                                    {{# }else{ }}
                                    <span class="badge badge-danger">关闭</span>
                                    {{# } }}

                                </td>
                                <td>

                                    {{# if(d[i].pcode=="weixinpay"){ }}
                                    <@shiro.hasPermission name="config:payment:editWeixin">
                                        <a href="${basePath}/config/payment/editWeixin/{{d[i].id}}"  class="btn btn-primary  btn-xs">
                                            <i class="fa fa-paste"></i> 编辑</a>
                                    </@shiro.hasPermission>
                                    {{# }else if(d[i].pcode=="alipay"){ }}
                                    <@shiro.hasPermission name="config:payment:editAlipay">
                                        <a href="${basePath}/config/payment/editAlipay/{{d[i].id}}"  class="btn btn-primary  btn-xs">
                                            <i class="fa fa-paste"></i> 编辑</a>
                                    </@shiro.hasPermission>
                                    {{# } }}


                                </td>
                            </tr>
                            {{# } }}
                        </script>
                        <tbody id="data_list"></tbody>
                    </table>

                    <div id="AjaxPage" style=" text-align: right;"></div>
                    <div id="allpage" style=" text-align: right;"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 等待加载动画 -->
<div class="spiner-example">
    <div class="sk-spinner sk-spinner-three-bounce">
        <div class="sk-bounce1"></div>
        <div class="sk-bounce2"></div>
        <div class="sk-bounce3"></div>
    </div>
</div>


<script src="${basePath}/static/hplus/js/jquery.min.js?v=2.1.4"></script>
<script src="${basePath}/static/hplus/js/bootstrap.min.js?v=3.3.6"></script>
<script src="${basePath}/static/hplus/js/plugins/iCheck/icheck.min.js"></script>
<script src="${basePath}/static/hplus/js/content.js?v=1.0.0"></script>
<script src="${basePath}/static/layer/layer.js"></script>
<script src="${basePath}/static/laypage-v1.3/laypage/laypage.js"></script>
<script src="${basePath}/static/laytpl/laytpl.js"></script>
<script src="${basePath}/static/js/date.js"></script>
<script type="text/javascript">
    var key="";
    var curCount=1;
    $(function(){

        initCheck();
        //运行

        $('#myForm').on('submit', function() {
            key=$("#key").val();
            Ajaxpage(curCount);
            return false; // 阻止表单自动提交事件
        });
        Ajaxpage(curCount);

    })
    function initCheck(){
        $('.all-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });
        $('#all_check').on('ifChecked', function(event){

            $('.i-checks').iCheck('check');


        });
        $('#all_check').on('ifUnchecked', function(event){

            $('.i-checks').iCheck('uncheck');
        });

    }
    //以下将以jquery.ajax为例，演示一个异步分页
    function Ajaxpage(curr){
        $(".spiner-example").css('display',''); //数据加载动画
        $.ajax({
            url: "${basePath}/config/payment/list",
            data:{page:curr||1,key:key},
            type: "POST",
            dataType:'json',
            success:function(data){
                var data=data.data;
                $(".spiner-example").css('display','none'); //数据加载完关闭动画
                if(data==''||data.records==null||data.records.length==0){
                    $("#data_list").html('<td colspan="20" style="padding-top:10px;padding-bottom:10px;font-size:16px;text-align:center">暂无数据</td>');
                }
                else
                {
                    var pages = data.pages;
                    dataList(data.records);
                    //显示分页
                    laypage({
                        cont: $('#AjaxPage'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                        pages: pages, //通过后台拿到的总页数
                        skin: '#1AB5B7',//分页组件颜色
                        groups: 3,//连续显示分页数
                        curr: curr || 1, //当前页
                        jump: function(obj, first){ //触发分页后的回调
                            if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
                                curCount=obj.curr;
                                Ajaxpage(curCount);
                            }
                        }
                    });
                }

            },
            error:function(er){

            }
        });

    };



    function dataList(list){

        var tpl = document.getElementById('datalist').innerHTML;
        laytpl(tpl).render(list, function(html){
            document.getElementById('data_list').innerHTML = html;
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
        });
    }

    function edit(id){
        window.location.href="${basePath}/config/payment/editWeixin/"+id;

    }






</script>

</body>
</html>