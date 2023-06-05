package ru.avm.vktest.service;

import com.vk.api.sdk.client.actors.UserActor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.avm.vktest.dto.VkData;
import ru.avm.vktest.dto.VkGroup;
import ru.avm.vktest.exception.VkServiceException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static java.util.stream.Collectors.toList;

//@Service
//@Slf4j
@RequiredArgsConstructor
public class QueueVkGroupHandler {
//    private final VkGroupHandler vkGroupHandlerImpl;
//    private final VkQueue vkQueue;
//
//    public Set<VkGroup> findUserGroupByTitle(UserActor actor, Set<Integer> userIds, String title) {
//        userIds.stream().map(userId -> producer(actor, userId, title)).forEach(vkQueue.getExecutor()::submit);
//        List<Callable<Set<VkGroup>>> consumers = userIds.stream().map(i -> consumer()).collect(toList());
//        Set<VkGroup> groups = new HashSet<>();
//        try {
//            List<Future<Set<VkGroup>>> future = vkQueue.getExecutor().invokeAll(consumers);
//            future.forEach(f -> {
//                try {
//                    groups.addAll(f.get());
//                } catch (InterruptedException | ExecutionException exception) {
//                    throw new VkServiceException(exception.getMessage());
//                }
//            });
//        } catch (InterruptedException exception) {
//            throw new VkServiceException(exception.getMessage());
//        }
//        return groups;
//    }
//
//    private Runnable producer(UserActor actor, Integer userId, String title){
//        return () -> {
//            try {
//                vkQueue.getBuffer().put(VkData.builder().actor(actor).userId(userId).title(title).build());
//            } catch (InterruptedException exception) {
//                throw new VkServiceException(exception.getMessage());
//            }
//        };
//    }
//
//    private Callable<Set<VkGroup>> consumer(){
//        return () -> {Set<VkGroup> groups;
//        try {
//            VkData consumed = vkQueue.getBuffer().take();
//            log.info("Started: " + consumed.getUserId());
//            Thread.sleep(1010);
//            groups = vkGroupHandlerImpl.findUserGroupByTitle(consumed.getActor(), consumed.getUserId(), consumed.getTitle());
//            log.info("Finished: " + consumed.getUserId());
//        } catch (InterruptedException e) {
//            throw new VkServiceException(e.getMessage());
//        }
//        return groups;
//        };
//    }
}

