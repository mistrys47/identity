package com.example.identity;

public class allowed_web_info {
    public String website_name;
    public String requested_details;

    public String getWebsite_name() {
        return website_name;
    }

    public String getRequested_details() {
        return requested_details;
    }

    public allowed_web_info(String website_name, String requested_details) {
        this.website_name = website_name;
        this.requested_details = requested_details;
    }
}
