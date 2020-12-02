package ITI.projet.mpb.daos;

import java.util.List;
import ITI.projet.mpb.pojos.Client;

public interface ClientDao 
{
	public List<Client> listAll();
	public List<Client> list(String dbSort);
	public List<Client> listByRole(Integer role, String dbSort);

	public Client getClientViaPseudo(String pseudo);
	public Client getClientViaEmail(String email);
	public Client getClient(Integer idClient);
	
	public void addClient(Client client);

	public void deleteClient(Integer idClient);

	public void editClient(Client client);
	public void editClientNoPwd(Client client);

	public String getRole(String pseudo);
}
