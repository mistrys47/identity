package com.example.identity;

public class verifier_info {
    String name;
    String url;
    public void setName1(String s)
    {
        this.name=s;
    }
    public void setUrl1(String s)
    {
        this.url=s;
    }
    public String getUrl1()
    {
        return this.url;
    }
    public String getName1()
    {
        return this.name;
    }

    public verifier_info(String name, String url) {
        this.name = name;
        this.url = url;
    }
}

