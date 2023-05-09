package com.example.demo.Services;

import com.example.demo.Entitys.Base;
import com.example.demo.Repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<E extends Base, ID extends Serializable> implements BaseServices<E, ID> {
    protected BaseRepository<E, ID> BaseRepository;
    public BaseServiceImpl(BaseRepository<E, ID> baseRepository) {
        this.BaseRepository = baseRepository;
    }


    @Override
    @Transactional
    public Page<E> findAllPaged(Pageable pageable) throws Exception {
        try {
            return BaseRepository.findAll(pageable);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @Override
    @Transactional
    public E findById(ID id) throws Exception {
        try {
            Optional<E> entityOptional = BaseRepository.findById(id);
            return entityOptional.get();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E save(E entity) throws Exception {
        try {
            entity = BaseRepository.save(entity);
            return entity;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E update(ID id, E entity) throws Exception {
        try {
            Optional<E> entityOptional = BaseRepository.findById(id);
            E persona = entityOptional.get();
            persona = BaseRepository.save(entity);
            return persona;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(ID id) throws Exception {
        try {
            if (BaseRepository.existsById(id)){
                BaseRepository.deleteById(id);
                return true;
            }else{
                throw new Exception();
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}