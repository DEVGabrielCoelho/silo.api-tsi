package br.com.telematica.siloapi.model.dto;

import br.com.telematica.siloapi.model.entity.SiloModulo;
import br.com.telematica.siloapi.model.enums.StatusDeviceEnum;
import br.com.telematica.siloapi.utils.Utils;

public class SiloModuloDTO extends Codigo {

	private SiloDTO silo;
	private String descricao;
	private Long totalSensor;
	private String numSerie;
	private Long timeoutKeepAlive;
	private Long timeoutMedicao;
	private String ultimaKeepAlive;
	private String ultimaMedicao;
	private Integer gmt;
	private String corKeepAlive;
	private String corMedicao;
	private StatusDeviceEnum status;
	private Double volumeCheio = Double.valueOf(0);
	private Double volumeStatus = Double.valueOf(0);

	public SiloModuloDTO(Long codigo, SiloDTO silo, String descricao, Long totalSensor, String numSerie, Long timeoutKeepAlive, Long timeoutMedicao, String ultimaKeepAlive, String ultimaMedicao, Integer gmt, String corKeepAlive, String corMedicao, StatusDeviceEnum status, Double volumeCheio, Double volumeStatus) {
		super(codigo);
		this.silo = silo;
		this.descricao = descricao;
		this.totalSensor = totalSensor;
		this.numSerie = numSerie;
		this.timeoutKeepAlive = timeoutKeepAlive;
		this.timeoutMedicao = timeoutMedicao;
		this.ultimaKeepAlive = ultimaKeepAlive;
		this.ultimaMedicao = ultimaMedicao;
		this.gmt = gmt;
		this.corKeepAlive = corKeepAlive;
		this.corMedicao = corMedicao;
		this.status = status;
		this.volumeCheio = volumeCheio;
		this.volumeStatus = volumeStatus;
	}

	public SiloModuloDTO(SiloModulo silomodulo) {
		this.setCodigo(silomodulo.getSmocod());
		this.silo = new SiloDTO(silomodulo.getSilo());
		this.descricao = silomodulo.getSmodes();
		this.totalSensor = silomodulo.getSmotse();
		this.numSerie = silomodulo.getSmonse();
		this.timeoutKeepAlive = silomodulo.getSmotke();
		this.timeoutMedicao = silomodulo.getSmotme();
		this.ultimaKeepAlive = silomodulo.getSmohke() == null ? null : Utils.convertDateToString(silomodulo.getSmohke());
		this.ultimaMedicao = silomodulo.getSmohme() == null ? null : Utils.convertDateToString(silomodulo.getSmohme());
		this.gmt = silomodulo.getSmogmt();
		this.corKeepAlive = silomodulo.getSmocke();
		this.corMedicao = silomodulo.getSmocme();
		this.status = StatusDeviceEnum.mapDescricaoToStatusDevice(silomodulo.getSmosta());
	}

	public SiloModuloDTO volumeSilo(Double volumeCheio, Double volumeStatus) {
		this.volumeCheio = Utils.converterMmParaM(volumeCheio);
		this.volumeStatus = Utils.converterMmParaM(volumeStatus);
		return this;
	}



	public SiloModuloDTO(Long codigo) {
		super(codigo);
	}

    public SiloDTO getSilo() {
        return silo;
    }

    public void setSilo(SiloDTO silo) {
        this.silo = silo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getTotalSensor() {
        return totalSensor;
    }

    public void setTotalSensor(Long totalSensor) {
        this.totalSensor = totalSensor;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public Long getTimeoutKeepAlive() {
        return timeoutKeepAlive;
    }

    public void setTimeoutKeepAlive(Long timeoutKeepAlive) {
        this.timeoutKeepAlive = timeoutKeepAlive;
    }

    public Long getTimeoutMedicao() {
        return timeoutMedicao;
    }

    public void setTimeoutMedicao(Long timeoutMedicao) {
        this.timeoutMedicao = timeoutMedicao;
    }

    public String getUltimaKeepAlive() {
        return ultimaKeepAlive;
    }

    public void setUltimaKeepAlive(String ultimaKeepAlive) {
        this.ultimaKeepAlive = ultimaKeepAlive;
    }

    public String getUltimaMedicao() {
        return ultimaMedicao;
    }

    public void setUltimaMedicao(String ultimaMedicao) {
        this.ultimaMedicao = ultimaMedicao;
    }

    public Integer getGmt() {
        return gmt;
    }

    public void setGmt(Integer gmt) {
        this.gmt = gmt;
    }

    public String getCorKeepAlive() {
        return corKeepAlive;
    }

    public void setCorKeepAlive(String corKeepAlive) {
        this.corKeepAlive = corKeepAlive;
    }

    public String getCorMedicao() {
        return corMedicao;
    }

    public void setCorMedicao(String corMedicao) {
        this.corMedicao = corMedicao;
    }

    public StatusDeviceEnum getStatus() {
        return status;
    }

    public void setStatus(StatusDeviceEnum status) {
        this.status = status;
    }

    public Double getVolumeCheio() {
        return volumeCheio;
    }

    public void setVolumeCheio(Double volumeCheio) {
        this.volumeCheio = volumeCheio;
    }

    public Double getVolumeStatus() {
        return volumeStatus;
    }

    public void setVolumeStatus(Double volumeStatus) {
        this.volumeStatus = volumeStatus;
    }


	

}
