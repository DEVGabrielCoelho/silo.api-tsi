package br.com.telematica.siloapi.records;

import java.util.List;

import org.springframework.lang.NonNull;

public record CheckAbrangenciaRec(@NonNull List<Long> listAbrangencia, @NonNull Integer isHier) {

}
