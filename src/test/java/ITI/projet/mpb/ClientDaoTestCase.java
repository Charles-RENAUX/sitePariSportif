package ITI.projet.mpb;

import static org.assertj.core.api.Assertions.assertThat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ITI.projet.mpb.daos.app.ClientDao;
import ITI.projet.mpb.daos.app.impl.ClientDaoImpl;
import ITI.projet.mpb.daos.DataSourceProvider;
import ITI.projet.mpb.entitie.Client;


public class ClientDaoTestCase {
	private ClientDao clientDao= new ClientDaoImpl();
	
	@Before
	public void initDb() throws Exception {
		try (Connection connection = DataSourceProvider.getDataSource().getConnection();
				Statement stmt = connection.createStatement()) {
			stmt.executeUpdate("DELETE FROM clients");
			stmt.executeUpdate("DELETE FROM roles");
			stmt.executeUpdate("INSERT INTO `roles`(`id_role`,`role`) VALUES (0,'Admin')");
			stmt.executeUpdate("INSERT INTO `roles`(`id_role`,`role`) VALUES (1,'User')");
			stmt.executeUpdate(
					"INSERT INTO `clients`(`id_client`,`nom`, `prenom`,`email` , `pseudo`, `mdp`, `solde`) "
							+ "VALUES (1, 'nom1', 'prenom1', 'nom@fe.fr', 'pseudo1', 'mdp', 0)");
			stmt.executeUpdate(
					"INSERT INTO `clients`(`id_client`,`nom`, `prenom`,`email` , `pseudo`, `mdp`, `solde`) "
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
				Client::getEmail,
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
		assertThat(client.getEmail()).isEqualTo("nom@fe.fr");
		assertThat(client.getMotDePasse()).isEqualTo("mdp");
		assertThat(client.getSolde()).isEqualTo(0);
	}
	
	@Test
	public void shouldGetClientViaEmail() {
		//WHEN
		Client client = clientDao.getClientViaEmail("nom@fe.fr");
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
				PreparedStatement stmt = connection.prepareStatement("SELECT * FROM clients WHERE id_client = ?")) {
			stmt.setInt(1, clientCreated.getId());
			try (ResultSet rs = stmt.executeQuery()) {
				assertThat(rs.next()).isTrue();
				assertThat(rs.getInt("id_client")).isEqualTo(clientCreated.getId());
				assertThat(rs.getString("nom")).isEqualTo("n");
				assertThat(rs.getString("prenom")).isEqualTo("p");
				assertThat(rs.getString("email")).isEqualTo("c@m.fr");
				assertThat(rs.getString("pseudo")).isEqualTo("ps");
				assertThat(rs.getString("mdp")).isEqualTo("mdpT");
				assertThat(rs.next()).isFalse();
			}
		}
		
	}
}
