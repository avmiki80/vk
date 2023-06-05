package ru.avm.vktest.service;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.extern.slf4j.Slf4j;
import ru.avm.vktest.exception.VkServiceException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
@Slf4j
public class VkApiClientGetter {
    private Long VK_REQUEST_DELAY;
    private final Lock lock = new ReentrantLock();
    private final TransportClient transportClient = new HttpTransportClient();
    private final VkApiClient vkApiClient = new VkApiClient(transportClient);

    public VkApiClientGetter(Long VK_REQUEST_DELAY) {
        this.VK_REQUEST_DELAY = VK_REQUEST_DELAY;
    }
//Todo переделать костыль на более производительное решение
    public VkApiClient getClient(){
        lock.lock();
//        log.info("Thread: " + Thread.currentThread().getName() +  ", start:");
        try {
            Thread.sleep(VK_REQUEST_DELAY);
        } catch (InterruptedException exception) {
            throw new VkServiceException(exception.getMessage());
        } finally {
//            log.info("Thread: " + Thread.currentThread().getName() +  ", finish:");
            lock.unlock();
        }
        return vkApiClient;
    }
}
