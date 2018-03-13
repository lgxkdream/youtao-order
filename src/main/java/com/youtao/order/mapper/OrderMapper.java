package com.youtao.order.mapper;

import java.util.Date;
import org.apache.ibatis.annotations.Param;
import com.youtao.order.pojo.Order;

/**
 * @title: OrderMapper
 * @description: 
 * @Copyright: Copyright (c) 2018
 * @Company: lgxkdream.github.io
 * @author gang.li
 * @version 1.0.0
 * @since 2018年3月13日 下午1:11:14
 */
public interface OrderMapper extends IMapper<Order> {

	public void paymentOrderScan(@Param("date") Date date);

}
