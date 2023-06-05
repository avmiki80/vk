package ru.avm.vktest.dto;

import lombok.Data;

@Data
public class VkGroup {
    private Integer id;
    private Integer vkId;
    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VkGroup)) return false;

        VkGroup vkGroup = (VkGroup) o;

        return getVkId().equals(vkGroup.getVkId());
    }

    @Override
    public int hashCode() {
        return getVkId().hashCode();
    }
}
