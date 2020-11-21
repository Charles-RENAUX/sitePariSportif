package ITI.projet.mpb.daos.home.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import ITI.projet.mpb.daos.app.ClientDao;
import ITI.projet.mpb.entitie.Client;

public class ClientDaoMockImpl implements ClientDao{
	private TreeMap<Integer,Client> clientLst;
	
	public ClientDaoMockImpl()
	{
		clientLst=new TreeMap<>();
		clientLst.put(1, new Client("RENAUX","Charles","charles.renaux@hei.yncrea.fr","PseudoCR","CRmdp"));
	}
	@Override
	public List<Client> lstClient() {
		
		return new ArrayList<>(clientLst.values());
	}

	@Override
	public Client getClient(String pseudo) {
		return clientLst.get(pseudo);
	}

	@Override
	public Client addClient(Client client) {
		Integer id = clientLst.lastKey() + 1;
		client.setId(id);
		clientLst.put(id, client);
		return client;
	}

	@Override
	public Client getClientViaCourriel(String courriel) {
		// TODO Auto-generated method stub
		return clientLst.get(courriel);
	}
	
	@Override
	public String getRole(int id) {
		// TODO Auto-generated method stub
		return "ok";
	}

}
