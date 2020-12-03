package ITI.projet.mpb.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientMdp {

    private Integer id;
    private String oldPwd;
    private String pwd1;
    private String pwd2;

    @JsonCreator
    public ClientMdp(@JsonProperty(value="id",required = true)Integer id,
                     @JsonProperty(value="oldpwd",required = true)String oldPwd,
                     @JsonProperty(value="pwd1",required = true)String pwd1,
                     @JsonProperty(value="pwd2",required = true)String pwd2){
        this.id=id;
        this.oldPwd=oldPwd;
        this.pwd1=pwd1;
        this.pwd2=pwd2;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
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
                "id=" + id +
                ", oldPwd='" + oldPwd + '\'' +
                ", pwd1='" + pwd1 + '\'' +
                ", pwd2='" + pwd2 + '\'' +
                '}';
    }
}
