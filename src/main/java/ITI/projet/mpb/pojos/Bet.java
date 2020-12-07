package ITI.projet.mpb.pojos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bet {

    private Integer id;
    private Integer idLeague;
    private String league;
    private LocalDateTime dateMatch;
    private String teamH;
    private String teamA;
    private String market;
    private String marketB;
    private Double odd1;
    private Double odd2;
    private Double odd3;
    private LocalDateTime dateOdd;

    public Bet(Integer id, Integer idLeague, String league, LocalDateTime dateMatch, String teamH, String teamA, String market, String marketB, Double odd1, Double odd2, Double odd3, LocalDateTime dateOdd) {
        this.id = id;
        this.idLeague = idLeague;
        this.league = league;
        this.dateMatch = dateMatch;
        this.teamH = teamH;
        this.teamA = teamA;
        this.market = market;
        this.marketB = marketB;
        this.odd1 = odd1;
        this.odd2 = odd2;
        this.odd3 = odd3;
        this.dateOdd = dateOdd;
    }

    public Bet(BetDto betDto){
        this.id= betDto.getId();
        this.idLeague= betDto.getIdLeague();
        this.league= betDto.getLeague();
        this.dateMatch=getDate(betDto.getDate_match());
        this.teamH= betDto.getTeamH();
        this.teamA=betDto.getTeamA();
        this.market= betDto.getMarket();
        this.marketB=betDto.getMarketB();
        this.odd1=betDto.getOdd1();
        this.odd2=betDto.getOdd2();
        this.odd3=betDto.getOdd3();
        this.dateOdd=getDate(betDto.getDate_odd());
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public LocalDateTime getDateMatch() {
        return dateMatch;
    }

    public void setDateMatch(LocalDateTime dateMatch) {
        this.dateMatch = dateMatch;
    }

    public String getTeamH() {
        return teamH;
    }

    public void setTeamH(String teamH) {
        this.teamH = teamH;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public Double getOdd1() {
        return odd1;
    }

    public void setOdd1(Double odd1) {
        this.odd1 = odd1;
    }

    public Double getOdd2() {
        return odd2;
    }

    public void setOdd2(Double odd2) {
        this.odd2 = odd2;
    }

    public Double getOdd3() {
        return odd3;
    }

    public void setOdd3(Double odd3) {
        this.odd3 = odd3;
    }

    public LocalDateTime getDateOdd() {
        return dateOdd;
    }

    public void setDateOdd(LocalDateTime dateOdd) {
        this.dateOdd = dateOdd;
    }

    public String getMarketB() {
        return marketB;
    }

    public void setMarketB(String marketB) {
        this.marketB = marketB;
    }

    public Integer getIdLeague() {
        return idLeague;
    }

    public void setIdLeague(Integer idLeague) {
        this.idLeague = idLeague;
    }

    public LocalDateTime getDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        return dateTime;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "id=" + id +
                ", idLeague=" + idLeague +
                ", league='" + league + '\'' +
                ", dateMatch=" + dateMatch +
                ", teamH='" + teamH + '\'' +
                ", teamA='" + teamA + '\'' +
                ", market='" + market + '\'' +
                ", marketB='" + marketB + '\'' +
                ", odd1=" + odd1 +
                ", odd2=" + odd2 +
                ", odd3=" + odd3 +
                ", dateOdd=" + dateOdd +
                '}';
    }
}
