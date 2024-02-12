package com.swe.sartoria.dto;

import java.util.List;

public class OrderResponse {
    private List<OrderDTO> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
