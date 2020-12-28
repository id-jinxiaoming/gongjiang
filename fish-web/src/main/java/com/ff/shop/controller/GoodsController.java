package com.ff.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.model.JsonTreeData;
import com.ff.common.util.JsonUtils;
import com.ff.shop.model.Goods;
import com.ff.shop.model.GoodsAlbum;
import com.ff.shop.model.GoodsCate;
import com.ff.shop.model.GoodsCateAttr;
import com.ff.shop.service.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @Description 商品列表
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/shop/goods")
public class GoodsController extends BaseController{
	@Reference
	private GoodsService goodsService;
	@Reference
	private BrandService brandService;
	@Reference
	private GoodsCateService goodsCateService;
	@Reference
	private GoodsAlbumService goodsAlbumService;


	private void showTest(){

		System.out.println("1V");
	}
	@RequiresPermissions(value = "shop:goods:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(String key,ModelMap map) {

		showTest();

		key= StringEscapeUtils.unescapeHtml(key);
		try {
			key = new String(key.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}

		map.put("key", key);
		return new ModelAndView("shop/goods/list",map);

	}


	@RequiresPermissions(value = "shop:goods:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(String key,HttpServletRequest request) {
		Page<Goods> page = getPage(request);
		EntityWrapper<Goods> ew=new EntityWrapper();
		ew.like("goods_name","%"+key+"%");
		ew.orderBy("goodsId",false);
		Page<Goods> data= goodsService.selectPage(page,ew);
		Map<String, Object> resultMap = new LinkedHashMap();

		resultMap.put("data",data);
		return resultMap;
	}

	@RequiresPermissions("shop:goods:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String[] id) {
		goodsService.deleteByPrimaryKey(id);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("msg","删除成功");
		resultMap.put("status",200);
		return resultMap;
	}
	private  void GetNode(List<JsonTreeData> tree,List<GoodsCate> dto,Integer layer){
		for (JsonTreeData item:tree) {
			GoodsCate cate=new GoodsCate();
			cate.setCateId(Integer.parseInt(item.getId()) );
			String tag="";
			for(int i=0;i<layer;i++)
			{
				tag+="  |--";
			}
			cate.setCateName(tag+item.getText());
			dto.add(cate);
			if(item.getChildren()!=null&&item.getChildren().size()>0)
			{
				GetNode(item.getChildren(),dto,layer+1);
			}
		}

	}
	@RequiresPermissions("shop:goods:add")
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public ModelAndView add(ModelMap map){
		List<JsonTreeData>  list=goodsCateService.selectTreeList();
		List<GoodsCate> dto=new ArrayList<>();
		GetNode(list,dto,0);
		map.put("cate",dto);
		map.put("brand", brandService.selectAll());
		return new ModelAndView("/shop/goods/add",map);
	}
	@RequiresPermissions("shop:goods:add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAdd(Goods model,HttpServletRequest request){
		Map<String, Object> resultMap = new LinkedHashMap();
		model.setCreatedDate(new Date().getTime()/1000);
		model.setGoodsContent(StringEscapeUtils.unescapeHtml(model.getGoodsContent()));
		Integer id=goodsService.insert(model);
		if(id!=0){
			String[] photo=request.getParameterValues("photo");
			if(photo!=null)
			{
				for (String item:photo) {
					GoodsAlbum album=new GoodsAlbum();
					album.setGoodsId(id);
					album.setImage(item);
					goodsAlbumService.insert(album);
				}
			}

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

	@RequiresPermissions("shop:goods:edit")
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id")Integer id, ModelMap map){
		List<JsonTreeData>  list=goodsCateService.selectTreeList();
		List<GoodsCate> dto=new ArrayList<>();
		GetNode(list,dto,0);
		map.put("cate",dto);
		map.put("brand", brandService.selectAll());
		map.put("goodsItem",goodsService.findByPrimaryKey(id.toString()));
		GoodsAlbum goodsMap=new GoodsAlbum();
		goodsMap.setGoodsId(id);
		List<GoodsAlbum> album=goodsAlbumService.selectByT(goodsMap);
		map.put("album", album);
		return new ModelAndView("/shop/goods/edit",map);
	}
	@RequiresPermissions("shop:goods:edit")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEdit(Goods model,HttpServletRequest request){
		Map<String, Object> resultMap = new LinkedHashMap();
		model.setGoodsContent(StringEscapeUtils.unescapeHtml(model.getGoodsContent()));
		Integer id=goodsService.updateByPrimaryKey(model);
		if(id!=0){
			//删除之前数据
			goodsAlbumService.deleteByGoodsId(model.getGoodsId());
			String[] photo=request.getParameterValues("photo");
			if(photo!=null)
			{

				for (String item:photo) {
					GoodsAlbum album=new GoodsAlbum();
					album.setGoodsId(model.getGoodsId());
					album.setImage(item);
					goodsAlbumService.insert(album);
				}
			}

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
