/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vicmerce;

/**
 *  订单对象
 * @author tianyong
 */
public class OrderVo {
    private String orderId;
    private String itemId;
    private String sku;
    private byte[] imageByte;
    private Boolean export;
    private int pdfIndex;
    public OrderVo() {
    }

    public OrderVo(String orderId, String itemId, String sku) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.sku = sku;
    }
    
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public byte[] getImageByte() {
        return imageByte;
    }

    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }
    public Boolean getExport(){
        return export;
    }
    public void setExport(Boolean export){
        this.export=export;
    }
    public int getPdfIndex() {
        return pdfIndex;
    }

    public void setPdfIndex(int pdfIndex) {
        this.pdfIndex = pdfIndex;
    }
}
