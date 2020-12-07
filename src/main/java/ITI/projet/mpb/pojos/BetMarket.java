package ITI.projet.mpb.pojos;

public enum BetMarket {
    HNA("1X2",3),
    AHh("AHh",2),
    AHa("AHa",2),
    OVER("OV",2),
    UNDER("UD",2);


    private String market;
    private Integer nbOdd;

    BetMarket(String market, Integer nbOdd) {
        this.market = market;
        this.nbOdd = nbOdd;
    }

    public String getMarket() {
        return market;
    }

    public Integer getNbOdd() {
        return nbOdd;
    }

    public static Integer getComparator(String market) {
        for (BetMarket BetMarket : BetMarket.values()){
            if(BetMarket.market.equals(market)){
                return BetMarket.nbOdd;
            }
        }
        throw new IllegalArgumentException(String.format("The market %s is unknown",market));
    }

    @Override
    public String toString() {
        return "BetMarket{" +
                "market='" + market + '\'' +
                ", nbOdd=" + nbOdd +
                '}';
    }
}
