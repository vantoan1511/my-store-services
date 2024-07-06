package com.mystore.service.user.entity;

import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSortingCriteria {

    @QueryParam("sortBy")
    private UserSortingField field = UserSortingField.FIRST_NAME;

    @QueryParam("descending")
    private boolean descending;
}
