package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class Transfers {

    private int transferId;
    private Integer transfer_type;
    private Integer transfer_status;
    private Integer account_from;
    private Integer account_to;
    private BigDecimal amount;

    public Integer getTransfer_status() {
        return transfer_status;
    }

    public void setTransfer_status(Integer transfer_status) {
        this.transfer_status = transfer_status;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public Integer getTransfer_type() {
        return transfer_type;
    }

    public void setTransfer_type(Integer transfer_type) {
        this.transfer_type = transfer_type;
    }

    public Integer getAccount_from() {
        return account_from;
    }

    public void setAccount_from(Integer account_from) {
        this.account_from = account_from;
    }

    public Integer getAccount_to() {
        return account_to;
    }

    public void setAccount_to(Integer account_to) {
        this.account_to = account_to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transfers{" +
                "transferId=" + transferId +
                ", transfer_type=" + transfer_type +
                ", transfer_status=" + transfer_status +
                ", account_from=" + account_from +
                ", account_to=" + account_to +
                ", amount=" + amount +
                '}';
    }
}
