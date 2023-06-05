package ru.avm.vktest.data;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.avm.vktest.dto.VkGroup;

@AllArgsConstructor
@NoArgsConstructor(staticName = "vkGroup")
@With
public class VkGroupDataBuilder implements TestDataBuilder<VkGroup>{
    private Integer id = 2000;
    private Integer vkId = 777;
    private String title = "Some title";

    @Override
    public VkGroup build() {
        final VkGroup vkGroup = new VkGroup();
        vkGroup.setId(id);
        vkGroup.setVkId(vkId);
        vkGroup.setTitle(title);
        return vkGroup;
    }
}
