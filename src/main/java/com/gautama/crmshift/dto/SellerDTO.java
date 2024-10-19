package com.gautama.crmshift.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class SellerDTO implements Serializable {

    private String name;
    private String contactInfo;

    public SellerDTO(String name, String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
    }
}