package ru.avm.vktest.service;

import com.vk.api.sdk.objects.friends.responses.GetResponse;
import com.vk.api.sdk.objects.groups.responses.GetObjectExtendedResponse;
import com.vk.api.sdk.queries.friends.FriendsGetQuery;
import com.vk.api.sdk.queries.groups.GroupsGetQueryWithObjectExtended;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class VkQueryHandlerConfig {
    @Value("${vk.request_delay}")
    private Long VK_REQUEST_DELAY;
    private final VkQueue vkQueue;

    @Bean
    public VkQueryHandler<FriendsGetQuery, GetResponse> vkFriendsQueryHandler(){
        return new VkQueryHandler<>(VK_REQUEST_DELAY, vkQueue);
    }
    @Bean
    public VkQueryHandler<GroupsGetQueryWithObjectExtended, GetObjectExtendedResponse> vkGroupsQueryHandler(){
        return new VkQueryHandler<>(VK_REQUEST_DELAY, vkQueue);
    }
}
