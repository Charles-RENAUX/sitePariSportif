package ITI.projet.mpb.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class BetDto {

    private Integer id;
    private Integer idLeague;
    private String market;
    private String marketB;
    private String league;
    private  String dateMatch;
    private String teamH;
    private String teamA;
    private Double odd1;
    private Double odd2;
    private Double odd3;
    private String dateOdd;

    @JsonCreator
    public BetDto(@JsonProperty(value="id",required = true)Integer id,
                  @JsonProperty(value="idLeague",required = true)Integer idLeague,
                  @JsonProperty(value="league",required = true)String league,
                  @JsonProperty(value="dateMatch",required = true)String date_match,
                  @JsonProperty(value="teamH",required = true)String teamH,
                  @JsonProperty(value="teamA",required = true)String teamA,
                  @JsonProperty(value="market",required = true)String market,
                  @JsonProperty(value="marketB",required = true)String marketB,
                  @JsonProperty(value="odd1",required = true)Double odd1,
                  @JsonProperty(value="odd2",required = true)Double odd2,
                  @JsonProperty(value="odd3",required = true)Double odd3,
                  @JsonProperty(value="dateOdd",required = true)String date_odd) {
        this.id = id;
        this.idLeague=idLeague;
        this.market = market;
        this.marketB = marketB;
        this.league = league;
        this.dateMatch = date_match;
        this.teamH = teamH;
        this.teamA = teamA;
        this.odd1 = odd1;
        this.odd2 = odd2;
        this.odd3 = odd3;
        this.dateOdd = date_odd;
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

    public String getMarketB() {
        return marketB;
    }

    public void setMarketB(String marketB) {
        this.marketB = marketB;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getDateMatch() {
        return dateMatch;
    }

    public void setDateMatch(String dateMatch) {
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

    public String getDateOdd() {
        return dateOdd;
    }

    public void setDateOdd(String dateOdd) {
        this.dateOdd = dateOdd;
    }

    public void setIdLeague(Integer idLeague) {
        this.idLeague = idLeague;
    }

    public Integer getIdLeague() {
        return idLeague;
    }

    public String date_to_str(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return date.format(formatter);
    }


    @Override
    public String toString() {
        return "BetDto{" +
                "id=" + id +
                ", idLeague=" + idLeague +
                ", market='" + market + '\'' +
                ", marketB='" + marketB + '\'' +
                ", league='" + league + '\'' +
                ", date_match='" + dateMatch + '\'' +
                ", teamH='" + teamH + '\'' +
                ", teamA='" + teamA + '\'' +
                ", odd1=" + odd1 +
                ", odd2=" + odd2 +
                ", odd3=" + odd3 +
                ", date_odd='" + dateOdd + '\'' +
                '}';
    }
}

