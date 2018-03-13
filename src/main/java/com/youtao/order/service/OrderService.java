package com.youtao.order.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtao.order.bean.YoutaoResult;
import com.youtao.order.dao.IOrder;
import com.youtao.order.pojo.Order;
import com.youtao.order.pojo.PageResult;
import com.youtao.order.pojo.ResultMsg;
import com.youtao.order.utils.ValidateUtil;

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

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private IOrder orderDao;
    
//    @Autowired
//    private RabbitTemplate rabbitTemplate;

    // TODO 事务不能回滚，需要修改
    public YoutaoResult createOrder(String json) {
        Order order = null;
        try {
            order = objectMapper.readValue(json, Order.class);
            // 校验Order对象
            ValidateUtil.validate(order);
        } catch (Exception e) {
            return YoutaoResult.build(400, "请求参数有误!");
        }

        try {
            // 生成订单ID，规则为：userid+当前时间戳
            String orderId = order.getUserId() + "" + System.currentTimeMillis();
            order.setOrderId(orderId);

            // 设置订单的初始状态为未付款
            order.setStatus(1);

            // 设置订单的创建时间
            order.setCreateTime(new Date());
            order.setUpdateTime(order.getCreateTime());

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
            
            return YoutaoResult.ok(orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return YoutaoResult.build(400, "保存订单失败!");
    }

    public Order queryOrderById(String orderId) {
        Order order = orderDao.queryOrderById(orderId);
        return order;
    }

    public PageResult<Order> queryOrderByUserNameAndPage(String buyerNick, int page, int count) {
        return orderDao.queryOrderByUserNameAndPage(buyerNick, page, count);
    }

    public ResultMsg changeOrderStatus(String json) {
        Order order = null;
        try {
            order = objectMapper.readValue(json, Order.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg("400", "请求参数有误!");
        }
        return this.orderDao.changeOrderStatus(order);
    }

}
