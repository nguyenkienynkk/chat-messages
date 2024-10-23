package com.example.chatmessages.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortType {

    ASC("asc"),
    DESC("desc");

    private final String value;
}
