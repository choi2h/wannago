package com.wannago.qna.service.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.wannago.qna.dto.MyAskResponse;
import com.wannago.qna.entity.Ask;

@Component
public class MyAskMapper {
    public MyAskResponse toAskResponse(Ask ask) {
        return MyAskResponse.from(ask);
    }

    public List<MyAskResponse> toAskResponseList(List<Ask> asks) {
        return asks.stream()
                .map(this::toAskResponse)
                .toList();
    }
}


