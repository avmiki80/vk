package ru.avm.vktest.service;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.friends.responses.GetResponse;
import com.vk.api.sdk.queries.friends.FriendsGetQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VkFriendHandlerImpl implements VkFriendHandler{
    private final VkApiClient vkApiClient;
    private final VkQueryHandler<FriendsGetQuery, GetResponse> vkFriendsQueryHandler;
    @Override
    public List<Integer> findUserFriends(UserActor actor) {
        return vkFriendsQueryHandler.findByQuery(vkApiClient.friends().get(actor)).getItems();
    }
}
