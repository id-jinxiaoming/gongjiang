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
                    <li class="active"><a href="${basePath}/order/aftersales/list">售后列表</a></li>
                </ul>
            </div>
        </div>
	    <div class="ibox-content" style="border-color: transparent">
	        <!--搜索框开始-->           
	        <div class="row">
	            <div class="col-sm-12">  
	                                                   
	                <form id="myForm" name="admin_list_sea" class="form-search" method="post" action="${basePath}/order/aftersales/list">
	                    <div class="col-sm-3" style="padding-left: 0px;padding-right: 0px;">
	                        <div class="input-group">
	                            <input type="text" id="key" class="form-control" name="key"  placeholder="输入需查询的订单号" />
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

		            <table class="table table-bordered table-hover">
		                <thead>

		                        <th style="text-align:center;">ID</th>
                                <th style="text-align:center;">订单号</th>
                                <th style="text-align:center;">申请用户</th>
                                <th style="text-align:center;">商品图片</th>
                                <th style="text-align:center;">商品名称</th>
                                <th style="text-align:center;">商品价格</th>
                                <th style="text-align:center;">商品规格</th>
                                <th style="text-align:center;">购买数量</th>
                                <th style="text-align:center;">问题</th>
                                <th style="text-align:center;">提交时间</th>
                                <th style="text-align:center;">状态</th>
                                <th style="text-align:center;">操作</th>
		                    </tr>
		                </thead>
		             	<script id="datalist" type="text/html">
		                {{# for(var i=0;i<d.length;i++){  }}
							<tr class="long-td">
                                <td>{{d[i].asId}}</td>
                                <td><a href="${basePath}/order/order/show/{{d[i].orderId}}"> {{d[i].orderId}}</a></td>
                                <td>
									<img src="{{d[i].avatar}}" class="img-circle" style="width:30px;height:30px" onerror="this.src='${basePath}/static/webupload/head_default.gif'"/>
                                    {{d[i].username}}
								</td>
                                <td><img src="{{d[i].goodsImage}}" /></td>
                                <td>{{d[i].goodsName}}</td>
                                <td>{{d[i].goodsPrice}}</td>
                                <td>{{d[i].goodsSpec}}</td>
                                <td>{{d[i].goodsQty}}</td>
		                       	<td>{{d[i].cause}}</td>
                                <td>{{d[i].createTime}}</td>
                                <td>
                                    {{# if(d[i].status==1){ }}
                                    待处理
                                    {{# }else if(d[i].status==2){ }}
                                    正在处理中
                                    {{# }else if(d[i].status==3){ }}
                                    已解决
                                    {{# }else if(d[i].status==4){ }}
									未解决
                                    {{# } }}


								</td>


                                <td>
								<@shiro.hasPermission name="order:aftersales:check">

                                    <button type="button" class="btn btn-primary  btn-xs" onclick="check('{{d[i].asId}}')"><i class="fa fa-paste"></i>审核</button>
								</@shiro.hasPermission>
								<@shiro.hasPermission name="order:aftersales:logs">

                                    <button type="button" class="btn btn-primary  btn-xs" onclick="show('{{d[i].asId}}')"><i class="fa fa-paste"></i>处理记录</button>
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
<div dis>


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



        $('#myForm').on('submit', function() {
            key=$("#key").val();
            Ajaxpage(curCount);
            return false; // 阻止表单自动提交事件
        });
        Ajaxpage(curCount);

    })

	//以下将以jquery.ajax为例，演示一个异步分页
	function Ajaxpage(curr){
        $(".spiner-example").css('display',''); //数据加载动画
		$.ajax({
			url: "${basePath}/order/aftersales/list",
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


    var indexWin = '';
    //openWin
    function check(id){

        //页面层
        indexWin = layer.open({
            type: 2,
            area: ['500px', '350px'],
            title:"售后处理",
            content:"${basePath}/order/aftersales/check/"+id

        });

    }

    function show(id){
        window.location.href="${basePath}/order/aftersales/logs/"+id;

    }
	
	function dataList(list){

	    var tpl = document.getElementById('datalist').innerHTML;
	    laytpl(tpl).render(list, function(html){
	        document.getElementById('data_list').innerHTML = html;

	    });
	}







	</script>
	
</body>
</html>