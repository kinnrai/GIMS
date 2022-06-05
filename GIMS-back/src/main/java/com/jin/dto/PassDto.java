package com.jin.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PassDto implements Serializable {
    private String oldPass;
    private String newPass;
}
