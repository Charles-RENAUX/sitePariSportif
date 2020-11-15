package ITI.projet.mpb.services;

import ITI.projet.mpb.daos.home.HomeDao;
import ITI.projet.mpb.daos.home.impl.HomeDaoImpl;

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

    public String getBookmakerName(Integer idBook,String typeBook){

        if (typeBook==null||"".equals(typeBook)){
            throw new IllegalArgumentException("You must choose a categorie for the book");
        }
        if (idBook==null){
            throw new IllegalArgumentException("The id can't be Null");
        }
            return homeDao.getBookmakerName(idBook,typeBook);
        }

    public String getBeginnerName(Integer idBeginner){
        if (idBeginner==null){throw new IllegalArgumentException("The id can't be null");}
        return homeDao.getBeginnerName(idBeginner);
    }

}
