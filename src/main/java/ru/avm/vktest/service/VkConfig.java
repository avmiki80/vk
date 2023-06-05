package ru.avm.vktest.service;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.friends.responses.GetResponse;
import com.vk.api.sdk.objects.groups.responses.GetObjectExtendedResponse;
import com.vk.api.sdk.queries.friends.FriendsGetQuery;
import com.vk.api.sdk.queries.groups.GroupsGetQueryWithObjectExtended;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.avm.vktest.exception.VkServiceException;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class VkConfig {
    @Value("${vk.app_id}")
    private Integer APP_ID;
    @Value("${vk.client_secret}")
    private String CLIENT_SECRET;
    @Value("${vk.redirect_url}")
    private String REDIRECT_URI;


    @Bean
    public VkApiClient vkApiClient(){
        TransportClient transportClient = new HttpTransportClient();
        return new VkApiClient(transportClient);
    }

    @Bean(autowireCandidate = false)
    @Scope("request")
    public UserActor getActor(VkApiClient vk, String code){
        UserActor actor;
        try {
            UserAuthResponse authResponse = vk.oAuth()
                    .userAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, REDIRECT_URI, code)
                    .execute();
            actor =  new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
        } catch (ApiException | ClientException e) {
            throw new VkServiceException(e.getMessage());
        }
        return actor;
    }
}
