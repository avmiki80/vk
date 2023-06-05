package ru.avm.vktest.service;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.groups.GroupFull;
import com.vk.api.sdk.objects.groups.responses.GetObjectExtendedResponse;
import com.vk.api.sdk.queries.groups.GroupsGetQueryWithObjectExtended;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.avm.vktest.converter.ToDTOMapper;
import ru.avm.vktest.dto.VkGroup;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VkGroupHandlerImpl implements VkGroupHandler{
    private final VkApiClient vkApiClient;
    private final VkQueryHandler<GroupsGetQueryWithObjectExtended, GetObjectExtendedResponse> vkGroupsQueryHandler;
    private final ToDTOMapper<GroupFull> toVkGroupFullMapper;
    @Override
    public Set<VkGroup> findUserGroupByTitle(UserActor actor, Integer userId, String title) {
        List<GroupFull> response;
        response = vkGroupsQueryHandler.findByQuery(vkApiClient.groups().getObjectExtended(actor).userId(userId).extended(true)).getItems();
        return toVkGroupFullMapper.convertToDtos(response, HashSet::new)
                .stream()
                .filter(g -> g.getTitle().toLowerCase().startsWith(title.toLowerCase()))
                .collect(Collectors.toSet());
    }
}
