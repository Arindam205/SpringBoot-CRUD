package com.Test1RorApplication.RORApplicationTesting.DTO;

import lombok.Data;

@Data
public enum SearchRequestType {
    ROR_ID("ror_id"),
    RATION_CARD_NUMBER("ration_card_number");

    public final String val;

    SearchRequestType(String val) {
        this.val = val;
    }
}
