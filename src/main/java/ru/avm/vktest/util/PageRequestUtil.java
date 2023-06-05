package ru.avm.vktest.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.avm.vktest.search.VkSearch;

import java.util.Objects;

import static ru.avm.vktest.util.Checker.isEmptyString;

public class PageRequestUtil {
    public static final String ID = "id";
    public static final String DESC = "desc";
    public static final String ASC = "asc";
    public static final int DEFAULT_PAGE_SIZE = 5;
    public static final int DEFAULT_PAGE_NUMBER = 0;

    public static PageRequest createPageRequest(VkSearch search){
        if(Objects.isNull(search.getPageNumber()) || search.getPageNumber() < 0)
            search.setPageNumber(DEFAULT_PAGE_NUMBER);
        if(Objects.isNull(search.getPageSize()) || search.getPageSize() < 1)
            search.setPageSize(DEFAULT_PAGE_SIZE);
        return PageRequest.of(search.getPageNumber(), search.getPageSize(), createSort(search.getSortColumn(), search.getSortDirection()));
    }

    public static Sort createSort(String column, String direction) {
        if (isEmptyString(column))
            column = ID;
        column = createSortColumn(column);
        if (isEmptyString(direction))
            direction = ASC;
        if (direction.equalsIgnoreCase(DESC))
            return Sort.by(Sort.Direction.DESC, column);
        return Sort.by(Sort.Direction.ASC, column);
    }
//Todo если станет много полей, то переделать через Map
    private static String createSortColumn(String column){
        switch (column){
            case "vk_id":
                return column;
            case "title":
                return column;
            default:
                return ID;
        }
//        return ID;
    }
}
