package com.rs.jadwal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class JadwalNotFoundException extends RuntimeException{

    public JadwalNotFoundException(String message)
    {
        super(message);
    }
}