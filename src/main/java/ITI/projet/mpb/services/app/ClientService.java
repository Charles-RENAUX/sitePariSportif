package ITI.projet.mpb.services.app;

import java.util.List;

import ITI.projet.mpb.daos.app.ClientDao;
import ITI.projet.mpb.daos.app.impl.ClientDaoImpl;
import ITI.projet.mpb.entitie.Client;

public class ClientService 
{
	private ClientService(){
		
	}
	
	
	private static class clientLibraryHolder
	{
		private final static ClientService instance= new ClientService();
	}
	
	public static ClientService getInstance()
	{
		return clientLibraryHolder.instance;
	}
	
	private ClientDao clientDao = new ClientDaoImpl();
	
	
	
	public List<Client> lstClient()
	{
		return clientDao.lstClient();
	}
	
	public Client getClient(String pseudo)
	{
		return clientDao.getClient(pseudo);
	}
	
	// Service pour une récupération de compte si pseudo/mdp oublié
	// verification du format 
	public Client getClientViaCourriel(String email)
	{
		return clientDao.getClientViaEmail(email);
	}
	
	public Client addClient(Client client)
	{
		if(client == null) {
			throw new IllegalArgumentException("The client can not be null.");
		}
		if(client.getNom() == null) {
			throw new IllegalArgumentException(" 'nom' can not be null.");
		}
		if(client.getPrenom() == null) {
			throw new IllegalArgumentException(" 'prenom' can not be null.");
		}
		if(client.getEmail() == null) {
			throw new IllegalArgumentException(" 'email' can not be null.");
		}
		if(client.getMotDePasse() == null) {
			throw new IllegalArgumentException(" 'mot de passe' can not be null.");
		}
		if(client.getPseudo() == null) {
			throw new IllegalArgumentException(" 'pseudo' can not be null.");
		}
		return clientDao.addClient(client);
	}
	
	
	
	public String getRole(int id) {
		
		return clientDao.getRole(id);
	}

	
}
