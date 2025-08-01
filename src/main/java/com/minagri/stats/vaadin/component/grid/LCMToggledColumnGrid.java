package com.minagri.stats.vaadin.component.grid;

import com.minagri.stats.vaadin.component.checkbox.LCMCheckbox;
import com.minagri.stats.vaadin.component.layout.LCMHorizontalLayout;
import com.minagri.stats.vaadin.component.layout.LCMVerticalLayout;
import com.minagri.stats.vaadin.css.LCMCss;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import lombok.Getter;

import java.util.Map;

@Getter
public class LCMToggledColumnGrid<T> extends LCMVerticalLayout {
	private LCMGridWithCookies<T> grid;
	private MenuBar filterMenu;
	private LCMHorizontalLayout menuLayout;

	public LCMToggledColumnGrid() {
		this.grid = new LCMGridWithCookies<>();
		this.grid.withSizeFull().withSizeFull()
				.withStyle("position", "relative")
				.withStyle("top", "0")
				.withStyle("left", "0");

		this.initFilterMenuBtn();

		this.menuLayout = new LCMHorizontalLayout().withAdd(filterMenu)
				.withNoPadding()
				.withStyle("z-index", "9")
				.withStyle("position", "absolute")
				.withStyle("top", "0");

		this.withAdd(grid, menuLayout)
				.withSizeFull()
				.withNoPadding()
				.withStyle("position", "relative");

		this.setHorizontalComponentAlignment(Alignment.END, menuLayout);
	}

	private void initFilterMenuBtn() {
		this.filterMenu = new MenuBar();
		this.filterMenu.setClassName(LCMCss.PAPER);
		this.filterMenu.getStyle().set("padding", "0");
		this.filterMenu.getStyle().set("background-color", "#fff");
		this.filterMenu.getStyle().set("border", "var(--lcm-border)");
		this.filterMenu.getStyle().set("border-radius", "var(--lcm-border-radius)");
	}

	public void setFilterMenu(Map<LCMColumn<T>, TranslationKey> toggleableColumns) {
		this.createMenuToggle(toggleableColumns);
		this.hideFilterMenu(true);

		this.menuLayout.getElement().addEventListener("mouseover", domEvent -> this.hideFilterMenu(false));
		this.menuLayout.getElement().addEventListener("mouseout", domEvent -> this.hideFilterMenu(true));

	}

	private void hideFilterMenu(boolean hide) {
		this.filterMenu.getStyle().set("visibility", hide ? "hidden" : "visible");
	}

	private void createMenuToggle(Map<LCMColumn<T>, TranslationKey> toggleableColumns) {
		this.filterMenu.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
		MenuItem menuItem = this.filterMenu.addItem(VaadinIcon.ELLIPSIS_DOTS_V.create());
		SubMenu subMenu = menuItem.getSubMenu();

		toggleableColumns.forEach(
				(column, header) -> {
					LCMCheckbox checkbox = new LCMCheckbox().withLabelKey(header);
					checkbox.setValue(column.isVisible());
					checkbox.addValueChangeListener(e -> this.grid.onColumnVisibilityChange(column, e.getValue()));
					subMenu.addItem(checkbox);
				}
		);
	}

	public void saveUserPreferences(String cookieName) {
		this.grid.applyUserPreferences(cookieName);
	}
}