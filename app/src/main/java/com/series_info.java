package com;

public class series_info {
    String id,name,StartDate,EndDate,ODIs,T20s,TESTS;

    public series_info(String id, String name, String startDate, String endDate, String ODIs, String t20s, String TESTS) {
        this.id = id;
        this.name = name;
        StartDate = startDate;
        EndDate = endDate;
        this.ODIs = ODIs;
        T20s = t20s;
        this.TESTS = TESTS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getODIs() {
        return ODIs;
    }

    public void setODIs(String ODIs) {
        this.ODIs = ODIs;
    }

    public String getT20s() {
        return T20s;
    }

    public void setT20s(String t20s) {
        T20s = t20s;
    }

    public String getTESTS() {
        return TESTS;
    }

    public void setTESTS(String TESTS) {
        this.TESTS = TESTS;
    }
}
