package ru.avm.vktest.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.avm.vktest.data.VkGroupDataBuilder;
import ru.avm.vktest.data.VkHistoryDataBuilder;
import ru.avm.vktest.dto.VkGroup;
import ru.avm.vktest.dto.VkHistory;
import ru.avm.vktest.service.history.HistoryService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.avm.vktest.exception.MessageException.*;

@SpringBootTest
public class HistoryServiceTest {
    @Autowired
    private HistoryService historyService;

    @Test
    public void ifNullParam_thenThrowsIllegalArgumentException(){
        assertAll(
                () -> {
                    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> historyService.save(null));
                    assertThat(exception.getMessage()).isEqualTo(EMPTY_OBJECT);
                }
        );
    }
    @Test
    public void ifNullParamRequest_thenThrowsIllegalArgumentException(){
        VkHistory vkHistory = VkHistoryDataBuilder.vkHistory().withRequestParam(null).build();
        assertAll(
                () -> {
                    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> historyService.save(vkHistory));
                    assertThat(exception.getMessage()).isEqualTo(NOT_SETTED_PARAM);
                }
        );
    }
    @Test
    public void ifEmptyParamRequest_thenThrowsIllegalArgumentException(){
        VkHistory vkHistory = VkHistoryDataBuilder.vkHistory().withRequestParam("").build();
        assertAll(
                () -> {
                    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> historyService.save(vkHistory));
                    assertThat(exception.getMessage()).isEqualTo(NOT_SETTED_PARAM);
                }
        );
    }
    @Test
    @Transactional
    @Sql(scripts ={"/reset_vk_history_id.sql"} )
    public void ifHistoryWithoutGroup_thenDoesNotThrows(){
        assertDoesNotThrow(() -> historyService.save(VkHistoryDataBuilder.vkHistory().build()));
    }
    @Test
    @Transactional
    @Sql(scripts ={"/reset_vk_history_id.sql"} )
    public void ifHistoryWithGroup_thenDoesNotThrows(){
        Set<VkGroup> groups = IntStream.range(10, 14)
                .mapToObj(i -> VkGroupDataBuilder.vkGroup().withTitle("ccc" + i).withVkId(400 + i).build())
                .collect(toSet());
        VkHistory data = VkHistoryDataBuilder.vkHistory().withGroups(groups).build();
        assertDoesNotThrow(() -> historyService.save(data));
    }
    @Test
    @Transactional
    @Sql(scripts ={"/reset_vk_history_id.sql"} )
    public void ifHistoryHasSavedGroup_thenDoesNotThrows(){
        Set<VkGroup> groups = new HashSet<>(Collections.singleton(VkGroupDataBuilder.vkGroup().withVkId(121).withTitle("хз1").build()));
        VkHistory data = VkHistoryDataBuilder.vkHistory().withGroups(groups).build();
        assertDoesNotThrow(() -> historyService.save(data));
    }
}
