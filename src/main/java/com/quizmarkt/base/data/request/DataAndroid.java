package com.quizmarkt.base.data.request;

import lombok.Data;

@Data
public class DataAndroid {
    private String orderId;
    private String packageName;
    private String productId;
    private long purchaseTime;
    private int purchaseState;
    private String purchaseToken;
    private int quantity;
    private boolean autoRenewing;
    private boolean acknowledged;
}