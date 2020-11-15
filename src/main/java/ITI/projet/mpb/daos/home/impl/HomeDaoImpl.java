package ITI.projet.mpb.daos.home.impl;

import ITI.projet.mpb.daos.home.HomeDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class HomeDaoImpl implements HomeDao {
    public String getBookmakerName(Integer idBook,String typeBook){
        String res="";
        String sql="SELECT * FROM bookmakers WHERE "+typeBook+"=true AND id_book=?";
        try { Connection cnx = DataSourceProvider.getDataSource().getConnection();
                PreparedStatement stmt=cnx.prepareStatement(sql);
                stmt.setInt(1,idBook);
                ResultSet rs= stmt.executeQuery();
                if(rs.next()){
                    res=rs.getString("name");
                }
            }
        catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }
    public String getBeginnerName(Integer idBeginner){
        String res="";
        String sql="SELECT * FROM beginners WHERE id_beginner=?";
        try { Connection cnx = DataSourceProvider.getDataSource().getConnection();
            PreparedStatement stmt=cnx.prepareStatement(sql);
            stmt.setInt(1,idBeginner);
            ResultSet rs= stmt.executeQuery();
            if(rs.next()){
                res=rs.getString("name");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }
}
