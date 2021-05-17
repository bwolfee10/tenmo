package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferData {

    private int transferId;
    private int transfer_type;
    private Integer transfer_status;
    private Integer account_from;
    private Integer account_to;
    private BigDecimal amount;

//    public TransferData(int transferId, Integer transfer_type, Integer transfer_status, Integer account_from, Integer account_to, BigDecimal amount) {
//        this.transferId = transferId;
//        this.transfer_type = transfer_type;
//        this.transfer_status = transfer_status;
//        this.account_from = account_from;
//        this.account_to = account_to;
//        this.amount = amount;
//    }


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

    public Integer getTransfer_status() {
        return transfer_status;
    }

    public void setTransfer_status(Integer transfer_status) {
        this.transfer_status = transfer_status;
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
        return "TransferData{" +
                "transferId=" + transferId +
                ", transfer_type=" + transfer_type +
                ", transfer_status=" + transfer_status +
                ", account_from=" + account_from +
                ", account_to=" + account_to +
                ", amount=" + amount +
                '}';
    }
}
