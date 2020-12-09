package ITI.projet.mpb.daos.impl;

import ITI.projet.mpb.daos.BetDao;
import ITI.projet.mpb.daos.DataSourceProvider;
import ITI.projet.mpb.exceptions.BetAlreadyException;
import ITI.projet.mpb.exceptions.BetNotFoundException;
import ITI.projet.mpb.pojos.Bet;
import ITI.projet.mpb.pojos.BetDto;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BetDaoImpl implements BetDao {


    @Override
    public List<BetDto> listAll() {
        List<BetDto> result= new ArrayList<BetDto>();
        try {
            DataSource dataSource = DataSourceProvider.getDataSource();
            try (Connection cnx = dataSource.getConnection();
                 Statement statement = cnx.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM bets INNER JOIN odds ON bets.id_bet=odds.id_bet ")) {
                while(resultSet.next()) {
                    result.add(createBetDtoFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<BetDto> listBySort(String sort,String tbname) {
        List<BetDto> result= new ArrayList<BetDto>();
        String sql="SELECT * FROM bets INNER JOIN odds ON bets.id_bet=odds.id_bet ORDER BY "+tbname+"."+sort;
        try {
            DataSource dataSource = DataSourceProvider.getDataSource();
            try (Connection cnx = dataSource.getConnection();
                 PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
                try(ResultSet rs = preparedStatement.executeQuery()) {
                    while(rs.next()) {
                        result.add(createBetDtoFromResultSet(rs));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<BetDto> listByFilter(String filter,String name,String tbname) {
        List<BetDto> result= new ArrayList<BetDto>();
        String sql="SELECT * FROM bets INNER JOIN odds ON bets.id_bet=odds.id_bet WHERE "+tbname+"."+filter+"="+str_query(name);
        try {
            DataSource dataSource = DataSourceProvider.getDataSource();
            try (Connection cnx = dataSource.getConnection();
                 PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
                try(ResultSet rs = preparedStatement.executeQuery()) {
                    while(rs.next()) {
                        result.add(createBetDtoFromResultSet(rs));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<BetDto> listByPair(String sort, String filter,String name,String tbname1,String tbname2) {
        List<BetDto> result= new ArrayList<BetDto>();
        String sql="SELECT * FROM bets INNER JOIN odds ON bets.id_bet=odds.id_bet WHERE "+tbname2+"."+filter+"="+str_query(name)+"ORDER BY "+tbname1+"."+sort;
        try {
            DataSource dataSource = DataSourceProvider.getDataSource();
            try (Connection cnx = dataSource.getConnection();
                 PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
                try(ResultSet rs = preparedStatement.executeQuery()) {
                    while(rs.next()) {
                        result.add(createBetDtoFromResultSet(rs));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public BetDto getBet(Integer idBet) {
        BetDto bet=null;
        String sql="SELECT * FROM bets INNER JOIN odds ON bets.id_bet=odds.id_bet WHERE bets.id_bet=?";
        try {
            DataSource dataSource = DataSourceProvider.getDataSource();
            try (Connection cnx = dataSource.getConnection();
                 PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
                preparedStatement.setInt(1, idBet);
                try(ResultSet rs = preparedStatement.executeQuery()) {
                    if(rs.next()) {
                        bet = createBetDtoFromResultSet(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            throw new BetNotFoundException("The bet doesn't exist");
        }
        return bet;
    }


    private BetDto createBetDtoFromResultSet(ResultSet rs) throws SQLException
    {
        return new BetDto(
                rs.getInt("id_bet"),
                rs.getInt("id_league"),
                rs.getString("league"),
                date_to_str(ts_to_date(rs.getLong("date_match"))),
                rs.getString("teamH"),
                rs.getString("teamA"),
                rs.getString("market"),
                rs.getString("marketB"),
                rs.getDouble("odd1"),
                rs.getDouble("odd2"),
                rs.getDouble("odd3"),
                date_to_str(ts_to_date(rs.getLong("date_odd"))));
    }

    @Override
    public LocalDateTime ts_to_date(Long timestamp){
        //Correction pour éviter une heure de décalage
        timestamp-=3600;
        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.of("Europe/Paris"));

        return date;
    }
    @Override
    public Long date_to_ts(LocalDateTime date){
        return TimeUnit.MILLISECONDS.
                toSeconds(Timestamp.valueOf(date).getTime())+3600;
    }

    @Override
    public String date_to_str(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return date.format(formatter);
    }

    public String str_query(String name){
        return "'"+name+"'";}

    @Override
    public void addBet(Bet bet) {
        String sql1 = "INSERT INTO bets (id_league,league,date_match,teamH,teamA) VALUES (?, ?, ?, ?, ?)";
        String sql2 = "INSERT INTO odds (id_bet,market,marketB,odd1,odd2,odd3,date_odd) VALUES " +
                "((SELECT id_bet FROM bets WHERE (date_match=? AND teamH=?)),?,?,?,?,?,?)";
        try {
            DataSource dataSource = DataSourceProvider.getDataSource();
            try (Connection cnx = dataSource.getConnection();
                 PreparedStatement preparedStatement1 = cnx.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS))
            {
                preparedStatement1.setInt(1,bet.getIdLeague());
                preparedStatement1.setString(2,bet.getLeague());
                preparedStatement1.setLong(3, date_to_ts(bet.getDateMatch()));
                preparedStatement1.setString(4, bet.getTeamH());
                preparedStatement1.setString(5,bet.getTeamA());
                preparedStatement1.executeUpdate();
                ResultSet ids = preparedStatement1.getGeneratedKeys();
                if (ids.next()) {
                }
            }
        }catch (SQLIntegrityConstraintViolationException e){
            throw new BetAlreadyException("The Bet already exist");
        }catch (SQLException e){
            e.printStackTrace();
        }
        try {
            DataSource dataSource = DataSourceProvider.getDataSource();
            try (Connection cnx = dataSource.getConnection();
                 PreparedStatement preparedStatement2 = cnx.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS))
            {
                preparedStatement2.setLong(1,date_to_ts(bet.getDateMatch()));
                preparedStatement2.setString(2,bet.getTeamH());
                preparedStatement2.setString(3,bet.getMarket());
                preparedStatement2.setString(4,bet.getMarketB());
                preparedStatement2.setDouble(5,bet.getOdd1());
                preparedStatement2.setDouble(6,bet.getOdd2());
                preparedStatement2.setDouble(7,bet.getOdd3());
                preparedStatement2.setLong(8, date_to_ts(bet.getDateOdd()));
                preparedStatement2.executeUpdate();
                ResultSet ids = preparedStatement2.getGeneratedKeys();
                if (ids.next()) {
                }
            }
        }catch (SQLIntegrityConstraintViolationException e){
            //throw new BetAlreadyException("The Odd already exist f");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public void editBet(Bet bet) {
        String sql="UPDATE odds,bets SET bets.id_league=?,bets.league=?,bets.date_match=?,bets.teamH=?,bets.teamA=?,"+
                "odds.market=?,odds.marketB=?,odds.odd1=?,odds.odd2=?,odds.odd3=?,odds.date_odd=? WHERE ((bets.id_bet=?) AND (odds.id_bet=?))";
        try {
            DataSource dataSource = DataSourceProvider.getDataSource();
            try (Connection cnx = dataSource.getConnection();
                 PreparedStatement preparedStatement2 = cnx.prepareStatement(sql))
            {
                preparedStatement2.setInt(1,bet.getIdLeague());
                preparedStatement2.setString(2, bet.getLeague());
                preparedStatement2.setLong(3,date_to_ts(bet.getDateMatch()));
                preparedStatement2.setString(4, bet.getTeamH());
                preparedStatement2.setString(5,bet.getTeamA());
                preparedStatement2.setString(6,bet.getMarket());
                preparedStatement2.setString(7, bet.getMarketB());
                preparedStatement2.setDouble(8,bet.getOdd1());
                preparedStatement2.setDouble(9,bet.getOdd2());
                preparedStatement2.setDouble(10,bet.getOdd3());
                preparedStatement2.setLong(11,date_to_ts(bet.getDateOdd()));
                preparedStatement2.setInt(12,bet.getId());
                preparedStatement2.setInt(13,bet.getId());

                preparedStatement2.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }catch (NullPointerException e) {
            throw new BetNotFoundException("The Bet doesn't exists");
        }

    }
    /*
    @Override
    public void editBet(Bet bet) {
        String sql="UPDATE odds,bets SET bets.id_league=?,bets.league=?,bets.date_match=?,bets.teamH=?,bets.teamA=?" +
                "odds.market=?,odds.marketB=?,odds.odd1=?,odds.odd2=?,odds.odd3=?,odds.date_odd=? WHERE (odds.id_bet=bets.id_bet AND odds.id_bet=?)";
        try {
            DataSource dataSource = DataSourceProvider.getDataSource();
            try (Connection cnx = dataSource.getConnection();
                 PreparedStatement preparedStatement2 = cnx.prepareStatement(sql))
            {
                preparedStatement2.setInt(1,bet.getIdLeague());
                preparedStatement2.setString(2,bet.getLeague());
                preparedStatement2.setLong(3,date_to_ts(bet.getDateMatch()));
                preparedStatement2.setString(4,bet.getTeamH());
                preparedStatement2.setString(5,bet.getTeamA());
                preparedStatement2.setString(6,bet.getMarket());
                preparedStatement2.setString(7,bet.getMarketB());
                preparedStatement2.setDouble(8,bet.getOdd1());
                preparedStatement2.setDouble(9,bet.getOdd2());
                preparedStatement2.setDouble(10,bet.getOdd3());
                preparedStatement2.setLong(11, date_to_ts(bet.getDateOdd()));
                preparedStatement2.setInt(12,bet.getId());
                preparedStatement2.executeUpdate();
            }
                } catch (SQLException e){
            e.printStackTrace();
        }catch (NullPointerException e) {
            e.printStackTrace();
            //throw new BetNotFoundException("The Bet doesn't exists");
        }

    }*/

    @Override
    public void deleteBet(Integer idBet) {
        String sql="DELETE odds,bets FROM odds INNER JOIN bets ON bets.id_bet=odds.id_bet WHERE odds.id_bet=?";
        try{
            DataSource dataSource=DataSourceProvider.getDataSource();
            try(Connection cnx= dataSource.getConnection();
                PreparedStatement preparedStatement = cnx.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
                preparedStatement.setInt(1,idBet);
                preparedStatement.executeUpdate();
                ResultSet ids = preparedStatement.getGeneratedKeys();
                if (ids.next()) {
                }
            }
        } catch (NullPointerException e){
            throw new BetNotFoundException("The Bet doesn't exist");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
