package com.swe.sartoria.buisiness_logic;

import com.swe.sartoria.dao.DAO;
import com.swe.sartoria.model_domain.Job;

import java.util.List;

// TODO: Exepction handling
public class JobController {
    private DAO dao;

    public JobController(DAO dao) {
        this.dao = dao;
    }

    public void addJob(Job job){
        dao.addJob(job);
    }

    public void deleteJob(long id){
        dao.deleteJob(id);
    }

    public void deleteJob(Job job){
        dao.deleteJob(job.getId());
    }

    public void updateJob(Job job){
        dao.updateJob(job);
    }


    public List<Job> getAllJobs(){
        return dao.getAllJobs();
    }

    public List<Job> getJobByName(String name){
        return dao.getJobByName(name);
    }

    public List<Job> getJobByCategory(String category){
        return dao.getJobByCategory(category);
    }

    public Job getJob(long id){
        return dao.getJob(id);
    }

}
