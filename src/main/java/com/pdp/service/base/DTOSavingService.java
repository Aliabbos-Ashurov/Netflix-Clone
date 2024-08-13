package com.pdp.service.base;

import com.pdp.dto.DTO;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  13:40
 **/
@Service
public interface DTOSavingService<T extends DTO, ID extends Serializable> {
    ID saveFromDTO(T dto);
}
