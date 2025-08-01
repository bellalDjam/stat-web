package com.minagri.stats.vaadin.component.layout;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FieldInsideFormItem {

	@Nonnull
	private final AbstractField field;
	@Nonnull
	private final LCMFormItem formItem;
	private LCMVerticalLayout info;

	public boolean isEnabled() {
		return field.isEnabled();
	}

	public boolean isVisible() {
		return getFormItem().isVisible();
	}

	public void setEnabled(boolean enabled) {
		field.setEnabled(enabled);
	}

	public void setVisible(boolean visible) {
		getFormItem().setVisible(visible);
	}

	public String getId() {
		return getFormItem().getId().orElse("");
	}

	public void addInfo(Component... components) {
		initInfo();
		this.info.add(components);
	}

	public void clearInfo() {
		if (this.info != null) {
			this.info.removeAll();
		}
	}

	private void initInfo() {
		if (this.info == null) {
			this.info = new LCMVerticalLayout().withNoSpacing();
			this.info.getStyle()
					.set("font-size", "var(--lumo-font-size-s)")
					.set("color", "var(--lumo-secondary-text-color)");
			getFormItem().add(this.info);
		}
	}

}
