package edu.illinois.cs465.myquizapp;

public class Game {
    public String date;
    public String myTeamName;
    public String opponentName;
    public String myTeamScore;
    public String opponentScore;
    public Game(String date, String myTeamName, String opponentName, String myTeamScore, String opponentScore ) {
        this.date = date;
        this.myTeamName = myTeamName;
        this.opponentName = opponentName;
        this.myTeamScore = myTeamScore;
        this.opponentScore = opponentScore;
    }
}
