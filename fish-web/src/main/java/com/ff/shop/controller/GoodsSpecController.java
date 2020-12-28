package com.ff.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.util.ListUtils;
import com.ff.common.util.StringUtils;
import com.ff.shop.model.Goods;
import com.ff.shop.model.GoodsOptional;
import com.ff.shop.model.GoodsOptionalType;
import com.ff.shop.model.GoodsSpec;
import com.ff.shop.service.GoodsOptionalService;
import com.ff.shop.service.GoodsOptionalTypeService;
import com.ff.shop.service.GoodsService;
import com.ff.shop.service.GoodsSpecService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author yuzhongxin
 * @Description 商品选项
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/shop/goodsSpec")
public class GoodsSpecController extends BaseController{
	@Reference
	private GoodsService goodsService;

	@Reference
	private GoodsSpecService goodsSpecService;

	@Reference
	private GoodsOptionalService goodsOptionalService;

	@Reference
	private GoodsOptionalTypeService goodsOptionalTypeService;


	@RequiresPermissions(value = "shop:goodsSpec:list")
	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable("id")Integer id,ModelMap map) {
		map.put("goodsId",id);
		Goods goods= goodsService.findByPrimaryKey(id.toString());
		if(goods!=null)
		map.put("goodsName",goods.getGoodsName());
		return new ModelAndView("shop/goodsSpec/list",map);

	}


	@RequiresPermissions(value = "shop:goodsSpec:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(Integer id,HttpServletRequest request) {
		Page<GoodsSpec> page = getPage(request);
		EntityWrapper<GoodsSpec> ew=new EntityWrapper();
		ew.eq("goods_id",id);
		ew.orderBy("id",false);
		Page<GoodsSpec> data= goodsSpecService.selectPage(page,ew);
		Map<String, Object> resultMap = new LinkedHashMap();

		resultMap.put("data",data);
		return resultMap;
	}

	@RequiresPermissions("shop:goodsSpec:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String[] id) {
		goodsSpecService.deleteByPrimaryKey(id);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("msg","删除成功");
		resultMap.put("status",200);
		return resultMap;
	}

	@RequiresPermissions("shop:goodsSpec:add")
	@RequestMapping(value="/add/{id}",method=RequestMethod.GET)
	public ModelAndView add(@PathVariable("id")Integer id,ModelMap map){
		map.put("goodsId",id);
		GoodsOptional goodsOptionalMap=new GoodsOptional();
		goodsOptionalMap.setGoodsId(id);
		List<GoodsOptional> goodsOptionals= goodsOptionalService.selectByT(goodsOptionalMap);
		List<GoodsOptionalType> goodsOptionalTypeLists=new ArrayList<>();
		if(goodsOptionals!=null){

			for(GoodsOptional optional:goodsOptionals){
				GoodsOptionalType goodsOptionalType= goodsOptionalTypeService.findByPrimaryKey(optional.getTypeId().toString());
				boolean flag=false;
				for(GoodsOptionalType item:goodsOptionalTypeLists){
					if(item.getTypeId()==optional.getTypeId())
					{
						flag=true;
						break;
					}
				}
				if(!flag){
					goodsOptionalTypeLists.add(goodsOptionalType);
				}
			}
		}

		map.put("goodsOptionals", goodsOptionals);
		map.put("typeList", goodsOptionalTypeLists);
		return new ModelAndView("shop/goodsSpec/add",map);
	}
	@RequiresPermissions("shop:goodsSpec:add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAdd(GoodsSpec model,String key){
		Map<String, Object> resultMap = new LinkedHashMap();
		String keyName="";
		String keyIds="";
		if(StringUtils.isNoneEmpty(key)){
			String[] keys=key.split(":");
			List<Integer> keyList=new ArrayList<>();
			//排序
			for(int i=0;i<keys.length;i++){
				keyList.add(Integer.parseInt(keys[i]));
			}
			Collections.sort(keyList);
			//生成KEY KEYNAME
			for(int i=0;i<keyList.size();i++){
				GoodsOptional goodsOptional= goodsOptionalService.findByPrimaryKey(keyList.get(i).toString());
				keyIds+=keyList.get(i)+":";
				keyName+=goodsOptional.getOptText()+":";
			}
		}
		model.setKey(keyIds.substring(0,keyIds.length()-1));
		model.setKeyName(keyName.substring(0,keyName.length()-1));
		if(goodsSpecService.insert(model)!=0){

			resultMap.put("status", 200);
			resultMap.put("message", "操作成功");
		}
		else
		{
			resultMap.put("status", 500);
			resultMap.put("message", "操作失败");
		}
		return resultMap;
	}


	@RequiresPermissions("shop:goodsSpec:edit")
	@RequestMapping(value="/edit/{id}/{specId}",method=RequestMethod.GET)
	public ModelAndView add(@PathVariable("id")Integer id,@PathVariable("specId")Integer specId,ModelMap map){

		GoodsOptional goodsOptionalMap=new GoodsOptional();
		goodsOptionalMap.setGoodsId(id);
		List<GoodsOptional> goodsOptionals= goodsOptionalService.selectByT(goodsOptionalMap);
		List<GoodsOptionalType> goodsOptionalTypeLists=new ArrayList<>();
		if(goodsOptionals!=null){
			for(GoodsOptional optional:goodsOptionals){
				GoodsOptionalType goodsOptionalType= goodsOptionalTypeService.findByPrimaryKey(optional.getTypeId().toString());
				boolean flag=false;
				for(GoodsOptionalType item:goodsOptionalTypeLists){
					if(item.getTypeId()==optional.getTypeId())
					{
						flag=true;
						break;
					}
				}
				if(!flag){
					goodsOptionalTypeLists.add(goodsOptionalType);
				}
			}
		}
		map.put("goodsOptionals", goodsOptionals);
		map.put("typeList", goodsOptionalTypeLists);
		GoodsSpec goodsSpec= goodsSpecService.findByPrimaryKey(specId.toString());
		map.put("goodsSpec",goodsSpec);
		map.put("goodsId",id);
		return new ModelAndView("shop/goodsSpec/edit",map);
	}
	@RequiresPermissions("shop:goodsSpec:edit")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEdit(GoodsSpec model,String key){
		Map<String, Object> resultMap = new LinkedHashMap();
		String keyName="";
		String keyIds="";
		if(StringUtils.isNoneEmpty(key)){
			String[] keys=key.split(":");
			List<Integer> keyList=new ArrayList<>();
			//排序
			for(int i=0;i<keys.length;i++){
				keyList.add(Integer.parseInt(keys[i]));
			}
			Collections.sort(keyList);
			//生成KEY KEYNAME
			for(int i=0;i<keyList.size();i++){
				GoodsOptional goodsOptional= goodsOptionalService.findByPrimaryKey(keyList.get(i).toString());
				keyIds+=keyList.get(i)+":";
				keyName+=goodsOptional.getOptText()+":";
			}
		}
		model.setKey(keyIds.substring(0,keyIds.length()-1));
		model.setKeyName(keyName.substring(0,keyName.length()-1));
		if(goodsSpecService.updateByPrimaryKey(model)!=0){

			resultMap.put("status", 200);
			resultMap.put("message", "操作成功");
		}
		else
		{
			resultMap.put("status", 500);
			resultMap.put("message", "操作失败");
		}
		return resultMap;
	}









}
