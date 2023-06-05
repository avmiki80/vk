package ru.avm.vktest.data;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.avm.vktest.dto.VkGroup;
import ru.avm.vktest.dto.VkHistory;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor(staticName = "vkHistory")
@With
public class VkHistoryDataBuilder implements TestDataBuilder<VkHistory>{
    private Integer id = 2000;
    private String requestParam = "хзззз";
    private LocalDateTime requestDate = LocalDateTime.now();
    private Set<VkGroup> groups = new HashSet<>();

    @Override
    public VkHistory build() {
        return VkHistory.builder().id(id).requestParam(requestParam).requestDate(requestDate).groups(groups).build();
    }
}
