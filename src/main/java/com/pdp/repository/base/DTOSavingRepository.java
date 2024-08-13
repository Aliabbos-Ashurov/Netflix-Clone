package com.pdp.repository.base;

import com.pdp.dto.DTO;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  12:59
 **/
@Repository
public interface DTOSavingRepository<T extends DTO, ID extends Serializable> {
    ID saveFromDTO(T dto);
}
