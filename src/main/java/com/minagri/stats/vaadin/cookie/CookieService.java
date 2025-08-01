package com.minagri.stats.vaadin.cookie;

import com.minagri.stats.core.java.Maps;
import com.minagri.stats.core.java.Strings;
import com.vaadin.flow.server.VaadinService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.*;
import jakarta.servlet.http.Cookie;

import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static jakarta.json.Json.createReader;
import static jakarta.json.Json.createWriter;
import static java.lang.Integer.MAX_VALUE;

@ApplicationScoped
public class CookieService implements Serializable {

	public String getCookieValue(final String cookieName, final String key) {
		Optional<JsonObject> cookie = Optional.ofNullable(getCookieJson(cookieName));
		return cookie.map(jsonObject -> jsonObject.getString(key, null)).orElse(null);
	}

	public String getCookieValue(final String cookieName) {
		return getCookie(cookieName)
				.map(Cookie::getValue)
				.map(s -> URLDecoder.decode(s, StandardCharsets.UTF_8))
				.orElse(null);
	}

	public JsonObject getCookieJson(final String cookieName) {
		Optional<JsonObject> cookie;
		try {
			cookie = Optional.ofNullable(getCookieValue(cookieName))
					.map(CookieService::toJson);
		} catch (Exception e) {
			cookie = Optional.empty();
		}
		return cookie.orElse(null);
	}

	public void refreshCookie(Map<String, String> cookieValues, String cookieName) {
		if (Maps.isNotEmpty(cookieValues)) {
			if (Strings.isEmpty(cookieName)) {
				throw new NullPointerException("Cookie name can not be empty");
			}

			refreshCookie(toString(cookieValues), cookieName);
		}
	}

	public void refreshCookie(JsonObject cookieValues, String cookieName) {
		if (Strings.isEmpty(cookieName)) {
			throw new NullPointerException("Cookie name can not be empty");
		}

		refreshCookie(toString(cookieValues), cookieName);
	}

	public void refreshCookie(final String cookieString, final String cookieName) {
		Cookie cookie = getCookie(cookieName).orElseGet(() -> new Cookie(cookieName, ""));

		cookie.setMaxAge(MAX_VALUE);

		cookie.setValue(URLEncoder.encode(cookieString, StandardCharsets.UTF_8));

		cookie.setPath(VaadinService.getCurrentRequest().getContextPath());
		VaadinService.getCurrentResponse().addCookie(cookie);
	}

	private static Optional<Cookie> getCookie(final String cookieName) {
		return Arrays.stream(VaadinService.getCurrentRequest().getCookies())
				.filter(c -> c.getName().equals(cookieName))
				.findFirst();
	}

	private static JsonObject toJson(final String value) {
		try (JsonReader reader = createReader(new StringReader(value))) {
			return reader.readObject();
		}
	}

	private static String toString(Map<String, String> cookieValues) {
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
		cookieValues.forEach(jsonObjectBuilder::add);
		return toString(jsonObjectBuilder.build());
	}

	private static String toString(JsonObject obj) {
		StringWriter writer = new StringWriter();
		try (JsonWriter jsonWriter = createWriter(writer)) {
			jsonWriter.writeObject(obj);
			return writer.toString();
		}
	}

}
