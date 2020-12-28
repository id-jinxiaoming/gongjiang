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
                    <li class="active"><a href="${basePath}/shop/goodsReview/list/${goodsId}">商品评价</a></li>
                </ul>
            </div>
        </div>
        <div class="ibox-content" style="border-color: transparent">


            <div class="example-wrap">
                <div class="example">
                    <div class="example">

					<@shiro.hasPermission name="shop:goodsReview:delete">
                        <button type="button" class="btn btn-primary btn-outline" onclick="DelAll()">批量删除</button>
					</@shiro.hasPermission>
                        <a href="${basePath}/shop/goods/list" style="float: right;"  class="btn btn-white">返回</a>
                    <table class="table table-bordered table-hover">
                        <thead>
                        <th style="text-align:center;">
                            <input type="checkbox"  value="" id="all_check" class="all-checks"   >
                        </th>
                        <th style="text-align:center;">ID</th>
                        <th style="text-align:center;">评价内容</th>
                        <th style="text-align:center;">评分</th>
                        <th style="text-align:center;">商品</th>
                        <th style="text-align:center;">用户</th>
                        <th style="text-align:center;">审核状态</th>
                        <th style="text-align:center;">回复</th>
                        <th style="text-align:center;">发表日期</th>
                        <th style="text-align:center;">操作</th>
                        </tr>
                        </thead>
                        <script id="datalist" type="text/html">
                            {{# for(var i=0;i<d.length;i++){  }}
                            <tr class="long-td">
                                <td>
                                    <input type="checkbox" class="i-checks" value="{{d[i].reviewId}}">
                                </td>
                                <td>{{d[i].reviewId}}</td>
                                <td>{{d[i].content}}</td>
                                <td>{{d[i].rating}}分
                                    {{# if(goodsReviewType){ }}
                                    {{# for(var j=0;j<goodsReviewType.length;j++){
                                        if(goodsReviewType[j].value==d[i].rating){}}
                                    【{{ goodsReviewType[j].name}}】
                                    {{# } }}
                                    {{# } }}
                                    {{# } }}
                                </td>
                                <td>{{d[i].goodsName}}</td>
                                <td>{{d[i].userName}}</td>

                                <td>
                                    {{# if(d[i].status==1){ }}
                                    审核通过
                                    {{# }else{ }}
                                    审核未通过
                                    {{# } }}

                                </td>
                                <td>
                                    {{# if(d[i].replied!=''){ }}
                                    {{d[i].replied}}
                                    {{# }else{ }}
                                    未回复
                                    {{# } }}

                                </td>
                                <td>{{getLocalTime(d[i].createdDate)}}</td>
                                <td>
                                <@shiro.hasPermission name="shop:goodsReview:audit">
                                    <button type="button" class="btn btn-primary btn-xs" onclick="audit('{{d[i].reviewId}}')">审核</button>
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
</div>
<!-- 等待加载动画 -->
<div class="spiner-example">
    <div class="sk-spinner sk-spinner-three-bounce">
        <div class="sk-bounce1"></div>
        <div class="sk-bounce2"></div>
        <div class="sk-bounce3"></div>
    </div>
</div>

    <!-- 审核 -->
    <div  id="auditWin" style="display: none;">
        <div class="ibox-content">
            <input type="hidden" id="reviewId">
            <div class="form-group">
                <label class="col-sm-3 control-label">回复内容：</label>
                <div class="col-sm-9">
                    <textarea id="replied" name="replied" class="form-control" ></textarea>

                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">状态：</label>
                <div class="col-sm-9">
                    <div class="radio i-checks">
                        <label>
                            <input type="radio" checked=checked value="1" name="status" id="status"> <i></i>通过</label>
                        <label>
                            <input type="radio"  value="0" name="status" id="status"> <i></i>不通过</label>
                    </div>

                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-4 col-sm-offset-4" style="margin-bottom: 15px">
                    <input type="button" value="确认" class="btn btn-primary" id="postform"/>
                </div>
            </div>
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
    var goodsReviewType = new Array();
    <#list goodsReviewType as item>
    var temp=[];
    temp.value='${item.value}';
    temp.name='${item.name}';
    goodsReviewType.push(temp);
    </#list>

</script>

<script type="text/javascript">
    var goodsId="${goodsId}";


    var curCount=1;

    $(function(){

        initCheck();
        //运行

        $('#myForm').on('submit', function() {

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
            url: "${basePath}/shop/goodsReview/list/"+goodsId,
            data:{page:curr||1},
            type: "POST",
            dataType:'json',
            success:function(data){

                //goodsReviewType=data.goodsReviewType;
                var data=data.data;

                $(".spiner-example").css('display','none'); //数据加载完关闭动画
                if(data==''||data==null||data.length==0){
                    $("#data_list").html('<td colspan="20" style="padding-top:10px;padding-bottom:10px;font-size:16px;text-align:center">暂无数据</td>');
                }
                else
                {

                    dataList(data);

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
                url: "${basePath}/shop/goodsReview/delete",
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



    var index = '';
    //审核
    function audit(id){
        $("#reviewId").val(id);
        //页面层
        index = layer.open({
            type: 1,
            area: ['40%', '60%'],
            title:'审核',
            shadeClose: true,
            content:$("#auditWin")

        });

    }

    //确认
    $("#postform").click(function(){


        var id = $("#reviewId").val();
        var replied = $("#replied").val();
        var status =  $("input[name='status']:checked").val();

        var data={ 'reviewId' : id, 'replied' : replied,'status':status};
        //写入库
        $.ajax({
            url: "${basePath}/shop/goodsReview/audit",
            data:data,
            type: "POST",
            dataType:'json',
            success:function(res){
                layer.close(index);
                if(res.status == 200){
                    layer.msg('操作成功', {time: 2000, icon:6});

                    Ajaxpage();
                }else{
                    layer.msg(res.message, {time: 2000, icon:5});

                }

            },
            error:function(er){

            }
        });
    })






</script>

</body>
</html>