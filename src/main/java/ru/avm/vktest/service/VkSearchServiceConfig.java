package ru.avm.vktest.service;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.objects.friends.responses.GetResponse;
import com.vk.api.sdk.objects.groups.GroupFull;
import com.vk.api.sdk.objects.groups.responses.GetByIdObjectLegacyResponse;
import com.vk.api.sdk.objects.groups.responses.GetObjectExtendedResponse;
import com.vk.api.sdk.queries.friends.FriendsGetQuery;
import com.vk.api.sdk.queries.groups.GroupsGetQueryWithObjectExtended;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Get;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.avm.vktest.converter.ToDTOMapper;
import ru.avm.vktest.service.history.HistoryService;

@Configuration
@RequiredArgsConstructor
public class VkSearchServiceConfig {
    private final VkApiClientGetter vkApiClientGetter;
    private final HistoryService historyService;
    private final QueueVkQueryHandler<FriendsGetQuery, GetResponse> vkFriendsQueryHandler;
    private final VkGroupHandler vkGroupHandlerImpl;
    @Bean
    public VkSearchService vkSearchServiceImp(){
        return new VkSearchServiceImpl(vkApiClientGetter, historyService, vkFriendsQueryHandler, vkGroupHandlerImpl);
    }

    @Bean
    public VkSearchService vkSearchService(){
        return new ValidatorVkSearchService(vkSearchServiceImp());
    }
}
