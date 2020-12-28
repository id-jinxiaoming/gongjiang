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
				<@shiro.hasPermission name="activiti:deployment:add">
                    <button type="button" class="btn btn-outline btn-primary" onclick="add()">创建</button>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="activiti:deployment:delete">
                    <button type="button" class="btn btn-outline btn-primary" onclick="DelAll()">批量删除</button>
				</@shiro.hasPermission>
		            <table class="table table-bordered table-hover">
		                <thead>
                        		<th style="text-align:center;">
									<input type="checkbox"  value="" id="all_check" class="all-checks"   >
                                </th>
		                        <th style="text-align:center;">ID</th>
                                <th style="text-align:center;">名称</th>
		                        <th style="text-align:center;">部署时间</th>
		                        <th style="text-align:center;">category</th>
                                <th style="text-align:center;">tenantId</th>

		                    </tr>
		                </thead>
		             	<script id="datalist" type="text/html">
		                {{# for(var i=0;i<d.length;i++){  }}
							<tr class="long-td">
                                <td>
                                    <input type="checkbox" class="i-checks" value="{{d[i].id}}">
                                </td>
		                        <td>{{d[i].id}}</td>
		                        <td>{{d[i].name}}</td>
							  	<td>{{d[i].deploymentTime}}</td>
								<td>{{d[i].category}}</td>
                                <td>{{d[i].tenantId}}</td>

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
			url: "${basePath}/activiti/deployment/list",
			data:{page:curr||1},
			type: "POST",
			dataType:'json',
			success:function(data){
				var data=data.data;

                //alert(JSON.stringify(data));
				$(".spiner-example").css('display','none'); //数据加载完关闭动画
				if(data==''||data.result==null||data.result.length==0){
				    $("#data_list").html('<td colspan="20" style="padding-top:10px;padding-bottom:10px;font-size:16px;text-align:center">暂无数据</td>');
				}
				else
				{

                    var pages = Math.ceil(data.totalCount/data.pageSize); //得到总页数

					dataList(data.result);
				    //显示分页
				    laypage({
				      cont: $('#AjaxPage'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
				      pages: pages, //通过后台拿到的总页数
				      skin: '#1AB5B7',//分页组件颜色
			          groups: 3,//连续显示分页数
				      curr: curr || 1, //当前页
				      jump: function(obj, first){ //触发分页后的回调
				        if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
				        	Ajaxpage(obj.curr);
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
	    window.location.href="${basePath}/activiti/deployment/add";

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
                url: "${basePath}/activiti/deployment/delete",
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

	</script>
	
</body>
</html>