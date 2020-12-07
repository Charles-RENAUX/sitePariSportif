package ITI.projet.mpb.services;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ITI.projet.mpb.daos.ClientDao;
import ITI.projet.mpb.daos.impl.ClientDaoImpl;
import ITI.projet.mpb.exceptions.ClientAlreadyException;
import ITI.projet.mpb.exceptions.ClientNotFoundException;
import ITI.projet.mpb.pojos.Client;
import ITI.projet.mpb.pojos.ClientSortable;

import static ITI.projet.mpb.services.MotDePasseServiceHash.genererMotDePasse;

public class ClientService {
	private ClientService() {
	}

	// Service pour une récupération de compte si pseudo/mdp oublié


	private static class clientLibraryHolder {
		private final static ClientService instance = new ClientService();
	}

	public static ClientService getInstance() {
		return clientLibraryHolder.instance;
	}

	private final ClientDao clientDao = new ClientDaoImpl();


	public List<Client> listAll() {
		return clientDao.listAll();
	}

	public List<Client> list(String jsSort) {
		if ("".equals(jsSort) || jsSort == null) {
			throw new IllegalArgumentException("The jSSort can't be null");
		}
		String dbSort = ClientSortable.getComparator(jsSort);
		return clientDao.list(dbSort);
	}

	public List<Client> listByRole(Integer role, String jsSort) {
		if (role < 0) {
			throw new IllegalArgumentException("The role can't be negative ");
		}
		String dbSort = ClientSortable.getComparator(jsSort);
		return clientDao.listByRole(role, dbSort);
	}


	public Client getClientViaPseudo(String pseudo) {
		if (pseudo==null||"".equals(pseudo)){throw new IllegalArgumentException("The Pseudo can't be Null");}

		return clientDao.getClientViaPseudo(pseudo);
	}

	public Client getClientViaEmail(String email) {
		if (email==null||"".equals(email)){throw new IllegalArgumentException("The e-mail can't be Null");}
		return clientDao.getClientViaEmail(email);
	}

	public Client getClient(Integer idClient){
		if(idClient<0){throw new IllegalArgumentException("The id can't be negative");}
		return clientDao.getClient(idClient);
	}

	public void deleteClient(Integer idClient){
		if(idClient<0){throw new IllegalArgumentException("The id can't be negative");}
		clientDao.deleteClient(idClient);
	}

	public void addClient(Client client) {
		if (client == null) {
			throw new IllegalArgumentException("The client can not be null.");
		}
		if (client.getPseudo() == null) {
			throw new IllegalArgumentException(" 'pseudo' can not be null.");
		}
		if (getClientViaPseudo(client.getPseudo())!=null){throw new ClientAlreadyException("Le pseudo est déjà pris");
		}
		if (client.getEmail() == null||"".equals(client.getEmail())) {
			throw new IllegalArgumentException(" 'email' can not be null.");
		}
		if (getClientViaEmail(client.getEmail())!=null){throw new ClientAlreadyException("L'adresse mail est déjà prise");
		}
		if (client.getNom() == null||"".equals(client.getNom())) {
			throw new IllegalArgumentException(" 'nom' can not be null.");
		}
		if (client.getPrenom() == null||"".equals(client.getPrenom())) {
			throw new IllegalArgumentException(" 'prenom' can not be null.");
		}
		if(!checkMail(client.getEmail())) {
			throw new IllegalArgumentException("'email' must be in the right format");
		}
		if (client.getMotDePasse() == null) {
			throw new IllegalArgumentException(" 'mot de passe' can not be null.");
		}

		clientDao.addClient(client);
	}

	public void editClient(Client client) {
		if (client == null) {
			throw new IllegalArgumentException("The client can not be null.");
		}
		try {
			getClientViaPseudo(client.getPseudo()).getId();
			if (getClientViaPseudo(client.getPseudo()).getId()!=client.getId()){throw new ClientAlreadyException("Le pseudo est déjà pris");
			}
		}catch (NullPointerException e){

		}
		if (client.getEmail() == null||"".equals(client.getEmail())) {
			throw new IllegalArgumentException(" 'email' can not be null.");
		}
		try {
			getClientViaEmail(client.getEmail()).getId();
			if (getClientViaEmail(client.getEmail()).getId()!=client.getId()){throw new ClientAlreadyException("Le pseudo est déjà pris");
			}
		}catch (NullPointerException e){

		}
		if (client.getNom() == null||"".equals(client.getNom())) {
			throw new IllegalArgumentException(" 'nom' can not be null.");
		}
		if (client.getPrenom() == null||"".equals(client.getPrenom())) {
			throw new IllegalArgumentException(" 'prenom' can not be null.");
		}
		if (client.getMotDePasse() == null) {
			throw new IllegalArgumentException(" 'mot de passe' can not be null.");
		}
		clientDao.editClient(client);
	}
	public void editClientNoPwd(Client client) {
		if (client == null) {
			throw new IllegalArgumentException("The client can not be null.");
		}
		if (client.getPseudo() == null) {
			throw new IllegalArgumentException(" 'pseudo' can not be null.");
		}
		if (getClient(client.getId()) == null) {
			throw new ClientNotFoundException("Le client n'existe pas");
		}
		try {
			getClientViaPseudo(client.getPseudo()).getId();
			if (getClientViaPseudo(client.getPseudo()).getId()!=client.getId()){throw new ClientAlreadyException("Le pseudo est déjà pris");
			}
		}catch (NullPointerException e){

		}
		if (client.getEmail() == null||"".equals(client.getEmail())) {
			throw new IllegalArgumentException(" 'email' can not be null.");
		}
		try {
			getClientViaEmail(client.getEmail()).getId();
			if (getClientViaEmail(client.getEmail()).getId()!=client.getId()){throw new ClientAlreadyException("Le pseudo est déjà pris");
			}
		}catch (NullPointerException e){

		}
		if (client.getNom() == null||"".equals(client.getNom())) {
			throw new IllegalArgumentException(" 'nom' can not be null.");
		}
		if (client.getPrenom() == null||"".equals(client.getPrenom())) {
			throw new IllegalArgumentException(" 'prenom' can not be null.");
		}
		if (client.getEmail() == null||"".equals(client.getEmail())) {
			throw new IllegalArgumentException(" 'email' can not be null.");
		}
		clientDao.editClientNoPwd(client);
	}

	public String getRole(String pseudo) {
		if ("".equals(pseudo)||pseudo==null) {
			throw new IllegalArgumentException("The String can't be null or empty");
		}
		return clientDao.getRole(pseudo);
	}

	public boolean checkMail(String mail){
		Pattern p = Pattern
				.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
		Matcher m = p.matcher(mail.toUpperCase());
		return m.matches();
	}


	public String getDefaultPwd() {
		String pwd = "ZeTdiUyZmZSRMdpl";
		return genererMotDePasse(pwd);
	}
}
