package ru.avm.vktest.service;

import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.objects.Validable;
import com.vk.api.sdk.objects.friends.responses.GetResponse;
import com.vk.api.sdk.objects.groups.responses.GetObjectExtendedResponse;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.avm.vktest.dto.VkData;

import javax.annotation.PreDestroy;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Component
@Getter
public class VkQueue {
    private final BlockingQueue<AbstractQueryBuilder<?, ?>> buffer = new LinkedBlockingQueue<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    @PreDestroy
    public void destroy(){
        executor.shutdown();
    }

}
