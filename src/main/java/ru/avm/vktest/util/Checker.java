package ru.avm.vktest.util;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

import static ru.avm.vktest.exception.MessageException.*;

public class Checker {

    public static <T> boolean isEmptyCollection(Collection<T> collection){
        return Objects.isNull(collection) || collection.isEmpty();
    }

    public static <T> void checkEmptyCollection(Collection<T> collection, String exceptionMessage){
        if(isEmptyCollection(collection))
            throw new IllegalArgumentException(exceptionMessage);
    }

    public static boolean isEmptyString(String str){
        return Objects.isNull(str) || str.trim().length() == 0;
    }

    public static void checkEmptyString(String str, String exceptionMessage){
        if(isEmptyString(str))
            throw new IllegalArgumentException(exceptionMessage);
    }

    public static void checkEmptyObject(Object dto){
        checkEmptyObject(dto, () -> {
            throw new IllegalArgumentException(EMPTY_OBJECT);
        });
    }
    public static <E extends RuntimeException> void checkEmptyObject(Object object, Supplier<E> supplier){
        if(Objects.isNull(object))
            supplier.get();
    }
    public static <T extends Number> boolean isEmptyId(T id) {
        return Objects.isNull(id) || id.equals(0L) || id.equals(0);
    }

    public static void checkEmptyId(Long id){
        checkEmptyId(id, ID_IS_ZERO);
    }
    public static <T extends Number> void checkEmptyId(T id, String exceptionMessage){
        if(isEmptyId(id))
            throw new IllegalArgumentException(exceptionMessage);
    }

    public static void checkNotEmptyId(Long id){
        if(!isEmptyId(id))
            throw new IllegalArgumentException(ID_NOT_ZERO);
    }
}
