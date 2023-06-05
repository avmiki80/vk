package ru.avm.vktest.service;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.avm.vktest.exception.VkServiceException;

@Configuration
@Slf4j
public class VkConfig {
    @Value("${vk.app_id}")
    private Integer APP_ID;
    @Value("${vk.client_secret}")
    private String CLIENT_SECRET;
    @Value("${vk.redirect_url}")
    private String REDIRECT_URI;
    @Value("${vk.request_delay}")
    private Long VK_REQUEST_DELAY;

    @Bean
    public VkApiClient vkApiClient(){
        TransportClient transportClient = new HttpTransportClient();
        return new VkApiClient(transportClient);
    }

    @Bean
    public VkApiClientGetter vkApiClientGetter(){
        return new VkApiClientGetter(VK_REQUEST_DELAY);
    }

    @Bean(autowireCandidate = false)
    @Scope("request")
    public UserActor getActor(VkApiClient vk, String code){
        log.info("vk.app_id: " + APP_ID +  " vk.client_secret: " + CLIENT_SECRET);
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
