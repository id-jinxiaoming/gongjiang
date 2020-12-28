package com.ff.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ff.common.base.BaseController;
import com.ff.common.model.JsonTreeData;
import com.ff.shop.model.*;
import com.ff.shop.model.bo.GoodsReviewBO;
import com.ff.shop.service.BrandService;
import com.ff.shop.service.GoodsCateBrandService;
import com.ff.shop.service.GoodsCateService;
import com.ff.shop.service.GoodsReviewService;
import com.ff.system.model.Dict;
import com.ff.system.service.DictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yuzhongxin
 * @Description 商品评价
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/shop/goodsReview")
public class GoodsReviewController extends BaseController{
	@Reference
	private GoodsReviewService goodsReviewService;
	@Reference
	private DictService dictService;

	@RequiresPermissions(value = "shop:goodsReview:list")
	@RequestMapping(value = "/list/{goodsId}", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable("goodsId")String goodsId,ModelMap map) {
		map.put("goodsId",goodsId);
		List<Dict> dict= dictService.selectByCode("goods_review_type");
		map.put("goodsReviewType",dict);

		return new ModelAndView("shop/goodsReview/list",map);

	}


	@RequiresPermissions(value = "shop:goodsReview:list")
	@RequestMapping(value = "/list/{goodsId}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(@PathVariable("goodsId")String goodsId,HttpServletRequest request) {
		GoodsReview review=new GoodsReview();
		review.setGoodsId(Integer.parseInt(goodsId));
		List<GoodsReviewBO> data= goodsReviewService.selectListByT(review);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",data);


		return resultMap;
	}

	@RequiresPermissions("shop:goodsReview:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String[] id) {
		goodsReviewService.deleteByPrimaryKey(id);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("msg","删除成功");
		resultMap.put("status",200);
		return resultMap;
	}



	@RequiresPermissions("shop:goodsReview:audit")
	@RequestMapping(value="/audit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEdit(GoodsReview goodsReview){
		goodsReviewService.updateByPrimaryKey(goodsReview);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("status", 200);
		resultMap.put("message", "操作成功");
		return resultMap;
	}







}
