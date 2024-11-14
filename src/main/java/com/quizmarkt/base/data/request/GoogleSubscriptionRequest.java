package com.quizmarkt.base.data.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.quizmarkt.base.data.converter.DataAndroidDeserializer;
import com.quizmarkt.base.data.converter.TransactionReceiptDeserializer;
import lombok.Data;

import java.util.List;

@Data
public class GoogleSubscriptionRequest {
    private boolean autoRenewingAndroid;
    @JsonDeserialize(using = DataAndroidDeserializer.class)
    private DataAndroid dataAndroid;
    private String developerPayloadAndroid;
    private boolean isAcknowledgedAndroid;
    private String obfuscatedAccountIdAndroid;
    private String obfuscatedProfileIdAndroid;
    private String packageNameAndroid;
    private String productId;
    private List<String> productIds;
    private int purchaseStateAndroid;
    private String purchaseToken;
    private String signatureAndroid;
    private long transactionDate;
    private String transactionId;
    @JsonDeserialize(using = TransactionReceiptDeserializer.class)
    private TransactionReceipt transactionReceipt;
}

