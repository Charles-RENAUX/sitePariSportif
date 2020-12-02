package ITI.projet.mpb;

import static ITI.projet.mpb.services.MotDePasseServiceHash.genererMotDePasse;
import static ITI.projet.mpb.services.MotDePasseServiceHash.validerMotDePasse;

public class Test {

    public static void main(String[] args) {
        String mdp1=genererMotDePasse("mdpT");
        if (validerMotDePasse("mdpT",mdp1)==true){
            System.out.println("C OK");
        }else{
            System.out.println("PB");
        }

    }
}
