package ru.avm.vktest.converter;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import ru.avm.vktest.dto.VkGroup;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static ru.avm.vktest.converter.UtilMapper.convertToCollection;
import static ru.avm.vktest.util.Checker.isEmptyCollection;

public abstract class ToDTOMapperImpl<T> implements ToDTOMapper<T>{
    private final ModelMapper mapper;
    private final Class<T> clazz;

    public ToDTOMapperImpl(ModelMapper mapper, Class<T> clazz) {
        this.mapper = mapper;
        this.clazz = clazz;
    }

    @PostConstruct
    public void init(){
        TypeMap<T, VkGroup> mapToDto = mapper.createTypeMap(clazz, VkGroup.class);
        mapToDto.setPostConverter(toDtoConverter());
    }

    private Converter<T, VkGroup> toDtoConverter() {
        return context -> {
            T source = context.getSource();
            VkGroup destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    protected abstract void mapSpecificFields(T source, VkGroup destination);

    @Override
    public VkGroup toDto(T entity) {
        return Objects.nonNull(entity)? mapper.map(entity, VkGroup.class) : null;
    }

    @Override
    public Collection<VkGroup> convertToDtos(Collection<T> entites) {
        if(isEmptyCollection(entites))
            return Collections.emptySet();
        Function<T, VkGroup> convert = (iter) -> toDto(iter);
        return convertToCollection(entites, convert);
    }

    @Override
    public Collection<VkGroup> convertToDtos(Collection<T> entites, Supplier<Collection<VkGroup>> toCollection) {
        if(isEmptyCollection(entites))
            return toCollection.get();
        return convertToCollection(entites, this::toDto, toCollection);
    }
}
