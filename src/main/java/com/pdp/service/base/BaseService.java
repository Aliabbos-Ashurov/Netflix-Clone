package com.pdp.service.base;

import com.pdp.model.BaseModel;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  13:39
 **/
@Service
public interface BaseService<T extends BaseModel, ID extends Serializable> {

    ID save(T t);

    void delete(ID id);

    T findById(ID id);

    List<T> findAll();
}
