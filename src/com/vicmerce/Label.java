/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vicmerce;

import java.util.ArrayList;
import java.util.List;

/**
 * 发货贴
 *
 * @author tianyong
 */
public class Label extends PdfFile {

    public Label(){}
    
    public Label(String fileName){
        super(fileName);
    }
    
    @Override
    protected String getItemId(String text) {
        String itemId = getValueByKey(text, "ITEM ID ", null);
        return itemId;
    }
    
    @Override
    protected String getOrderId(String text) {
        String orderId = getValueByKey(text, "ORDER ID ", null);
        return orderId;
    }

    @Override
    protected List<String> getItemIds(String text) {
        List<String> itemIds=new ArrayList();
        itemIds.add(getItemId(text));
        return itemIds;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
