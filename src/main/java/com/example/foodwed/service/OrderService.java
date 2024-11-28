package com.example.foodwed.service;

import com.example.foodwed.dto.Request.OrderCreateRequest;
import com.example.foodwed.dto.Request.OrderUpdateRequest;
import com.example.foodwed.dto.response.PaginatedResponse;
import com.example.foodwed.entity.Orders;
import com.example.foodwed.exception.Appexception;
import com.example.foodwed.exception.ErrorCode;
import com.example.foodwed.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Tạo mới một đơn hàng
     */
    public Orders create(OrderCreateRequest request) {
        Orders order = new Orders();
        // Thiết lập thời gian tạo đơn hàng
        order.setUid(request.getUid());
        order.setName(request.getName());
        order.setAddress(request.getAddress());
        order.setPhone(request.getPhone());
        order.setIngredien(request.getIngredien());
        order.setPrice(request.getPrice());
        order.setQuantity(request.getQuantity());
        order.setTotalPrice(request.getTotalPrice());
        order.setIsactive(false);
        order.setOrderTime(LocalDateTime.now());



        // Lưu đơn hàng
        return orderRepository.save(order);
    }

    /**
     * Xóa đơn hàng theo ID
     */
    @Transactional
    public void delete(String id) {
        // Kiểm tra xem đơn hàng có tồn tại không
        if (!orderRepository.existsById(id)) {
            throw new Appexception(ErrorCode.ORDER_NOT_FOUND);
        }

        // Xóa đơn hàng
        orderRepository.deleteById(id);
    }

    /**
     * Cập nhật thông tin đơn hàng
     */
    public Orders update(String id, OrderUpdateRequest updatedOrder) {
        // Lấy đơn hàng từ cơ sở dữ liệu
        Orders existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new Appexception(ErrorCode.ORDER_NOT_FOUND));

        // Cập nhật thông tin

        existingOrder.setName(updatedOrder.getName());
        existingOrder.setAddress(updatedOrder.getAddress());
        existingOrder.setPhone(updatedOrder.getPhone());
        existingOrder.setIngredien(updatedOrder.getIngredien());
        existingOrder.setPrice(updatedOrder.getPrice());
        existingOrder.setQuantity(updatedOrder.getQuantity());
        existingOrder.setIsactive(updatedOrder.isIsactive());
        existingOrder.setTotalPrice(updatedOrder.getTotalPrice());

        // Lưu lại đơn hàng đã cập nhật
        return orderRepository.save(existingOrder);
    }

    /**
     * Lấy danh sách đơn hàng phân trang
     */
    public PaginatedResponse<Orders> getAllPaginated(int page, int size, boolean isActive) {
        PageRequest pageable = PageRequest.of(page, size , Sort.by(Sort.Direction.DESC, "orderTime"));
        Page<Orders> orderPage = orderRepository.findByIsactive(isActive, pageable);

        // Trả về DTO chứa dữ liệu phân trang
        return new PaginatedResponse<>(
                orderPage.getContent(),
                orderPage.getNumber(),
                orderPage.getSize(),
                orderPage.getTotalElements(),
                orderPage.getTotalPages(),
                orderPage.isLast()
        );
    }
}
