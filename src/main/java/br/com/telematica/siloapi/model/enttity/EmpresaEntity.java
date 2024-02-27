package br.com.telematica.siloapi.model.enttity;

import jakarta.persistence.*;

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
    private Integer empcnpl;

    public EmpresaEntity() {
    }

    public EmpresaEntity(Integer empcod, String empnom, Integer empcnpl) {
        this.empcod = empcod;
        this.empnom = empnom;
        this.empcnpl = empcnpl;
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

    public Integer getEmpcnpl() {
        return empcnpl;
    }

    public void setEmpcnpl(Integer empcnpl) {
        this.empcnpl = empcnpl;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EmpresaEntity{");
        sb.append("empcod=").append(empcod);
        sb.append(", empnom='").append(empnom).append('\'');
        sb.append(", empcnpl=").append(empcnpl);
        sb.append('}');
        return sb.toString();
    }
}
