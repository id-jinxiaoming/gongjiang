package com.ff.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ff.common.base.BaseController;
import com.ff.common.util.JsonUtils;
import com.ff.shop.model.Goods;
import com.ff.shop.model.GoodsAttr;
import com.ff.shop.model.GoodsCateAttr;
import com.ff.shop.service.GoodsAttrService;
import com.ff.shop.service.GoodsCateAttrService;
import com.ff.shop.service.GoodsService;
import com.ff.shop.vo.GoodsAttrVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yuzhongxin
 * @Description 商品属性规格
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/shop/goodsAttr")
public class GoodsAttrController extends BaseController{
	@Reference
	private GoodsAttrService goodsAttrService;

	@Reference
	private GoodsCateAttrService goodsCateAttrService;

	@Reference
	private GoodsService goodsService;
	



	@RequiresPermissions("shop:goodsAttr:set")
	@RequestMapping(value="/set/{goodsId}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("goodsId")String goodsId, ModelMap map){
		Goods goods= goodsService.findByPrimaryKey(goodsId.toString());

		GoodsCateAttr attr=new GoodsCateAttr();
		attr.setCateId(goods.getCateId());
		List<GoodsCateAttr> list= goodsCateAttrService.selectByT(attr);
		if(list!=null&&list.size()>0)
		{
			for (GoodsCateAttr i:list) {
				if(!i.getOpts().equals(""))
				{
					List<String> opts=JsonUtils.jsonToList(i.getOpts(),String.class);
					i.setOpts(StringUtils.join(opts.toArray(),','));

				}
			}
		}


		map.put("attr", list);
		map.put("goodsId", goodsId);
		GoodsAttr goodsAttr=new GoodsAttr();
		goodsAttr.setGoodsId(Integer.parseInt(goodsId));
		List<GoodsAttr> goodsAttrList= goodsAttrService.selectByT(goodsAttr);
		map.put("goodsAttr",goodsAttrList);

		return new ModelAndView("/shop/goodsAttr/set",map);

	}
	@RequiresPermissions("shop:goodsAttr:set")
	@RequestMapping(value="/set",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEdit(GoodsAttrVO goodsAttrs){
		boolean flag=true;
		for (GoodsAttr item:goodsAttrs.getGoodsAttrs()) {
			if(flag)
			{
				goodsAttrService.deleteByGoodsId(item.getGoodsId());
				flag=false;
			}

			goodsAttrService.insert(item);
		}
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("status", 200);
		resultMap.put("message", "操作成功");
		return resultMap;
	}
	


	

}
