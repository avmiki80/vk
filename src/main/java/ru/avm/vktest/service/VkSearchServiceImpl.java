package ru.avm.vktest.service;

import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.avm.vktest.dto.VkGroup;
import ru.avm.vktest.dto.VkHistory;
import ru.avm.vktest.exception.VkServiceException;
import ru.avm.vktest.service.history.HistoryService;

import java.util.*;

@RequiredArgsConstructor
@Slf4j
public class VkSearchServiceImpl implements VkSearchService {
    private final VkApiClientGetter vkApiClientGetter;
    private final VkGroupHandler vkGroupHandlerImpl;
    private final HistoryService historyService;
    @Override
    public Set<VkGroup> findUserGroupByTitle(UserActor actor, String title) {
        Set<VkGroup> groups = vkGroupHandlerImpl.findUserGroupByTitle(actor, actor.getId(), title);
        historyService.save(VkHistory.builder().requestParam(title).groups(groups).build());
        return groups;
    }

    @Override
    public Set<VkGroup> findUserAndFriendGroupByTitle(UserActor actor, String title) {
        Set<VkGroup> vkGroups = new HashSet<>();
        try {
//            com.vk.api.sdk.objects.friends.responses.GetResponse friends = vkApiClient.friends().get(actor).execute();
//            GetFieldsResponse friends = vkApiClient.friends().getWithFields(
//                    actor,
//                    com.vk.api.sdk.objects.users.Fields.FIRST_NAME_NOM,
//                    com.vk.api.sdk.objects.users.Fields.LAST_NAME_NOM,
//                    com.vk.api.sdk.objects.users.Fields.NICKNAME).execute();
            log.info("Start-----");
            long start = System.currentTimeMillis();
            vkApiClientGetter.getClient().friends().get(actor).execute().getItems().forEach(f -> {
                log.info("userId: " + f);
                vkGroups.addAll(vkGroupHandlerImpl.findUserGroupByTitle(actor, f, title));
            });
            log.info("userId: " + actor.getId());
            vkGroups.addAll(vkGroupHandlerImpl.findUserGroupByTitle(actor, actor.getId(), title));
            log.info("Finish ----" + (System.currentTimeMillis() - start));
        } catch (ApiException | ClientException e) {
            throw new VkServiceException(e.getMessage());
        }
        return vkGroups;
    }
}
