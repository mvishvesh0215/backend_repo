package com.vidya_industries.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vidya_industries.Entity.OrderDetails;
import com.vidya_industries.Entity.OrderEntity;
import com.vidya_industries.Entity.OrderStatus;
import com.vidya_industries.Entity.UserEntity;

public interface OrderDao extends JpaRepository<OrderEntity, Long> {

	List<OrderEntity> findByUserAndOrderDetails(UserEntity userEntity,OrderDetails orderDetails);

	List<OrderEntity> findByUser(UserEntity userEntity);

//	List<OrderEntity> findByUserAndOrderStatusNot(UserEntity userFound, OrderStatus completed);

//	List<OrderEntity> findByOrderStatusNot(OrderStatus completed);

	List<OrderEntity> findByOrderStatus(OrderStatus completed);

	List<OrderEntity> findByOrderStatusNotIn(List<OrderStatus> asList);

	List<OrderEntity> findByUserAndOrderStatusNotIn(UserEntity userFound, List<OrderStatus> asList);

}
