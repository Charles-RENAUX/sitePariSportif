package ITI.projet.mpb.daos.home.impl;

import ITI.projet.mpb.daos.home.HomeDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class HomeDaoImpl implements HomeDao {
    public String getBookmakerName(String nameBook,String typeBook){
        String res="";
        String sql="SELECT * FROM bookmakers WHERE name=? and type=?";
        try { Connection cnx = DspHome.getDataSource().getConnection();
                PreparedStatement stmt=cnx.prepareStatement(sql);
                stmt.setString(1,nameBook);
                stmt.setString(2,typeBook);
                ResultSet rs= stmt.executeQuery();
                if(rs.next()){
                    res=nameBook;
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
        try { Connection cnx = DspHome.getDataSource().getConnection();
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
