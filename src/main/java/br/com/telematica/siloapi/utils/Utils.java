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

	private static final DateTimeFormatter dtfEditado = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
	private static final DateTimeFormatter dtfPadrao = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdfbase = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	public static String convertDateToString() {
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
		String formatoDesejado = dataSemOffset.format(dtfEditado);
		return formatoDesejado;
	}

	public static String getDataHoraGMT0() {
		Instant agoraGMT = Instant.now();
		OffsetDateTime offsetDateTimeGMT0 = agoraGMT.atOffset(ZoneOffset.UTC);
		String formattedDateTime = offsetDateTimeGMT0.format(dtfPadrao);
		return formattedDateTime;
	}

	public static Date convertStringToDate(String dateString) {
		Objects.requireNonNull(dateString, "A Data de entrada para conversão de string para Date está nula.");
		LocalDateTime localDateTime = LocalDateTime.parse(dateString, dtfEditado);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static String convertDateToString(Date date) {
		Objects.requireNonNull(date, "A Data de entrada para conversão de Date para String está nula.");
		LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return dtfEditado.format(localDateTime);
	}

	public static String newDateString() {
		return convertDateToString(new Date());
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

	public static Pageable consultaPage(String ordenarEntity, @NonNull String direcao, Integer pagina, Integer tamanho) {
		Sort.Direction sortDirection = Sort.Direction.fromString(direcao);
		Sort sort = Sort.by(sortDirection, ordenarEntity);
		Pageable pageable = PageRequest.of(pagina, tamanho, sort);
		return pageable;
	}

	public static ResponseGlobalModel responseMessageError(String message) {
		return new ResponseGlobalModel(true, message, Utils.convertDateToString());
	}

	public static ResponseGlobalModel responseMessageSucess(String message) {
		return new ResponseGlobalModel(false, message, Utils.convertDateToString());
	}

	public static String sdfBaseDateforString() {
		var date = new Date();
		return sdfbase.format(date);
	}

	public static String sdfDateforString(Date date) {
		return sdf.format(date);
	}

	public static Date sdfStringforDate(String date) throws Exception {
		try {
			return sdf.parse(date);
		} catch (Exception e) {
			throw new Exception("Error parsing date: " + e.getMessage());
		}
	}

	public static Date sdfDateTimeZone(String msidth) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

		try {
			Date date = sdf.parse(msidth);
			System.out.println("Date: " + date);
			return date;
		} catch (ParseException e) {
			throw new ParseException("Error parsing date: " + e.getMessage(), 0);
		}
	}

}
