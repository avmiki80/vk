package ru.avm.vktest.service.group;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.avm.vktest.dto.VkGroup;
import ru.avm.vktest.search.VkSearch;

import static ru.avm.vktest.util.Checker.checkEmptyObject;

@RequiredArgsConstructor
public class ValidatorGroupService implements GroupService{
    private final GroupService groupService;
    @Override
    public Page<VkGroup> search(VkSearch search) {
        checkEmptyObject(search);
        return groupService.search(search);
    }
}
