/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vicmerce;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import static com.vicmerce.Order.PDF_HEIGHT;
import static com.vicmerce.Order.PDF_WIDTH;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 *
 * @author tianyong
 */
public class AddDocRunner implements Callable<FutureVo> {

    private int index;
    private final Rectangle pageSize = new Rectangle(PDF_HEIGHT, PDF_WIDTH);
    private List<OrderVo> orderVos;
    private LinkedHashMap<String, OrderVo> fapiaoVos;
    private LinkedHashMap<String, OrderVo> labelVos;
    private Fapiao fapiao;
    private Label label;
    
    public AddDocRunner(){}
    
    public AddDocRunner(int index,List<OrderVo> orderVos,Map<String, OrderVo> fapiaoVos,Map<String, OrderVo> labelVos,
                        Fapiao fapiao,Label label){
        this.index = index;
        this.orderVos = new ArrayList(orderVos);
        this.fapiaoVos = new LinkedHashMap(fapiaoVos);
        this.labelVos = new LinkedHashMap(labelVos);
        this.fapiao = fapiao;
        this.label = label;
    }
    
    @Override
    public FutureVo call() throws Exception{
        System.out.println("运行了线程:"+index);
        List<Image> result = new ArrayList<>();
        for (OrderVo orderVo : orderVos) {
            addDoc(orderVo, result);
        }
        FutureVo futureVo = new FutureVo(index,result);
        return futureVo;
    }
    
    private void addDoc(OrderVo orderVo,List<Image> result) throws BadElementException, Exception, DocumentException, IOException {
        String itemId = orderVo.getItemId();
        //按照itemId获取发货贴
        OrderVo labelVo = labelVos.get(itemId);
        if (null==labelVo) {
            return;
        }
        //按照orderId获取发票
        OrderVo fapiaoVo = fapiaoVos.get(itemId);
        if (null==fapiaoVo) {
            return;
        }
        //                byte[] labelbs = labelVo.getImageByte();
        //long labelstartTime = System.currentTimeMillis();//记录开始时间
        byte[] labelbs = label.getPageByIndex(labelVo.getOrderId(), labelVo.getPdfIndex(),"label");
        //long labelendTime = System.currentTimeMillis();//记录结束时间
        //float labelexcTime = (float) (labelendTime - labelstartTime) / 1000;
        //System.out.println("保存发货贴执行时间：" + labelexcTime + "s");
        Image labelimg = Image.getInstance(labelbs);
        labelimg.scaleAbsolute(pageSize);
        result.add(labelimg);

        //                byte[] fapiaobs = fapiaoVo.getImageByte();
        //long fapiaostartTime = System.currentTimeMillis();//记录开始时间
        byte[] fapiaobs = fapiao.getPageByIndex(fapiaoVo.getOrderId(), fapiaoVo.getPdfIndex(),"fapiao");
//        long fapiaoendTime = System.currentTimeMillis();//记录结束时间
//        float fapiaoexcTime = (float) (fapiaoendTime - fapiaostartTime) / 1000;
//        System.out.println("保存发票执行时间：" + fapiaoexcTime + "s");
        Image fapiaoimg = Image.getInstance(fapiaobs);
        fapiaoimg.scaleAbsolute(pageSize);
        result.add(fapiaoimg);
    }
}
