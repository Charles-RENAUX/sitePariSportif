package ITI.projet.mpb.services;

import ITI.projet.mpb.daos.home.HomeDao;
import ITI.projet.mpb.daos.home.impl.HomeDaoImpl;

import java.util.List;

public class HomeService {
    private static class HomeServiceHolder {
        private static HomeService instance = new HomeService();
    }

    public static HomeService getInstance() {
        return HomeServiceHolder.instance;
    }

    private HomeService() {
    }

    private HomeDao homeDao= new HomeDaoImpl();

    public String getBookmakerName(String nameBook){
        if (nameBook==null||"".equals(nameBook)){
            throw new IllegalArgumentException("The bookmaker must have a name");
        }
            return homeDao.getBookmakerName(nameBook);
        }

    public List<String> getBeginnerName(String nameBeginner){
        if (nameBeginner==null||"".equals(nameBeginner)){throw new IllegalArgumentException("The article must have a name");}
        return homeDao.getBeginnerName(nameBeginner);
    }

}
