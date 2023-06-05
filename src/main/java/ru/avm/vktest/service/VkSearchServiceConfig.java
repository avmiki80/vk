package ru.avm.vktest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.avm.vktest.service.history.HistoryService;

@Configuration
@RequiredArgsConstructor
public class VkSearchServiceConfig {
    private final HistoryService historyService;
    private final VkFriendHandler vkFriendHandlerImpl;
    private final VkGroupHandler vkGroupHandlerImpl;

    @Bean
    public VkSearchService vkSearchServiceImp(){
        return new VkSearchServiceImpl(historyService, vkFriendHandlerImpl, vkGroupHandlerImpl);
    }

    @Bean
    public VkSearchService vkSearchService(){
        return new ValidatorVkSearchService(vkSearchServiceImp());
    }

}
