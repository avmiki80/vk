package ru.avm.vktest.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
public class VkHistory {
    private final Integer id;
    private final String requestParam;
    private final LocalDateTime requestDate;
    private final Set<VkGroup> groups;
}
