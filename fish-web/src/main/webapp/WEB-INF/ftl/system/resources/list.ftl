<!DOCTYPE html>
<html>

<head>

	
	<#include "/common/header.ftl">



</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="ibox float-e-margins">
	    
	    <div class="ibox-content" style="border-color: transparent">

	        <div class="example-wrap">
	            <div class="example">
                <@shiro.hasPermission name="system:resources:add">
                    <a href="${basePath}/system/resources/add/0"  class="btn btn-outline btn-primary">
                        创建权限</a>
                </@shiro.hasPermission>

		            <table id="treeTable1"  class="table table-bordered table-hover" >

                        <thead>
                        	<tr>
		                        <th  style="text-align:center;" style="width:200px;">名称</th>
                                <th style="text-align:center;">URL</th>
		                        <th style="text-align:center;">权限</th>
		                        <th style="text-align:center;">ICON</th>
                                <th style="text-align:center;">类型</th>
                                <th style="text-align:center;">排序</th>
                                <th style="text-align:center;">操作</th>
		                    </tr>
                        </thead>
		             	<script id="datalist" type="text/html">
		                {{# for(var i=0;i<d.length;i++){  }}
                        {{# if(d[i].url=="/"){ }}

                        <tr class="long-td"  id='{{d[i].id}}'>

                            <td style="text-align:left;"><span controller='true'>{{d[i].name}}</span></td>
                            <td>{{d[i].url}}</td>
                            <td>{{d[i].permission}}</td>
                            <td><i class="fa {{d[i].icon}}"></i></td>
                            <td>
                                {{# if(d[i].type==1){ }}
                                菜单

                                {{# }else if(d[i].type==2){ }}
                                功能
                                {{# } }}

                            </td>
                            <td>{{d[i].sort}}</td>
                            <td>
                            <@shiro.hasPermission name="system:resources:add">
                                <a href="${basePath}/system/resources/add/{{d[i].id}}"  class="btn btn-primary btn-outline btn-xs">
                                    <i class="fa fa-paste"></i> 添加子权限</a>
                            </@shiro.hasPermission>
							<@shiro.hasPermission name="system:resources:edit">
                                <a href="${basePath}/system/resources/edit/{{d[i].id}}"  class="btn btn-primary btn-outline btn-xs">
                                    <i class="fa fa-paste"></i> 编辑</a>
							</@shiro.hasPermission>
                            <@shiro.hasPermission name="system:resources:delete">
                                <a href="javascript:;"  class="btn btn-danger btn-outline btn-xs" onclick="del('{{d[i].id}}')">
                                    <i class="fa fa-trash-o"></i> 删除</a>
                            </@shiro.hasPermission>
                            </td>
                        </tr>


                        {{# }else{ }}
                        <tr class="long-td"  id='{{d[i].id}}' pid='{{d[i].pid}}'>

                            <td style="text-align:left;">{{d[i].name}}</td>
                            <td>{{d[i].url}}</td>
                            <td>{{d[i].permission}}</td>
                            <td> <i class="fa {{d[i].icon}}"></i></td>
                            <td>
                                {{# if(d[i].type==1){ }}
                                    菜单

                                {{# }else if(d[i].type==2){ }}
									功能
                                {{# } }}
								</td>
                            <td>{{d[i].sort}}</td>
                            <td>
                            <@shiro.hasPermission name="system:resources:add">
                                <a href="${basePath}/system/resources/add/{{d[i].id}}"  class="btn btn-primary btn-outline btn-xs">
                                    <i class="fa fa-paste"></i> 添加子权限</a>
                            </@shiro.hasPermission>
                            <@shiro.hasPermission name="system:resources:edit">
                                <a href="${basePath}/system/resources/edit/{{d[i].id}}"  class="btn btn-primary btn-outline btn-xs">
                                    <i class="fa fa-paste"></i> 编辑</a>
                            </@shiro.hasPermission>
                            <@shiro.hasPermission name="system:resources:delete">
                                <a href="javascript:;"  class="btn btn-danger btn-outline btn-xs" onclick="del('{{d[i].id}}')">
                                    <i class="fa fa-trash-o"></i> 删除</a>
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
  	<script src="${basePath}/static/laypage-v1.3/laypage/laypage.js"></script>
  	<script src="${basePath}/static/laytpl/laytpl.js"></script>
	<script src="${basePath}/static/treeTable/jquery.treeTable.js"></script>
  	
	<script type="text/javascript">

	var key="${key?default('')}";
	$(function(){


        //运行
        Ajaxpage();

    })

	//以下将以jquery.ajax为例，演示一个异步分页
	function Ajaxpage(){
		$.ajax({
			url: "${basePath}/system/resources/list",
			data:{key:key},
			type: "POST",
			dataType:'json',
			success:function(data){

				$(".spiner-example").css('display','none'); //数据加载完关闭动画


				if(data==''||data.data==null||data.data.length==0){
				    $("#data_list").html('<td colspan="20" style="padding-top:10px;padding-bottom:10px;font-size:16px;text-align:center">暂无数据</td>');
				}
				else
				{

					dataList(data.data);

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

	//删除
	function del(id){

        layer.confirm('确认删除吗?', {icon: 3, title:'提示'}, function(index){

            $.ajax({
                url: "${basePath}/system/resources/delete",
                data:"id="+id,
                type: "POST",
                dataType:'json',
                success:function(res){
                    if(res.status == 200){
                        layer.msg('删除成功',{icon:1,time:1500,shade: 0.1});
                        window.location.href="${basePath}/system/resources/list";

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