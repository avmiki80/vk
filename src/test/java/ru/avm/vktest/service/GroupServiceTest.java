package ru.avm.vktest.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import ru.avm.vktest.dto.VkGroup;
import ru.avm.vktest.search.VkSearch;
import ru.avm.vktest.service.group.GroupService;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.avm.vktest.exception.MessageException.*;
import static ru.avm.vktest.util.PageRequestUtil.DESC;

@SpringBootTest
public class GroupServiceTest {
    @Autowired
    private GroupService groupService;
    @Test
    public void ifSearchEmpty_thenThrowsIllegaArgumentException(){
        assertAll(
                () -> {
                    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> groupService.search(null));
                    assertThat(exception.getMessage()).isEqualTo(EMPTY_OBJECT);
                }
        );
    }
    @Test
    public void ifSearchHasXZ_thenReturnFiveGroups(){
        VkSearch search = new VkSearch();
        search.setTitle("хз");
        Page<VkGroup> result = groupService.search(search);
        assertAll(
                () -> assertThat(result).isNotEmpty(),
                () -> assertThat(result.getContent()).hasSize(5),
                () -> assertThat(result.getContent().stream().map(VkGroup::getTitle).collect(Collectors.toList()))
                        .containsOnly("хз1", "хз2", "хз3", "хз5", "хз6")
        );
    }
    @Test
    public void ifSearchHasXZAndPageSizeThree_thenReturnFiveGroups(){
        VkSearch search = new VkSearch();
        search.setTitle("хз");
        search.setPageSize(3);
        Page<VkGroup> result = groupService.search(search);
        assertAll(
                () -> assertThat(result).isNotEmpty(),
                () -> assertThat(result.getContent()).hasSize(3),
                () -> assertThat(result.getContent().stream().map(VkGroup::getTitle).collect(Collectors.toList()))
                        .containsOnly("хз1", "хз2", "хз3")
        );
    }
    @Test
    public void ifSearchHasXZAndPageSizeThreeAndPageNumberOne_thenReturnFiveGroups(){
        VkSearch search = new VkSearch();
        search.setTitle("хз");
        search.setPageSize(3);
        search.setPageNumber(1);
        Page<VkGroup> result = groupService.search(search);
        assertAll(
                () -> assertThat(result).isNotEmpty(),
                () -> assertThat(result.getContent()).hasSize(2),
                () -> assertThat(result.getContent().stream().map(VkGroup::getTitle).collect(Collectors.toList()))
                        .containsOnly("хз5", "хз6")
        );
    }
    @Test
    public void ifSearchHasXZAndPageSizeThreeAndSettedSortParam_thenReturnFiveGroups(){
        VkSearch search = new VkSearch();
        search.setTitle("хз");
        search.setSortDirection(DESC);
        search.setSortColumn("title");
        search.setPageSize(3);
        Page<VkGroup> result = groupService.search(search);
        assertAll(
                () -> assertThat(result).isNotEmpty(),
                () -> assertThat(result.getContent()).hasSize(3),
                () -> assertThat(result.getContent().stream().map(VkGroup::getTitle).collect(Collectors.toList()))
                        .containsOnly("хз5", "хз6", "хз3")
        );
    }
    @Test
    public void ifSearchHasAllNullParam_thenReturnFiveGroups(){
        VkSearch search = new VkSearch();
        search.setTitle(null);
        search.setSortColumn(null);
        search.setSortDirection(null);
        search.setPageNumber(null);
        search.setPageSize(null);
        Page<VkGroup> result = groupService.search(search);
        assertAll(
                () -> assertThat(result).isNotEmpty(),
                () -> assertThat(result.getContent()).hasSize(5),
                () -> assertThat(result.getContent().stream().map(VkGroup::getTitle).collect(Collectors.toList()))
                        .containsOnly("хз1", "хз2", "хз3", "хз5", "з4")
        );
    }
}
