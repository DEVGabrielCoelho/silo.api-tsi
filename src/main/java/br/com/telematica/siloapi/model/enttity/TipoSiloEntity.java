package br.com.telematica.siloapi.model.enttity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipo_silo")
public class TipoSiloEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer tsicod;
    @Column(nullable = false)
    private Integer empcod;
    @Column(length = 50 ,nullable = false)
    private String tsides;

    public TipoSiloEntity() {
    }

    public TipoSiloEntity(Integer tsicod, Integer empcod, String tsides) {
        this.tsicod = tsicod;
        this.empcod = empcod;
        this.tsides = tsides;
    }

    public Integer getTsicod() {
        return tsicod;
    }

    public void setTsicod(Integer tsicod) {
        this.tsicod = tsicod;
    }

    public Integer getEmpcod() {
        return empcod;
    }

    public void setEmpcod(Integer empcod) {
        this.empcod = empcod;
    }

    public String getTsides() {
        return tsides;
    }

    public void setTsides(String tsides) {
        this.tsides = tsides;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TipoSiloEntity [tsicod=");
        builder.append(tsicod);
        builder.append(", empcod=");
        builder.append(empcod);
        builder.append(", tsides=");
        builder.append(tsides);
        builder.append("]");
        return builder.toString();
    }

}
