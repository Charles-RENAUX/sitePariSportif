package ITI.projet.mpb.entitie;

public class Client 
{
	private Integer id;
	private String nom;
	private String prenom;
	private String courriel;
	private String pseudo;
	private String motDePasse;
	private double solde;
	private String role;
	
	

	public Client(String nom,String prenom,String courriel,String pseudo, String mDP)
	{
		this.nom=nom;
		this.prenom=prenom;
		this.courriel=courriel;
		this.pseudo=pseudo;
		this.motDePasse=mDP;
		this.solde=0;
	}
	
	public Client(int id,String nom,String prenom,String courriel,String pseudo, String mDP)
	{
		this.id=id;
		this.nom=nom;
		this.prenom=prenom;
		this.courriel=courriel;
		this.pseudo=pseudo;
		this.motDePasse=mDP;
		this.solde=0;
	}
	
	public Client(int id,String nom,String prenom,String courriel,String pseudo, String mDP,double solde)
	{
		this.id=id;
		this.nom=nom;
		this.prenom=prenom;
		this.courriel=courriel;
		this.pseudo=pseudo;
		this.motDePasse=mDP;
		this.solde=solde;
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

	public String getCourriel() {
		return courriel;
	}

	public void setCourriel(String courriel) {
		this.courriel = courriel;
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
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	

}
