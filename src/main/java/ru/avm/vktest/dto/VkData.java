package ru.avm.vktest.dto;

import com.vk.api.sdk.client.actors.UserActor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
public class VkData {
    private UserActor actor;
    private Integer userId;
    private String title;
}
