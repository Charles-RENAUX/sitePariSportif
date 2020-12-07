package ITI.projet.mpb.pojos;

public enum BetFilter {
    MARKET("MARKET","market"),
    LEAGUE("LEAGUE","league"),
    TEAM("TEAM","team");

    private String jsFilter;
    private String filter;

    BetFilter(String jsSort,String dbSort){
        this.jsFilter =jsFilter;
        this.filter =dbSort;
    }

    public String getFilter() {
        return filter;
    }

    public String getJsFilter() {
        return jsFilter;
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
