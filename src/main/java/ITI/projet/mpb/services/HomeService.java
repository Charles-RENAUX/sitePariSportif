package ITI.projet.mpb.services;

public class HomeService {
    private static class HomeServiceHolder {
        private static HomeService instance = new HomeService();
    }

    public static HomeService getInstance() {
        return HomeServiceHolder.instance;
    }

    private HomeService() {
    }

    public String getBookmakerName(Integer idBook,String type){
        return "";
    }

    public String getBeginnerName(Integer idBeginner){
        return "";
    }

}
