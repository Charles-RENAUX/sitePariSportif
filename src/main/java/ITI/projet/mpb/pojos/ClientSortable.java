package ITI.projet.mpb.pojos;

import java.util.Comparator;

public enum ClientSortable {
    NOM("NOM","nom"),
    PRENOM("PRENOM","prenom"),
    PSEUDO("PSEUDO","pseudo"),
    ID("ID","id_client");

    private String jsSort;
    private String dbSort;

    ClientSortable(String jsSort,String dbSort){
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
        for (ClientSortable clientSortable : ClientSortable.values()){
            if(clientSortable.jsSort.equals(jsSort)){
                return clientSortable.dbSort;
            }
        }
        throw new IllegalArgumentException(String.format("The JS Sortable  %s has no equivalent"));
    }
}




