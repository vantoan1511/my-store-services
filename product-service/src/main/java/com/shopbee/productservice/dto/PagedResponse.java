package com.shopbee.productservice.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {
    private Long totalItems;
    private Long numberOfItems;
    private Integer page;
    private Integer size;
    private boolean hasNext;
    private boolean hasPrevious;
    private List<T> items;

    public PagedResponse(List<T> items) {
        this((long) items.size(), (long) items.size(), 1, Integer.MAX_VALUE, false, false, items);
    }

    public PagedResponse(Long totalItems, Long numberOfItems, Integer page, Long size, List<T> items) {
        this(totalItems, numberOfItems, page, size, page * size < totalItems, page > 1, items);
    }

    public static <T> PagedResponse<T> from(List<T> items, PageRequest pageRequest) {
        List<T> pagedItems = items.stream()
                .skip((pageRequest.getPage() - 1) * pageRequest.getSize())
                .limit(pageRequest.getSize())
                .toList();
        return new PagedResponse<>(
                (long) items.size(), (long) pagedItems.size(), pageRequest.getPage(), pageRequest.getSize(), pagedItems);
    }

}
