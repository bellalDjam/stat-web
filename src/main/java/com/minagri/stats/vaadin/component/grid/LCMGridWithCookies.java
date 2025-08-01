package com.minagri.stats.vaadin.component.grid;

import com.minagri.stats.core.java.Strings;
import com.minagri.stats.core.jaxrs.control.JsonObjects;
import com.minagri.stats.vaadin.authorization.model.Action;
import com.minagri.stats.vaadin.cookie.CookieService;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.ColumnReorderEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.data.event.SortEvent;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.ValueProvider;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.json.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Slf4j
public class LCMGridWithCookies<VALUECLASS> extends LCMGrid<VALUECLASS> {

	private String cookieName = "";
	private static final String SEPARATOR = ",";
	private CookieService cookieService;

	@Override
	public LCMGridWithCookies<VALUECLASS> getFluent() {
		return this;
	}

	@Override
	public LCMGridWithCookies<VALUECLASS> setAction(Action action) {
		super.setAction(action);
		return this;
	}

	public void setAdaptableHeight(final int gridItemCount) {
		setAdaptableHeight(gridItemCount, 10);
	}

	public void setAdaptableHeight(final int gridItemCount, final int maxVisibleItems) {
		if (gridItemCount < maxVisibleItems) {
			this.setHeight((55 + (gridItemCount * 45)) + "px");
		} else {
			this.withHeightUndefined();
		}
	}

	private LCMColumn<VALUECLASS> applyTemplateColumnConfig(LCMColumn<VALUECLASS> column, TranslationKey columnTitle) {
		return column.withHeaderKey(columnTitle)
				.withTextAlignCenter()
				.withResizable()
				.withAutoWidth()
				.withVisible()
				.withKey(columnTitle.toString());
	}

	public void doSelectionWithEnter() {
		this.getElement()
				.executeJs(
						"this.addEventListener('keydown', e => e.key === 'Enter' &&(this.$connector.doSelection([this.getEventContext(e).item], true)) )");
	}

	public LCMGridWithCookies<VALUECLASS> applyUserPreferences(String cookieName) {
		this.cookieName = cookieName;
		this.cookieService = CDI.current().select(CookieService.class).get();

		try {
			// read saved grid config from cookies
			readConfigFromCookies();
			// Listener must be added after the read from the cookie as this action might perform so changes on the grid (and so avoid useless fire of reset of cookie)
			// add column reorder listener
			addColumnReorderListener(this::onColumnReorder);
			// add sort listener
			addSortListener(this::onSort);
		} catch (Exception ex) {
			// Reset the user's preferences in case the columns have been changed (e.g. new column added in a table).
			log.error("Issue with cookies - default user preferences set", ex);
		}

		return this;
	}

	private void onColumnReorder(ColumnReorderEvent<VALUECLASS> event) {
		String newOrder = event.getColumns().stream().map(Column::getKey).collect(Collectors.joining(SEPARATOR));
		saveConfigToCookies(newOrder, getCookieColumnOrderKey());
	}

	public void onColumnVisibilityChange(LCMColumn<VALUECLASS> column, boolean visible) {
		column.setVisible(visible);
		String newVisibility = getColumns().stream()
				.filter(Column::isVisible)
				.map(Column::getKey)
				.collect(Collectors.joining(SEPARATOR));
		saveConfigToCookies(newVisibility, getCookieColumnVisibilityKey());
	}

	private void onSort(SortEvent<Grid<VALUECLASS>, GridSortOrder<VALUECLASS>> event) {
		String newSorting = event.getSortOrder()
				.stream()
				.map(sort -> sort.getSorted().getKey() + ":" + sort.getDirection().name())
				.collect(Collectors.joining(SEPARATOR));
		saveConfigToCookies(newSorting, getCookieColumnSortingOrderKey());
	}

	private void readConfigFromCookies() {
		checkIfCookieNameSet();

		applyColumnOrderFromCookie();
		applyColumnVisibilityFromCookie();
		applyColumnSortingOrderFromCookie();
	}

	private void applyColumnOrderFromCookie() {
		String columnOrderCookie = cookieService.getCookieValue(this.cookieName, getCookieColumnOrderKey());
		if (!Strings.isEmpty(columnOrderCookie)) {
			String[] columnOrder = columnOrderCookie.split(SEPARATOR);
			List<Column<VALUECLASS>> columns = Arrays.stream(columnOrder)
					.map(this::getColumnByKey)
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
			try {
				setColumnOrder(columns);
			} catch (IllegalArgumentException ex) {
				// Reset the user's preferences in case the columns have been changed (e.g. new column added in a table).
				log.debug("The ordering of columns based on cookie has not been done due to differences with previous version - default preference set", ex);
			}
		}
	}

	private void applyColumnVisibilityFromCookie() {
		String columnVisibilityCookie = cookieService.getCookieValue(this.cookieName, getCookieColumnVisibilityKey());
		if (!Strings.isEmpty(columnVisibilityCookie)) {
			String[] visibleColumns = columnVisibilityCookie.split(SEPARATOR);
			for (Column<VALUECLASS> column : getColumns()) {
				column.setVisible(Arrays.asList(visibleColumns).contains(column.getKey()));
			}
		}
	}

	private void applyColumnSortingOrderFromCookie() {
		String columnSortingOrderCookie = cookieService.getCookieValue(this.cookieName, getCookieColumnSortingOrderKey());
		if (!Strings.isEmpty(columnSortingOrderCookie)) {
			String[] sortingOrder = columnSortingOrderCookie.split(SEPARATOR);
			sort(Arrays.stream(sortingOrder).map(sort -> {
				String[] parts = sort.split(":");
				Column<VALUECLASS> column = getColumnByKey(parts[0]);
				SortDirection direction = SortDirection.valueOf(parts[1]);
				return new GridSortOrder<>(column, direction);
			}).collect(Collectors.toList()));
		}
	}

	private String getCookieColumnSortingOrderKey() {
		return this.cookieName + "_sortingOrder";
	}

	private String getCookieColumnVisibilityKey() {
		return this.cookieName + "_visibility";
	}

	private String getCookieColumnOrderKey() {
		return this.cookieName + "_order";
	}

	private void saveConfigToCookies(final String newValue, final String key) {
		checkIfCookieNameSet();
		JsonObjectBuilder newCookieBuilder = Optional.ofNullable(cookieService.getCookieJson(this.cookieName))
				.map(oldCookie -> copyCookieExceptKey(key, oldCookie))
				.orElseGet(LCMGridWithCookies::createJsonObjectBuilder);
		newCookieBuilder.add(key, newValue);
		cookieService.refreshCookie(JsonObjects.toString(newCookieBuilder.build()), this.cookieName);
	}

	private JsonObjectBuilder copyCookieExceptKey(final String keyToExclude, final JsonObject oldCookie) {
		JsonObjectBuilder jsonObjectBuilder = createJsonObjectBuilder();
		for (Map.Entry<String, JsonValue> entry : oldCookie.entrySet()) {
			if (!Strings.equals(entry.getKey(), keyToExclude)) {
				jsonObjectBuilder.add(entry.getKey(), entry.getValue());
			}
		}
		return jsonObjectBuilder;
	}

	private static JsonObjectBuilder createJsonObjectBuilder() {
		JsonBuilderFactory jsonBuilderFactory = Json.createBuilderFactory(null);
		return jsonBuilderFactory.createObjectBuilder();
	}

	private void checkIfCookieNameSet() {
		if (Strings.isEmpty(this.cookieName)) {
			throw new NullPointerException("Grid cookie name should not be empty, call applyUserPreferences first");
		}
	}


	public LCMColumn<VALUECLASS> addColumn(Renderer<VALUECLASS> renderer) {
		return (LCMColumn<VALUECLASS>) super.addColumn(renderer);
	}


	public LCMColumn<VALUECLASS> addColumn(String propertyName) {
		return (LCMColumn<VALUECLASS>) super.addColumn(propertyName);
	}


	public LCMColumn<VALUECLASS> addColumn(ValueProvider<VALUECLASS, ?> valueProvider) {
		return (LCMColumn<VALUECLASS>) super.addColumn(valueProvider);
	}


	public <V extends Component> LCMColumn<VALUECLASS> addComponentColumn(
			ValueProvider<VALUECLASS, V> componentProvider) {
		return (LCMColumn<VALUECLASS>) super.addComponentColumn(componentProvider);
	}


	protected BiFunction<Renderer<VALUECLASS>, String, Column<VALUECLASS>> getDefaultColumnFactory() {
		return this::createLCMColumn;
	}

	protected LCMColumn<VALUECLASS> createLCMColumn(Renderer<VALUECLASS> renderer, String columnId) {
		return new LCMColumn<>(this, columnId, renderer);
	}

	public LCMColumn<VALUECLASS> addSortableTemplateColumn(TranslationKey columnTitle,
														   ValueProvider<VALUECLASS, ?> valueProvider) {
		return addTemplateColumn(columnTitle, valueProvider).withSortable();
	}

	public LCMColumn<VALUECLASS> addTemplateColumn(TranslationKey columnTitle,
												   ValueProvider<VALUECLASS, ?> valueProvider) {
		return applyTemplateColumnConfig(addColumn(valueProvider), columnTitle);
	}

	public <V extends Component> LCMColumn<VALUECLASS> addSortableTemplateComponentColumn(TranslationKey columnTitle,
																						  ValueProvider<VALUECLASS, V> componentProvider, Comparator<VALUECLASS> sortableComparator) {
		return addTemplateComponentColumn(columnTitle, componentProvider).withSortable().withComparator(sortableComparator);
	}

	public <V extends Component> LCMColumn<VALUECLASS> addTemplateComponentColumn(TranslationKey columnTitle,
																				  ValueProvider<VALUECLASS, V> componentProvider) {
		return applyTemplateColumnConfig(addComponentColumn(componentProvider), columnTitle);
	}
}