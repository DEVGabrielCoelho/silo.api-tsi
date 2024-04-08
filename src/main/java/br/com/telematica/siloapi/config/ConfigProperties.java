package br.com.telematica.siloapi.config;

import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.nimbusds.jose.shaded.gson.Gson;

import jakarta.annotation.PostConstruct;

@Configuration
public class ConfigProperties {

	private static Logger logger = LoggerFactory.getLogger(ConfigProperties.class);

	@Value("${application.authenticationList}")
	private Optional<String[]> AUTHENTICATION_LIST_URL;
	@Value("${application.authPost}")
	private Optional<String[]> AUTH_POST;
	@Value("${application.authGets}")
	private Optional<String[]> AUTH_GET;
	@Value("${application.authPuts}")
	private Optional<String[]> AUTH_PUT;
	@Value("${application.authDels}")
	private Optional<String[]> AUTH_DEL;
	@Value("${application.auth.time.min}")
	private Optional<Long> AUTH_TIME_MIN;

	@PostConstruct
	public void validateProperties() {
		checkProperty("AUTHENTICATION_LIST_URL", AUTHENTICATION_LIST_URL);
		checkProperty("AUTH_POST", AUTH_POST);
		checkProperty("AUTH_GET", AUTH_GET);
		checkProperty("AUTH_PUT", AUTH_PUT);
		checkProperty("AUTH_DEL", AUTH_DEL);
		checkPropertyLong("AUTH_TIME_MIN", AUTH_TIME_MIN);
	}

	private void checkProperty(String propertyName, Optional<String[]> propertyValue) {
		propertyValue.orElseThrow(() -> new IllegalStateException("Configuration error: Property '" + propertyName + "' is not defined in config.properties"));
	}

	private void checkPropertyLong(String propertyName, Optional<Long> propertyValue) {
		propertyValue.orElseThrow(() -> new IllegalStateException("Configuration error: Property '" + propertyName + "' is not defined in config.properties"));
	}

	@PostConstruct
	public Optional<Long> AUTH_TIME_MIN() {
		logger.debug("AUTH_TIME_MIN: " + new Gson().toJson(AUTH_TIME_MIN));
		return AUTH_TIME_MIN;
	}

	@PostConstruct
	public Optional<String[]> AUTHENTICATION_LIST_URL() {
		logger.debug("AUTHENTICATION_LIST_URL: " + new Gson().toJson(AUTHENTICATION_LIST_URL));
		return AUTHENTICATION_LIST_URL;
	}

	@PostConstruct
	public String[] AUTH_POST() {
		var formatString = addStringToArray(AUTH_POST);
		logger.debug("AUTH_POST: " + new Gson().toJson(formatString));
		return formatString;
	}

	@PostConstruct
	public String[] AUTH_GET() {
		var formatString = addStringToArray(AUTH_GET);
		logger.debug("AUTH_POST: " + new Gson().toJson(formatString));
		return formatString;
	}

	@PostConstruct
	public String[] AUTH_PUT() {
		var formatString = addStringToArray(AUTH_PUT);
		logger.debug("AUTH_POST: " + new Gson().toJson(formatString));
		return formatString;
	}

	@PostConstruct
	public String[] AUTH_DEL() {
		var formatString = addStringToArray(AUTH_DEL);
		logger.debug("AUTH_POST: " + new Gson().toJson(formatString));
		return formatString;
	}

	public static String[] addStringToArray(Optional<String[]> string) {
		String admin = "admin";

		validateInput(string, admin);

		int newArrayLength = string.get().length + 1;

		String[] newArray = new String[newArrayLength];

		System.arraycopy(string, 0, newArray, 0, string.get().length);

		newArray[newArrayLength - 1] = admin;

		return Arrays.stream(newArray).map(String::toUpperCase).toArray(String[]::new);
	}

	private static void validateInput(Optional<String[]> aUTH_DEL2, String newString) {
		if (aUTH_DEL2 == null) {
			throw new NullPointerException("Original array cannot be null");
		}

		if (newString == null) {
			throw new NullPointerException("New string cannot be null");
		}
	}

}
