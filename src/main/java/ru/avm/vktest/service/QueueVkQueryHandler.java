package ru.avm.vktest.service;

import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.objects.Validable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.avm.vktest.exception.VkServiceException;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RequiredArgsConstructor
@Slf4j
public class QueueVkQueryHandler<Q extends AbstractQueryBuilder<?, R>, R extends Validable> {
    private final VkQueue vkQueue;

    public R findByQuery(Q query){
        vkQueue.getExecutor().execute(producer(query));
        R response;
        Future<R> future = vkQueue.getExecutor().submit(consumer());
        try {
            response = future.get();
        } catch (InterruptedException | ExecutionException exception) {
            throw new VkServiceException(exception.getMessage());
        }
        return response;
    }

    private Runnable producer(Q query){
        return () -> {
            try {
                vkQueue.getBuffer().put(query);
            } catch (InterruptedException exception) {
                throw new VkServiceException(exception.getMessage());
            }
        };
    }
    private Callable<R> consumer(){
        return () -> {
            R result;
            log.info("Start");
            try {
                AbstractQueryBuilder<?, R> consumed = (AbstractQueryBuilder<?, R>) vkQueue.getBuffer().take();
                result = consumed.execute();
                Thread.sleep(1010);
            } catch (InterruptedException e) {
                throw new VkServiceException(e.getMessage());
            }
            log.info("Finish");
            return result;
        };
    }
}
