package com.swe.sartoria.controller;

import com.swe.sartoria.dto.OrderDTO;
import com.swe.sartoria.dto.OrderResponse;
import com.swe.sartoria.service.DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/orders")
public class OrderController {
    private final DAO dao;

    @Autowired
    public OrderController( DAO dao) {
        this.dao = dao;
    }


    @GetMapping("/testMethod")
    public void controllerHit(){
        System.out.println("Orders controller hit");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            for (GrantedAuthority authority : auth.getAuthorities()) {
                System.out.println(authority.getAuthority());
            }
        }else {
            System.out.println("auth is null");
        }
    }

    @GetMapping("/getOrderStatusById/{id}")
    public ResponseEntity<String> getOrderStatusById(@PathVariable Long id){
        OrderDTO order = dao.getOrderById(id);
        return new ResponseEntity<>(order.getStatus(), HttpStatus.OK);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<OrderResponse> getAllOrders(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    )
    {
        return ResponseEntity.ok(dao.getAllOrders(pageNo, pageSize));
    }

    @GetMapping("/searchByCostumer/{search}")
    public ResponseEntity<OrderResponse> searchOrdersByCosumerString(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @PathVariable String search
    )
    {
        return ResponseEntity.ok(dao.searchByCostumerString(search, pageNo, pageSize));
    }


    @GetMapping("/getOrdersByCostumerId/{id}")
    public ResponseEntity<OrderResponse> getOrdersByCostumerId(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @PathVariable Long id
    )
    {
        return ResponseEntity.ok(dao.getByCostumerId(id, pageNo, pageSize));
    }

    @GetMapping("/getOrderById/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id){
        return new ResponseEntity<>(dao.getOrderById(id), HttpStatus.OK);
    }


    @PostMapping("/addOrder")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public ResponseEntity<OrderDTO> addOrder(@RequestBody OrderDTO orderDto){
        return new ResponseEntity<>(dao.addOrder(orderDto), HttpStatus.CREATED);
    }

    @PutMapping("/updateOrder")
    public ResponseEntity<OrderDTO> updateORder(@RequestBody OrderDTO orderDTO){
        return new ResponseEntity<>(dao.updateOrder(orderDTO, orderDTO.getId()), HttpStatus.OK);
    }

    @DeleteMapping("/deleteOrder/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id){
        dao.deleteOrder(id);
        return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
    }
}
