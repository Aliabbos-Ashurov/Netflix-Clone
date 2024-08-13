package com.pdp.dto;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  09:58
 **/
public record SignUpDTO(String fullname, String username, String password, String email) implements DTO {
}
