package ru.avm.vktest.service;

import com.vk.api.sdk.client.actors.UserActor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.avm.vktest.dto.VkGroup;
import ru.avm.vktest.dto.VkHistory;
import ru.avm.vktest.service.history.HistoryService;

import java.util.*;

@RequiredArgsConstructor
@Slf4j
public class VkSearchServiceImpl implements VkSearchService {
    private final HistoryService historyService;
    private final VkFriendHandler vkFriendHandlerImpl;
    private final VkGroupHandler vkGroupHandlerImpl;

    @Override
    public Set<VkGroup> findUserGroupByTitle(UserActor actor, String title) {
        Set<VkGroup> groups = vkGroupHandlerImpl.findUserGroupByTitle(actor, actor.getId(), title);
        historyService.save(VkHistory.builder().requestParam(title).groups(groups).build());
        return groups;
    }

    @Override
    public Set<VkGroup> findUserAndFriendGroupByTitle(UserActor actor, String title) {
        Set<VkGroup> vkGroups = new HashSet<>();
        vkFriendHandlerImpl.findUserFriends(actor)
                .forEach(userId -> vkGroups.addAll(vkGroupHandlerImpl.findUserGroupByTitle(actor, userId, title)));
        vkGroups.addAll(vkGroupHandlerImpl.findUserGroupByTitle(actor, actor.getId(), title));
        return vkGroups;
    }
}
