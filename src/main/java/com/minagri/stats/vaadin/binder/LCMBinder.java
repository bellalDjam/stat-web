package com.minagri.stats.vaadin.binder;

import com.minagri.stats.vaadin.translation.control.Translator;
import com.minagri.stats.vaadin.translation.entity.CommonMessage;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.function.ValueProvider;

public class LCMBinder<BEAN> extends Binder<BEAN> {

    public LCMBinder(PropertySet<BEAN> propertySet) {
        super(propertySet);
    }

    public LCMBinder(Class<BEAN> beanType) {
        super(beanType);
    }

    public LCMBinder() {
    }

    public LCMBinder(Class<BEAN> beanType, boolean scanNestedDefinitions) {
        super(beanType, scanNestedDefinitions);
    }

    @Override
    public <FIELDVALUE> LCMBindingBuilder<BEAN, FIELDVALUE> forField(HasValue<?, FIELDVALUE> field) {
        return (LCMBindingBuilder<BEAN, FIELDVALUE>) super.forField(field);
    }

    @Override
    public <FIELDVALUE> LCMBindingBuilder<BEAN, FIELDVALUE> forMemberField(HasValue<?, FIELDVALUE> field) {
        return (LCMBindingBuilder<BEAN, FIELDVALUE>) super.forMemberField(field);
    }

    @Override
    protected <FIELDVALUE, TARGET> BindingBuilder<BEAN, TARGET> doCreateBinding(
            HasValue<?, FIELDVALUE> field,
            Converter<FIELDVALUE, TARGET> converter,
            BindingValidationStatusHandler handler) {
        return new LCMBindingBuilderImpl<>(this, field, converter, handler);
    }

    @SafeVarargs
    public final <FIELDVALUE> Binding<BEAN, FIELDVALUE> bindField(HasValue<?, FIELDVALUE> field, ValueProvider<BEAN, FIELDVALUE> getter, Setter<BEAN, FIELDVALUE> setter, LcmValidator<? super FIELDVALUE>... validators) {
        LCMBindingBuilder<BEAN, FIELDVALUE> bindingBuilder = forField(field);
        for (LcmValidator<? super FIELDVALUE> validator : validators) {
            bindingBuilder.withValidator(validator);
        }
        return bindingBuilder.bind(getter, setter);
    }

    @SafeVarargs
    public final <FIELDVALUE> Binding<BEAN, FIELDVALUE> bindWriteOnlyField(HasValue<?, FIELDVALUE> field, Setter<BEAN, FIELDVALUE> setter, LcmValidator<? super FIELDVALUE>... validators) {
        LCMBindingBuilder<BEAN, FIELDVALUE> bindingBuilder = forField(field);
        for (LcmValidator<? super FIELDVALUE> validator : validators) {
            bindingBuilder.withValidator(validator);
        }
        return bindingBuilder.bindWriteOnly(setter);
    }

    @SafeVarargs
    public final <FIELDVALUE> Binding<BEAN, FIELDVALUE> bindValidationOnlyField(HasValue<?, FIELDVALUE> field, LcmValidator<? super FIELDVALUE>... validators) {
        LCMBindingBuilder<BEAN, FIELDVALUE> bindingBuilder = forField(field);
        for (LcmValidator<? super FIELDVALUE> validator : validators) {
            bindingBuilder.withValidator(validator);
        }
        return bindingBuilder.bindValidationOnly();
    }

    public interface LCMBindingBuilder<BEAN, TARGET> extends BindingBuilder<BEAN, TARGET> {
        Binding<BEAN, TARGET> bindWriteOnly(Setter<BEAN, TARGET> setter);

        Binding<BEAN, TARGET> bindValidationOnly();

        @Override
        LCMBindingBuilder<BEAN, TARGET> withValidator(Validator<? super TARGET> validator);

        @Override
        default LCMBindingBuilder<BEAN, TARGET> withValidator(SerializablePredicate<? super TARGET> predicate, String message) {
            return (LCMBindingBuilder<BEAN, TARGET>) BindingBuilder.super.withValidator(predicate, message);
        }

        @Override
        default LCMBindingBuilder<BEAN, TARGET> withValidator(SerializablePredicate<? super TARGET> predicate, String message, ErrorLevel errorLevel) {
            return (LCMBindingBuilder<BEAN, TARGET>) BindingBuilder.super.withValidator(predicate, message, errorLevel);
        }

        @Override
        default LCMBindingBuilder<BEAN, TARGET> withValidator(SerializablePredicate<? super TARGET> predicate, ErrorMessageProvider errorMessageProvider) {
            return (LCMBindingBuilder<BEAN, TARGET>) BindingBuilder.super.withValidator(predicate, errorMessageProvider);
        }

        @Override
        default LCMBindingBuilder<BEAN, TARGET> withValidator(SerializablePredicate<? super TARGET> predicate, ErrorMessageProvider errorMessageProvider, ErrorLevel errorLevel) {
            return (LCMBindingBuilder<BEAN, TARGET>) BindingBuilder.super.withValidator(predicate, errorMessageProvider, errorLevel);
        }

        @Override
        <NEWTARGET> LCMBindingBuilder<BEAN, NEWTARGET> withConverter(Converter<TARGET, NEWTARGET> converter);

        @Override
        default <NEWTARGET> LCMBindingBuilder<BEAN, NEWTARGET> withConverter(SerializableFunction<TARGET, NEWTARGET> toModel, SerializableFunction<NEWTARGET, TARGET> toPresentation) {
            return (LCMBindingBuilder<BEAN, NEWTARGET>) BindingBuilder.super.withConverter(toModel, toPresentation);
        }

        @Override
        default <NEWTARGET> LCMBindingBuilder<BEAN, NEWTARGET> withConverter(SerializableFunction<TARGET, NEWTARGET> toModel, SerializableFunction<NEWTARGET, TARGET> toPresentation, String errorMessage) {
            return (LCMBindingBuilder<BEAN, NEWTARGET>) BindingBuilder.super.withConverter(toModel, toPresentation, errorMessage);
        }

        @Override
        default LCMBindingBuilder<BEAN, TARGET> withNullRepresentation(TARGET nullRepresentation) {
            return (LCMBindingBuilder<BEAN, TARGET>) BindingBuilder.super.withNullRepresentation(nullRepresentation);
        }

        @Override
        default LCMBindingBuilder<BEAN, TARGET> withStatusLabel(HasText label) {
            return (LCMBindingBuilder<BEAN, TARGET>) BindingBuilder.super.withStatusLabel(label);
        }

        @Override
        LCMBindingBuilder<BEAN, TARGET> withValidationStatusHandler(BindingValidationStatusHandler handler);

        @Override
        default LCMBindingBuilder<BEAN, TARGET> asRequired(String errorMessage) {
            return (LCMBindingBuilder<BEAN, TARGET>) BindingBuilder.super.asRequired(errorMessage);
        }

        @Override
        default LCMBindingBuilder<BEAN, TARGET> asRequired() {
            return (LCMBindingBuilder<BEAN, TARGET>) BindingBuilder.super.asRequired();
        }

        @Override
        LCMBindingBuilder<BEAN, TARGET> asRequired(ErrorMessageProvider errorMessageProvider);

        @Override
        LCMBindingBuilder<BEAN, TARGET> asRequired(Validator<TARGET> customRequiredValidator);

        default LCMBindingBuilder<BEAN, TARGET> withValidator(SerializablePredicate<? super TARGET> predicate, Enum<?> errorKey, Object... messageParameters) {
            ErrorMessageProvider errorMessageProvider = Translator.createErrorMessageProvider(errorKey, messageParameters);
            return (LCMBindingBuilder<BEAN, TARGET>) BindingBuilder.super.withValidator(predicate, errorMessageProvider);
        }

        default LCMBindingBuilder<BEAN, TARGET> withValidator(SerializablePredicate<? super TARGET> predicate, Enum<?> errorKey, ErrorLevel errorLevel, Object... messageParameters) {
            ErrorMessageProvider errorMessageProvider = Translator.createErrorMessageProvider(errorKey, messageParameters);
            return (LCMBindingBuilder<BEAN, TARGET>) BindingBuilder.super.withValidator(predicate, errorMessageProvider, errorLevel);
        }

        default LCMBindingBuilder<BEAN, TARGET> withValidator(SerializablePredicate<? super TARGET> predicate) {
            return withValidator(predicate, CommonMessage.INVALID_VALUE);
        }

        default LCMBindingBuilder<BEAN, TARGET> withValidator(LcmValidator<? super TARGET> validator) {
            return withValidator(validator.getPredicate(), Translator.translate(validator.getMessageTranslation()));
        }
    }

    public static class LCMBindingBuilderImpl<BEAN, FIELDVALUE, TARGET> extends BindingBuilderImpl<BEAN, FIELDVALUE, TARGET> implements LCMBindingBuilder<BEAN, TARGET> {
        public LCMBindingBuilderImpl(Binder<BEAN> binder, HasValue<?, FIELDVALUE> field, Converter<FIELDVALUE, TARGET> converterValidatorChain, BindingValidationStatusHandler statusHandler) {
            super(binder, field, converterValidatorChain, statusHandler);
        }

        @Override
        public Binding<BEAN, TARGET> bindWriteOnly(Setter<BEAN, TARGET> setter) {
            return bind(x -> null, setter);
        }

        @Override
        public Binding<BEAN, TARGET> bindValidationOnly() {
            return bind(x -> null, (b, v) -> {});
        }

        @Override
        public LCMBindingBuilder<BEAN, TARGET> withValidator(Validator<? super TARGET> validator) {
            return (LCMBindingBuilder<BEAN, TARGET>) super.withValidator(validator);
        }

        @Override
        public <NEWTARGET> LCMBindingBuilder<BEAN, NEWTARGET> withConverter(Converter<TARGET, NEWTARGET> converter) {
            return (LCMBindingBuilder<BEAN, NEWTARGET>) super.withConverter(converter);
        }

        @Override
        public LCMBindingBuilder<BEAN, TARGET> withValidationStatusHandler(BindingValidationStatusHandler handler) {
            return (LCMBindingBuilder<BEAN, TARGET>) super.withValidationStatusHandler(handler);
        }

        @Override
        public LCMBindingBuilder<BEAN, TARGET> asRequired(ErrorMessageProvider errorMessageProvider) {
            return (LCMBindingBuilder<BEAN, TARGET>) super.asRequired(errorMessageProvider);
        }

        @Override
        public LCMBindingBuilder<BEAN, TARGET> asRequired(Validator<TARGET> customRequiredValidator) {
            return (LCMBindingBuilder<BEAN, TARGET>) super.asRequired(customRequiredValidator);
        }

        @Override
        protected <NEWTARGET> LCMBindingBuilder<BEAN, NEWTARGET> withConverter(Converter<TARGET, NEWTARGET> converter, boolean resetNullRepresentation) {
            return (LCMBindingBuilder<BEAN, NEWTARGET>) super.withConverter(converter, resetNullRepresentation);
        }
    }
}
