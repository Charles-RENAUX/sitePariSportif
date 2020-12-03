package ITI.projet.mpb.pojos;

public class Client {
	private Integer id;
	private String nom;
	private String prenom;
	private String email;
	private String pseudo;
	private String motDePasse;
	private double solde;
	private Integer idRole;


	//Creation d'un client sans id, et sans solde
	public Client(String nom, String prenom, String email, String pseudo, String mDP, Integer idRole) {
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.pseudo = pseudo;
		this.motDePasse = mDP;
		this.solde = 0;
		this.idRole = idRole;

	}


	//Creation d'un client avec id et avec solde
	public Client(int id, String nom, String prenom, String email, String pseudo, String mDP, double solde, Integer idRole) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.pseudo = pseudo;
		this.motDePasse = mDP;
		this.solde = solde;
		this.idRole = idRole;
	}

	//Creation d'un client à éditer à partir d'une requête ajax et du défaut pwd

	public Client(ClientDto clientDto, String pwd) {
		this.id = clientDto.getId();
		this.nom = clientDto.getNom();
		this.prenom = clientDto.getPrenom();
		this.email = clientDto.getEmail();
		this.pseudo = clientDto.getPseudo();
		this.idRole = clientDto.getIdRole();
		this.motDePasse = pwd;
		this.solde = clientDto.getSolde();
		this.idRole = clientDto.getIdRole();
	}


	public void setId(Integer id) {
		this.id = id;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public double getSolde() {
		return solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}

	public Integer getId() {
		return id;
	}

	public Integer getidRole() {
		return idRole;
	}

	public void setidRole(Integer idRole) {
		this.idRole = idRole;
	}

}
