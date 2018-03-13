package com.youtao.order.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youtao.order.dao.IOrder;
import com.youtao.order.pojo.Order;
import com.youtao.order.pojo.PageResult;
import com.youtao.order.pojo.ResultMsg;

/**
 * @title: OrderService
 * @description: 订单管理Service
 * @Copyright: Copyright (c) 2018
 * @Company: lgxkdream.github.io
 * @author gang.li
 * @version 1.0.0
 * @since 2018年3月13日 下午1:12:04
 */
@Service
public class OrderService {

    @Autowired
    private IOrder orderDao;
    
//    @Autowired
//    private RabbitTemplate rabbitTemplate;

    public String createOrder(Order order) {
        // 生成订单ID，规则为：userid+当前时间戳
        String orderId = order.getUserId() + "" + System.currentTimeMillis();
        order.setOrderId(orderId);

        // 设置订单的初始状态为未付款
        order.setStatus(1);

        // 设置订单的创建时间
        Date date = new Date();
        order.setCreateTime(date);
        order.setUpdateTime(date);

        // 设置买家评价状态，初始为未评价
        order.setBuyerRate(0);

        // 持久化order对象
        orderDao.createOrder(order);
        
        //发送消息到RabbitMQ
//            Map<String, Object> msg = new HashMap<String, Object>(3);
//            msg.put("userId", order.getUserId());
//            msg.put("orderId", order.getOrderId());
//            List<Long> itemIds = new ArrayList<Long>(order.getOrderItems().size());
//            for (OrderItem orderItem : order.getOrderItems()) {
//                itemIds.add(orderItem.getItemId());
//            }
//            msg.put("itemIds", itemIds);
//            this.rabbitTemplate.convertAndSend(objectMapper.writeValueAsString(msg));
        
        return orderId;
    }

    public Order queryOrderById(String orderId) {
        Order order = orderDao.queryOrderById(orderId);
        return order;
    }

    public PageResult<Order> queryOrderByUserNameAndPage(String buyerNick, int page, int count) {
        return orderDao.queryOrderByUserNameAndPage(buyerNick, page, count);
    }

    public ResultMsg changeOrderStatus(Order order) {
    	Date date = new Date();
    	Integer status = order.getStatus();
    	switch (status) {
		case 2: // 已付款
			order.setPaymentTime(date);
			break;
        case 3: // 已发货
			order.setConsignTime(date);
			break;
        case 4: // 交易成功
        	order.setEndTime(date);
	        break;
        case 5: // 交易关闭
        	order.setCloseTime(date);
        	break;
		default:
			// 订单状态错误
			return new ResultMsg("400", "订单状态有误!");
		}
        return this.orderDao.changeOrderStatus(order);
    }

}
