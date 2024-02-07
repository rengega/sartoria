package com.swe.sartoria.buisiness_logic;

import com.swe.sartoria.dao.DAO;
import com.swe.sartoria.model_domain.Job;

public class JobController {
    private DAO dao;

    public JobController(DAO dao) {
        this.dao = dao;
    }

    public void addJob(Job job){
        dao.addJob(job);
    }


}
