package ru.avm.vktest.converter;

import ru.avm.vktest.dto.VkGroup;
import ru.avm.vktest.model.Group;

import java.util.Collection;
import java.util.function.Supplier;

public interface ToDTOMapper<T> {
    VkGroup toDto(T entity);
    Collection<VkGroup> convertToDtos(Collection<T> entites);
    Collection<VkGroup> convertToDtos(Collection<T> entites, Supplier<Collection<VkGroup>> toCollection);
}
