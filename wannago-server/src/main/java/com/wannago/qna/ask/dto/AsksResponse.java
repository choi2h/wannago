package com.wannago.qna.ask.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AsksResponse {
    private final List<AskResponse> asks;

    public AsksResponse() {
        this.asks = new ArrayList<>();
    }

    public void addAskResponse(AskResponse askResponse) {
        asks.add(askResponse);
    }
}
