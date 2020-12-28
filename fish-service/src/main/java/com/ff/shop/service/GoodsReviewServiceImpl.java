package com.ff.shop.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseServiceImpl;
import com.ff.common.model.ResponseData;
import com.ff.order.dao.OrderGoodsMapper;
import com.ff.order.model.OrderGoods;
import com.ff.shop.dao.GoodsReviewMapper;
import com.ff.shop.model.GoodsReview;
import com.ff.shop.model.bo.GoodsReviewBO;
import com.ff.shop.model.bo.GoodsReviewUserBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service("goodsReviewService")
@Transactional(readOnly=true)
public class GoodsReviewServiceImpl extends BaseServiceImpl<GoodsReview> implements GoodsReviewService{

	@Autowired
	GoodsReviewMapper mapper;

	@Autowired
	OrderGoodsMapper orderGoodsMapper;


	@Override
	public List<GoodsReviewBO> selectListByT(GoodsReview t) {

		return mapper.selectListByT(t);
	}

	@Override
	public List<GoodsReviewUserBO> selectListByGoodsId(Integer id, Page page) {
		return mapper.selectListByGoodsId(id,page);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ResponseData GoodsReview(List<GoodsReview> goodsReviews, String orderId, Integer userId) {
		ResponseData result = new ResponseData();
		//TODO:检测是否允许评论
		Map<String, Object> map=new HashMap<>();
		map.put("order_id",orderId);
		map.put("is_reviewed",0);
		List<OrderGoods> orderGoodsList= orderGoodsMapper.selectByMap(map);
		if(orderGoodsList==null||orderGoodsList.size()<=0){
			result.setState(500);
			result.setMessage("非法操作");
			return result;
		}
		//TODO:写入商品评价认证
		for(GoodsReview r:goodsReviews){
			r.setStatus(1);
			r.setUserId(userId);
			r.setCreatedDate(new Date().getTime()/1000);
			mapper.insert(r);

		}

		//TODO:订单商品已评状态
		for(OrderGoods g:orderGoodsList){
			g.setIsReviewed(1);
			orderGoodsMapper.updateById(g);
		}

		result.setState(200);
		result.setMessage("操作成功");
		return result;
	}


}
