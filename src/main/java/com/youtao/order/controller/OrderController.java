package com.youtao.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtao.order.bean.YoutaoResult;
import com.youtao.order.pojo.Order;
import com.youtao.order.pojo.PageResult;
import com.youtao.order.pojo.ResultMsg;
import com.youtao.order.service.OrderService;
import com.youtao.order.utils.ValidateUtil;

/**
 * @title: OrderController
 * @description: 订单管理Controller
 * @Copyright: Copyright (c) 2018
 * @Company: lgxkdream.github.io
 * @author gang.li
 * @version 1.0.0
 * @since 2018年3月13日 下午1:10:07
 */
@RequestMapping("/order")
@Controller
public class OrderController {
	
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Autowired
	private OrderService orderService;
	
	/**
	 * 创建订单
	 * @param json
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create" , method = RequestMethod.POST)
	public YoutaoResult createOrder(@RequestBody String json) {
		try {
			Order order = null;
			try {
			    order = MAPPER.readValue(json, Order.class);
			    // 校验Order对象
			    ValidateUtil.validate(order);
			} catch (Exception e) {
			    return YoutaoResult.build(400, "请求参数有误!");
			}
			String orderId = orderService.createOrder(order);
			return YoutaoResult.ok(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return YoutaoResult.build(400, "保存订单失败!");
	}
	
	
	/**
	 * 根据订单ID查询订单
	 * @param orderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/query/{orderId}" ,method = RequestMethod.GET)
	public Order queryOrderById(@PathVariable("orderId") String orderId) {
		return orderService.queryOrderById(orderId);
	}

	/**
	 * 根据用户名分页查询订单
	 * @param buyerNick
	 * @param page
	 * @param count
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/query/{buyerNick}/{page}/{count}")
	public PageResult<Order> queryOrderByUserNameAndPage(@PathVariable("buyerNick") String buyerNick,@PathVariable("page") Integer page,@PathVariable("count") Integer count) {
		return orderService.queryOrderByUserNameAndPage(buyerNick, page, count);
	}

	
	/**
	 * 修改订单状态
	 * @param json
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/changeOrderStatus",method = RequestMethod.POST)
	public ResultMsg changeOrderStatus(@RequestBody String json) {
		Order order = null;
        try {
            order = MAPPER.readValue(json, Order.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg("400", "请求参数有误!");
        }
		return orderService.changeOrderStatus(order);
	}
}
