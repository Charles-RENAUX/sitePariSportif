/*package ITI.projet.mpb.daos.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import ITI.projet.mpb.daos.ClientDao;
import ITI.projet.mpb.pojos.Client;

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
	public void addClient(Client client) {
		Integer id = clientLst.lastKey() + 1;
		client.setId(id);
		clientLst.put(id, client);
		return client;
	}

	@Override
	public Client getClientViaEmail(String email) {
		// TODO Auto-generated method stub
		return clientLst.get(email);
	}
	
	@Override
	public String getRole(int id) {
		// TODO Auto-generated method stub
		return "ok";
	}

}
*/
