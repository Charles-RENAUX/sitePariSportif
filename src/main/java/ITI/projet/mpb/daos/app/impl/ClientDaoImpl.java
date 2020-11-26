package ITI.projet.mpb.daos.app.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import ITI.projet.mpb.daos.DataSourceProvider;
import ITI.projet.mpb.daos.app.ClientDao;
import ITI.projet.mpb.entitie.Client;


public class ClientDaoImpl implements ClientDao
{

	@Override
	public List<Client> lstClient() {
		List<Client> result= new ArrayList<Client>();
		try {
			DataSource dataSource = DataSourceProvider.getDataSource();
			try (Connection cnx = dataSource.getConnection();
					Statement statement = cnx.createStatement();
					ResultSet resultSelect = statement.executeQuery("SELECT * FROM clients")) {
				while(resultSelect.next()) {
					result.add(createClientFromResultSet(resultSelect));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private Client createClientFromResultSet(ResultSet resultSet) throws SQLException
	{
		return new Client(
				resultSet.getInt("id_client"),
				resultSet.getString("nom"),
				resultSet.getString("prenom"),
				resultSet.getString("email"),
				resultSet.getString("pseudo"),
				resultSet.getString("mdp"),
				resultSet.getDouble("solde")
				);	
	}

	@Override
	public Client getClient(String pseudo) 
	{
		Client client=null;
		String sql="SELECT * FROM clients WHERE pseudo=?";
		try {
			DataSource dataSource = DataSourceProvider.getDataSource();
			try (Connection cnx = dataSource.getConnection();
					PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
				preparedStatement.setString(1, pseudo);
				try(ResultSet result = preparedStatement.executeQuery()) {
					if(result.next()) {
						client = createClientFromResultSet(result);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return client;
	}
	
	@Override
	public Client getClientViaEmail(String email)
	{
		Client client=null;
		String sql="SELECT * FROM clients WHERE email=?";
		try {
			DataSource dataSource = DataSourceProvider.getDataSource();
			try (Connection cnx = dataSource.getConnection();
					PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
				preparedStatement.setString(1, email);
				try(ResultSet result = preparedStatement.executeQuery()) {
					if(result.next()) {
						client = createClientFromResultSet(result);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return client;
	}
	

	@Override
	public Client addClient(Client client) {
		String sql = "INSERT INTO clients (nom,prenom,email,pseudo,mdp,solde) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			DataSource dataSource = DataSourceProvider.getDataSource();
			try (Connection cnx = dataSource.getConnection();
					PreparedStatement preparedStatement = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) 
			{
				preparedStatement.setString(1,client.getNom());
				preparedStatement.setString(2,client.getPrenom());
				preparedStatement.setString(3,client.getEmail());
				preparedStatement.setString(4,client.getPseudo());
				preparedStatement.setString(5,client.getMotDePasse());
				preparedStatement.setDouble(6, client.getSolde());
				preparedStatement.executeUpdate();
				ResultSet ids = preparedStatement.getGeneratedKeys();
				if (ids.next()) {
					client.setId(ids.getInt(1));
					return client;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Erreur lors de la mise à jour du Client");
	}

	@Override
	public String getRole(int id) {
		Client client = null;
		String sql="SELECT role FROM roles JOIN ClientsRoles ON roles.id_role=ClientsRoles.id_Role WHERE ClientsRoles.id_Client=?";
		try {
			DataSource dataSource = DataSourceProvider.getDataSource();
			try (Connection cnx = dataSource.getConnection();
					PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
				preparedStatement.setInt(1, id);
				try(ResultSet result = preparedStatement.executeQuery()) {
					if(result.next()) {
						client = createClientFromResultSet(result);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return client.getRole();
	}

	

}
