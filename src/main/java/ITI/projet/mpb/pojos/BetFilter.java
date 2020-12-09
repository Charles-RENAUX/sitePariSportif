package ITI.projet.mpb.pojos;

public enum BetFilter {
    MARKET("MARKET","market","odds"),
    LEAGUE("LEAGUE","league","bets"),
    TEAM("TEAM","team","bets");

    private String jsFilter;
    private String filter;
    private String tbName;


    BetFilter(String jsFilter,String dbSort,String tbName){
        this.jsFilter =jsFilter;
        this.filter =dbSort;
        this.tbName=tbName;
    }

    public String getFilter() {
        return filter;
    }

    public String getJsFilter() {
        return jsFilter;
    }

    public String getTbName() {
        return tbName;
    }

    public static String getTbName(String filter) {
        for (BetFilter BetFilter : BetFilter.values()){
            if(BetFilter.filter.equals(filter)){
                return BetFilter.tbName;
            }
        }
        throw new IllegalArgumentException(String.format("The JS Sortable  %s has no equivalent",filter));
    }

    public static String getComparator(String jsFilter) {
        for (BetFilter BetFilter : BetFilter.values()){
            if(BetFilter.jsFilter.equals(jsFilter)){
                return BetFilter.filter;
            }
        }
        throw new IllegalArgumentException(String.format("The JS Filter  %s has no equivalent",jsFilter));
    }

    @Override
    public String toString() {
        return "BetFilter{" +
                "jsSort='" + jsFilter + '\'' +
                ", dbSort='" + filter + '\'' +
                '}';
    }

}
