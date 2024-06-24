package br.com.telematica.siloapi.records;

import java.util.List;

import org.springframework.lang.NonNull;

public record CheckAbrangenciaRec(List<Long> listAbrangencia, @NonNull Integer isHier) {

}
