package ITI.projet.mpb;

import ITI.projet.mpb.daos.BetDao;
import ITI.projet.mpb.daos.DataSourceProvider;
import ITI.projet.mpb.daos.impl.BetDaoImpl;
import ITI.projet.mpb.pojos.Bet;

import static ITI.projet.mpb.services.MotDePasseServiceHash.validerMotDePasse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;



public class BetDaoTestCase {

    private BetDao betDao = new BetDaoImpl();

    @Before
    public void initDb() throws Exception {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {

            stmt.executeUpdate("DELETE FROM odds");
            stmt.executeUpdate("DELETE FROM bets");
            stmt.executeUpdate("ALTER TABLE odds AUTO_INCREMENT = 1");
            stmt.executeUpdate("ALTER TABLE bets AUTO_INCREMENT = 1");
            stmt.executeUpdate("INSERT INTO bets(id_league,league,date_match,teamH,teamA) VALUES (771,'FRA1',1607806800,'Lens','Montpellier')");
            stmt.executeUpdate("INSERT INTO bets(id_league,league,date_match,teamH,teamA) VALUES (1981,'ENG1',1607880600,'Fulham','Liverpool')");
            stmt.executeUpdate("INSERT INTO bets(id_league,league,date_match,teamH,teamA) VALUES (771,'FRA1',1607878800,'Lille','Bordeaux')");

            stmt.executeUpdate("INSERT INTO odds(id_bet,market,marketB,odd1,odd2,odd3,date_odd) VALUES (" +
                    "(SELECT id_bet FROM bets WHERE (date_match=1607806800 AND teamH='Lens')),'AHh','+0.00',1.85,2.05,null,1607369400)");
            stmt.executeUpdate("INSERT INTO odds(id_bet,market,marketB,odd1,odd2,odd3,date_odd) VALUES (" +
                    "(SELECT id_bet FROM bets WHERE (date_match=1607880600 AND teamH='Fulham')),'HNA',null,8,5,1.36,1607369400)");
            stmt.executeUpdate("INSERT INTO odds(id_bet,market,marketB,odd1,odd2,odd3,date_odd) VALUES (" +
                    "(SELECT id_bet FROM bets WHERE (date_match=1607878800 AND teamH='Lille')),'OV','1.5',1.28,2.45,null,1607369400)");
        }
    }

    @Test
    public void shouldTsToDate() {
        //CGIVEN
        Long ts = Long.valueOf(1607806800);
        LocalDateTime dateC = LocalDateTime.of(2020, 12, 12, 21, 0);
        //WHEN
        LocalDateTime date = betDao.ts_to_date(ts);
        //THEN
        assertThat(date).isEqualTo(dateC);
    }


    @Test
    public void shouldListAll() {
        //WHEN
        List<Bet> listAll = betDao.listAll();
        //THEN
        assertThat(listAll).hasSize(3);
        assertThat(listAll).extracting(
                Bet::getId,
                Bet::getIdLeague,
                Bet::getLeague,
                Bet::getDateMatch,
                Bet::getTeamH,
                Bet::getTeamA,
                Bet::getMarket,
                Bet::getMarketB,
                Bet::getOdd1,
                Bet::getOdd2,
                Bet::getOdd3,
                Bet::getDateOdd
        );
    }

    @Test
    public void shouldListBySort() {
        //WHEN
        List<Bet> listSort = betDao.listBySort("date_match");
        //THEN
        assertThat(listSort).hasSize(3);
        assertThat(listSort).extracting(
                Bet::getId,
                Bet::getIdLeague,
                Bet::getLeague,
                Bet::getDateMatch,
                Bet::getTeamH,
                Bet::getTeamA,
                Bet::getMarket,
                Bet::getMarketB,
                Bet::getOdd1,
                Bet::getOdd2,
                Bet::getOdd3,
                Bet::getDateOdd
        );
        assertThat(listSort.get(0).getDateMatch()).isEqualTo(LocalDateTime.of(2020, 12, 12, 21, 0));
    }

    @Test
    public void shouldGetBet() {
        //WHEN
        Bet bet = betDao.getBet(2);
        //THEN
        assertThat(bet).isNotNull();
        assertThat(bet.getId()).isEqualTo(2);
        assertThat(bet.getIdLeague()).isEqualTo(1981);
        assertThat(bet.getLeague()).isEqualTo("ENG1");
        assertThat(bet.getDateMatch()).isEqualTo(LocalDateTime.of(2020, 12, 13, 17, 30));
        assertThat(bet.getTeamH()).isEqualTo("Fulham");
        assertThat(bet.getTeamA()).isEqualTo("Liverpool");
        assertThat(bet.getMarket()).isEqualTo("HNA");
        assertThat(bet.getMarketB()).isNull();
        assertThat(bet.getOdd1()).isEqualTo(8);
        assertThat(bet.getOdd2()).isEqualTo(5);
        assertThat(bet.getOdd3()).isEqualTo(1.36);
        assertThat(bet.getDateOdd()).isEqualTo(LocalDateTime.of(2020, 12, 7, 19, 30));
    }

    @Test
    public void shouldDeleteBet() throws Exception {
        //GIVEN
        Integer idBet = 3;
        //WHEN
        betDao.deleteBet(3);
        //THEN
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM odds INNER JOIN bets ON bets.id_bet=odds.id_bet WHERE odds.id_bet = ?")) {
            stmt.setInt(1, idBet);
            try (ResultSet rs = stmt.executeQuery()) {
                assertThat(rs.next()).isFalse();
            }
        }
    }

    @Test
    public void shouldAddBet() throws Exception {
        //GIVEN
        LocalDateTime dateMatch = LocalDateTime.of(2020, 12, 12, 15, 30);
        LocalDateTime dateBet = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        Bet betToAdd = new Bet(4, 811, "GER1", dateMatch, "Dortmund", "Stuttgart", "HNA", null, 1.45, 4.5, 6.5, dateBet);
        //WHEN
        betDao.addBet(betToAdd);
        //THEN
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM odds INNER JOIN bets ON bets.id_bet=odds.id_bet WHERE (bets.date_match=? AND bets.teamH=?)")) {
            stmt.setDouble(1, betDao.date_to_ts(betToAdd.getDateMatch()));
            stmt.setString(2, betToAdd.getTeamH());
            try (ResultSet rs = stmt.executeQuery()) {
                assertThat(rs.next()).isTrue();
                assertThat( rs.getInt("id_bet")).isEqualTo(4);
                assertThat( rs.getInt("id_league")).isEqualTo(811);
                assertThat(rs.getString("league")).isEqualTo("GER1");
                assertThat(betDao.ts_to_date(rs.getLong("date_match"))).isEqualTo(dateMatch);
                assertThat(rs.getString("teamH")).isEqualTo("Dortmund");
                assertThat(rs.getString("teamA")).isEqualTo("Stuttgart");
                assertThat(rs.getString("market")).isEqualTo("HNA");
                assertThat(rs.getString("marketB")).isNull();
                assertThat(rs.getDouble("odd1")).isEqualTo(1.45);
                assertThat(rs.getDouble("odd2")).isEqualTo(4.5);
                assertThat(rs.getDouble("odd3")).isEqualTo(6.5);
                assertThat(betDao.ts_to_date(rs.getLong("date_odd"))).isEqualTo(dateBet);
                assertThat(rs.next()).isFalse();
            }
        }
    }
}