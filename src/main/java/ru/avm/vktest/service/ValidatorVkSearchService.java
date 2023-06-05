package ru.avm.vktest.service;

import com.vk.api.sdk.client.actors.UserActor;
import lombok.RequiredArgsConstructor;
import ru.avm.vktest.dto.VkGroup;
import ru.avm.vktest.exception.VkServiceException;

import java.util.Set;

import static ru.avm.vktest.exception.MessageException.EMPTY_ACTOR;
import static ru.avm.vktest.util.Checker.checkEmptyObject;

@RequiredArgsConstructor
public class ValidatorVkSearchService implements VkSearchService{
    private final VkSearchService vkSearchService;
    @Override
    public Set<VkGroup> findUserGroupByTitle(UserActor actor, String title) {
        //Todo можно заменить проверку параметров во всех сервисах на паттерн стратегию
        checkEmptyObject(actor, () -> {
           throw new VkServiceException(EMPTY_ACTOR);
        });
        return vkSearchService.findUserGroupByTitle(actor, title);
    }

    @Override
    public Set<VkGroup> findUserAndFriendGroupByTitle(UserActor actor, String title) {
        //Todo можно заменить проверку параметров во всех сервисах на паттерн стратегию
        checkEmptyObject(actor, () -> {
            throw new VkServiceException(EMPTY_ACTOR);
        });
        return vkSearchService.findUserAndFriendGroupByTitle(actor, title);
    }
}
