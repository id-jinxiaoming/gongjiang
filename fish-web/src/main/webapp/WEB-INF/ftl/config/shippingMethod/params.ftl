<!DOCTYPE html>
<html>

<head>


	<#include "/common/header.ftl">


</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content">
          <div class="form-group">
                <label class="col-sm-3 control-label">选择区域：<input type="checkbox" id="checkAllProvince" id="inlineCheckbox1">全部 <label class="checkbox-inline">


                <div class="col-sm-2">
                    <div class="col-sm-10">

                        <#list province as item>
                            <label class="checkbox-inline" style="width: 150px;">
                                <input type="checkbox" name="provice" class="province" value="${item.provinceId}" title="${item.province}" >${item.province}</label>
                        </#list>
                    </div>
                </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label">计费类型：</label>


                <div class="col-sm-3">
                    <select class="form-control m-b" name="type" id="type" >
                        <option value="fixed">固定收费</option>
                        <option value="weight">计重收费</option>
                        <option value="piece">计件收费</option>
                    </select>
                </div>

            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
                <div class="col-sm-6" id="fixed" style="display:block">
                    <div class="col-sm-10">
                        <div class="col-md-2" style="margin-bottom: 5px;">

                            固定收费：<input type="text" value="0"  id="charges"  />元
                        </div>
                    </div>

                </div>
                <div class="col-sm-10"  id="weight" style="display:none;">
                    <div class="col-sm-10">
                        <div class="col-md-2" style="margin-bottom: 5px;">
                            首重重量：<input type="text"  value="0"  id="firstWeight"  >Kg 首重费用：<input type="text" value="0"  id="firstPiece"  >元
                        </div>

                        <div class="col-md-2" style="margin-bottom: 5px;">
                            续重重量：<input type="text"  value="0"  id="addWeight">Kg  续重费用：<input type="text"  value="0"  id="addPiece" >元
                        </div>
                    </div>
                </div>
                <div class="col-sm-10"  id="piece" style="display:none;">
                    <div class="col-sm-10">
                        <div class="col-md-2" style="margin-bottom: 5px;">
                            起始件数：<input type="text" value="0"  id="firstCharges">件 起始费用：<input type="text" value="0" id="firstPiece1" >元
                        </div>

                        <div class="col-md-2" style="margin-bottom: 5px;">
                            续增件数：<input type="text"  value="0"  id="addCharges">件 续增费用：<input type="text" value="0"  id="addPiece1">元
                        </div>
                    </div>
                </div>

            </div>
                <div class="hr-line-dashed"></div>
            <div class="form-group">
                <div class="col-sm-8 col-sm-offset-3">
                    <button class="btn btn-primary" type="submit" onclick="submit();">提交</button>

                </div>
            </div>

    </div>
</div>


<!-- 全局js -->
<script src="${basePath}/static/hplus/js/jquery.min.js?v=2.1.4"></script>
<script src="${basePath}/static/hplus/js/bootstrap.min.js?v=3.3.6"></script>

<!-- 自定义js -->
<script src="${basePath}/static/hplus/js/content.js?v=1.0.0"></script>



<script src="${basePath}/static/common/js/jquery.form.js"></script>
<script src="${basePath}/static/layer/layer.js"></script>
<script >

    var pramas={};
    pramas.type="fixed";
    pramas.area=new Array();
    pramas.charges=0;
    pramas.firstWeight=0;
    pramas.firstCharges=0;
    pramas.addWeight=0;
    pramas.addCharges=0;
    pramas.firstPiece=0;
    pramas.addPiece=0;
    var index = parent.layer.getFrameIndex(window.name);
    var content="";
    $().ready(function() {

        $("#checkAllProvince").change(function(){
            var ischecked = $(this).prop("checked");
            $(".province").prop("checked",ischecked);
        });
        $("#type").change(function(){
            pramas.type=$("#type").val();
           switch (pramas.type){
               case 'fixed':
                   $("#fixed").css("display","block");
                   $("#weight").css("display","none");
                   $("#piece").css("display","none");
                   break;
               case 'weight':

                   $("#fixed").css("display","none");
                   $("#weight").css("display","block");
                   $("#piece").css("display","none");
                   break;
               case 'piece':

                   $("#fixed").css("display","none");
                   $("#weight").css("display","none");
                   $("#piece").css("display","block");
                   break;
           }
        });

    });
    function submit(){

        content=" <b>收费方式:</b>";
        switch (pramas.type){
            case 'fixed':
                content+="固定收费";
                pramas.charges=$("#charges").val();
                if(pramas.charges<0){
                    layer.msg("金额不能为空");
                    return;
                }
                content+=" <b>金额:</b> "+pramas.charges+"元";
                break;
            case 'weight':
                content+="计重收费";
                pramas.firstWeight=$("#firstWeight").val();
                pramas.addWeight=$("#addWeight").val();
                pramas.firstPiece=$("#firstPiece").val();
                pramas.addPiece=$("#addPiece").val();
                if(pramas.firstWeight<=0){
                    layer.msg("首重重量不能为空");
                    return;
                }
                content+="<b>首重重量:</b>"+pramas.firstWeight+"Kg";
                if(pramas.addWeight<=0){
                    layer.msg("续重重量不能为空");
                    return;
                }
                content+=" <b>续重重量:</b>"+pramas.addWeight+"Kg";
                if(pramas.firstPiece<=0){
                    layer.msg("首重费用不能为空");
                    return;
                }
                content+=" <b>首重费用:</b>"+pramas.firstPiece+"元";
                if(pramas.addPiece<=0){
                    layer.msg("续重费用不能为空");
                    return;
                }
                content+=" <b>续重费用:</b>"+pramas.addPiece+"元";
                break;
            case 'piece':
                content+="计件收费";
                pramas.firstCharges=$("#firstCharges").val();
                pramas.addCharges=$("#addCharges").val();
                pramas.firstPiece=$("#firstPiece1").val();
                pramas.addPiece=$("#addPiece1").val();
                if(pramas.firstCharges<=0){
                    layer.msg("起始件数不能为空");
                    return;
                }
                content+=" <b>起始件数:</b>"+pramas.firstCharges+"件";
                if(pramas.addCharges<=0){
                    layer.msg("续增件数不能为空");
                    return;
                }
                content+=" <b>续增件数:</b>"+pramas.addCharges+"件";
                if(pramas.firstPiece<=0){
                    layer.msg("起始费用不能为空");
                    return;
                }
                content+=" <b>起始费用:</b>"+pramas.firstPiece+"元";
                if(pramas.addPiece<=0){
                    layer.msg("续增费用不能为空");
                    return;
                }
                content+=" <b>续增费用:</b>"+pramas.addPiece+"元";
                break;
        }
        content+=" <b>区域:</b>";
        $('input[name="provice"]:checked').each(function(){
            pramas.area.push($(this).val());//向数组中添加元素
            content+=$(this).attr("title")+" ";
        });

        if(pramas.area.length==0){
            layer.msg("请选择区域");
            return;
        }


        parent.addParams(pramas,content);
        parent.layer.close(index);
    }


</script>




</body>

</html>