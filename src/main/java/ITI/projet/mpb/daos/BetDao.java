package ITI.projet.mpb.daos;

import ITI.projet.mpb.pojos.Bet;
import ITI.projet.mpb.pojos.BetDto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface BetDao {


    public List<BetDto> listAll();
    public List<BetDto> listBySort(String sort,String tbname);
    public List<BetDto> listByFilter(String filter,String name,String tbname);
    public List<BetDto> listByPair(String sort,String filter,String name,String tbname1,String tbname2);

    public BetDto getBet(Integer idBet);

    public void addBet(Bet bet);
    public void editBet(Bet bet);
    public void deleteBet(Integer idBet);

    public LocalDateTime ts_to_date(Long timestamp);
    public Long date_to_ts(LocalDateTime date);
    public String date_to_str(LocalDateTime date);

}
