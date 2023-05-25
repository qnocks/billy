package com.qnocks.billy.subscription.type;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum Periodicity {

    DAY(1),
    WEEK(7),
    MONTH(30),
    YEAR(365);

    private final int value;
}
