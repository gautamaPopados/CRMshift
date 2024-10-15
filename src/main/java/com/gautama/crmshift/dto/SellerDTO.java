package com.gautama.crmshift.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class SellerDTO implements Serializable {

    private String name;
    private String contactInfo;

    public SellerDTO() {}

    public SellerDTO(String name, String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
    }
}