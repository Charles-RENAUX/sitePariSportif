package ITI.projet.mpb.pojos;

public enum BetSortable {
    DATE("DATE","date_match"),
    DATEODD("DATEO","date_odd"),
    ID("ID","id_bet"),
    IDLEAGUE("IDL","id_league");

    private String jsSort;
    private String dbSort;

    BetSortable(String jsSort,String dbSort){
        this.jsSort=jsSort;
        this.dbSort=dbSort;
    }

    public String getDbSort() {
        return dbSort;
    }

    public String getJsSort() {
        return jsSort;
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
