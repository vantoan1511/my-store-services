package com.mystore.service.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSortingCriteria {

    private SortingField field;

    private boolean ascending;
}
