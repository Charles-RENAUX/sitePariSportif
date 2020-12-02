package ITI.projet.mpb;

import static ITI.projet.mpb.services.MotDePasseServiceHash.genererMotDePasse;
import static ITI.projet.mpb.services.MotDePasseServiceHash.validerMotDePasse;
import static org.assertj.core.api.Assertions.assertThat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ITI.projet.mpb.daos.ClientDao;
import ITI.projet.mpb.daos.impl.ClientDaoImpl;
import ITI.projet.mpb.daos.DataSourceProvider;
import ITI.projet.mpb.pojos.Client;


public class ClientDaoTestCase {
	private ClientDao clientDao = new ClientDaoImpl();

	@Before
	public void initDb() throws Exception {
		try (Connection connection = DataSourceProvider.getDataSource().getConnection();
			 Statement stmt = connection.createStatement()) {
			stmt.executeUpdate("DELETE FROM clients");
			stmt.executeUpdate("DELETE FROM roles");
			stmt.executeUpdate("INSERT INTO `roles`(`id_role`,`role`) VALUES (2,'Admin')");
			stmt.executeUpdate("INSERT INTO `roles`(`id_role`,`role`) VALUES (1,'User')");
			String mdp1=genererMotDePasse("mdp1");
			String mdp2=genererMotDePasse("mdp2");
			String mdp3=genererMotDePasse("mdp3");
			String mdp4=genererMotDePasse("mdp4");
			System.out.println(mdp1);
			stmt.executeUpdate(
					"INSERT INTO `clients`(`id_client`,`nom`, `prenom`,`email` , `pseudo`, `mdp`, `solde`,`id_role`) "
							+ "VALUES (1, 'nom1', 'prenom1', 'nom1@fe.fr', 'pseudo1', '$argon2i$v=19$m=65536,t=5,p=1$7CFs/LfoperuSHgxdwQSf3oW5jV+le2YiVQvXf0EnZha2mZSFNd4+0e+Kq9vCuA0rWK4ZHV9n8JkeTkbsdGVJMykQ3z2gfyEzCfliszkNtiuHUEluinPVkhdFClmpraJO7JzpwwXbNoiArZrqC3REffqOKe1717IxOIKYaAb/1A$ScnLjTmQGqulw8Uyuyks9ujqSX7m1xXZDbFp9ARNf7ibAhfS+NEf+ihL2w225TP7nfXrnm8hs/0H0fGtKMAL49xIhYvTEhsF7uM0HIlK1zu8q0UAv3S+XO7y3mMFBCDrFL//tcOdpoPygrNANbLjhb6PgBRrSep9GGLarwnmg4s', 0,2)");
			stmt.executeUpdate(
					"INSERT INTO `clients`(`id_client`,`nom`, `prenom`,`email` , `pseudo`, `mdp`, `solde`,`id_role`) "
							+ "VALUES (2, 'nom2', 'prenom2', 'nom2@fe.fr', 'pseudo2','$argon2i$v=19$m=65536,t=5,p=1$rebuudtSd1tlC66mHlIdijqe3uW7CdI236lNrOkPkLJbmVj8mr3TIW+VJ0Dhsj3edQZiocW+TSfSjfo5BXNqy1iowKlKA1JPbC68FAL8V65LbCR3cgrYjN+gT1W6NuT9N2IzjX49L4+NCFeB2/SF1wP22kvuN9dvwZ0sfWXAT4M$o2qlkYHRxe00BN3YBzyCXnX3v5e6xIzseABCWs3UdO6xWUD4Io42XrijineRAInPTkda+Z6+7CP57xU/LLD67xhG5AhcyeTqfgO+O2MIg4+udjJKVd6m3HuqC+Inz8qFUqviIAQCN8On0u46M5o3iRQvk7Xho7Iei91EIm7i+G0',0,1)");
			stmt.executeUpdate(
					"INSERT INTO `clients`(`id_client`,`nom`, `prenom`,`email` , `pseudo`, `mdp`, `solde`,`id_role`) "
							+ "VALUES (3, 'nom3', 'prenom3', 'nom3@fe.fr', 'pseudo3','$argon2i$v=19$m=65536,t=5,p=1$h4cqQAun5PkRBV947BnI8L6MXdrCdmhuaJJg5EcQL4aKveIC86uNcDInkr3J4fmTWfiJWcPWuTzpb0+olD32FPbwFjlVoHnrM7jGToHC/Wu+v9DpdS3bBevToCazhQGR5dTFObQN3zE7tp9E3CN4FpggZG1wEfHROgZLToWJtjI$kCA9Qyqmw04y3qi9hty0mMEAHVYibfXl2otU+LpzW0kpYrZ29RFLqq9X2JXbXE9u6Cj6xU3mW724pSQIKvoNeGUFU3siGWlm97Q7cPTzgzD06wNUCRFIKU/LHGe2I/dBb51n7CZMaGpztnOGAhqDquLlMf8FYyunzE8FcelTqfg', 0,1)");
			stmt.executeUpdate(
					"INSERT INTO `clients`(`id_client`,`nom`, `prenom`,`email` , `pseudo`, `mdp`, `solde`,`id_role`) "
							+ "VALUES (4, 'nom4', 'prenom4', 'nom4@fe.fr', 'pseudo4','$argon2i$v=19$m=65536,t=5,p=1$nSPMzio4W19BvwJtE4xYbynUjeTZ8u8AK+d7ARYJ+nYT71yxoE7FO2PmhO2Sw086DwBxq5fLVZVTDKyxwXmF2ayrcId1k+jl8BFYc+YqRDciFr3tmrs1Jqe9/IZ+nUhOgB3YTJAlnpQY/7zkY92ZigUB5mXzknLlViwyTjwJh3w$o87pGZmxPRc4DqrlkrZmwNdwg3MG3QwzXJTyuRmVOQJHxfe1R7cZ9sd0UZjIl0p7WG36sIQEaobxlilC9GKt0hxtYOyBgX6QLNJbv6e1WQYZYbYZ0anm/Y9jDSWfgUJ5ObFPm2QLJ/OP379RMMhJGM7l54ZnPwIPG3KV/5xDf3Y', 0,1)");

		}
	}

	@Test
	public void shouldListAll() {
		List<Client> listAll = clientDao.listAll();
		assertThat(listAll).hasSize(4);

		assertThat(listAll).extracting(
				Client::getId,
				Client::getNom,
				Client::getPrenom,
				Client::getEmail,
				Client::getPseudo,
				Client::getMotDePasse,
				Client::getSolde,
				Client::getidRole
		);
	}

	@Test
	public void shouldList() {
		List<Client> list = clientDao.list("pseudo");
		assertThat(list).hasSize(4);
		assertThat(list).extracting(
				Client::getId,
				Client::getNom,
				Client::getPrenom,
				Client::getEmail,
				Client::getPseudo,
				Client::getMotDePasse,
				Client::getSolde,
				Client::getidRole
		);
		assertThat(list.get(0).getPseudo()).isEqualTo("pseudo1");
	}

	@Test
	public void shouldListByRole() {
		List<Client> listByRole = clientDao.listByRole(1, "pseudo");
		assertThat(listByRole).hasSize(3);
		assertThat(listByRole).extracting(
				Client::getId,
				Client::getNom,
				Client::getPrenom,
				Client::getEmail,
				Client::getPseudo,
				Client::getMotDePasse,
				Client::getSolde,
				Client::getidRole
		);
		assertThat(listByRole.get(0).getPseudo()).isEqualTo("pseudo2");

	}


	@Test
	public void shouldGetClientViaPseudo() {
		//WHEN
		Client client = clientDao.getClientViaPseudo("pseudo1");
		//THEN
		assertThat(client).isNotNull();
		assertThat(client.getId()).isEqualTo(1);
		assertThat(client.getNom()).isEqualTo("nom1");
		assertThat(client.getPrenom()).isEqualTo("prenom1");
		assertThat(client.getEmail()).isEqualTo("nom1@fe.fr");
		assertThat(validerMotDePasse("mdp1", client.getMotDePasse())).isTrue();
		assertThat(client.getSolde()).isEqualTo(0);
	}

	@Test
	public void shouldGetClientViaEmail() {
		//GIVEN
		String mdp3=genererMotDePasse("mdp3");
		//WHEN
		Client client = clientDao.getClientViaEmail("nom3@fe.fr");
		//THEN
		assertThat(client).isNotNull();
		assertThat(client.getId()).isEqualTo(3);
		assertThat(client.getNom()).isEqualTo("nom3");
		assertThat(client.getPrenom()).isEqualTo("prenom3");
		assertThat(client.getPseudo()).isEqualTo("pseudo3");
		assertThat(validerMotDePasse("mdp3", client.getMotDePasse())).isTrue();
		assertThat(client.getSolde()).isEqualTo(0);
	}

	@Test
	public void shouldGetClient(){
		//WHEN
		Client client = clientDao.getClient(2);
		//THEN
		assertThat(client).isNotNull();
		assertThat(client.getId()).isEqualTo(2);
		assertThat(client.getNom()).isEqualTo("nom2");
		assertThat(client.getPrenom()).isEqualTo("prenom2");
		assertThat(client.getPseudo()).isEqualTo("pseudo2");
		assertThat(validerMotDePasse("mdp2", client.getMotDePasse())).isTrue();
		assertThat(client.getSolde()).isEqualTo(0);
	}


	@Test
	public void shouldAddClient() throws Exception {
		//GIVEN
		Client clientToCreate = new Client ("n","p","c@m.fr","ps1",genererMotDePasse("mdpT"),1);
		//WHEN
		clientDao.addClient(clientToCreate);
		//THEN
		try (Connection connection = DataSourceProvider.getDataSource().getConnection();
				PreparedStatement stmt = connection.prepareStatement("SELECT * FROM clients WHERE pseudo = ?")) {
			stmt.setString(1, clientToCreate.getPseudo());
			try (ResultSet rs = stmt.executeQuery()) {
				assertThat(rs.next()).isTrue();
				assertThat(rs.getString("nom")).isEqualTo("n");
				assertThat(rs.getString("prenom")).isEqualTo("p");
				assertThat(rs.getString("email")).isEqualTo("c@m.fr");
				assertThat(rs.getString("pseudo")).isEqualTo("ps1");
				assertThat(validerMotDePasse("mdpT",rs.getString("mdp"))).isTrue();
				assertThat(rs.getInt("id_role")).isEqualTo(1);
				assertThat(rs.next()).isFalse();
			}
		}
		
	}
	@Test
	public void shoulEditClient() throws Exception {
		//GIVEN
		Client clientToUpdate = new Client (1,"n","p","c@m.fr","ps2","mdpT",5,1);
		//WHEN
		clientDao.editClient(clientToUpdate);
		//THEN
		try (Connection connection = DataSourceProvider.getDataSource().getConnection();
			 PreparedStatement stmt = connection.prepareStatement("SELECT * FROM clients WHERE id_client = ?")) {
			stmt.setInt(1, clientToUpdate.getId());
			try (ResultSet rs = stmt.executeQuery()) {
				assertThat(rs.next()).isTrue();
				assertThat(rs.getInt("id_client")).isEqualTo(1);
				assertThat(rs.getString("nom")).isEqualTo("n");
				assertThat(rs.getString("prenom")).isEqualTo("p");
				assertThat(rs.getString("email")).isEqualTo("c@m.fr");
				assertThat(rs.getString("pseudo")).isEqualTo("ps2");
				assertThat(rs.getString("mdp")).isEqualTo("mdpT");
				assertThat(rs.getDouble("solde")).isEqualTo(5);
				assertThat(rs.getInt("id_role")).isEqualTo(1);
				assertThat(rs.next()).isFalse();
			}
		}

	}

	@Test
	public void shoulEditClientNoPwd() throws Exception {
		//GIVEN
		Client clientToUpdate = new Client (1,"n","p","c@m.fr","ps2","mdpT",5,1);
		//WHEN
		clientDao.editClientNoPwd(clientToUpdate);
		//THEN
		try (Connection connection = DataSourceProvider.getDataSource().getConnection();
			 PreparedStatement stmt = connection.prepareStatement("SELECT * FROM clients WHERE id_client = ?")) {
			stmt.setInt(1, clientToUpdate.getId());
			try (ResultSet rs = stmt.executeQuery()) {
				assertThat(rs.next()).isTrue();
				assertThat(rs.getInt("id_client")).isEqualTo(1);
				assertThat(rs.getString("nom")).isEqualTo("n");
				assertThat(rs.getString("prenom")).isEqualTo("p");
				assertThat(rs.getString("email")).isEqualTo("c@m.fr");
				assertThat(rs.getString("pseudo")).isEqualTo("ps2");
				assertThat(rs.getDouble("solde")).isEqualTo(5);
				assertThat(rs.getInt("id_role")).isEqualTo(1);
				assertThat(rs.next()).isFalse();
			}
		}

	}

	@Test
	public void shouldDeleteClient() throws Exception{
		//GIVEN
		Integer idClient=4;
		//WHEN
		clientDao.deleteClient(idClient);
		//THEN
		try (Connection connection = DataSourceProvider.getDataSource().getConnection();
			 PreparedStatement stmt = connection.prepareStatement("SELECT * FROM clients WHERE id_client = ?")) {
			stmt.setInt(1, idClient);
			try (ResultSet rs = stmt.executeQuery()) {
				assertThat(rs.next()).isFalse();
			}
	}
	}

	@Test
	public void shouldGetRole() throws Exception {
		//WHEN
		String role=clientDao.getRole("pseudo1");
		//THEN
		assertThat(role).isEqualTo("Admin");
	}
}