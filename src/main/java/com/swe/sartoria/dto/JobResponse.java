package com.swe.sartoria.dto;

import java.util.List;

public class JobResponse {
    private List<JobDTO> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
