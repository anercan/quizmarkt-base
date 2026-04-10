package com.quizmarkt.base.data.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.quizmarkt.base.data.converter.DataAndroidDeserializer;
import lombok.Data;

@Data
public class GoogleSubscriptionRequest {
    private String id;
    private String productId;
    private long transactionDate;
    private String purchaseToken;
    private String platform;
    private String store;
    private int quantity;
    private String purchaseState;
    private boolean isAutoRenewing;
    private String transactionId;
    private boolean autoRenewingAndroid;
    @JsonDeserialize(using = DataAndroidDeserializer.class)
    private DataAndroid dataAndroid;
    private String signatureAndroid;
    private boolean isAcknowledgedAndroid;
    private String packageNameAndroid;
    private String obfuscatedAccountIdAndroid;
    private String obfuscatedProfileIdAndroid;
    private String developerPayloadAndroid;
    private boolean isSuspendedAndroid;
}