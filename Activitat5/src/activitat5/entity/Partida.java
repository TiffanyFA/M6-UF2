package activitat5.entity;
// Generated 28/02/2020 17:45:48 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Partida generated by hbm2java
 */
public class Partida  implements java.io.Serializable {


     private Integer partidaId;
     private Date data;
     private String guanyador;
     private Set moviments = new HashSet(0);

    public Partida() {
    }

    public Partida(Date data, String guanyador, Set moviments) {
       this.data = data;
       this.guanyador = guanyador;
       this.moviments = moviments;
    }
   
    public Integer getPartidaId() {
        return this.partidaId;
    }
    
    public void setPartidaId(Integer partidaId) {
        this.partidaId = partidaId;
    }
    public Date getData() {
        return this.data;
    }
    
    public void setData(Date data) {
        this.data = data;
    }
    public String getGuanyador() {
        return this.guanyador;
    }
    
    public void setGuanyador(String guanyador) {
        this.guanyador = guanyador;
    }
    public Set getMoviments() {
        return this.moviments;
    }
    
    public void setMoviments(Set moviments) {
        this.moviments = moviments;
    }




}


