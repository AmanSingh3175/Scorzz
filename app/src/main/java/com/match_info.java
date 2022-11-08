package com;

public class match_info {
    String id,team1,team2;
    String matchStatus,date,venue;

    public match_info(String id, String team1, String team2,String date,String venue,String matchStatus) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.date = date;
        this.venue = venue;
        this.matchStatus = matchStatus;

    }

    public String getId() {
        return id;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public String getDate() {
        return date;
    }

    public String getVenue() {
        return venue;
    }

    public String getMatchStatus() {
        return matchStatus;
    }
}
