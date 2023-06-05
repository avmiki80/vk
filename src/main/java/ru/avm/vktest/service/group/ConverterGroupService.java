package ru.avm.vktest.service.group;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.avm.vktest.converter.ToDTOMapper;
import ru.avm.vktest.dto.VkGroup;
import ru.avm.vktest.model.Group;
import ru.avm.vktest.search.VkSearch;

import java.util.ArrayList;
import java.util.List;

import static ru.avm.vktest.util.PageRequestUtil.createPageRequest;

@RequiredArgsConstructor
public class ConverterGroupService implements GroupService{
    private final GroupServiceJpa groupService;
    private final ToDTOMapper<Group> toVkGroupMapper;

    @Override
    public Page<VkGroup> search(VkSearch search) {
        PageRequest pageRequest = createPageRequest(search);
        Page<Group> page = groupService.search(search, pageRequest);
        return new PageImpl<>((List<VkGroup>) toVkGroupMapper.convertToDtos(page.getContent(), ArrayList::new), pageRequest, page.getTotalElements());
    }
}
