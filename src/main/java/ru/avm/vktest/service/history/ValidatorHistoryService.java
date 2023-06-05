package ru.avm.vktest.service.history;

import lombok.RequiredArgsConstructor;
import ru.avm.vktest.dto.VkHistory;

import static ru.avm.vktest.exception.MessageException.NOT_SETTED_PARAM;
import static ru.avm.vktest.util.Checker.checkEmptyObject;
import static ru.avm.vktest.util.Checker.checkEmptyString;

@RequiredArgsConstructor
public class ValidatorHistoryService implements HistoryService {
    private final HistoryService historyService;
    @Override
    public void save(VkHistory history) {
        checkEmptyObject(history);
        checkEmptyString(history.getRequestParam(), NOT_SETTED_PARAM);
        historyService.save(history);
    }
}
