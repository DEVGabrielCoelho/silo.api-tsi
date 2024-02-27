package br.com.telematica.siloapi.model.enttity;

import br.com.telematica.siloapi.model.enums.RoleColectionEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "nivel")
public class NivelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long nivcod;
    @Column(nullable = false)
    private String nivnom;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleColectionEnum nivrol;
    @Column(nullable = false)
    private Integer percod;


    public NivelEntity() {
    }

    public Long getNivcod() {
        return nivcod;
    }

    public void setNivcod(Long nivcod) {
        this.nivcod = nivcod;
    }

    public String getNivnom() {
        return nivnom;
    }

    public void setNivnom(String nivnom) {
        this.nivnom = nivnom;
    }

    public RoleColectionEnum getNivrol() {
        return nivrol;
    }

    public void setNivrol(RoleColectionEnum nivrol) {
        this.nivrol = nivrol;
    }

    public Integer getPercod() {
        return percod;
    }

    public void setPercod(Integer percod) {
        this.percod = percod;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("NivelEntity [nivcod=");
        builder.append(nivcod);
        builder.append(", nivnom=");
        builder.append(nivnom);
        builder.append(", nivrol=");
        builder.append(nivrol);
        builder.append(", percod=");
        builder.append(percod);
        builder.append("]");
        return builder.toString();
    }
    



}
