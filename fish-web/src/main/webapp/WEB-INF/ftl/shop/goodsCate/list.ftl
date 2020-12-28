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
                    <li class="active"><a href="${basePath}/shop/goodsCate/list">商品分类</a></li>
                </ul>
            </div>
        </div>
	    <div class="ibox-content" style="border-color: transparent">

	
	        <div class="example-wrap">
	            <div class="example">
				<@shiro.hasPermission name="shop:goodsCate:add">
                    <a href="${basePath}/shop/goodsCate/add/0"  class="btn btn-primary btn-outline">
                       创建分类</a>
				</@shiro.hasPermission>

		            <table id="treeTable1" class="table table-bordered table-hover">
		                <thead>
                        		<th style="text-align:center;">名称</th>
                                <th style="text-align:center;">图片</th>
                                <th style="text-align:center;">排序</th>
                                <th style="text-align:center;">操作</th>
		                    </tr>
		                </thead>
		             	<script id="datalist" type="text/html">
		                {{# for(var i=0;i<d.length;i++){  }}
                        {{# if(d[i].pid=="0"){ }}
							<tr class="long-td" id='{{d[i].id}}'>



                                <td style="text-align:left;"><span controller='true'>{{d[i].text}}</span></td>
                                <td><img src="{{d[i].attributes.url}}"  width="100px" height="100px"/></td>
                                <td>{{d[i].attributes.seq}}</td>
                                <td>
								<@shiro.hasPermission name="shop:goodsCate:add">
                                    <a href="${basePath}/shop/goodsCate/add/{{d[i].id}}"  class="btn btn-primary btn-xs">
                                        <i class="fa fa-paste"></i>创建子分类</a>
								</@shiro.hasPermission>
								<@shiro.hasPermission name="shop:goodsCate:edit">
                                    <a href="${basePath}/shop/goodsCate/edit/{{d[i].id}}"  class="btn btn-primary btn-xs">
                                        <i class="fa fa-paste"></i> 编辑</a>
								</@shiro.hasPermission>
								<@shiro.hasPermission name="shop:goodsCate:delete">
                                    <button type="button" class="btn btn-primary btn-xs" onclick="DelAll({{d[i].id}})">删除</button>
								</@shiro.hasPermission>
								<@shiro.hasPermission name="shop:goodsCateAttr:list">
                                    <a href="${basePath}/shop/goodsCateAttr/list/{{d[i].id}}"  class="btn btn-primary btn-xs">
                                        <i class="fa fa-paste"></i>分类属性</a>
								</@shiro.hasPermission>

								</td>
		                    </tr>
                        {{# }else{ }}
							<tr class="long-td" id='{{d[i].id}}' pid='{{d[i].pid}}'>

								<td style="text-align:left;">{{d[i].text}}</td>
                                <td><img src="{{d[i].attributes.url}}"  width="100px" height="100px"/></td>
								<td>{{d[i].attributes.seq}}</td>
								<td>
								<@shiro.hasPermission name="shop:goodsCate:add">
                                    <a href="${basePath}/shop/goodsCate/add/{{d[i].id}}"  class="btn btn-primary btn-xs">
                                        <i class="fa fa-paste"></i>创建子分类</a>
								</@shiro.hasPermission>
								<@shiro.hasPermission name="shop:goodsCate:edit">
									<a href="${basePath}/shop/goodsCate/edit/{{d[i].id}}"  class="btn btn-primary btn-xs">
										<i class="fa fa-paste"></i> 编辑</a>
								</@shiro.hasPermission>
								<@shiro.hasPermission name="shop:goodsCate:delete">
                                    <button type="button" class="btn btn-primary btn-xs" onclick="DelAll({{d[i].id}})">删除</button>
								</@shiro.hasPermission>
								<@shiro.hasPermission name="shop:goodsCateAttr:list">
                                    <a href="${basePath}/shop/goodsCateAttr/list/{{d[i].id}}"  class="btn btn-primary btn-xs">
                                        <i class="fa fa-paste"></i>分类属性</a>
								</@shiro.hasPermission>

								</td>
							</tr>

                        {{# } }}
		                {{# } }}
		            	</script>
		                <tbody id="data_list"></tbody>		                
		            </table>


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
  	<script src="${basePath}/static/laytpl/laytpl.js"></script>
	<script src="${basePath}/static/treeTable/jquery.treeTable.js"></script>
	<script type="text/javascript">

	$(function(){

        //运行

        Ajaxpage();

    })

	//以下将以jquery.ajax为例，演示一个异步分页
	function Ajaxpage(){
        $(".spiner-example").css('display',''); //数据加载动画
		$.ajax({
			url: "${basePath}/shop/goodsCate/list",
			type: "POST",
			dataType:'json',
			success:function(data){
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
            var option1 = {
                theme: 'vsStyle',
                expandLevel: 2,
            };
            $('#treeTable1').treeTable(option1);//根据表格id来初始化
	    });
	}

	//删除所有记录
	function DelAll(id){

        layer.confirm('确认删除吗?', {icon: 3, title:'提示'}, function(index){

            $.ajax({
                url: "${basePath}/shop/goodsCate/delete",
                data:"id="+id,
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




	</script>
	
</body>
</html>