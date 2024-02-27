package br.com.telematica.siloapi.model.dto;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Medicão")
public class MedicaoDTO {

    @Schema(description = "Data", example = "2021-08-01T00:00:00.000Z", required = true)
    private Date data;
    @Schema(description = "Código", example = "1", required = true)
    private Integer codigo;
    @Schema(description = "Umidade", example = "1.0", required = false)
    private Double umidade;
    @Schema(description = "Ana", example = "1.0", required = false)
    private Double ana;
    @Schema(description = "Barometro", example = "1.0", required = false)
    private Double barometro;
    @Schema(description = "Temperatura", example = "1.0", required = false)
    private Double temperatura;
    @Schema(description = "Distancia", example = "1.0", required = false)
    private Double distancia;

    public MedicaoDTO() {
    }

    public MedicaoDTO(Date data, Integer codigo, Double umidade, Double ana, Double barometro, Double temperatura, Double distancia) {
        this.data = data;
        this.codigo = codigo;
        this.umidade = umidade;
        this.ana = ana;
        this.barometro = barometro;
        this.temperatura = temperatura;
        this.distancia = distancia;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Double getUmidade() {
        return umidade;
    }

    public void setUmidade(Double umidade) {
        this.umidade = umidade;
    }

    public Double getAna() {
        return ana;
    }

    public void setAna(Double ana) {
        this.ana = ana;
    }

    public Double getBarometro() {
        return barometro;
    }

    public void setBarometro(Double barometro) {
        this.barometro = barometro;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MedicaoDTO [data=");
        builder.append(data);
        builder.append(", codigo=");
        builder.append(codigo);
        builder.append(", umidade=");
        builder.append(umidade);
        builder.append(", ana=");
        builder.append(ana);
        builder.append(", barometro=");
        builder.append(barometro);
        builder.append(", temperatura=");
        builder.append(temperatura);
        builder.append(", distancia=");
        builder.append(distancia);
        builder.append("]");
        return builder.toString();
    }

}
