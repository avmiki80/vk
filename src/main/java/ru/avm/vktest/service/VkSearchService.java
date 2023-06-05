package ru.avm.vktest.service;

import com.vk.api.sdk.client.actors.UserActor;
import ru.avm.vktest.dto.VkGroup;

import java.util.List;
import java.util.Set;

public interface VkSearchService {
    Set<VkGroup> findUserGroupByTitle(UserActor actor, String title);
    Set<VkGroup> findUserAndFriendGroupByTitle(UserActor actor, String title);
}
