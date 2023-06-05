package ru.avm.vktest.service.group;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.avm.vktest.model.Group;
import ru.avm.vktest.repo.GroupRepo;
import ru.avm.vktest.search.VkSearch;

@Service
@RequiredArgsConstructor
public class GroupServiceJpa {
    private final GroupRepo groupRepo;

    public Page<Group> search(VkSearch search, PageRequest pageRequest){
        return groupRepo.search(search.getTitle(), pageRequest);
    }
}
