package ru.avm.vktest.service.group;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.avm.vktest.dto.VkGroup;
import ru.avm.vktest.search.VkSearch;

public interface GroupService {
    Page<VkGroup> search(VkSearch search);
}
