package ru.avm.vktest.service;

import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.friends.responses.GetResponse;
import com.vk.api.sdk.objects.groups.GroupFull;
import com.vk.api.sdk.objects.groups.responses.GetObjectExtendedResponse;
import com.vk.api.sdk.queries.friends.FriendsGetQuery;
import com.vk.api.sdk.queries.groups.GroupsGetQueryWithObjectExtended;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.avm.vktest.converter.ToDTOMapper;
import ru.avm.vktest.dto.VkGroup;
import ru.avm.vktest.dto.VkHistory;
import ru.avm.vktest.exception.VkServiceException;
import ru.avm.vktest.service.history.HistoryService;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class VkSearchServiceImpl implements VkSearchService {
    private final VkApiClientGetter vkApiClientGetter;
    private final HistoryService historyService;
    private final QueueVkQueryHandler<FriendsGetQuery, GetResponse> vkFriendsQueryHandler;
    private final VkGroupHandler vkGroupHandlerImpl;

    @Override
    public Set<VkGroup> findUserGroupByTitle(UserActor actor, String title) {
        Set<VkGroup> groups = vkGroupHandlerImpl.findUserGroupByTitle(actor, actor.getId(), title)
                .stream()
                .filter(g -> g.getTitle().toLowerCase().startsWith(title.toLowerCase()))
                .collect(Collectors.toSet());;
        historyService.save(VkHistory.builder().requestParam(title).groups(groups).build());
        return groups;
    }

    @Override
    public Set<VkGroup> findUserAndFriendGroupByTitle(UserActor actor, String title) {
        Set<VkGroup> vkGroups = new HashSet<>();
        log.info("Start-----");
        long start = System.currentTimeMillis();
        List<Integer> userIds = vkFriendsQueryHandler.findByQuery(vkApiClientGetter.getClient().friends().get(actor)).getItems();
        for (Integer userId: userIds) {
            vkGroups.addAll(vkGroupHandlerImpl.findUserGroupByTitle(actor, userId, title));
        }
        log.info("userId: " + actor.getId());
        vkGroups.addAll(vkGroupHandlerImpl.findUserGroupByTitle(actor, actor.getId(), title));
        log.info("Finish ----" + (System.currentTimeMillis() - start));
        return vkGroups;
    }
}
