package com.minagri.stats.vaadin.cookie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public abstract class CookieForm {

	@JsonIgnore
	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
			.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	@Inject
	@JsonIgnore
	CookieService cookieService;

	public <T> void populateFieldsFromCookieIfPresent(final Class<T> clazz, String cookieName) {
		Optional.ofNullable(cookieService.getCookieJson(cookieName)).ifPresent(cookieJson -> {
			final Set<String> keys = getNonNullJsonKeys(cookieJson);
			try {
				T newValue = objectMapper.readValue(cookieJson.toString(), clazz);
				Arrays.stream(this.getClass().getDeclaredFields())
						.filter(field -> keys.contains(field.getName()))
						.forEach(field -> populateField(field, this, newValue));
			} catch (JsonProcessingException e) {
				log.warn("Invalid json retrieved from the cookie", e);
			}

		});
	}

	public void populateCookieFromFields(String cookieName) {
		try {
			cookieService.refreshCookie(objectMapper.writeValueAsString(this), cookieName);
		} catch (JsonProcessingException e) {
			log.warn("Invalid json produced from the fields", e);
		}
	}

	private static Set<String> getNonNullJsonKeys(JsonObject cookieJson) {
		return cookieJson.entrySet()
				.stream()
				.filter(entrySet -> entrySet.getValue() != null)
				.map(Map.Entry::getKey)
				.collect(Collectors.toSet());
	}

	@SneakyThrows
	private static <T> void populateField(final Field field, final T currentObject, final T newObject) {
		field.setAccessible(true);
		final Object oldValue = field.get(currentObject);
		if (oldValue == null) {
			final Object valueToInsert = field.get(newObject);
			field.set(currentObject, valueToInsert);
		}
	}
}
