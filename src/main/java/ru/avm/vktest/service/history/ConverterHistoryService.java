package ru.avm.vktest.service.history;

import lombok.RequiredArgsConstructor;
import ru.avm.vktest.converter.ToEntityMapper;
import ru.avm.vktest.dto.VkHistory;
import ru.avm.vktest.model.History;


@RequiredArgsConstructor
public class ConverterHistoryService implements HistoryService {
    private final HistoryServiceJpa historyService;
    private final ToEntityMapper<VkHistory, History> toHistoryMapper;
    @Override
    public void save(VkHistory history) {
        History entity = toHistoryMapper.toEntity(history);
        historyService.save(entity);
    }
}
