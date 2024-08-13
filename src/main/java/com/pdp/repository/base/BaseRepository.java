package com.pdp.repository.base;

import com.pdp.model.BaseModel;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  12:41
 **/
@Repository
public interface BaseRepository<T extends BaseModel, ID extends Serializable> {


    ID save(T object);

    void delete(ID id);

    T findById(ID id);

    List<T> findAll();
}
