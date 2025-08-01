package com.minagri.stats.vaadin.component.layout;

import com.minagri.stats.core.java.Strings;
import com.minagri.stats.vaadin.component.html.LCMDiv;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.formlayout.FormLayout;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class GridFormLayout extends LCMDiv {

	@Getter
	private List<FieldInsideFormItem> allFieldsWithFormItem = new ArrayList<>();

	private Focusable elementToFocusOnAfterLastField;

	public GridFormLayout() {
		this.withStyle("display", "grid");
	}

	public GridFormLayout withRows(int number) {
		this.withStyle("grid-template-rows", String.format("repeat(%d , minmax(41.5px,auto)", number));
		return this;
	}

	public GridFormLayout withColumns(int number) {
		this.withStyle("grid-template-columns", String.format("repeat(%d, 1fr)", number));
		return this;
	}

	public GridFormLayout withRowGap(String rowGap) {
		if (Strings.isNotBlank(rowGap)) {
			this.withStyle("grid-row-gap", rowGap);
		}
		return this;
	}

	public GridFormLayout withColumnGap(String columnGap) {
		if (Strings.isNotBlank(columnGap)) {
			this.withStyle("grid-column-gap", columnGap);
		}
		return this;
	}

	public GridFormLayout withFocusAfterLastField(Focusable componentToFocusOn) {
		this.elementToFocusOnAfterLastField = componentToFocusOn;
		return this;
	}

	public LCMFormItem addDefaultFormItem(String gridRow, String gridColumn, Component field, String id, TranslationKey translationKey, Component... extraComponentsToAdd) {
		return addFormItemWithTopLabel(gridRow, gridColumn, field, id, translationKey, "small-field", extraComponentsToAdd);
	}

	public LCMFormItem addFormItemWithTopLabel(String gridRow, String gridColumn, Component field, String id, TranslationKey translationKey, String className, Component... extraComponentsToAdd) {
		return addFormItem(gridRow, gridColumn, field, id, translationKey, className, FormLayout.ResponsiveStep.LabelsPosition.TOP, extraComponentsToAdd);
	}

	public LCMFormItem addFormItemWithSideLabel(String gridRow, String gridColumn, Component field, String id, TranslationKey translationKey, String className, Component... extraComponentsToAdd) {
		return addFormItem(gridRow, gridColumn, field, id, translationKey, className, FormLayout.ResponsiveStep.LabelsPosition.ASIDE, extraComponentsToAdd);
	}

	public void focusNextField(AbstractField currentField) {
		List<Focusable> fields = getAllFieldsWithFormItem().stream().filter(fieldInsideFormItem -> fieldInsideFormItem.isEnabled() && fieldInsideFormItem.isVisible())
				.map(fieldInsideFormItem -> (Focusable) fieldInsideFormItem.getField()).collect(Collectors.toList());
		int indexOfCurrent = fields.indexOf(currentField);
		if (indexOfCurrent > -1 && indexOfCurrent < fields.size() - 1) {
			fields.get(indexOfCurrent + 1).focus();
		} else if (indexOfCurrent == fields.size() - 1 && elementToFocusOnAfterLastField != null) {
			elementToFocusOnAfterLastField.focus();
		}
	}

	private LCMFormItem addFormItem(String gridRow, String gridColumn, Component field, String id, TranslationKey translationKey, String className, FormLayout.ResponsiveStep.LabelsPosition position, Component... extraComponentsToAdd) {
		LCMFormItem formItem = new LCMFormItem()
				.withAdd(field)
				.withId(id);

		if (!isNull(translationKey)) {
			formItem.withLabel(translationKey);
		}

		formItem.getElement().setAttribute("label-position", position.equals(FormLayout.ResponsiveStep.LabelsPosition.TOP) ? "top" : "aside");
		if (className != null) {
			formItem.withClassName(className);
		}
		if (gridRow != null) {
			if (field instanceof AbstractField) {
				formItem.withStyle("grid-row", gridRow);
			} else if (field instanceof HasSize) {
				((HasStyle) field).getStyle().set("grid-row", gridRow);
			}
		}
		if (gridColumn != null) {
			if (field instanceof AbstractField) {
				formItem.withStyle("grid-column", gridColumn);
			} else if (field instanceof HasSize) {
				((HasStyle) field).getStyle().set("grid-column", gridColumn);
			}
		}
		if (extraComponentsToAdd != null) {
			formItem.add(extraComponentsToAdd);
		}
		if (field instanceof HasSize) {
			((HasSize) field).setWidthFull();
		}
		if (field instanceof HasStyle && className != null) {
			((HasStyle) field).addClassName("small-field");
		}
		field.setId(id + "_field");
		if (field instanceof AbstractField) {
			this.add(formItem);
			this.allFieldsWithFormItem.add(new FieldInsideFormItem((AbstractField) field, formItem));
		} else {
			field.setId(id);
			this.add(field);
		}
		return formItem;
	}

	public void setFocusOnField(AbstractField currentField) {
		List<Focusable> fields = getAllFieldsWithFormItem().stream().filter(fieldInsideFormItem -> fieldInsideFormItem.isEnabled() && fieldInsideFormItem.isVisible())
				.map(fieldInsideFormItem -> (Focusable) fieldInsideFormItem.getField()).collect(Collectors.toList());
		int indexOfCurrent = fields.indexOf(currentField);
		fields.get(indexOfCurrent).focus();
	}

	public List<FieldInsideFormItem> getEnabledFormItems() {
		return getAllFieldsWithFormItem()
				.stream()
				.filter(item -> item.isEnabled() && item.isVisible())
				.collect(Collectors.toList());
	}


}