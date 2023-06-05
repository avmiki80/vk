package ru.avm.vktest.converter;


import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import ru.avm.vktest.model.BaseEntity;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static ru.avm.vktest.converter.UtilMapper.convertToCollection;
import static ru.avm.vktest.util.Checker.isEmptyCollection;

public abstract class ToEntityMapperImpl<D, E extends BaseEntity> implements ToEntityMapper<D, E>{
    private final Class<D> clazzDto;
    private final Class<E> clazzEntity;
    private final ModelMapper mapper;

    public ToEntityMapperImpl(Class<D> clazzDto, Class<E> clazzEntity, ModelMapper mapper) {
        this.clazzDto = clazzDto;
        this.clazzEntity = clazzEntity;
        this.mapper = mapper;
    }

    @PostConstruct
    public void init(){
        TypeMap<D, E> mapToDto = mapper.createTypeMap(clazzDto, clazzEntity);
        mapToDto.setPostConverter(toEntityConverter());
    }

    private Converter<D, E> toEntityConverter() {
        return context -> {
            D source = context.getSource();
            E destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    protected abstract void mapSpecificFields(D source, E destination);

    @Override
    public E toEntity(D dto) {
        return Objects.nonNull(dto)? mapper.map(dto, clazzEntity) : null;
    }

    @Override
    public Collection<E> convertToEntities(Collection<D> dtos) {
        if(isEmptyCollection(dtos))
            return Collections.emptySet();
        Function<D, E> converter = (iter) -> toEntity(iter);
        return convertToCollection(dtos, converter, HashSet::new);
    }
    @Override
    public Collection<E> convertToEntities(Collection<D> dtos, Supplier<Collection<E>> toCollection) {
        if(isEmptyCollection(dtos))
            return toCollection.get();
        Function<D, E> converter = this::toEntity;
        return convertToCollection(dtos, converter, toCollection);
    }

}
