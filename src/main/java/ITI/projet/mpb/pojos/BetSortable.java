package ITI.projet.mpb.pojos;

public enum BetSortable {
    DATE("DATE","date_match","bets"),
    DATEODD("DATEO","date_odd","odds"),
    ID("ID","id_bet","bets"),
    IDLEAGUE("IDL","id_league","bets");

    private String jsSort;
    private String dbSort;
    private String tbName;

    BetSortable(String jsSort,String dbSort,String tbName){
        this.jsSort=jsSort;
        this.dbSort=dbSort;
        this.tbName=tbName;
    }

    public String getDbSort() {
        return dbSort;
    }

    public String getJsSort() {
        return jsSort;
    }

    public static String getTbName(String dbSort) {
        for (BetSortable BetSortable : BetSortable.values()){
            if(BetSortable.dbSort.equals(dbSort)){
                return BetSortable.tbName;
            }
        }
        throw new IllegalArgumentException(String.format("The JS Sortable  %s has no equivalent",dbSort));
    }

    public static String getComparator(String jsSort) {
        for (BetSortable BetSortable : BetSortable.values()){
            if(BetSortable.jsSort.equals(jsSort)){
                return BetSortable.dbSort;
            }
        }
        throw new IllegalArgumentException(String.format("The JS Sortable  %s has no equivalent",jsSort));
    }

    @Override
    public String toString() {
        return "BetSortable{" +
                "jsSort='" + jsSort + '\'' +
                ", dbSort='" + dbSort + '\'' +
                '}';
    }

}
