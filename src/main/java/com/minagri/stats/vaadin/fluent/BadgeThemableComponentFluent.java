package com.minagri.stats.vaadin.fluent;

import com.minagri.stats.core.java.Strings;
import com.vaadin.flow.component.Component;
import lombok.Getter;

import java.util.*;

public interface BadgeThemableComponentFluent<FLUENT extends Component> {

	FLUENT getFluent();

	default FLUENT withBadgeVariant(BadgeVariant... variants) {
		return withBadgeVariant(variants != null ? Arrays.stream(variants).map(BadgeVariant::getCssName).toArray(String[]::new) : null);
	}

	default FLUENT withBadgeVariant(String... variants) {
		if (variants == null || variants.length == 0) {
			return getFluent();
		}

		String currentTheme = getFluent().getElement().getAttribute("theme");

		List<String> params = new ArrayList<>();
		if (Strings.isNotBlank(currentTheme)) {
			params.addAll(Arrays.stream(Strings.split(currentTheme, " ").toArray(new String[0])).filter(Strings::isNotBlank).toList());
		}

		if (!params.contains("badge")) {
			params.add("badge");
		}

		for (String variant : variants) {
			params.add(variant);
		}

		getFluent().getElement().setAttribute("theme", Strings.joinWithSpace(params));

		return getFluent();
	}

	default FLUENT withoutBadgeVariant(BadgeVariant... variants) {
		if (variants == null || variants.length <= 0) {
			return getFluent();
		}

		return withoutBadgeVariant(Arrays.stream(variants).map(BadgeVariant::getCssName).toArray(String[]::new));
	}

	default FLUENT withoutBadgeVariant(String... variants) {
		if (variants == null || variants.length <= 0) {
			return getFluent();
		}

		String currentTheme = getFluent().getElement().getAttribute("theme");

		List<String> params = new ArrayList<>();
		if (Strings.isNotBlank(currentTheme)) {
			params.addAll(Arrays.stream(currentTheme.split(" ")).filter(Strings::isNotBlank).toList());
		}

		for (String variant : variants) {
			params.remove(variant);
		}

		getFluent().getElement().setAttribute("theme", Strings.joinWithSpace(params));
		return getFluent();
	}

	default FLUENT withoutBadge() {
		return withoutBadgeVariant("badge");
	}

	default FLUENT withBadge() {
		return withBadgeVariant((String) null);
	}

	@Getter
	enum BadgeVariant {
		PRIMARY("primary"),
		SMALL("small"),
		PILL("pill"),

		NORMAL(""),
		SUCCESS("success"),
		ERROR("error"),
		CONTRAST("contrast"),
		DEVELOPMENT("development"),
		QUALITY("quality"),
		REFERENCE("reference"),
		MAINTENANCE("maintenance"),
		PREPRODUCTION("preproduction"),
		EXTERNAL_ACCEPTANCE("external-acceptance"),
		TRAINING("training"),
		PRODUCTION("production");

		private String cssName;

		BadgeVariant(String cssName) {
			this.cssName = cssName;
		}

		public static Optional<BadgeVariant> fromCssName(String cssName) {
			Objects.requireNonNull(cssName);

			return Arrays.stream(BadgeVariant.values())
					.filter(badgeVariant -> badgeVariant.getCssName().equals(cssName))
					.findFirst();
		}
	}
}
