package com.wannago.qna.service.mapper;

import com.wannago.qna.dto.AskResponse;
import com.wannago.qna.entity.Ask;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AskMapper {
    public AskResponse toAskResponse(Ask ask) {
        return AskResponse.from(ask);
    }

    public List<AskResponse> toAskResponseList(List<Ask> asks) {
        return asks.stream()
                .map(this::toAskResponse)
                .toList();
    }
}
