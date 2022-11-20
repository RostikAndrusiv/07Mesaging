package com.rostik.andrusiv.util;


import com.rostik.andrusiv.entity.EventDto;
import com.rostik.andrusiv.entity.EventEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    EventDto EventModelToDto(EventEntity eventEntity);

    EventEntity dtoToEventModel(EventDto eventDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCustomerFromDto(EventDto dto, @MappingTarget EventEntity entity);
}
