package com.ana.app.user.DTOs;

public class ResponseDTO {
    public ResponseStatusEnum status;
    public ResponseDTO(ResponseStatusEnum status){
        this.status = status;
    }
}

