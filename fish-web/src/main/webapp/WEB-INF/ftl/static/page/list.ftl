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
                    <li class="active"><a href="${basePath}/static/page/list">静态页面列表</a></li>
                </ul>
            </div>
        </div>
        <div class="ibox-content" style="border-color: transparent">
            <div class="hr-line-dashed"></div>

            <div class="example-wrap">
                <div class="example">
                    <table class="table table-bordered table-hover">
                        <thead>
                        <th style="text-align:center;">
                            <input type="checkbox"  value="" id="all_check" class="all-checks"   >
                        </th>
                        <th style="text-align:center;">ID</th>
                        <th style="text-align:center;">信用评价页面</th>
                        <th style="text-align:center;">咨询服务页面</th>
                        <th style="text-align:center;">关于我们</th>
                        <th style="text-align:center;">客服二维码</th>
                        <th style="text-align:center;">工友群二维码</th>
                        <th style="text-align:center;">操作</th>
                        </tr>
                        </thead>
                        <script id="datalist" type="text/html">
                            {{# for(var i=0;i<d.length;i++){  }}
                            <tr class="long-td">
                                <td>
                                    <input type="checkbox" class="i-checks" value="{{d[i].id}}">
                                </td>
                                <td>{{d[i].id}}</td>
                                <td>
                                    <a href="javascript:;"  onclick="openImg('{{d[i].creditEvaluation}}')">
                                        <img src="{{d[i].creditEvaluation}}" style="width:80px;height:80px" onerror="this.src='${basePath}/static/webupload/empty_mid.png'"/>
                                    </a>
                                </td>
                                <td>
                                    <a href="javascript:;"  onclick="openImg('{{d[i].consultingService}}')">
                                        <img src="{{d[i].consultingService}}" style="width:80px;height:80px ;background-color: rgb(0,0,0,0.4)" onerror="this.src='${basePath}/static/webupload/empty_mid.png'"/>
                                    </a>
                                </td>
                                <td>
                                    <a href="javascript:;"  onclick="openImg('{{d[i].aboutUs}}')">
                                        <img src="{{d[i].aboutUs}}" style="width:80px;height:80px" onerror="this.src='${basePath}/static/webupload/empty_mid.png'"/>
                                    </a>
                                </td>
                                <td>
                                    <a href="javascript:;"  onclick="openImg('{{d[i].customerService}}')">
                                        <img src="{{d[i].customerService}}" style="width:80px;height:80px" onerror="this.src='${basePath}/static/webupload/empty_mid.png'"/>
                                    </a>
                                </td>
                                <td>
                                    <a href="javascript:;"  onclick="openImg('{{d[i].workmates}}')">
                                        <img src="{{d[i].workmates}}" style="width:80px;height:80px" onerror="this.src='${basePath}/static/webupload/empty_mid.png'"/>
                                    </a>
                                </td>
                                <td>
								<@shiro.hasPermission name="static:page:edit">
                                    <a href="${basePath}/static/page/edit/{{d[i].id}}"  class="btn btn-primary  btn-xs">
                                        <i class="fa fa-paste"></i> 编辑</a>
								</@shiro.hasPermission>
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
            url: "${basePath}/static/page/list",
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
        window.location.href="${basePath}/static/page/edit/"+id;

    }

    function openImg(img){
        layer.open({
            type: 1,
            title: false,
            closeBtn: 0,
            skin: 'layui-layer-nobg', //没有背景色
            shadeClose: true,
            content:'<img width="100%" src="'+img+'" />'
        });
    }

</script>

</body>
</html>