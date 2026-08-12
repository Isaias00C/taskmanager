package com.irede.java.repository;

public interface Repository<T> {
    
    void add(T t);
    T getById(int id);
    T delete(T t);
    T update(T t);
    
}
