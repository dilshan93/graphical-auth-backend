package com.userauth.be.graphicalauthbackend.dto;

import lombok.Data;

@Data
public class BackendMessage {
private String msg;

    public BackendMessage(String msg) {
        this.msg = msg;
    }
}
