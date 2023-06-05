package ru.avm.vktest.service.history;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.avm.vktest.dto.VkGroup;
import ru.avm.vktest.model.Group;
import ru.avm.vktest.model.History;
import ru.avm.vktest.repo.GroupRepo;
import ru.avm.vktest.repo.HistoryRepo;
import ru.avm.vktest.search.VkSearch;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryServiceJpa {
    private final HistoryRepo historyRepo;
    private final GroupRepo groupRepo;

    @Transactional
    @Retryable(maxAttempts = 5, recover = "saveRecover",
            exclude = {IllegalArgumentException.class, NoSuchElementException.class})
    public History save(History history){
        Map<Integer, Group> persistGroups = groupRepo.findByVkIds(history.getGroups().stream().map(Group::getVkId).collect(Collectors.toSet()))
                .stream()
                .collect(Collectors.toMap(Group::getVkId, Function.identity()));

        Map<Integer, Group> forSaveGroups = history.getGroups()
                .stream()
                .collect(Collectors.toMap(Group::getVkId, Function.identity()));

        Set<Integer> different = new HashSet<>(forSaveGroups.keySet());
        different.removeAll(persistGroups.keySet());
        history.setGroups(new HashSet<>(persistGroups.values()));
        groupRepo.saveAll(different.stream().map(forSaveGroups::get).collect(Collectors.toSet())).forEach(history::addGroup);
        return historyRepo.save(history);
    }

    @Recover
    public History saveRecover(RuntimeException ex, History history){
        throw ex;
    }
}
