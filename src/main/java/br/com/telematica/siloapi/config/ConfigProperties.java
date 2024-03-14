package br.com.telematica.siloapi.config;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.nimbusds.jose.shaded.gson.Gson;

import jakarta.annotation.PostConstruct;

@Configuration
@PropertySource("classpath:config.properties")
public class ConfigProperties {

	private static Logger logger = LoggerFactory.getLogger(ConfigProperties.class);

	@Value("${authenticationList}")
	private String[] AUTHENTICATION_LIST_URL;
	@Value("${authPost}")
	private String[] AUTH_POST;
	@Value("${authGets}")
	private String[] AUTH_GET;
	@Value("${authPuts}")
	private String[] AUTH_PUT;
	@Value("${authDels}")
	private String[] AUTH_DEL;
	@Value("${auth.time.min}")
	private Long AUTH_TIME_MIN;

	@PostConstruct
	public void validateProperties() {
		checkProperty("AUTHENTICATION_LIST_URL", AUTHENTICATION_LIST_URL);
		checkProperty("AUTH_POST", AUTH_POST);
		checkProperty("AUTH_GET", AUTH_GET);
		checkProperty("AUTH_PUT", AUTH_PUT);
		checkProperty("AUTH_DEL", AUTH_DEL);
		checkProperty("AUTH_TIME_MIN", AUTH_TIME_MIN);
	}

	private void checkProperty(String propertyName, String[] propertyValue) {
		if (propertyValue == null) {
			throw new IllegalStateException("Configuration error: Property '" + propertyName + "' is not defined in config.properties");
		}
	}

	private void checkProperty(String propertyName, Long propertyValue) {
		if (propertyValue == null) {
			throw new IllegalStateException("Configuration error: Property '" + propertyName + "' is not defined in config.properties");
		}
	}

	@PostConstruct
	public Long AUTH_TIME_MIN() {
		logger.debug("AUTH_TIME_MIN: " + new Gson().toJson(AUTH_TIME_MIN));
		return AUTH_TIME_MIN;
	}

	@PostConstruct
	public String[] AUTHENTICATION_LIST_URL() {
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

	public static String[] addStringToArray(String[] originalArray) {
		String admin = "admin";

		validateInput(originalArray, admin);

		int newArrayLength = originalArray.length + 1;

		String[] newArray = new String[newArrayLength];

		System.arraycopy(originalArray, 0, newArray, 0, originalArray.length);

		newArray[newArrayLength - 1] = admin;

		return Arrays.stream(newArray).map(String::toUpperCase).toArray(String[]::new);
	}

	private static void validateInput(String[] originalArray, String newString) {
		if (originalArray == null) {
			throw new NullPointerException("Original array cannot be null");
		}

		if (newString == null) {
			throw new NullPointerException("New string cannot be null");
		}
	}

}
