package com.berk.odevberk;

public class putPDF {
    public String name;
    public String url;
    public String situation;


    public putPDF(){ }

    public putPDF(String name,String url,String situation)
    {
        this.name=name;
        this.url=url;
        this.situation=situation;

    }

    public String getSituation(){return situation;}

    public void setSituation(String situation){this.situation=situation;}

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
