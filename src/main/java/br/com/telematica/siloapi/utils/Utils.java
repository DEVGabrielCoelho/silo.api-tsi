package br.com.telematica.siloapi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import br.com.telematica.siloapi.exception.ResponseGlobalModel;

public class Utils {

	private Utils() {
		throw new IllegalStateException("Utility class");
	}

	private static final DateTimeFormatter dtfEditado = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
	private static final DateTimeFormatter dtfPadrao = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	private static SimpleDateFormat sdf;
	private static final SimpleDateFormat sdfbase = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	public static String convertDateToString(Date smohke) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
		return dtfPadrao.format(localDateTime);
	}

	public static Date addGmtToDateTime(Integer gmt) {
		ZoneOffset offsetGMT = ZoneOffset.ofHours(gmt / 60);
		OffsetDateTime offsetDateTime = OffsetDateTime.parse(getDataHoraGMT0(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		OffsetDateTime offsetDateTimeGMT = offsetDateTime.withOffsetSameInstant(offsetGMT);
		return Date.from(offsetDateTimeGMT.toInstant());
	}

	public static String addGmtToDateTimeSendString(Date date) {
		Objects.requireNonNull(date, "A Data de entrada para conversão de Date para String está nula.");
		LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return dtfEditado.format(localDateTime);
	}

	public static String removeGmtToDateTime(String horarioComGMT, Integer gmtEmMinutos) {
		LocalDateTime dataAtual = LocalDateTime.parse(horarioComGMT, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		LocalDateTime dataSemOffset = dataAtual.minusMinutes(gmtEmMinutos);
		return dataSemOffset.format(dtfEditado);
	}

	public static String getDataHoraGMT0() {
		Instant agoraGMT = Instant.now();
		OffsetDateTime offsetDateTimeGMT0 = agoraGMT.atOffset(ZoneOffset.UTC);
		return offsetDateTimeGMT0.format(dtfPadrao);
	}

	public static Date convertStringToDate(String dateString) {
		Objects.requireNonNull(dateString, "A Data de entrada para conversão de string para Date está nula.");
		LocalDateTime localDateTime = LocalDateTime.parse(dateString, dtfEditado);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static String dateToString(Date date) {
		Objects.requireNonNull(date, "A Data de entrada para conversão de Date para String está nula.");
		LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return dtfEditado.format(localDateTime);
	}

	public static String newDateString() {
		return dateToString(new Date());
	}

	public static String bytesParaBase64(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}

	public static byte[] base64ParaBytes(String base64String) {
		return Base64.getDecoder().decode(base64String);
	}

	public static String encode(String input) {
		return Base64.getEncoder().encodeToString(input.getBytes());
	}

	public static String decode(String base64Input) {
		byte[] decodedBytes = Base64.getDecoder().decode(base64Input);
		return new String(decodedBytes);
	}

	public static Pageable consultaPage(String ordenarEntity, @NonNull String direcao, Integer pagina,
			Integer tamanho) {
		Sort.Direction sortDirection = Sort.Direction.fromString(direcao);
		Sort sort = Sort.by(sortDirection, ordenarEntity);
		return PageRequest.of(pagina, tamanho, sort);
	}

	public static ResponseGlobalModel responseMessageError(String message) {
		return new ResponseGlobalModel(true, message, Utils.convertDateToString(new Date()));
	}

	public static ResponseGlobalModel responseMessageSucess(String message) {
		return new ResponseGlobalModel(false, message, Utils.convertDateToString(new Date()));
	}

	public static String sdfBaseDateforString() {
		var date = new Date();
		return sdfbase.format(date);
	}

	public static String sdfDateforString(Date date) {
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static Date sdfStringforDate(String date) throws ParseException {
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			throw new ParseException("Error parsing date: " + e.getMessage(), 0);
		}
	}

	public static Date sdfDateTimeZone(String msidth) throws ParseException {
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

		try {
			return sdf.parse(msidth);
		} catch (ParseException e) {
			throw new ParseException("Error parsing date: " + e.getMessage(), 0);
		}
	}

	public static Double calcularVolumeVertical(double raio, double altura) {
		if (raio < 0 || altura < 0) {
			throw new IllegalArgumentException("O raio e a altura devem ser positivos.");
		}
		return Math.PI * Math.pow(raio, 2) * altura;
	}

	public static Double calcularVolumeHorizontal(double comprimento, double largura, double altura) {
		if (comprimento < 0 || largura < 0 || altura < 0) {
			throw new IllegalArgumentException("O comprimento, a largura e a altura devem ser positivos.");
		}
		return comprimento * largura * altura;
	}


	public static String convertTimestampToDateStr(int timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
        return dateTime.format(formatter);
    }
	public static Date convertTimestampToDate(int timestamp) throws ParseException {
        String timeStampStr = convertTimestampToDateStr(timestamp);
        return sdfStringforDate(timeStampStr);
    }

	public static double converterCmParaMm(double cm) {
        return cm * 10.0;
    }

    // Função para converter mm para cm
    public static double converterMmParaCm(double mm) {
        return mm / 10.0;
    }

	public static double converterMmParaM(double mm) {
        return mm / 1000.0;
    }

}
