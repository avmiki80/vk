package ru.avm.vktest.service;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.GroupFull;
import com.vk.api.sdk.objects.groups.responses.GetByIdObjectLegacyResponse;
import com.vk.api.sdk.objects.groups.responses.GetObjectExtendedResponse;
import com.vk.api.sdk.queries.friends.FriendsGetQuery;
import com.vk.api.sdk.queries.groups.GroupsGetQuery;
import com.vk.api.sdk.queries.groups.GroupsGetQueryWithExtended;
import com.vk.api.sdk.queries.groups.GroupsGetQueryWithObjectExtended;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.avm.vktest.converter.ToDTOMapper;
import ru.avm.vktest.dto.VkGroup;
import ru.avm.vktest.exception.VkServiceException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VkGroupHandlerImpl implements VkGroupHandler{
    private final VkApiClientGetter vkApiClientGetter;
    private final QueueVkQueryHandler<GroupsGetQueryWithObjectExtended, GetObjectExtendedResponse> vkGroupsQueryHandler;
    private final ToDTOMapper<GroupFull> toVkGroupFullMapper;
    @Override
    public Set<VkGroup> findUserGroupByTitle(UserActor actor, Integer userId, String title) {
        List<GroupFull> response;
        response = vkGroupsQueryHandler.findByQuery(vkApiClientGetter.getClient().groups().getObjectExtended(actor).userId(userId).extended(true)).getItems();
        return toVkGroupFullMapper.convertToDtos(response, HashSet::new)
                .stream()
                .filter(g -> g.getTitle().toLowerCase().startsWith(title.toLowerCase()))
                .collect(Collectors.toSet());
    }
}
