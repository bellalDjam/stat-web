package com.minagri.stats.core.java;

import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public interface Classes {

    @SneakyThrows
    static List<Field> getFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));

        Class<?> superClass = clazz.getSuperclass();
        while (superClass != null) {
            fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
            superClass = superClass.getSuperclass();
        }

        return fields;
    }

    static boolean hasField(Class<?> clazz, String field) {
        return getField(clazz, field) != null;
    }

    @SneakyThrows
    static Field getField(Class<?> clazz, String fieldName) {
        String[] fieldNameParts = fieldName.split("\\.");

        Class<?> containingClass = clazz;
        if (fieldNameParts.length > 1) {
            for (int i = 0; i < fieldNameParts.length - 1; i++) {
                String inBetweenFieldName = fieldNameParts[i];
                Field inBetweenField = getField(containingClass, inBetweenFieldName);
                if (inBetweenField == null) {
                    return null;
                }

                containingClass = inBetweenField.getType();
            }
        }

        String finalFieldName = fieldNameParts[fieldNameParts.length - 1];

        Field field = Arrays.stream(containingClass.getDeclaredFields())
                .filter(declaredField -> declaredField.getName().equals(finalFieldName))
                .findFirst()
                .orElse(null);

        if (field == null && containingClass.getSuperclass() != null) {
            return getField(containingClass.getSuperclass(), fieldName);
        }

        return field;
    }

    static List<Field> getFieldsOfType(Class<?> clazz, Class<?> fieldType) {
        return getFields(clazz).stream()
                .filter(field -> fieldType.isAssignableFrom(field.getType()))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    static Class<?> getFieldType(Class<?> clazz, String fieldName) {
        Field field = getField(clazz, fieldName);
        return field != null ? field.getType() : null;
    }

    @SuppressWarnings("unchecked")
    static <S, T> Class<T> getGenericTypeParameter(Class<? extends S> subClass, Class<S> superClass) {
        Type type = subClass.getGenericSuperclass();

        while (!(type instanceof ParameterizedType) || ((ParameterizedType) type).getRawType() != superClass) {
            if (type instanceof ParameterizedType) {
                type = ((Class<?>) ((ParameterizedType) type).getRawType()).getGenericSuperclass();
            } else {
                type = ((Class<?>) type).getGenericSuperclass();
            }
        }

        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        if (actualTypeArguments != null && actualTypeArguments.length > 0) {
            return (Class<T>) actualTypeArguments[0];
        }
        return null;
    }

    static <T extends Annotation> T getAnnotation(Class<?> annotatedClass, Class<T> annotationClass) {
        T annotation = annotatedClass.getAnnotation(annotationClass);
        while (annotation == null && annotatedClass.getSuperclass() != null) {
            annotatedClass = annotatedClass.getSuperclass();
            annotation = annotatedClass.getAnnotation(annotationClass);
        }
        return annotation;
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    static <T extends Annotation> List<T> getAnnotations(Class<?> annotatedClass, Class<T> annotationClass) {
        Repeatable repeatable = annotationClass.getAnnotation(Repeatable.class);
        Class<? extends Annotation> annotationListClass = repeatable != null ? repeatable.value() : null;

        LinkedHashSet<T> annotations = new LinkedHashSet<>();
        Class<?> lookupClass = annotatedClass;
        while (lookupClass != null) {
            T annotation = annotatedClass.getAnnotation(annotationClass);
            if (annotation != null) {
                annotations.add(annotation);
            }

            if (annotationListClass != null) {
                Annotation annotationList = lookupClass.getAnnotation(annotationListClass);
                if (annotationList != null) {
                    T[] annotationListValue = (T[]) annotationListClass.getMethod("value").invoke(annotationList);
                    annotations.addAll(Arrays.asList(annotationListValue));
                }
            }

            lookupClass = lookupClass.getSuperclass();
        }
        return new ArrayList<>(annotations);
    }

    static <T extends Annotation> List<T> getAnnotations(Object annotatedObject, Class<T> annotationClass) {
        return getAnnotations(annotatedObject.getClass(), annotationClass);
    }

    static <T extends Annotation> T getAnnotation(Object annotatedObject, Class<T> annotationClass) {
        return getAnnotation(annotatedObject.getClass(), annotationClass);
    }

    static Class<?> getAnnotatedClass(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        Class<?> lookupClass = clazz;
        while (lookupClass != null) {
            if (lookupClass.getDeclaredAnnotation(annotationClass) != null) {
                return lookupClass;
            }
            lookupClass = lookupClass.getSuperclass();
        }
        return null;
    }

    @SneakyThrows
    static List<Method> getMethods(Class<?> clazz) {
        List<Method> methods = new ArrayList<>(Arrays.asList(clazz.getDeclaredMethods()));

        Class<?> superClass = clazz.getSuperclass();
        while (superClass != null) {
            methods.addAll(Arrays.asList(superClass.getDeclaredMethods()));
            superClass = superClass.getSuperclass();
        }

        return methods;
    }

    @SuppressWarnings("unchecked")
    static <S, I> Class<S> getSuperClassWithInterface(Class<? extends S> subClass, Class<I> interfaceClass) {
        Class<?> classToCheck = subClass;
        while (classToCheck != null) {
            if (Arrays.asList(classToCheck.getInterfaces()).contains(interfaceClass)) {
                return (Class<S>) classToCheck;
            }
            classToCheck = classToCheck.getSuperclass();
        }
        return null;
    }

}
