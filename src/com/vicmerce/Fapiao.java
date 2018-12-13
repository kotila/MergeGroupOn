/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vicmerce;

import com.vicmerce.util.BarCode;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * 发票
 *
 * @author tianyong
 */
public class Fapiao extends PdfFile {

    private BarCode barCode = new BarCode();
    public Fapiao(){}
    
    public Fapiao(String fileName){
        super(fileName);
    }
    
    @Override
    protected String getItemId(String text) {
        String itemId = getValueByKey(text, "Quantity", " ");
        return itemId;
    }
    
    @Override
    protected List<String> getItemIds(String text) {
        List<String> itemIds= getValuesByFlag(text, "Quantity","If you have any questions about yourgoods," ," ");
        return itemIds;
    } 
    
    @Override
    protected String getOrderId(String text) {
        String orderId = getValueByKey(text, "Shipped Via", "standard");
        return orderId;
    }

    @Override
    protected void editImage(String orderId,BufferedImage image) {
        try {
            if(null==orderId||orderId.isEmpty()){
                return;
            }
            byte[] bar = barCode.genGroupOnBarcode(orderId);
            InputStream buffIn = new ByteArrayInputStream(bar, 0, bar.length);
            BufferedImage bufferedImage = ImageIO.read(buffIn);
            Image barimg = (Image) bufferedImage;

            int width = image.getWidth();
            int height = image.getHeight();
            
            Graphics g = image.getGraphics();
            g.drawImage(barimg, 800, 3050, null);
            g.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
