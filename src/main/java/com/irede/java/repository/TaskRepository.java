package com.irede.java.repository;

import java.util.ArrayList;
import com.irede.java.models.Task;

public class TaskRepository implements Repository<Task>{
    private ArrayList<Task> repo;

    public TaskRepository() {
        this.repo = new ArrayList<Task>();       
    }

    public Task findTaskByTitle(String title) {
        for (Task t : repo){
            if(t.getTitle().equals(title)){
                return t;
            }
        }

        return null;
    }

    public ArrayList<Task> getRepo() {
        return repo;
    }

    @Override
    public void add(Task t) {
        repo.add(t);
    }

    @Override
    public Task getById(int id) {
        return repo.get(id);
    }

    @Override
    public Task delete(Task t) {
        repo.remove(t);
        return t;
    }

    @Override
    public Task update(Task t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}