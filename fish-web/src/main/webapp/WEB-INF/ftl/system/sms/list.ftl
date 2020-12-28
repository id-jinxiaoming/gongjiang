<!DOCTYPE html>
<html>

<head>


<#include "/common/header.ftl">


    <style type="text/css">
    .long-tr th{
        text-align: center
    }
    .long-td td{
        text-align: center
    }
    /* TAB */
	.nav-tabs.nav>li>a {
	    padding: 10px 25px;
	    margin-right: 0;
	}
	.nav-tabs.nav>li>a:hover,
	.nav-tabs.nav>li.active>a {
	    border-top: 3px solid #1ab394;
	    padding-top: 8px;
	}
	.nav-tabs>li>a {
	    color: #A7B1C2;
	    font-weight: 500;
	    margin-right: 2px;
	    line-height: 1.42857143;
	    border: 1px solid transparent;
	    border-radius: 0;
	}
    </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated">
	<div class="ibox float-e-margins">
		<#include "/system/setting/menu.ftl">
		<div class="ibox-content" style="border-color: transparent">      
        <div class="row">
            <div class="col-sm-12">  				
            <div  class="col-sm-2" style="width: 100px">
                <div class="input-group" >  
	                <@shiro.hasPermission name="system:sms:add">
	                    <a href="${basePath}/system/sms/add" class="btn btn-outline btn-primary" type="button">添加短信模板</a>
	                </@shiro.hasPermission>
                </div>
            </div>                                                                            
            </div>
        </div>
	       <div class="hr-line-dashed"></div>
	        <div class="example-wrap">
	            <div class="example">
		            <table class="table table-bordered table-hover">
		                <thead>
		                    <tr class="long-tr">
		                        <th>ID</th>
		                        <th>标签</th>
                                <th>ID</th>
		                        <th style="width:30%">说明</th>
		                        <th style="width:40%">模板</th>		                       
		                        <th>状态</th>
		                        <th>操作</th>
		                    </tr>
		                </thead>
		             	<script id="datalist" type="text/html">
		                {{# for(var i=0;i<d.length;i++){  }}
							<tr class="long-td">
		                        <td>{{d[i].smsId}}</td>
		                        <td>{{d[i].smsKey}}</td>
                                <td>{{d[i].smsTemplateId}}</td>
		                        <td>{{d[i].smsExplain}}</td>
								<td>{{d[i].smsTmpl}}</td>	
								<td>
									 {{# if(d[i].isOpen==1){ }}
										开启
									 {{# }else{ }}
										关闭
									 {{# } }}
								</td>
		                        <td>
		                          	<@shiro.hasPermission name="system:sms:edit">
		                          	 <a href="${basePath}/system/sms/edit/{{d[i].smsId}}"  class="btn btn-primary btn-xs">
		                            <i class="fa fa-paste"></i> 编辑</a>
		                            </@shiro.hasPermission>
		                            &nbsp;&nbsp;
		                            <@shiro.hasPermission name="system:sms:delete">
					         		 <a href="javascript:;"  class="btn btn-danger btn-xs" onclick="Del('{{d[i].smsId}}')">
		                            <i class="fa fa-trash-o"></i> 删除</a>
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
  	<script src="${basePath}/static/hplus/js/content.min.js?v=1.0.0"></script>
  	<script src="${basePath}/static/layer/layer.js"></script>
  	<script src="${basePath}/static/laypage-v1.3/laypage/laypage.js"></script>
  	<script src="${basePath}/static/laytpl/laytpl.js"></script>
  	<script src="${basePath}/static/zTree_v3/js/jquery.ztree.core.js"></script>
  	<script src="${basePath}/static/zTree_v3/js/jquery.ztree.excheck.min.js"></script>
  	<script src="${basePath}/static/zTree_v3/js/jquery.ztree.exedit.min.js"></script>
	<script type="text/javascript">
	var key="${key?default('')}";
    $(function(){
        //运行
        Ajaxpage();

    })
    function Ajaxpage(curr){
        $.ajax({
            url: "${basePath}/system/sms/list",
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
	//删除
	function Del(id)
	{
		layer.confirm('确认删除此模板?', {icon: 3, title:'提示'}, function(index){
			$.ajax({
				url: "${basePath}/system/sms/delete",
				data:{id:id},
				type: "POST",
				dataType:'json',
				success:function(res){
					
					if(res.status == 200){
			            layer.msg('删除成功',{icon:1,time:1500,shade: 0.1});
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