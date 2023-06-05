package ru.avm.vktest.service.history;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.avm.vktest.converter.ToEntityMapper;
import ru.avm.vktest.dto.VkHistory;
import ru.avm.vktest.model.History;

@Configuration
@RequiredArgsConstructor
public class HistoryServiceConfig {
    private final HistoryServiceJpa historyServiceJpa;
    private final ToEntityMapper<VkHistory, History> toHistoryMapper;
    @Bean
    public HistoryService historyConverter(){
        return new ConverterHistoryService(historyServiceJpa, toHistoryMapper);
    }
    @Bean
    public HistoryService historyService(){
        return new ValidatorHistoryService(historyConverter());
    }

}
