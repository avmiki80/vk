package ru.avm.vktest.service.group;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.avm.vktest.converter.ToDTOMapper;
import ru.avm.vktest.model.Group;

@Configuration
@RequiredArgsConstructor
public class GroupServiceConfig {
    private final ToDTOMapper<Group> toVkGroupFromEntityMapper;
    private final GroupServiceJpa groupServiceJpa;

    @Bean
    public GroupService groupConverter(){
        return new ConverterGroupService(groupServiceJpa, toVkGroupFromEntityMapper);
    }

    @Bean
    public GroupService groupService(){
        return new ValidatorGroupService(groupConverter());
    }
}
