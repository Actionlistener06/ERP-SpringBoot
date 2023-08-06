package com.patika.shoppingapp.service;

import com.patika.shoppingapp.model.Base;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public abstract class BaseServiceImpl <T extends Base, ID extends Serializable>{
    protected abstract JpaRepository<T, ID> getRepository();
    public T find(ID id) throws EntityNotFoundException {
        Optional<T> entity = getRepository().findById(id);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException("Could not find entity with id: " + id);
        }
        return entity.get();
    }

    public T create(T entity)  {
        try {
            entity = getRepository().save(entity);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
        }
        return entity;
    }

    @Transactional
    public void delete(ID id) throws EntityNotFoundException {
        T entity = find(id);
        getRepository().delete(entity);
    }

    public List<T> findAll() {
        return getRepository().findAll();
    }
    

    public Page<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

}
