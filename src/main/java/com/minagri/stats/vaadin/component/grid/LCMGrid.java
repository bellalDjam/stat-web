package com.minagri.stats.vaadin.component.grid;

import com.minagri.stats.core.exception.Exceptions;
import com.minagri.stats.core.java.Predicates;
import com.minagri.stats.vaadin.authorization.model.Action;
import com.minagri.stats.vaadin.binder.LCMBinder;
import com.minagri.stats.vaadin.component.button.LCMButton;
import com.minagri.stats.vaadin.component.html.LCMLabel;
import com.minagri.stats.vaadin.component.html.LCMSpan;
import com.minagri.stats.vaadin.component.icon.LcmIcon;
import com.minagri.stats.vaadin.component.layout.LCMGridLayout;
import com.minagri.stats.vaadin.component.layout.LCMVerticalLayout;
import com.minagri.stats.vaadin.component.text.LCMText;
import com.minagri.stats.vaadin.fluent.*;
import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.Translation;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.i18n.LocaleChangeEvent;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.*;

import static java.util.Collections.emptyList;

public class LCMGrid<VALUECLASS> extends Grid<VALUECLASS> implements
        ComponentFluentAuthorized<LCMGrid<VALUECLASS>>,
        HasSizeFluent<LCMGrid<VALUECLASS>>,
        HasEnabledFluent<LCMGrid<VALUECLASS>>,
        FocusableFluent<LCMGrid<VALUECLASS>>,
        HasStyleFluent<LCMGrid<VALUECLASS>>,
        HasThemeFluent<LCMGrid<VALUECLASS>>,
        IsGridFluent<LCMGrid<VALUECLASS>, VALUECLASS>,
        HasTranslation {

    private Map<String, Translation> translationsLinkedToMethodsMap = new HashMap<>();
    private List<LCMEditColumn<VALUECLASS, ?, ?>> editColumns = new ArrayList<>();
    private Action action;
    private LCMVerticalLayout footerLayout;
    private LCMLabel footerItemCountLabel;
    private LCMGridLayout footerButtonLayout;
    private LCMVerticalLayout emptyStateLayout;
    private SerializableFunction<VALUECLASS, String> tooltipGenerator;

    @Override
    public Map<String, Translation> getTranslationsLinkedToMethodsMap() {
        return translationsLinkedToMethodsMap;
    }

    public LCMGrid<VALUECLASS> selectFirstRow() {
        getListDataView().getItems().findFirst().ifPresent(this::select);
        return this;
    }

    public LCMGrid<VALUECLASS> selectAndFocusFirstRow() {
        // Select AND focus the first row.
        // Waiting for a better solution provided by Vaadin team
        // see https://github.com/vaadin/vaadin-grid-flow/issues/634
        getElement().getNode()
                .runWhenAttached(ui -> ui.getPage().executeJs(
                        "setTimeout(function(){let firstTd = $0.shadowRoot.querySelector('tr:first-child > td:first-child'); firstTd.click(); firstTd.focus(); },0)", getElement()));
        return this;
    }

    @Override
    public LCMGrid<VALUECLASS> getFluent() {
        return this;
    }

    @Override
    public LCMGrid<VALUECLASS> setAction(Action action) {
        this.action = action;
        return null;
    }

    @Override
    public Action getAction() {
        return this.action;
    }

    @Override
    public LCMColumn<VALUECLASS> addColumn(Renderer<VALUECLASS> renderer) {
        return (LCMColumn<VALUECLASS>) super.addColumn(renderer);
    }

    @Override
    public LCMColumn<VALUECLASS> addColumn(String propertyName) {
        return (LCMColumn<VALUECLASS>) super.addColumn(propertyName);
    }

    @Override
    public LCMColumn<VALUECLASS> addColumn(ValueProvider<VALUECLASS, ?> valueProvider) {
        return (LCMColumn<VALUECLASS>) super.addColumn(valueProvider);
    }

    @Override
    public <V extends Comparable<? super V>> LCMColumn<VALUECLASS> addColumn(ValueProvider<VALUECLASS, V> valueProvider, String... sortingProperties) {
        return (LCMColumn<VALUECLASS>) super.addColumn(valueProvider, sortingProperties);
    }

    @Override
    public <V extends Component> LCMColumn<VALUECLASS> addComponentColumn(ValueProvider<VALUECLASS, V> componentProvider) {
        return (LCMColumn<VALUECLASS>) super.addComponentColumn(componentProvider);
    }

    @Override
    protected BiFunction<Renderer<VALUECLASS>, String, Column<VALUECLASS>> getDefaultColumnFactory() {
        return (renderer, columnId) -> new LCMColumn<>(this, columnId, renderer);
    }

    @Override
    protected void onDataProviderChange() {
        super.onDataProviderChange();
        editColumns.forEach(LCMEditColumn::clear);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> LCMColumn<VALUECLASS> addColumn(ValueProvider<VALUECLASS, T> valueProvider, ValueProvider<T, ?> displayValueProvider) {
        LCMColumn<VALUECLASS> column = addColumn(i -> {
            T value = valueProvider.apply(i);
            return value != null ? displayValueProvider.apply(value) : null;
        });

        column.withComparator(item -> {
            T value = valueProvider.apply(item);
            return value instanceof Comparable comparableValue ? comparableValue : (Comparable) Objects.toString(value);
        });
        column.withSortable(false);

        return column;
    }

    public void doSelectionWithEnter() {
        this.getElement().executeJs("this.addEventListener('keydown', e => e.key === 'Enter' &&(this.$connector.doSelection([this.getEventContext(e).item], true)) )");
    }

    public List<VALUECLASS> getSelectedItemsAsList() {
        return getSelectedItems().stream().toList();
    }

    public <C extends Component & HasValue<?, CV>, CV> LCMEditColumn<VALUECLASS, C, CV> addEditColumn(Class<C> componentClass, Function<VALUECLASS, CV> getter) {
        return addEditColumn(item -> {
            C component = Exceptions.sneakyThrows(() -> componentClass.getConstructor().newInstance());

            CV value = getter.apply(item);
            if (value != null) {
                component.setValue(value);
            }

            if (component instanceof HasSize) {
                ((HasSize) component).setSizeFull();
            }
            return component;
        });
    }

    public <C extends Component & HasValue<?, CV>, CV> LCMEditColumn<VALUECLASS, C, CV> addEditColumn(ValueProvider<VALUECLASS, C> componentProvider) {
        AtomicReference<LCMEditColumn<VALUECLASS, C, CV>> columnReference = new AtomicReference<>();

        ComponentRenderer<C, VALUECLASS> componentRenderer = new ComponentRenderer<>(item -> {
            C component = componentProvider.apply(item);
            columnReference.get().setEditComponent(item, component);
            return component;
        });

        BiFunction<Renderer<VALUECLASS>, String, LCMEditColumn<VALUECLASS, C, CV>> columnFactory = (renderer, columnId) -> {
            LCMEditColumn<VALUECLASS, C, CV> editColumn = new LCMEditColumn<>(this, columnId, renderer);
            columnReference.set(editColumn);
            return editColumn;
        };

        LCMEditColumn<VALUECLASS, C, CV> editColumn = addColumn(componentRenderer, columnFactory);
        editColumns.add(editColumn);
        return editColumn;
    }

    public LCMGridEdit<VALUECLASS> editItems(BiConsumer<VALUECLASS, LCMBinder<VALUECLASS>> bindingProvider) {
        List<VALUECLASS> editedItems = getEditedItems();

        boolean valid = true;
        for (VALUECLASS editedItem : editedItems) {
            LCMBinder<VALUECLASS> binder = new LCMBinder<>();
            bindingProvider.accept(editedItem, binder);

            if (!binder.writeBeanIfValid(editedItem)) {
                valid = false;
            }
        }

        LCMGridEdit<VALUECLASS> gridEdit = new LCMGridEdit<>();
        gridEdit.setEditedItems(editedItems);
        gridEdit.setValid(valid);
        return gridEdit;
    }

    public List<VALUECLASS> getEditedItems() {
        return getListDataView().getItems()
                .filter(item -> editColumns.stream().anyMatch(editColumn -> editColumn.hasChanges(item)))
                .toList();
    }

    public void clearItems() {
        setItems(emptyList());
        clearFooterItemCountLabel();
        hideEmptyStateText();
    }

    public VALUECLASS getSelectedItem() {
        Set<VALUECLASS> selectedItems = getSelectedItems();
        if (selectedItems.isEmpty()) {
            return null;
        }
        return selectedItems.iterator().next();
    }

    public void setLazyLoadedItems(Integer count, Function<LCMGridPage, List<VALUECLASS>> itemFunction) {
        setLazyLoadedItems(() -> count, itemFunction);
    }

    public void setLazyLoadedItems(Supplier<Integer> countFunction, Function<LCMGridPage, List<VALUECLASS>> itemFunction) {
        setColumnRendering(ColumnRendering.LAZY);
        setDataProvider(new LCMGridLazyDataProvider<>(this, countFunction, itemFunction));
    }

    @Override
    public void setDataProvider(DataProvider<VALUECLASS, ?> dataProvider) {
        super.setDataProvider(dataProvider);

        Element gridElement = getElement();
        gridElement.executeJs("""
                    (function() {
                        if (!$0._recalcScrollListenerAdded) {
                            let recalcScrollListenerRunning = false;
                            const onScroll = () => {
                                if (recalcScrollListenerRunning) {
                                    return;
                                }
                                recalcScrollListenerRunning = true;
                                const currentScrollTop = $0.$.table.scrollTop;
                                const currentScrollLeft = $0.$.table.scrollLeft;
                                $0.recalculateColumnWidths();
                                $0.$.table.scrollTop = currentScrollTop;
                                $0.$.table.scrollLeft = currentScrollLeft;
                                recalcScrollListenerRunning = false;
                            };
                
                            $0.$.table.addEventListener('scroll', onScroll);
                            $0._recalcScrollListenerAdded = true;
                        }
                
                        const scrollEvent = new Event('scroll');
                        $0.$.table.dispatchEvent(scrollEvent);
                    })();
                """, gridElement);
    }

    @SuppressWarnings("unchecked")
    public void scrollToIndexAndSelectLazyLoadedItem(Number rowIndex, Predicate<VALUECLASS> selectionFunction) {
        LCMGridLazyDataProvider<VALUECLASS> dataProvider = (LCMGridLazyDataProvider<VALUECLASS>) getDataProvider();
        dataProvider.triggerSelection(selectionFunction);
        scrollToIndex(rowIndex.intValue());
    }

    public <PROPERTY> void scrollToIndexAndSelectLazyLoadedItem(Number rowIndex, Function<VALUECLASS, PROPERTY> itemPropertyFunction, PROPERTY selectionPropertyValue) {
        scrollToIndexAndSelectLazyLoadedItem(rowIndex, Predicates.propertyEqualTo(itemPropertyFunction, selectionPropertyValue));
    }

    public void setLimitedItems(Integer limit, Supplier<Integer> countFunction, Function<LCMGridPage, List<VALUECLASS>> itemFunction) {
        setColumnRendering(ColumnRendering.LAZY);
        setDataProvider(new LCMGridLimitedDataProvider<>(this, limit, countFunction, itemFunction));
        if (this.getSelectionModel() instanceof GridMultiSelectionModel<?> multiSelectionModel) {
            multiSelectionModel.setSelectAllCheckboxVisibility(GridMultiSelectionModel.SelectAllCheckboxVisibility.VISIBLE);
        }
    }

    public void setLimitedItems(Integer limit, Integer count, Function<LCMGridPage, List<VALUECLASS>> itemFunction) {
        setLimitedItems(limit, () -> count, itemFunction);
    }

    @Override
    public GridListDataView<VALUECLASS> setItems(Collection<VALUECLASS> items) {
        if (items.isEmpty()) {
            showEmptyStateText();
            clearFooterItemCountLabel();
        } else {
            setTotalResultsFooterItemCountLabel(items.size());
        }
        return super.setItems(items);
    }

    @Override
    public void scrollToIndex(int rowIndex) {
        super.scrollToIndex(rowIndex);

        getElement().getNode().runWhenAttached(ui -> ui.getPage().executeJs(
                "setTimeout(function(){$0.scrollToIndex(" + rowIndex + ");},0)", getElement()));
    }

    @SafeVarargs
    public final LCMGrid<VALUECLASS> withColumn(ValueProvider<VALUECLASS, ?> valueProvider, TranslationKey headerKey, Consumer<LCMColumn<VALUECLASS>>... columnConsumers) {
        LCMColumn<VALUECLASS> column = addColumn(valueProvider);
        column.withHeaderKey(headerKey);
        Arrays.stream(columnConsumers).forEach(columnConsumer -> columnConsumer.accept(column));
        return this;
    }

    @SafeVarargs
    public final LCMGrid<VALUECLASS> withColumn(ValueProvider<VALUECLASS, ?> valueProvider, String header, Consumer<LCMColumn<VALUECLASS>>... columnConsumers) {
        LCMColumn<VALUECLASS> column = addColumn(valueProvider);
        column.withHeader(header);
        Arrays.stream(columnConsumers).forEach(columnConsumer -> columnConsumer.accept(column));
        return this;
    }

    @SafeVarargs
    public final <T> LCMGrid<VALUECLASS> withColumn(ValueProvider<VALUECLASS, T> valueProvider, ValueProvider<T, ?> displayValueProvider, TranslationKey headerKey, Consumer<LCMColumn<VALUECLASS>>... columnConsumers) {
        LCMColumn<VALUECLASS> column = addColumn(valueProvider, displayValueProvider);
        column.withHeaderKey(headerKey);
        Arrays.stream(columnConsumers).forEach(columnConsumer -> columnConsumer.accept(column));
        return this;
    }

    @SafeVarargs
    public final <T> LCMGrid<VALUECLASS> withColumn(ValueProvider<VALUECLASS, T> valueProvider, ValueProvider<T, ?> displayValueProvider, String header, Consumer<LCMColumn<VALUECLASS>>... columnConsumers) {
        LCMColumn<VALUECLASS> column = addColumn(valueProvider, displayValueProvider);
        column.withHeader(header);
        Arrays.stream(columnConsumers).forEach(columnConsumer -> columnConsumer.accept(column));
        return this;
    }

    @SafeVarargs
    public final LCMGrid<VALUECLASS> withLocalizedTextColumn(Function<VALUECLASS, String> valueProviderNl, Function<VALUECLASS, String> valueProviderFr, TranslationKey headerKey, Consumer<LCMColumn<VALUECLASS>>... columnConsumers) {
        LCMColumn<VALUECLASS> column = addComponentColumn(item -> new LCMText().setLocalizedText(valueProviderNl.apply(item), valueProviderFr.apply(item)));
        column.withHeaderKey(headerKey);
        Arrays.stream(columnConsumers).forEach(columnConsumer -> columnConsumer.accept(column));
        return this;
    }

    @SafeVarargs
    public final LCMGrid<VALUECLASS> withLocalizedTextColumn(Function<VALUECLASS, String> valueProviderNl, Function<VALUECLASS, String> valueProviderFr, String header, Consumer<LCMColumn<VALUECLASS>>... columnConsumers) {
        LCMColumn<VALUECLASS> column = addComponentColumn(item -> new LCMText().setLocalizedText(valueProviderNl.apply(item), valueProviderFr.apply(item)));
        column.withHeader(header);
        Arrays.stream(columnConsumers).forEach(columnConsumer -> columnConsumer.accept(column));
        return this;
    }

    @SafeVarargs
    public final LCMGrid<VALUECLASS> withEnumColumn(Function<VALUECLASS, Enum<?>> enumValueProvider, TranslationKey headerKey, Consumer<LCMColumn<VALUECLASS>>... columnConsumers) {
        LCMColumn<VALUECLASS> column = addComponentColumn(item -> new LCMText().withText(enumValueProvider.apply(item)));
        column.withHeaderKey(headerKey);
        Arrays.stream(columnConsumers).forEach(columnConsumer -> columnConsumer.accept(column));
        return this;
    }

    @SafeVarargs
    public final LCMGrid<VALUECLASS> withEnumColumn(Function<VALUECLASS, Enum<?>> enumValueProvider, String header, Consumer<LCMColumn<VALUECLASS>>... columnConsumers) {
        LCMColumn<VALUECLASS> column = addComponentColumn(item -> new LCMText().withText(enumValueProvider.apply(item)));
        column.withHeader(header);
        Arrays.stream(columnConsumers).forEach(columnConsumer -> columnConsumer.accept(column));
        return this;
    }

    @SafeVarargs
    public final <P> LCMGrid<VALUECLASS> withBooleanColumn(Function<VALUECLASS, Boolean> booleanValueProvider, TranslationKey headerKey, Consumer<LCMColumn<VALUECLASS>>... columnConsumers) {
        LCMColumn<VALUECLASS> column = addComponentColumn(item -> new LCMText().withText(booleanValueProvider.apply(item)));
        column.withHeaderKey(headerKey);
        Arrays.stream(columnConsumers).forEach(columnConsumer -> columnConsumer.accept(column));
        return this;
    }

    @SafeVarargs
    public final <P> LCMGrid<VALUECLASS> withBooleanColumn(Function<VALUECLASS, Boolean> booleanValueProvider, String header, Consumer<LCMColumn<VALUECLASS>>... columnConsumers) {
        LCMColumn<VALUECLASS> column = addComponentColumn(item -> new LCMText().withText(booleanValueProvider.apply(item)));
        column.withHeader(header);
        Arrays.stream(columnConsumers).forEach(columnConsumer -> columnConsumer.accept(column));
        return this;
    }

    public LCMGrid<VALUECLASS> withIconButtonColumn(Function<VALUECLASS, LcmIcon> iconSupplier, Consumer<VALUECLASS> clickListener, Consumer<LCMColumn<VALUECLASS>> columnConsumer) {
        LCMColumn<VALUECLASS> column = (LCMColumn<VALUECLASS>) addComponentColumn(item -> new LCMButton()
                .withCompactIcon(iconSupplier.apply(item))
                .withClickListener(() -> clickListener.accept(item)))
                .withFlexGrow(0)
                .setAutoWidth(true);

        columnConsumer.accept(column);
        return this;
    }

    public LCMGrid<VALUECLASS> withIconButtonColumn(VaadinIcon icon, Consumer<VALUECLASS> clickListener, Consumer<LCMColumn<VALUECLASS>> columnConsumer) {
        return withIconButtonColumn(i -> new LcmIcon(icon), clickListener, columnConsumer);
    }

    public LCMGrid<VALUECLASS> withIconButtonColumn(VaadinIcon icon, Consumer<VALUECLASS> clickListener) {
        return withIconButtonColumn(icon, clickListener, column -> {
        });
    }

    public LCMGrid<VALUECLASS> withIconButtonColumn(Function<VALUECLASS, LcmIcon> iconSupplier, Consumer<VALUECLASS> clickListener) {
        return withIconButtonColumn(iconSupplier, clickListener, column -> {
        });
    }

    public LCMGrid<VALUECLASS> withIconButtonColumn(VaadinIcon icon, Consumer<VALUECLASS> clickListener, TranslationKey headerKey) {
        return withIconButtonColumn(icon, clickListener, column -> column.withHeaderKey(headerKey));
    }

    public LCMGrid<VALUECLASS> withIconButtonColumn(Function<VALUECLASS, LcmIcon> iconSupplier, Consumer<VALUECLASS> clickListener, TranslationKey headerKey) {
        return withIconButtonColumn(iconSupplier, clickListener, column -> column.withHeaderKey(headerKey));
    }

    public LCMGrid<VALUECLASS> withConditionalIconButtonColumn(Predicate<VALUECLASS> predicate, VaadinIcon icon, Consumer<VALUECLASS> clickListener, Consumer<LCMColumn<VALUECLASS>> columnConsumer) {
        return withConditionalIconButtonColumn(predicate, item -> new LcmIcon(icon), clickListener, columnConsumer);
    }

    public LCMGrid<VALUECLASS> withConditionalIconButtonColumn(Predicate<VALUECLASS> predicate, Function<VALUECLASS, LcmIcon> iconSupplier, Consumer<VALUECLASS> clickListener) {
        return withConditionalIconButtonColumn(predicate, iconSupplier, clickListener, column -> {
        });
    }

    public LCMGrid<VALUECLASS> withConditionalIconButtonColumn(Predicate<VALUECLASS> predicate, Function<VALUECLASS, LcmIcon> iconSupplier, Consumer<VALUECLASS> clickListener, Consumer<LCMColumn<VALUECLASS>> columnConsumer) {
        LCMColumn<VALUECLASS> column = addComponentColumn(item -> {
            if (predicate.test(item)) {
                return new LCMButton()
                        .withCompactIcon(iconSupplier.apply(item))
                        .withClickListener(() -> clickListener.accept(item));
            } else {
                return new Div();
            }
        });

        column.withFlexGrow(0).setAutoWidth(true);
        columnConsumer.accept(column);

        return this;
    }


    public LCMGrid<VALUECLASS> withConditionalIconButtonColumn(Predicate<VALUECLASS> predicate, VaadinIcon icon, Consumer<VALUECLASS> clickListener) {
        return withConditionalIconButtonColumn(predicate, icon, clickListener, column -> {
        });

    }

    public LCMGrid<VALUECLASS> withConditionalIconButtonColumn(Predicate<VALUECLASS> predicate, VaadinIcon icon, Consumer<VALUECLASS> clickListener, TranslationKey headerKey) {
        return withConditionalIconButtonColumn(predicate, icon, clickListener, column -> column.withHeaderKey(headerKey));
    }

    public LCMGrid<VALUECLASS> withItemDetailsToggleColumn(Function<VALUECLASS, Component> itemDetailsRenderer) {
        LCMColumn<VALUECLASS> manualItemDetailsIconButtonColumn = addComponentColumn(item -> new LCMButton()
                .withCompactIcon(isDetailsVisible(item) ? VaadinIcon.MINUS_CIRCLE_O : VaadinIcon.PLUS_CIRCLE_O)
                .withClickListener(() -> setDetailsVisible(item, !isDetailsVisible(item))));

        manualItemDetailsIconButtonColumn
                .withFlexGrow(0)
                .setAutoWidth(true);

        setDetailsVisibleOnClick(false);
        setItemDetailsRenderer(new ComponentRenderer<>(itemDetailsRenderer::apply));
        return this;
    }

    @SafeVarargs
    public final LCMGrid<VALUECLASS> withIconColumn(Function<VALUECLASS, LcmIcon> iconSupplier, Consumer<LCMColumn<VALUECLASS>>... columnConsumers) {
        LCMColumn<VALUECLASS> column = (LCMColumn<VALUECLASS>) addComponentColumn(iconSupplier::apply)
                .withFlexGrow(0)
                .setAutoWidth(true);

        Arrays.stream(columnConsumers).forEach(columnConsumer -> columnConsumer.accept(column));
        return this;
    }

    @SafeVarargs
    public final LCMGrid<VALUECLASS> withIconColumn(Function<VALUECLASS, LcmIcon> iconSupplier, TranslationKey headerKey, Consumer<LCMColumn<VALUECLASS>>... columnConsumers) {
        LCMColumn<VALUECLASS> column = (LCMColumn<VALUECLASS>) addComponentColumn(iconSupplier::apply)
                .withHeaderKey(headerKey)
                .withFlexGrow(0)
                .setAutoWidth(true);

        Arrays.stream(columnConsumers).forEach(columnConsumer -> columnConsumer.accept(column));
        return this;
    }

    @SafeVarargs
    public final LCMGrid<VALUECLASS> withIconColumn(Function<VALUECLASS, LcmIcon> iconSupplier, VaadinIcon headerIcon, Consumer<LCMColumn<VALUECLASS>>... columnConsumers) {
        LCMColumn<VALUECLASS> column = (LCMColumn<VALUECLASS>) addComponentColumn(iconSupplier::apply)
                .withHeader(new LcmIcon(headerIcon).withSizeSmall())
                .withFlexGrow(0)
                .setAutoWidth(true);

        Arrays.stream(columnConsumers).forEach(columnConsumer -> columnConsumer.accept(column));
        return this;
    }

    public <C extends Component> LCMGrid<VALUECLASS> withComponentColumn(Function<VALUECLASS, C> componentProvider, Consumer<LCMColumn<VALUECLASS>> columnConsumer) {
        LCMColumn<VALUECLASS> column = addComponentColumn(componentProvider::apply);
        columnConsumer.accept(column);
        return this;
    }

    public <C extends Component> LCMGrid<VALUECLASS> withComponentColumn(Function<VALUECLASS, C> componentProvider, TranslationKey headerKey) {
        return withComponentColumn(componentProvider, column -> column.withHeaderKey(headerKey));
    }

    public <C extends Component> LCMGrid<VALUECLASS> withComponentColumn(Function<VALUECLASS, C> componentProvider, TranslationKey headerKey, Consumer<LCMColumn<VALUECLASS>> columnConsumer) {
        return withComponentColumn(componentProvider, column -> {
            column.withHeaderKey(headerKey);
            columnConsumer.accept(column);
        });
    }

    @SafeVarargs
    public final LCMGrid<VALUECLASS> withLinkColumn(Function<VALUECLASS, String> valueSupplier, TranslationKey headerKey, Consumer<VALUECLASS> clickListener, Consumer<LCMColumn<VALUECLASS>>... columnConsumers) {
        LCMColumn<VALUECLASS> column = addComponentColumn(item -> {
            LCMSpan linkSpan = new LCMSpan();
            linkSpan.setText(valueSupplier.apply(item));
            linkSpan.withStyle("cursor", "pointer");
            linkSpan.withStyle("color", "var(--lumo-primary-text-color)");
            linkSpan.withStyle("text-decoration", "underline");
            linkSpan.withClickListener(() -> clickListener.accept(item));
            return linkSpan;
        });

        column.withHeaderKey(headerKey);
        Arrays.stream(columnConsumers).forEach(columnConsumer -> columnConsumer.accept(column));
        return this;
    }

    public LCMGrid<VALUECLASS> withItems(List<VALUECLASS> items) {
        setItems(items);
        return this;
    }

    public LCMGrid<VALUECLASS> withCompact() {
        return withThemeVariants(GridVariant.LUMO_COMPACT);
    }

    public LCMGrid<VALUECLASS> withNoBorder() {
        return withThemeVariants(GridVariant.LUMO_NO_BORDER);
    }

    public LCMGrid<VALUECLASS> withRowStripes() {
        return withThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    }

    public LCMGrid<VALUECLASS> withAllRowsVisible() {
        setAllRowsVisible(true);
        return this;
    }

    public LCMGrid<VALUECLASS> withAllRowsVisibleAndMaxHeightInPixels(Integer maxHeightInPixels) {
        setAllRowsVisible(true);
        setMaxHeight(maxHeightInPixels + "px");
        setOverflow("auto");
        return this;
    }

    public LCMGrid<VALUECLASS> setTotalResultsFooterItemCountLabel(int total) {
        String labelNl = "Totaal aantal rijen: " + total;
        String labelFr = "Nombre total de lignes: " + total;

        if (footerItemCountLabel != null) {
            footerItemCountLabel.setVisible(true);
            footerItemCountLabel.setLocalizedText(labelNl, labelFr);
        }
        return this;
    }

    public LCMGrid<VALUECLASS> setLimitedResultsFooterItemCountLabel(int displayed, int total) {
        String labelNl = "Weergegeven: " + displayed + " van de " + total + " rijen";
        String labelFr = "Affiché: " + displayed + " des " + total + " lignes";

        if (footerItemCountLabel != null) {
            footerItemCountLabel.setVisible(true);
            footerItemCountLabel.setLocalizedText(labelNl, labelFr);
        }
        return this;
    }

    public LCMGrid<VALUECLASS> clearFooterItemCountLabel() {
        if (footerItemCountLabel != null) {
            footerItemCountLabel.setVisible(false);
            footerItemCountLabel.setText("");
        }
        return this;
    }

    public LCMGrid<VALUECLASS> withFooterSelectionButton(LCMButton button) {
        return withFooterSelectionButton(button, item -> true);
    }

    public LCMGrid<VALUECLASS> withFooterSelectionButton(LCMButton button, Supplier<Boolean> enableFunction) {
        return withFooterSelectionButton(button, item -> enableFunction.get());
    }

    public LCMGrid<VALUECLASS> withFooterSelectionButton(LCMButton button, Function<VALUECLASS, Boolean> enableFunction) {
        footerButtonLayout.setVisible(true);
        footerButtonLayout.add(button);
        footerButtonLayout.setColumns(footerButtonLayout.getComponentCount());

        button.setEnabled(!getSelectedItems().isEmpty() && getSelectedItems().stream().allMatch(enableFunction::apply));

        addSelectionListener(event -> {
            Set<VALUECLASS> selectedItems = getSelectedItems();
            button.setEnabled(!selectedItems.isEmpty() && selectedItems.stream().allMatch(enableFunction::apply));
        });

        return this;
    }

    public LCMGrid<VALUECLASS> withFooterSelectionButton(TranslationKey textKey, Runnable clickListener) {
        return withFooterSelectionButton(new LCMButton().withTextKey(textKey).withClickListener(clickListener));
    }

    public LCMGrid<VALUECLASS> withFooterSelectionButton(TranslationKey textKey, Runnable clickListener, Supplier<Boolean> enableFunction) {
        return withFooterSelectionButton(new LCMButton().withTextKey(textKey).withClickListener(clickListener), enableFunction);
    }

    public LCMGrid<VALUECLASS> withFooterSelectionButton(TranslationKey textKey, Runnable clickListener, Function<VALUECLASS, Boolean> enableFunction) {
        return withFooterSelectionButton(new LCMButton().withTextKey(textKey).withClickListener(clickListener), enableFunction);
    }

    public LCMGrid<VALUECLASS> withFooter() {
        if (footerLayout == null) {
            footerLayout = new LCMVerticalLayout();
            footerItemCountLabel = new LCMLabel()
                    .addTo(footerLayout)
                    .withVisible(false)
                    .withStyle("font-size", "var(--lumo-font-size-s)");
            footerButtonLayout = new LCMGridLayout()
                    .addTo(footerLayout)
                    .setColumnGap()
                    .withVisible(false)
                    .withStyle("align-self", "flex-start");

            appendFooterRow(); // skip 1st hidden row
            FooterRow footerRow = appendFooterRow();
            FooterRow.FooterCell footerCell = footerRow.join(footerRow.getCells());
            footerCell.setComponent(footerLayout);
        }
        return this;
    }

    public LCMGrid<VALUECLASS> withEmptyStateText() {
        return withEmptyStateText(Translator.createTranslation("Geen resultaten", "Aucun résultat"));
    }

    public LCMGrid<VALUECLASS> withEmptyStateText(TranslationKey textKey) {
        return withEmptyStateText(Translator.createTranslation(textKey));
    }

    public LCMGrid<VALUECLASS> withEmptyStateText(Translation translation) {
        emptyStateLayout = new LCMVerticalLayout()
                .withSizeFull()
                .withAlignItemsStart()
                .withJustifyContentModeStart()
                .withAdd(new LCMSpan()
                        .withFontWeightBold()
                        .withFontSizeInEm(1.3)
                        .withColorLumoTertiary()
                        .withText(translation));
        return this;
    }

    public void showEmptyStateText() {
        setEmptyStateComponent(emptyStateLayout);
    }

    public void hideEmptyStateText() {
        setEmptyStateComponent(null);
    }

    @Override
    public void setTooltipGenerator(SerializableFunction<VALUECLASS, String> tooltipGenerator) {
        this.tooltipGenerator = tooltipGenerator;
        super.setTooltipGenerator(tooltipGenerator);
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        HasTranslation.super.localeChange(event);
        Optional.ofNullable(tooltipGenerator).ifPresent(this::setTooltipGenerator);
    }
}
