package com.youtao.order.dao;

import com.youtao.order.pojo.Order;
import com.youtao.order.pojo.PageResult;
import com.youtao.order.pojo.ResultMsg;

/**
 * @title: IOrder
 * @description: 订单DAO接口
 * @Copyright: Copyright (c) 2018
 * @Company: lgxkdream.github.io
 * @author gang.li
 * @version 1.0.0
 * @since 2018年3月13日 下午1:10:28
 */
public interface IOrder {

    /**
     * 创建订单
     * 
     * @param order
     */
    public void createOrder(Order order);

    /**
     * 根据订单ID查询订单
     * 
     * @param orderId
     * @return
     */
    public Order queryOrderById(String orderId);

    /**
     * 根据用户名分页查询订单信息
     * 
     * @param buyerNick 买家昵称，用户名
     * @param start 分页起始取数位置
     * @param count 查询数据条数
     * @return 分页结果集
     */
    public PageResult<Order> queryOrderByUserNameAndPage(String buyerNick, Integer page, Integer count);

    /**
     * 更改订单状态，由service层控制修改逻辑
     * 
     * @param order
     * @return
     */
    public ResultMsg changeOrderStatus(Order order);

}
