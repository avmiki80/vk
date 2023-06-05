package ru.avm.vktest.converter;

import com.vk.api.sdk.objects.groups.GroupFull;
import com.vk.api.sdk.objects.groups.responses.GetByIdObjectLegacyResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.avm.vktest.dto.VkGroup;
import ru.avm.vktest.dto.VkHistory;
import ru.avm.vktest.model.BaseEntity;
import ru.avm.vktest.model.Group;
import ru.avm.vktest.model.History;

import java.util.Set;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper getMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return mapper;
    }

    @Bean
    public ToEntityMapper<VkGroup, Group> toGroupMapper(){
        return new ToEntityMapperImpl<VkGroup, Group>(VkGroup.class, Group.class, getMapper()) {
            @Override
            protected void mapSpecificFields(VkGroup source, Group destination) {
                destination.setTitle(source.getTitle());
                destination.setVkId(source.getVkId());
                destination.setId(null);
            }
        };
    }

    @Bean
    public ToEntityMapper<VkHistory, History> toHistoryMapper(){
        return new ToEntityMapperImpl<VkHistory, History>(VkHistory.class, History.class, getMapper()) {
            @Override
            protected void mapSpecificFields(VkHistory source, History destination) {
                destination.setRequestParam(source.getRequestParam());
                destination.setGroups((Set<Group>) toGroupMapper().convertToEntities(source.getGroups()));
            }
        };
    }

    @Bean
    public ToDTOMapper<GetByIdObjectLegacyResponse> toVkGroupMapper(){
        return new ToDTOMapperImpl<GetByIdObjectLegacyResponse>(getMapper(), GetByIdObjectLegacyResponse.class) {
            @Override
            protected void mapSpecificFields(GetByIdObjectLegacyResponse source, VkGroup destination) {
                destination.setTitle(source.getName());
                destination.setVkId(source.getId());
                destination.setId(null);
            }
        };
    }
    @Bean
    public ToDTOMapper<GroupFull> toVkGroupFullMapper(){
        return new ToDTOMapperImpl<GroupFull>(getMapper(), GroupFull.class) {
            @Override
            protected void mapSpecificFields(GroupFull source, VkGroup destination) {
                destination.setTitle(source.getName());
                destination.setVkId(source.getId());
                destination.setId(null);
            }
        };
    }

    @Bean
    public ToDTOMapper<Group> toVkGroupFromEntityMapper(){
        return new ToDTOMapperImpl<Group>(getMapper(), Group.class) {
            @Override
            protected void mapSpecificFields(Group source, VkGroup destination) {
                destination.setTitle(source.getTitle());
                destination.setVkId(source.getVkId());
                destination.setId(source.getId());
            }
        };
    }
}
