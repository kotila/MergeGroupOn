/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vicmerce;

import com.vicmerce.util.ImageUtil;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 * pdf文件
 *
 * @author tianyong
 */
public abstract class PdfFile {

    private static final Pattern pattern = Pattern.compile("[0-9]*");
    private static final String SEPARATOR = System.getProperty("line.separator");

    private PDDocument doc = null;
    private PDDocumentCatalog docCatalog;
    private PDFTextStripper stripper;
    private List<PDPage> pages;
            
    public PdfFile(){
        
    }
    
    public PdfFile(String fileName){
        try {
            //加载文件
            File file = new File(fileName);
            InputStream in = null;
            if (file.exists()) {
                in = new FileInputStream(file);
            }

            if (!fileName.endsWith("pdf")) {
                throw new Exception("文件格式应为pdf");
            }
            
            // 读取导入的PDF文件
            doc = PDDocument.load(in);// 读取图像
            docCatalog = doc.getDocumentCatalog();
            stripper = new PDFTextStripper();
            stripper.setSortByPosition(false);
            pages = docCatalog.getAllPages();// 获取全部图像
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }
    
    public void close(){
        if (doc != null) {
            try {
                doc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    protected abstract String getOrderId(String text);

    protected abstract String getItemId(String text);

    protected abstract List<String> getItemIds(String text);

    protected void editImage(String orderId, BufferedImage image) {

    }

    public Map<String, OrderVo> importPdf(String fileName) throws Exception {
        //System.out.println("import pdf start");
        Map<String, OrderVo> orderVos = null;

        //加载文件
        File file = new File(fileName);
        InputStream in = null;
        try {
            if (file.exists()) {
                in = new FileInputStream(file);
            }

            if (!fileName.endsWith("pdf")) {
                throw new Exception("文件格式应为pdf");
            }

            String[] textlines = null;
            String text;

            try {

                orderVos = new LinkedHashMap<String, OrderVo>();

                // 读取导入的PDF文件
                doc = PDDocument.load(in);// 读取图像
                docCatalog = doc.getDocumentCatalog();
                stripper = new PDFTextStripper();
                stripper.setSortByPosition(false);
                pages = docCatalog.getAllPages();// 获取全部图像
                for (int i = 0; i < pages.size(); i++) {
                    // 按页分解
                    PDPage page = pages.get(i);

                    stripper.setStartPage(i + 1);
                    stripper.setEndPage(i + 1);
                    text = stripper.getText(doc);
                    textlines = text.split(SEPARATOR);

                    // 如果数组不存在，则抛出pdf格式不正常，缺少Sender's reference:\n关键字
                    if (textlines.length <= 0) {
                        throw new Exception("pdf格式不正常");
                    }

                    //获取orderId
                    String orderId = getOrderId(text);
                    //获取orderId
                    //String itemId = getItemId(text);
                    List<String> itemIds = getItemIds(text);
                    //获取图片
                    //BufferedImage image = page.convertToImage(BufferedImage.TYPE_BYTE_GRAY, 300);

                    //处理image
                    //editImage(orderId, image);

//                    byte[] imageByte = ImageUtil.getBytesFromPage(image);
                    byte[] imageByte = null;
                    for (String itemId : itemIds) {
                        OrderVo ordersVo = new OrderVo();// 订单
                        ordersVo.setOrderId(orderId);
                        ordersVo.setItemId(itemId);
                        ordersVo.setImageByte(imageByte);
                        ordersVo.setPdfIndex(i);
                        orderVos.put(itemId, ordersVo);
                    }

                }
            } catch (Exception e) {
                throw e;
            } finally {
                if (doc != null) {
                    try {
                        doc.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return orderVos;
        } catch (Exception e) {
            throw e;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Map<String, OrderVo> importPdf() throws Exception {
        //System.out.println("import pdf start");
        Map<String, OrderVo> orderVos = null;
        
        try {
            orderVos = new LinkedHashMap<String, OrderVo>();

            for (int i = 0; i < pages.size(); i++) {
                // 按页分解
                PDPage page = pages.get(i);

                stripper.setStartPage(i + 1);
                stripper.setEndPage(i + 1);
                String text = stripper.getText(doc);
                String[] textlines = text.split(SEPARATOR);

                // 如果数组不存在，则抛出pdf格式不正常，缺少Sender's reference:\n关键字
                if (textlines.length <= 0) {
                    throw new Exception("pdf格式不正常");
                }

                //获取orderId
                String orderId = getOrderId(text);
                //获取orderId
                //String itemId = getItemId(text);
                List<String> itemIds = getItemIds(text);
                //获取图片
                //BufferedImage image = page.convertToImage(BufferedImage.TYPE_BYTE_GRAY, 300);

                //处理image
                //editImage(orderId, image);

//                    byte[] imageByte = ImageUtil.getBytesFromPage(image);
                byte[] imageByte = null;
                for (String itemId : itemIds) {
                    OrderVo ordersVo = new OrderVo();// 订单
                    ordersVo.setOrderId(orderId);
                    ordersVo.setItemId(itemId);
                    ordersVo.setImageByte(imageByte);
                    ordersVo.setPdfIndex(i);
                    orderVos.put(itemId, ordersVo);
                }

            }
        } catch (Exception e) {
            throw e;
        }

        return orderVos;
    }
    
    public byte[] getPageByIndex(String orderId,int index,String type) throws Exception {
        // System.out.println("getPageByIndex orderId:"+orderId+" index:"+index+" type:"+type+" start");
        
        try {
            PDPage page = pages.get(index);
  
            //获取图片
            //long fapiaostartTime = System.currentTimeMillis();//记录开始时间
            BufferedImage image = page.convertToImage(BufferedImage.TYPE_BYTE_GRAY, 300);
            //long fapiaoendTime = System.currentTimeMillis();//记录结束时间
            //float fapiaoexcTime = (float) (fapiaoendTime - fapiaostartTime) / 1000;
            //System.out.println("保存"+type+"执行时间：" + fapiaoexcTime + "s");

            //处理image
            editImage(orderId, image);

            byte[] imageByte = ImageUtil.getBytesFromPage(image);
            return imageByte;
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    protected String getValueByKey(String str, String key, String separator) {
        String[] orderTimes = str.split(key);
        if (null == separator) {
            separator = SEPARATOR;
        }
        String orderTime = orderTimes[1];
        String result = orderTime.substring(0, orderTime.indexOf(separator));
//        String[] split = orderTimes[1].split(separator);
        return result.trim();
    }

    protected List<String> getValuesByFlag(String str, String beginStr, String endStr, String separator) {
        List<String> ret = new ArrayList();
        String itemContent = str.substring(str.indexOf(beginStr) + beginStr.length() + 1, str.lastIndexOf(endStr));
        String[] items = itemContent.split("\n");
        for (String item : items) {
            String[] words = item.split(separator);
            if (words.length > 0&&words[0].length()>8) {
                Matcher isNum = pattern.matcher(words[0]);
                if(isNum.matches() ){
                    ret.add(words[0]);
                }
            }
        }
        if(ret.size()>1){
            System.out.println("重复id:"+ret);
        }
        return ret;
    }
    
}
