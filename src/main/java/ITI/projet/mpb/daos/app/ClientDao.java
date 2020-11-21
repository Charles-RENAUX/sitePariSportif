package ITI.projet.mpb.daos.app;

import java.util.List;

import ITI.projet.mpb.entitie.Client;

public interface ClientDao 
{
	public List<Client> lstClient();
	
	public Client getClient(String pseudo);
	
	public Client addClient(Client client);

	public Client getClientViaCourriel(String courriel);
	
	public String getRole(int id);
}
