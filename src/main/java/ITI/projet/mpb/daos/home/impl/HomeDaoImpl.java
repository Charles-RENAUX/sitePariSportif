package ITI.projet.mpb.daos.home.impl;

import ITI.projet.mpb.daos.DataSourceProvider;
import ITI.projet.mpb.daos.home.HomeDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeDaoImpl implements HomeDao {

    public String getBookmakerName(String nameBook){
        String resName="";
        String sql="SELECT * FROM bookmakers WHERE name=?";
        try { Connection cnx = DataSourceProvider.getDataSource().getConnection();
                PreparedStatement stmt=cnx.prepareStatement(sql);
                stmt.setString(1,nameBook);
                ResultSet rs= stmt.executeQuery();
                if(rs.next()){
                    resName=rs.getString("name");

                }
            }
        catch (SQLException e){
            e.printStackTrace();
        }
        return resName;
    }
    public List <String> getBeginnerName(String nameBeginner){
        List<String> resMap=new ArrayList<>();
        String sql="SELECT * FROM beginners WHERE name=?";
        try { Connection cnx = DataSourceProvider.getDataSource().getConnection();
            PreparedStatement stmt=cnx.prepareStatement(sql);
            stmt.setString(1,nameBeginner);
            ResultSet rs= stmt.executeQuery();
            if(rs.next()){
                resMap.add(rs.getString("tym"));
                resMap.add(rs.getString("image"));
                resMap.add(rs.getString("title"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return resMap;
    }
}
