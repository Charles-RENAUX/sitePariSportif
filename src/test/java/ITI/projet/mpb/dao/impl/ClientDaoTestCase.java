package ITI.projet.mpb.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ITI.projet.mpb.daos.app.ClientDao;
import ITI.projet.mpb.daos.home.impl.ClientDaoImpl;
import ITI.projet.mpb.daos.home.impl.DataSourceProvider;
import ITI.projet.mpb.entitie.Client;


public class ClientDaoTestCase {
	private ClientDao clientDao= new ClientDaoImpl();
	
	@Before
	public void initDb() throws Exception {
		try (Connection connection = DataSourceProvider.getDataSource().getConnection();
				Statement stmt = connection.createStatement()) {
			stmt.executeUpdate("DELETE FROM clients");
			stmt.executeUpdate("DELETE FROM roles");
			stmt.executeUpdate("INSERT INTO `roles`(`id`,`role`) VALUES (0,'Admin')");
			stmt.executeUpdate("INSERT INTO `roles`(`id`,`role`) VALUES (1,'User')");
			stmt.executeUpdate(
					"INSERT INTO `clients`(`id`,`nom`, 'prenom','courriel' , 'pseudo', 'mdp', 'solde') "
							+ "VALUES (1, 'nom1', 'prenom1', 'nom@fe.fr', 'pseudo1', 'mdp', 0)");
			stmt.executeUpdate(
					"INSERT INTO `clients`(`id`,`nom`, 'prenom','courriel' , 'pseudo', 'mdp', 'solde') "
							+ "VALUES (2, 'nom2', 'prenom2', 'nom2@fe.fr', 'pseudo2', 'mdp2', 0)");
		}
	}
	
	@Test
	public void shouldListClient () {
		List<Client> lstClient = clientDao.lstClient();
		assertThat(lstClient).hasSize(2);
		
		assertThat(lstClient).extracting(
				Client::getId,
				Client::getNom,
				Client::getPrenom,
				Client::getCourriel,
				Client::getPseudo,
				Client::getMotDePasse,
				Client::getSolde
				);
	}
	
	@Test
	public void shouldGetClient() {
		//WHEN
		Client client = clientDao.getClient("pseudo1");
		//THEN
		assertThat(client).isNotNull();
		assertThat(client.getId()).isEqualTo(1);
		assertThat(client.getNom()).isEqualTo("nom1");
		assertThat(client.getPrenom()).isEqualTo("prenom1");
		assertThat(client.getCourriel()).isEqualTo("nom@fe.fr");
		assertThat(client.getMotDePasse()).isEqualTo("mdp");
		assertThat(client.getSolde()).isEqualTo(0);
	}
	
	@Test
	public void shouldGetClientViaEmai() {
		//WHEN
		Client client = clientDao.getClient("nom@fe.fr");
		//THEN
		assertThat(client).isNotNull();
		assertThat(client.getId()).isEqualTo(1);
		assertThat(client.getNom()).isEqualTo("nom1");
		assertThat(client.getPrenom()).isEqualTo("prenom1");
		assertThat(client.getPseudo()).isEqualTo("pseudo1");
		assertThat(client.getMotDePasse()).isEqualTo("mdp");
		assertThat(client.getSolde()).isEqualTo(0);
	}
	
	@Test
	public void shouldAddClient() throws Exception {
		//GIVEN
		Client clientToCreate = new Client ("n","p","c@m.fr","ps","mdpT");
		//WHEN
		Client clientCreated=clientDao.addClient(clientToCreate);
		//THEN
		try (Connection connection = DataSourceProvider.getDataSource().getConnection();
				PreparedStatement stmt = connection.prepareStatement("SELECT * FROM film WHERE film_id = ?")) {
			stmt.setInt(1, clientCreated.getId());
			try (ResultSet rs = stmt.executeQuery()) {
				assertThat(rs.next()).isTrue();
				assertThat(rs.getInt("id")).isEqualTo(clientCreated.getId());
				assertThat(rs.getInt("nom")).isEqualTo("n");
				assertThat(rs.getInt("prenom")).isEqualTo("p");
				assertThat(rs.getInt("courriel")).isEqualTo("c@m.fr");
				assertThat(rs.getInt("pseudo")).isEqualTo("ps");
				assertThat(rs.getInt("mdp")).isEqualTo("mdpT");
				assertThat(rs.next()).isFalse();
			}
		}
		
	}
}
