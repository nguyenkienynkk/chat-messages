package com.example.chatmessages.utils;

import com.example.chatmessages.validator.ValidateUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtils {

    public static Pageable createPageable(int pageNo, int pageSize, String sortBy, String sortType) {

        ValidateUtils.validatePageable(pageNo, pageSize);

        if (sortType.equals("asc"))
            return PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy).ascending());

        return PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy).descending());
    }

}
