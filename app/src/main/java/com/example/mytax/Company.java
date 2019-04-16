package com.example.mytax;

public class Company {
    private String companyName ;
    private String expectedTax;
    private String actualTax;
    private String salary;

    public Company(String companyName, String salary, String expectedTax,String actualTax) {
        this.companyName = companyName;
        this.salary = expectedTax;
        this.expectedTax = expectedTax;
        this.actualTax =actualTax ;
    }

    public Company(){
    }



    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getExpectedTax() {
        return expectedTax;
    }

    public void setExpectedTax(String expectedTax) {
        this.expectedTax = expectedTax;
    }

    public String getActualTax() {
        return actualTax;
    }

    public void setActualTax(String actualTax) {
        this.actualTax = actualTax;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary= salary;
    }



}
