package ITI.projet.mpb.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientMdp {

    private String pseudo;
    private String pwd1;
    private String pwd2;

    @JsonCreator
    public ClientMdp(@JsonProperty(value="pseudo",required = true)String pseudo,
                     @JsonProperty(value="pwd1",required = true)String pwd1,
                     @JsonProperty(value="pwd2",required = true)String pwd2){
        this.pseudo=pseudo;
        this.pwd1=pwd1;
        this.pwd2=pwd2;

    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPwd1() {
        return pwd1;
    }

    public void setPwd1(String pwd1) {
        this.pwd1 = pwd1;
    }

    public String getPwd2() {
        return pwd2;
    }

    public void setPwd2(String pwd2) {
        this.pwd2 = pwd2;
    }

    @Override
    public String toString() {
        return "ClientMdp{" +
                "pseudo='" + pseudo + '\'' +
                ", pwd1='" + pwd1 + '\'' +
                ", pwd2='" + pwd2 + '\'' +
                '}';
    }
}
