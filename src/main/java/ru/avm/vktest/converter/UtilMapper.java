package ru.avm.vktest.converter;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class UtilMapper {
    public static <T, R> Collection<R> convertToCollection(Collection<T> datas, Function<T, R> converter){
        return datas.stream().map(converter).collect(Collectors.toSet());
    }
    public static <T, R> Collection<R> convertToCollection(Collection<T> datas, Function<T, R> converter, Supplier<Collection<R>> toCollection){
        return datas.stream().map(converter).collect(Collectors.toCollection(toCollection));
    }
}
