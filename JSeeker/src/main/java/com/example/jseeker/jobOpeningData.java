package com.example.jseeker;

import java.util.Date;

public class jobOpeningData {
    private Integer ID;
    private String Combany_Name;
    private String Jobtitle;
    private String Description;
    private String Type;
    private Double Salary;
    private Date Deadline;

    public  jobOpeningData(Integer id,String c_name,String title,String des,String type,Double salary,Date deadline){
        this.ID=id;
        this.Combany_Name=c_name;
        this.Jobtitle=title;
        this.Type=type;
        this.Description =des;
        this.Deadline=deadline;
        this.Salary=salary;
    }

    public Integer getId(){
        return this.ID;
    }
    public String getCombany_Name(){
        return this.Combany_Name;
    }
    public String getJobtitle(){
        return this.Jobtitle;
    }
    public String getType(){
        return this.Type;
    }
    public String getDescription(){
        return this.Description;
    }
    public Date getDeadline(){
        return Deadline;
    }
    public Double getSalary(){
        return this.Salary;
    }


}
