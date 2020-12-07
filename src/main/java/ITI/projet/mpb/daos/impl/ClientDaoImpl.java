package ITI.projet.mpb.daos.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import ITI.projet.mpb.daos.DataSourceProvider;
import ITI.projet.mpb.daos.ClientDao;
import ITI.projet.mpb.exceptions.ClientAlreadyException;
import ITI.projet.mpb.exceptions.ClientNotFoundException;
import ITI.projet.mpb.pojos.Client;


public class ClientDaoImpl implements ClientDao
{

	@Override
	public List<Client> listAll() {
		List<Client> result= new ArrayList<Client>();
		try {
			DataSource dataSource = DataSourceProvider.getDataSource();
			try (Connection cnx = dataSource.getConnection();
					Statement statement = cnx.createStatement();
					ResultSet resultSet = statement.executeQuery("SELECT * FROM clients")) {
				while(resultSet.next()) {
					result.add(createClientFromResultSet(resultSet));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Client> list(String dbSort)
	{
		List<Client> result=new ArrayList<>();
		String sql="SELECT * FROM clients ORDER BY "+dbSort;
		try {
			DataSource dataSource = DataSourceProvider.getDataSource();
			try (Connection cnx = dataSource.getConnection();
				 PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
				System.out.println(dbSort);
				try(ResultSet resultSet = preparedStatement.executeQuery()) {
					while(resultSet.next()) {
						result.add(createClientFromResultSet(resultSet));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Client> listByRole(Integer role,String dbSort)
	{
		List<Client> result=new ArrayList<>();
		String sql="SELECT * FROM clients JOIN roles on clients.id_role=roles.id_role WHERE clients.id_role=? ORDER BY "+dbSort;
		try {
			DataSource dataSource = DataSourceProvider.getDataSource();
			try (Connection cnx = dataSource.getConnection();
				 PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
				preparedStatement.setInt(1, role);
				try(ResultSet resultSet = preparedStatement.executeQuery()) {
					while(resultSet.next()) {
						result.add(createClientFromResultSet(resultSet));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private Client createClientFromResultSet(ResultSet resultSet) throws SQLException
	{
		System.out.println(resultSet.getString("pseudo"));
		return new Client(
				resultSet.getInt("id_client"),
				resultSet.getString("nom"),
				resultSet.getString("prenom"),
				resultSet.getString("email"),
				resultSet.getString("pseudo"),
				resultSet.getString("mdp"),
				resultSet.getDouble("solde"),
				resultSet.getInt("id_role")
				);	
	}

	@Override
	public Client getClientViaPseudo(String pseudo)
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
		}catch (NullPointerException e){
			throw new ClientNotFoundException("The client doesn't exist");
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
		}catch (NullPointerException e){
			throw new ClientNotFoundException("The client doesn't exist");
		}
		return client;
	}

	public Client getClient(Integer idClient)
	{
		Client client=null;
		String sql="SELECT * FROM clients WHERE id_client=?";
		try {
			DataSource dataSource = DataSourceProvider.getDataSource();
			try (Connection cnx = dataSource.getConnection();
				 PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
				preparedStatement.setInt(1, idClient);
				try(ResultSet result = preparedStatement.executeQuery()) {
					if(result.next()) {
						client = createClientFromResultSet(result);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (NullPointerException e){
			throw new ClientNotFoundException("The client doesn't exist");
		}
		return client;
	}



	@Override
	public void addClient(Client client) {
		String sql = "INSERT INTO clients (nom,prenom,email,pseudo,mdp,solde,id_role) VALUES (?, ?, ?, ?, ?, ?,?)";
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
				preparedStatement.setInt(7, client.getidRole());
				preparedStatement.executeUpdate();
				ResultSet ids = preparedStatement.getGeneratedKeys();
				if (ids.next()) {
				}
			}
		}catch (SQLIntegrityConstraintViolationException e){
			throw new ClientAlreadyException("The client already exist");
		}catch (SQLException e){

		}
	}

	@Override
	public void deleteClient(Integer idClient){
		String sql="DELETE FROM `clients` WHERE id_client=?";
		try{
			DataSource dataSource=DataSourceProvider.getDataSource();
			try(Connection cnx= dataSource.getConnection();
			PreparedStatement preparedStatement = cnx.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
				preparedStatement.setInt(1,idClient);
				preparedStatement.executeUpdate();
				ResultSet ids = preparedStatement.getGeneratedKeys();
				if (ids.next()) {
				}
			}
		}catch (SQLException e){
			e.printStackTrace();
		}catch (NullPointerException e){
			throw new ClientNotFoundException("The client doesn't exist");
		}
	}


	@Override
	public void editClient(Client client) {
		String sql="UPDATE clients SET nom=? , prenom=?, email=?, pseudo=?, solde=?,mdp=?,id_role=? WHERE id_client=?";
		try {
		DataSource dataSource = DataSourceProvider.getDataSource();
		try (Connection cnx = dataSource.getConnection();
			 PreparedStatement preparedStatement = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, client.getNom());
			preparedStatement.setString(2, client.getPrenom());
			preparedStatement.setString(3, client.getEmail());
			preparedStatement.setString(4, client.getPseudo());
			preparedStatement.setDouble(5, client.getSolde());
			preparedStatement.setString(6, client.getMotDePasse());
			preparedStatement.setInt(7,client.getidRole());
			preparedStatement.setInt(8, client.getId());
			preparedStatement.executeUpdate();
			ResultSet ids = preparedStatement.getGeneratedKeys();
			if (ids.next()) {
			}
		}
	}
	catch (SQLException e) {
		e.printStackTrace();
		}
		catch (NullPointerException e){
			throw new ClientNotFoundException("The client doesn't exist");
		}
	}

	@Override
	public void editClientNoPwd(Client client) {
		String sql="UPDATE clients SET nom=? , prenom=?, email=?, pseudo=?, solde=?,id_role=? WHERE id_client=?";
		try{
			DataSource dataSource = DataSourceProvider.getDataSource();
			try (Connection cnx = dataSource.getConnection();
				 PreparedStatement preparedStatement = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
			{
				preparedStatement.setString(1,client.getNom());
				preparedStatement.setString(2,client.getPrenom());
				preparedStatement.setString(3,client.getEmail());
				preparedStatement.setString(4,client.getPseudo());
				preparedStatement.setDouble(5,client.getSolde());
				preparedStatement.setInt(6,client.getidRole());
				preparedStatement.setInt(7, client.getId());
				preparedStatement.executeUpdate();
				ResultSet ids = preparedStatement.getGeneratedKeys();
				if (ids.next()) {
				}
			}

		}catch (SQLException e){
			e.printStackTrace();
		}	catch (NullPointerException e){
			throw new ClientNotFoundException("The client doesn't exist");
		}
	}

	@Override
	public String getRole(String pseudo) {
		String role="";
		String sql="SELECT * FROM clients JOIN roles ON clients.id_role=roles.id_role WHERE pseudo=?";
		try {
			DataSource dataSource = DataSourceProvider.getDataSource();
			try (Connection cnx = dataSource.getConnection();
					PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
				preparedStatement.setString(1, pseudo);
				try(ResultSet result = preparedStatement.executeQuery()) {
					if(result.next()) {
						role= result.getString("role");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return role;
	}

	

}
