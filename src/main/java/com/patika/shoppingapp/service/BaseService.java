package com.patika.shoppingapp.service;

import com.patika.shoppingapp.model.Base;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends Base, ID extends Serializable> {

    T find(ID id) throws EntityNotFoundException;

    T create(T entity) ;

    void delete(ID id) throws EntityNotFoundException;

    List<T> findAll();

    Page<T> findAll(Pageable pageable);

    Long count();

}