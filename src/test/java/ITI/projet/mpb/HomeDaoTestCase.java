package ITI.projet.mpb;

import ITI.projet.mpb.dao.DataSourceProvider;
import ITI.projet.mpb.dao.home.HomeDao;
import ITI.projet.mpb.dao.home.impl.HomeDaoImpl;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

public class HomeDaoTestCase {

    HomeDao homeDao= new HomeDaoImpl();
    @Before
    public void initDb() throws Exception{
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("INSERT INTO `beginners` (`id_beginner`,`name`) VALUES (150,'test')");
            stmt.executeUpdate("INSERT INTO `bookmakers` (`id_book`,`name`,`type`) VALUES (150,'test','test')");
        }catch (Exception e){
            ;
        }
    }

    @Test
    public void shouldGetBeginner() throws Exception{
        //WHEN
        String resName=homeDao.getBeginnerName("test");
        //THEN
        assertThat(resName).isEqualTo("test");
    }

    @Test
    public void shouldGetBookmaker() throws Exception{
        //WHEN
        String resName=homeDao.getBookmakerName("test","test");
        //THEN
        assertThat(resName).isEqualTo("test");
    }


}