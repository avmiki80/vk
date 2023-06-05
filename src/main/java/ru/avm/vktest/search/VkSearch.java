package ru.avm.vktest.search;

import lombok.Data;

@Data
public class VkSearch {
    private String title;
    private Integer pageNumber = 0;
    private Integer pageSize = 5;
    private String sortColumn = "id";
    private String sortDirection = "asc";
}
