package ITI.projet.mpb.services;

import ITI.projet.mpb.daos.BetDao;
import ITI.projet.mpb.daos.impl.BetDaoImpl;
import ITI.projet.mpb.pojos.*;

import java.util.List;

public class BetService {

    private BetService() {
    }

    private static class clientLibraryHolder {
        private final static BetService instance = new BetService();
    }

    public static BetService getInstance() {
        return BetService.clientLibraryHolder.instance;
    }

    private final BetDao betDao = new BetDaoImpl();

    public List<BetDto> listAll(){
        return betDao.listAll();
    }
    public List<BetDto> listBySort(String jsSort){
        if (jsSort==null||"".equals(jsSort)) {throw new IllegalArgumentException("The jsSort can't be null");}
        String sort= BetSortable.getComparator(jsSort);
        String tbname=BetSortable.getTbName(sort);
        return betDao.listBySort(sort,tbname);
    }
    public List<BetDto> listByFilter(String jsFilter,String name){
        if (jsFilter==null||"".equals(jsFilter)) {throw new IllegalArgumentException("The jsFilter can't be null");}
        if (name==null||"".equals(name)) {throw new IllegalArgumentException("The name can't be null");}
        String filter= BetFilter.getComparator(jsFilter);
        String tbname=BetFilter.getTbName(filter);
        return betDao.listByFilter(filter,name,tbname);
    }
    public List<BetDto> listByPair(String jsSort,String jsFilter,String name){
        if (jsSort==null||"".equals(jsSort)) {throw new IllegalArgumentException("The jsSort can't be null");}
        String sort= BetSortable.getComparator(jsSort);
        if (jsFilter==null||"".equals(jsFilter)) {throw new IllegalArgumentException("The jsFilter can't be null");}
        if (name==null||"".equals(name)) {throw new IllegalArgumentException("The name can't be null");}
        String filter= BetFilter.getComparator(jsFilter);
        String tbname1=BetSortable.getTbName(sort);
        String tbname2=BetFilter.getTbName(filter);
        return betDao.listByPair(sort,filter,name,tbname1,tbname2);
    }

    public BetDto getBet(Integer idBet){
        if(idBet<1) throw new IllegalArgumentException("The idBet can't be null or negative");
        return betDao.getBet(idBet);
    }

    public void addBet(Bet bet) {
        bet=checkBet(bet);
        betDao.addBet(bet);
    }
    public void editBet(Bet bet){
        if (bet.getId()<1){throw new IllegalArgumentException("The id can't be null or negative");}
        bet=checkBet(bet);
        betDao.editBet(bet);
    }

    public Bet checkBet(Bet bet){
        if (bet.getDateMatch()==null){throw new IllegalArgumentException("The dateMatch can't be null");}
        if (bet.getMarket()==null||"".equals(bet.getMarket())){throw new IllegalArgumentException("The market can't be null");}
        if (bet.getIdLeague()<1){throw new IllegalArgumentException("The idLeague can't be null or negative");}
        if (bet.getLeague()==null||"".equals(bet.getLeague())){throw new IllegalArgumentException("The league can't be negative");}
        if (bet.getDateOdd()==null){throw new IllegalArgumentException("The dateOdd can't be null");}
        if (bet.getOdd1()<1){throw new IllegalArgumentException("The odd1 has to be over 1");}
        if (bet.getOdd2()<1){throw new IllegalArgumentException("The odd2 has to be over 1");}
        if(bet.getOdd3()<1&&bet.getOdd3()!=0.0){throw new IllegalArgumentException("The odd3 has to be over 1");}
        if (bet.getTeamA()==null||"".equals(bet.getTeamA())){throw new IllegalArgumentException("The teamA can't be null");}
        if (bet.getTeamH()==null||"".equals(bet.getTeamH())){throw new IllegalArgumentException("The teamH can't be null");}
        try{if("".equals(bet.getMarketB())){bet.setMarketB(null);}}catch (NullPointerException e) {}
        return bet;
    }


    public void deleteBet(Integer idBet){
        if(idBet<1){ throw new IllegalArgumentException("The idBet can't be null or negative");}
        betDao.deleteBet(idBet);
    }



}
