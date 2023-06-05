package ru.avm.vktest.converter;

import ru.avm.vktest.dto.VkGroup;
import ru.avm.vktest.model.BaseEntity;
import ru.avm.vktest.model.Group;

import java.util.Collection;
import java.util.function.Supplier;

public interface ToEntityMapper<D, E extends BaseEntity> {
    E toEntity(D dto);
    Collection<E> convertToEntities(Collection<D> dtos);
    Collection<E> convertToEntities(Collection<D> dtos, Supplier<Collection<E>> toCollection);
}
