package ru.avm.vktest.service;

import com.vk.api.sdk.client.actors.UserActor;

import java.util.List;

public interface VkFriendHandler {
    List<Integer> findUserFriends(UserActor actor);
}
