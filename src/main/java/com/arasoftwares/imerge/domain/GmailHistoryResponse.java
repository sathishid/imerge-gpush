package com.arasoftwares.imerge.domain;

import java.math.BigInteger;

public class GmailHistoryResponse {
    private String emailAddress;
    private BigInteger historyId;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public BigInteger getHistoryId() {
        return historyId;
    }

    public void setHistoryId(BigInteger historyId) {
        this.historyId = historyId;
    }


}


   