package br.com.telematica.siloapi.model.enttity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "permissao")
public class PermissaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer percod;
    @Column(length = 100, nullable = false)
    private String perdes;
    @Column(length = 1)
    private Long perpla;
    @Column(length = 1)
    private Long persil;
    @Column(length = 1)
    private Long permed;
    @Column(length = 1)
    private Long pertsi;
    @Column(length = 1)
    private Long peremp;

    public PermissaoEntity() {
    }

    public PermissaoEntity(Integer percod, String perdes, Long perpla, Long persil, Long permed, Long pertsi, Long peremp) {
        this.percod = percod;
        this.perdes = perdes;
        this.perpla = perpla;
        this.persil = persil;
        this.permed = permed;
        this.pertsi = pertsi;
        this.peremp = peremp;
    }

    public Integer getPercod() {
        return percod;
    }

    public void setPercod(Integer percod) {
        this.percod = percod;
    }

    public String getPerdes() {
        return perdes;
    }

    public void setPerdes(String perdes) {
        this.perdes = perdes;
    }

    public Long getPerpla() {
        return perpla;
    }

    public void setPerpla(Long perpla) {
        this.perpla = perpla;
    }

    public Long getPersil() {
        return persil;
    }

    public void setPersil(Long persil) {
        this.persil = persil;
    }

    public Long getPermed() {
        return permed;
    }

    public void setPermed(Long permed) {
        this.permed = permed;
    }

    public Long getPertsi() {
        return pertsi;
    }

    public void setPertsi(Long pertsi) {
        this.pertsi = pertsi;
    }

    public Long getPeremp() {
        return peremp;
    }

    public void setPeremp(Long peremp) {
        this.peremp = peremp;
    }

}
