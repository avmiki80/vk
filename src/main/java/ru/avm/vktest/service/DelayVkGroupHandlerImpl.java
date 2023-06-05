package ru.avm.vktest.service;

import com.vk.api.sdk.client.actors.UserActor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.avm.vktest.dto.VkGroup;
import ru.avm.vktest.exception.VkServiceException;

import java.util.Set;
import java.util.concurrent.*;


@Service
@RequiredArgsConstructor
@Slf4j
@Deprecated
public class DelayVkGroupHandlerImpl implements VkGroupHandler{
    @Value("${vk.request_delay}")
    private Long VK_REQUEST_DELAY;
    private final long LOCK_WAIT_TIME = 2000L;
    private final VkGroupHandler vkGroupHandlerImpl;

    @Override
    public Set<VkGroup> findUserGroupByTitle(UserActor actor, Integer userId, String title) {
        CountDownLatch lock = new CountDownLatch(1);
        Set<VkGroup> groups;
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<Set<VkGroup>> future = executorService.schedule(findUserGroupByTitle(actor, userId, title, lock), VK_REQUEST_DELAY, TimeUnit.MILLISECONDS);
        try {
            lock.await(LOCK_WAIT_TIME, TimeUnit.MILLISECONDS);
            groups = future.get();
        } catch (InterruptedException | ExecutionException exception) {
            throw new VkServiceException(exception.getMessage());
        } finally {
            executorService.shutdown();
        }
        return groups;
    }

    private Callable<Set<VkGroup>> findUserGroupByTitle(UserActor actor, Integer userId, String title, CountDownLatch lock){
        return () -> {
            Set<VkGroup> groups;
            try {
                groups = vkGroupHandlerImpl.findUserGroupByTitle(actor, userId, title);
            } catch (VkServiceException e) {
                throw new VkServiceException(e.getMessage());
            } finally {
                log.info("UserId: " + userId);
                lock.countDown();
            }
            return groups;
        };
    }
}
