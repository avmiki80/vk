package ru.avm.vktest.service;

import com.vk.api.sdk.client.actors.UserActor;
import ru.avm.vktest.dto.VkGroup;

import java.util.Set;

public interface VkGroupHandler {
    Set<VkGroup> findUserGroupByTitle(UserActor actor, Integer userId, String title);
}
