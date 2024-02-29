package br.com.telematica.siloapi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "empresa")
public class EmpresaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer empcod;
    @Column(length = 150, nullable = false)
    private String empnom;
    @Column(length = 150, nullable = false)
    private Integer empcnp;

    public EmpresaEntity() {
    }

    public EmpresaEntity(Integer empcod, String empnom, Integer empcnpl) {
        this.empcod = empcod;
        this.empnom = empnom;
        this.empcnp = empcnpl;
    }

    public Integer getEmpcod() {
        return empcod;
    }

    public void setEmpcod(Integer empcod) {
        this.empcod = empcod;
    }

    public String getEmpnom() {
        return empnom;
    }

    public void setEmpnom(String empnom) {
        this.empnom = empnom;
    }

    public Integer getEmpcnp() {
        return empcnp;
    }

    public void setEmpcnp(Integer empcnpl) {
        this.empcnp = empcnpl;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EmpresaEntity{");
        sb.append("empcod=").append(empcod);
        sb.append(", empnom='").append(empnom).append('\'');
        sb.append(", empcnp=").append(empcnp);
        sb.append('}');
        return sb.toString();
    }
}
