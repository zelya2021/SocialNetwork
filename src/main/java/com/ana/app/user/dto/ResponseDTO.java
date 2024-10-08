package com.ana.app.user.dto;

public class ResponseDTO {
    public ResponseStatusEnum status;
    public ResponseDTO(ResponseStatusEnum status){
        this.status = status;
    }
}

