package ru.avm.vktest.service;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.objects.groups.responses.GetByIdObjectLegacyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.avm.vktest.converter.ToDTOMapper;
import ru.avm.vktest.service.history.HistoryService;

@Configuration
@RequiredArgsConstructor
public class VkSearchServiceConfig {
    private final VkApiClientGetter vkApiClientGetter;
    private final HistoryService historyService;
    private final VkGroupHandler vkGroupHandlerImpl;

    @Bean
    public VkSearchService vkSearchServiceImp(){
        return new VkSearchServiceImpl(vkApiClientGetter, vkGroupHandlerImpl, historyService);
    }

    @Bean
    public VkSearchService vkSearchService(){
        return new ValidatorVkSearchService(vkSearchServiceImp());
    }
}
