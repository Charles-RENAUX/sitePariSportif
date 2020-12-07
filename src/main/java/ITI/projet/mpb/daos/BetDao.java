package ITI.projet.mpb.daos;

import ITI.projet.mpb.pojos.Bet;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface BetDao {


    public List<Bet> listAll();
    public List<Bet> listBySort(String sort);
    public List<Bet> listByFilter(String filter,String name);
    public List<Bet> listByPair(String sort,String filter,String name);

    public Bet getBet(Integer idBet);

    public void addBet(Bet bet);
    public void editBet(Bet bet);
    public void deleteBet(Integer idBet);

    public LocalDateTime ts_to_date(Long timestamp);
    public Long date_to_ts(LocalDateTime date);

}
