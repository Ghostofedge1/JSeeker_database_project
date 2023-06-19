package com.example.jseeker;

import java.util.Date;

public class ApplicationStatusClass {

    public  Integer M_ID;
    public Integer Jobopening_ID;
    public String App_status;
    public Date Sent_Date;
    private String Combany_Name;
    private String Jobtitle;
    private String Description;
    private String Type;


    public ApplicationStatusClass( Integer M_ID,Integer Jobopening_ID,String App_status,Date Sent_Date,String Combany_Name,String Jobtitle,String Description,String Type){
        this.M_ID=M_ID;
        this.Jobopening_ID=Jobopening_ID;
        this.App_status=App_status;
        this.Sent_Date=Sent_Date;
        this.Combany_Name=Combany_Name;
        this.Jobtitle=Jobtitle;
        this.Description=Description;
        this.Type=Type;

    }


    public  Integer getM_ID(){
        return this.M_ID;
    }
    public  Integer getJobopening_ID(){
        return this.Jobopening_ID;
    }
    public String getApp_status(){
        return this.App_status;
    }
    public Date getSent_Date(){
        return this.Sent_Date;
    }
    public String getCombany_Name(){
        return this.Combany_Name;
    }
    public String getJobtitle(){
        return this.Jobtitle;
    }
    public String getDescription(){
        return this.Description;
    }
    public String getType(){
        return this.Type;
    }
}
