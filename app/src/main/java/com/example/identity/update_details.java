package com.example.identity;

public class update_details {
    String field_name;
    String value;
    String expiry_date;

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public update_details(String field_name, String value, String expiry_date) {
        this.field_name = field_name;
        this.value = value;
        this.expiry_date = expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }
}
