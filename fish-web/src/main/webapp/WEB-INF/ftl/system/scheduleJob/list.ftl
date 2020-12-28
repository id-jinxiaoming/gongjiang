<!DOCTYPE html>
<html>

<head>

	
	<#include "/common/header.ftl">

	
  
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="ibox float-e-margins">
	    
	    <div class="ibox-content" style="border-color: transparent">


	        <div class="hr-line-dashed"></div>
	
	        <div class="example-wrap">
	            <div class="example">
				<@shiro.hasPermission name="system:scheduleJob:add">
                    <button type="button" class="btn btn-outline btn-primary" onclick="add()">创建</button>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="system:scheduleJob:delete">
                    <button type="button" class="btn btn-outline btn-primary" onclick="DelAll()">删除</button>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="system:scheduleJob:stop">
                    <button type="button" class="btn btn-outline btn-primary" onclick="stop()">暂停</button>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="system:scheduleJob:resume">
                    <button type="button" class="btn btn-outline btn-primary" onclick="resume()">恢复</button>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="system:scheduleJob:startNow">
                    <button type="button" class="btn btn-outline btn-primary" onclick="startNow()">执行一次</button>
				</@shiro.hasPermission>




		            <table class="table table-bordered table-hover">
		                <thead>
                        		<th style="text-align:center;">
									<input type="checkbox"  value="" id="all_check" class="all-checks"   >
                                </th>
		                        <th style="text-align:center;">任务名</th>
                                <th style="text-align:center;">任名组</th>
		                        <th style="text-align:center;">表达式</th>
		                        <th style="text-align:center;">状态</th>
                                <th style="text-align:center;">类名</th>
                                <th style="text-align:center;">操作</th>
		                    </tr>
		                </thead>
		             	<script id="datalist" type="text/html">
		                {{# for(var i=0;i<d.length;i++){  }}
							<tr class="long-td">
                                <td>
                                    <input type="checkbox" class="i-checks" value="{{d[i].name}}" group="{{d[i].group}}">
                                </td>
		                        <td>{{d[i].name}}</td>
		                        <td>{{d[i].group}}</td>
							  	<td>{{d[i].cronExpression}}</td>
                                <td>{{d[i].status}}</td>
								<td>{{d[i].className}}</td>
                                <td>
								<@shiro.hasPermission name="system:scheduleJob:edit">
                                    <button type="button" class="btn btn-primary  btn-sm" onclick="edit('{{d[i].name}}','{{d[i].group}}')">编辑</button>
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
  	
	<script type="text/javascript">
	var key="${key?default('')}";
	$(function(){

        initCheck();
        //运行
        Ajaxpage();

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
		$.ajax({
			url: "${basePath}/system/scheduleJob/list",
			type: "POST",
			dataType:'json',
			success:function(data){

				$(".spiner-example").css('display','none'); //数据加载完关闭动画
				if(data==''||data.records==null||data.records.length==0){
				    $("#data_list").html('<td colspan="20" style="padding-top:10px;padding-bottom:10px;font-size:16px;text-align:center">暂无数据</td>');
				}
				else
				{

					dataList(data.records);

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
	    window.location.href="${basePath}/system/scheduleJob/add";

	}
    function edit(name,group){
        window.location.href="${basePath}/system/scheduleJob/edit/"+name+"/"+group;

    }
	//删除所有记录
	function DelAll(){

        var nameArray=new Array();
        var groudArray=new Array();
        $(".i-checks").each(function(){
            if(true == $(this).is(':checked')){
				if($(this).val()!="")
				{
                    nameArray.push($(this).val());
                    groudArray.push($(this).attr('group'));
				}
            }

        });

        if(nameArray.length<=0)
		{
            layer.msg('没有选中任何记录',{icon:0,time:1500,shade: 0.1});
            return;

		}

        layer.confirm('确认删除吗?', {icon: 3, title:'提示'}, function(index){

            $.ajax({
                url: "${basePath}/system/scheduleJob/delete",
                data:"name="+nameArray+"&group="+groudArray,
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


    //暂停所有记录
    function stop(){

        var nameArray=new Array();
        var groudArray=new Array();
        $(".i-checks").each(function(){
            if(true == $(this).is(':checked')){
                if($(this).val()!="")
                {
                    nameArray.push($(this).val());
                    groudArray.push($(this).attr('group'));
                }
            }

        });

        if(nameArray.length<=0)
        {
            layer.msg('没有选中任何记录',{icon:0,time:1500,shade: 0.1});
            return;

        }

        layer.confirm('确认暂停吗?', {icon: 3, title:'提示'}, function(index){

            $.ajax({
                url: "${basePath}/system/scheduleJob/stop",
                data:"name="+nameArray+"&group="+groudArray,
                type: "POST",
                dataType:'json',
                success:function(res){
                    if(res.status == 200){
                        layer.msg('操作成功',{icon:1,time:1500,shade: 0.1});
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

    //恢复
    function resume(){

        var nameArray=new Array();
        var groudArray=new Array();
        $(".i-checks").each(function(){
            if(true == $(this).is(':checked')){
                if($(this).val()!="")
                {
                    nameArray.push($(this).val());
                    groudArray.push($(this).attr('group'));
                }
            }

        });

        if(nameArray.length<=0)
        {
            layer.msg('没有选中任何记录',{icon:0,time:1500,shade: 0.1});
            return;

        }

        layer.confirm('确认恢复吗?', {icon: 3, title:'提示'}, function(index){

            $.ajax({
                url: "${basePath}/system/scheduleJob/resume",
                data:"name="+nameArray+"&group="+groudArray,
                type: "POST",
                dataType:'json',
                success:function(res){
                    if(res.status == 200){
                        layer.msg('操作成功',{icon:1,time:1500,shade: 0.1});
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

    //执行一次
    function startNow(){

        var nameArray=new Array();
        var groudArray=new Array();
        $(".i-checks").each(function(){
            if(true == $(this).is(':checked')){
                if($(this).val()!="")
                {
                    nameArray.push($(this).val());
                    groudArray.push($(this).attr('group'));
                }
            }

        });

        if(nameArray.length<=0)
        {
            layer.msg('没有选中任何记录',{icon:0,time:1500,shade: 0.1});
            return;

        }

        layer.confirm('确认执行吗?', {icon: 3, title:'提示'}, function(index){

            $.ajax({
                url: "${basePath}/system/scheduleJob/startNow",
                data:"name="+nameArray+"&group="+groudArray,
                type: "POST",
                dataType:'json',
                success:function(res){
                    if(res.status == 200){
                        layer.msg('操作成功',{icon:1,time:1500,shade: 0.1});
                        $('#all_check').iCheck('uncheck');
                        window.location="${basePath}/system/scheduleJob/list";


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