package com.example.mytax;

public class Company {
    private String companyName ;
    private int expectedTax;

    public Company(String companyName, int expectedTax, int actualTax, int difference) {
        this.companyName = companyName;
        this.expectedTax = expectedTax;
        this.actualTax = actualTax;
        this.difference = difference;
    }

    public Company(){
    }

    public Company(String companyName, int expectedTax, int actualTax) {
        this.companyName = companyName;
        this.expectedTax = expectedTax;
        this.actualTax = actualTax;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getExpectedTax() {
        return expectedTax;
    }

    public void setExpectedTax(int expectedTax) {
        this.expectedTax = expectedTax;
    }

    public int getActualTax() {
        return actualTax;
    }

    public void setActualTax(int actualTax) {
        this.actualTax = actualTax;
    }

    public int getDifference() {
        return difference;
    }

    public void setDifference(int difference) {
        this.difference = difference;
    }

    private int actualTax;
    private int difference;

}
