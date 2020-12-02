package ITI.projet.mpb;

import ITI.projet.mpb.daos.DataSourceProvider;
import ITI.projet.mpb.daos.HomeDao;
import ITI.projet.mpb.daos.impl.HomeDaoImpl;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class HomeDaoTestCase {

    HomeDao homeDao= new HomeDaoImpl();
    @Before
    public void initDb() throws Exception{
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("INSERT INTO `beginners` (`id_beginner`,`name`,`tym`,`image`,`title`) VALUES (0,'test','test',0,'test')");
            stmt.executeUpdate("INSERT INTO `bookmakers` (`id_bookmaker`,`name`) VALUES (0,'test')");
        }catch (SQLIntegrityConstraintViolationException e1){}
    }

    @Test
    public void shouldGetBookmaker() throws Exception{
        //WHEN
        String resName=homeDao.getBookmakerName("test");
        //THEN
        assertThat(resName).isEqualTo("test");


    }

    @Test
    public void shouldGetBeginner() throws Exception{
        //WHEN
        List<String> resMap=homeDao.getBeginnerName("test");
        //THEN
        assertThat(resMap.get(1)).isEqualTo("0");
        assertThat(resMap.get(0)).isEqualTo("test");
        assertThat(resMap.get(2)).isEqualTo("test");
    }


}