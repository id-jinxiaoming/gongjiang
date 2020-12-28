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
                    <li class="active"><a href="${basePath}/shop/goods/list">商品列表</a></li>
                </ul>
            </div>
        </div>
        <div class="ibox-content" style="border-color: transparent">
            <!--搜索框开始-->
            <div class="row">
                <div class="col-sm-12">

                    <form id="myForm" name="admin_list_sea" class="form-search" method="post" action="${basePath}/shop/goods/list">
                        <div class="col-sm-3" style="padding-right: 0px;padding-left: 0px;">
                            <div class="input-group">
                                <input type="text" id="key" class="form-control" name="key"  placeholder="输入需查询的商品名" />
                                <span class="input-group-btn">
	                                <button type="submit" class="btn btn-primary"><i class="fa fa-search"></i> 搜索</button>
	                            </span>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <!--搜索框结束-->
            <div class="hr-line-dashed"></div>

            <div class="example-wrap">
                <div class="example">
				<@shiro.hasPermission name="shop:goods:add">
                    <button type="button" class="btn btn-outline btn-primary" onclick="add()">创建</button>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="shop:goods:delete">
                    <button type="button" class="btn btn-outline btn-primary" onclick="DelAll()">批量删除</button>
				</@shiro.hasPermission>
                    <table class="table table-bordered table-hover">
                        <thead>
                        <th style="text-align:center;">
                            <input type="checkbox"  value="" id="all_check" class="all-checks"   >
                        </th>
                        <th style="text-align:center;">ID</th>
                        <th style="text-align:center;">商品图片</th>
                        <th style="text-align:center;">商品名称</th>
                        <th style="text-align:center;">货号</th>
                        <th style="text-align:center;">价格 (元)</th>
                        <th style="text-align:center;">可用积分</th>
                        <th style="text-align:center;">库存</th>
                        <th style="text-align:center;">状态</th>
                        <th style="text-align:center;">创建时间</th>
                        <th style="text-align:center;">操作</th>

                        </tr>
                        </thead>
                        <script id="datalist" type="text/html">
                            {{# for(var i=0;i<d.length;i++){  }}
                            <tr class="long-td">
                                <td>
                                    <input type="checkbox" class="i-checks" value="{{d[i].goodsId}}">
                                </td>
                                <td>{{d[i].goodsId}}</td>
                                <td>
                                    <a href="javascript:;"  onclick="openImg('{{d[i].goodsImage}}')">
                                        <img src="{{d[i].goodsImage}}" style="width:80px;height:80px" onerror="this.src='${basePath}/static/webupload/empty_mid.png'"/>
                                    </a>
                                </td>
                                <td style="text-align: left;">{{d[i].goodsName}}
                                    {{# if(d[i].newarrival==1){ }}
                                    <span class="badge badge-success">新品</span>
                                    {{# } }}
                                    {{# if(d[i].newarrival==1){ }}
                                    <span class="badge badge-warning">推荐</span>
                                    {{# } }}
                                    {{# if(d[i].bargain==1){ }}
                                    <span class="badge badge-danger">特价</span>
                                    {{# } }}
                                </td>
                                <td>{{d[i].goodsSn}}</td>
                                <td>{{d[i].nowPrice}}</td>
                                <td>{{d[i].integral}}</td>
                                <td>{{d[i].stockQty}}</td>
                                <td>
                                    {{# if(d[i].status==1){ }}
                                    <span class="badge badge-success">上架</span>

                                    {{# }else{ }}
                                    <span class="badge badge-danger">下架</span>
                                    {{# } }}

                                </td>
                                <td>{{getLocalTime(d[i].createdDate)}}</td>
                                <td>
								<@shiro.hasPermission name="shop:goods:edit">
                                    <a href="${basePath}/shop/goods/edit/{{d[i].goodsId}}"  class="btn btn-primary  btn-xs">
                                        <i class="fa fa-paste"></i> 编辑</a>
								</@shiro.hasPermission>
                                <@shiro.hasPermission name="shop:goodsOptional:list">
                                    <a href="${basePath}/shop/goodsOptional/list/{{d[i].goodsId}}"  class="btn btn-primary  btn-xs">
                                        <i class="fa fa-paste"></i> 商品选项</a>
                                </@shiro.hasPermission>
                                <@shiro.hasPermission name="shop:goodsSpec:list">
                                    <a href="${basePath}/shop/goodsSpec/list/{{d[i].goodsId}}"  class="btn btn-primary  btn-xs">
                                        <i class="fa fa-paste"></i> 商品规格</a>
                                </@shiro.hasPermission>
                                <@shiro.hasPermission name="shop:goodsAttr:set">
                                    <a href="${basePath}/shop/goodsAttr/set/{{d[i].goodsId}}"  class="btn btn-primary  btn-xs">
                                        <i class="fa fa-paste"></i> 商品属性</a>
                                </@shiro.hasPermission>

                                <@shiro.hasPermission name="shop:goodsReview:list">
                                    <a href="${basePath}/shop/goodsReview/list/{{d[i].goodsId}}"  class="btn btn-primary  btn-xs">
                                        <i class="fa fa-paste"></i> 商品评价</a>
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
            url: "${basePath}/shop/goods/list",
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
    function add(){
        window.location.href="${basePath}/shop/goods/add";

    }
    function edit(id){
        window.location.href="${basePath}/shop/goods/edit/"+id;

    }
    //删除所有记录
    function DelAll(){
        var checkArray=new Array();
        $(".i-checks").each(function(){
            if(true == $(this).is(':checked')){
                if($(this).val()!="")
                {
                    checkArray.push($(this).val());
                }
            }

        });
        if(checkArray.length<=0)
        {
            layer.msg('没有选中任何记录',{icon:0,time:1500,shade: 0.1});
            return;

        }
        layer.confirm('确认删除吗?', {icon: 3, title:'提示'}, function(index){

            $.ajax({
                url: "${basePath}/shop/goods/delete",
                data:"id="+checkArray,
                type: "POST",
                dataType:'json',
                success:function(res){
                    if(res.status == 200){
                        layer.msg('删除成功',{icon:1,time:1500,shade: 0.1});
                        $('#all_check').iCheck('uncheck');
                        Ajaxpage();


                    }else{
                        layer.msg(res.message,{icon:0,time:1500,shade: 0.1});
                    }

                },
                error:function(er){

                }
            });
            layer.close(index);
        })


    }


    function openImg(img){
        layer.open({
            type: 1,
            title: false,
            closeBtn: 0,
            skin: 'layui-layer-nobg', //没有背景色
            shadeClose: true,
            content:'<img width="100%" height="100%" src="'+img+'" />'
        });
    }




</script>

</body>
</html>