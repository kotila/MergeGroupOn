/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vicmerce;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.jstl.sql.Result;

/**
 *
 * @author liyimin
 */
public class mytest {

    public static void main(String args[]) {
        GroupOnDao groupOnDao = new GroupOnDao();
        OrderVo orderVo = new OrderVo();
         orderVo.setOrderId("0002");
         orderVo.setItemId("item00000002");
         orderVo.setSku("sku0002");
        orderVo.setExport(true);
         groupOnDao.test(orderVo);
        groupOnDao.test_save(orderVo);
        Result result = groupOnDao.test_query(orderVo);
        for (int i = 0; i < result.getRowCount(); i++) {
            Map rowMap = result.getRows()[i];
            System.out.print(rowMap.get("itemId"));
        }
//        String outstr="supplier_id=26090&token=nzvOiEDwWJ1hIeXs1CxdrVm4rC3gS7e";
//        String https="https://scm.commerceinterface.com/api/v4/mark_exported";
//        ClientApi.httpsRequest(https,"POST",outstr);
/*
    String allStr="Groupon Goods Returns\n" +
"Thanks for shopping with Groupon Goods!\n" +
"-\n" +
"https://www.groupon.co.uk/goods\n" +
"https://www.groupon.co.uk/customer_support\n" +
"Order Date Bill To Ship To Sue Louch\n" +
"4 ALDERLEY ROAD\n" +
"BROMSGROVE\n" +
"GB\n" +
"B61 7LX\n" +
"2017-07-14 Sue Louch\n" +
"Order ID Shipped Via\n" +
"444686517 standard\n" +
"Item Number Item Description Quantity\n" +
"509520435 Full HD dash car accident camera - black 1\n" +
"509520436 Full HD dash car accident camera - black 1\n" +
"If you have any questions about yourgoods, please contact us at https://www.groupon.co.uk/customer_support.\n" +
"RETURN INSTRUCTIONS\n" +
"Please follow the 3 steps below. Refunds can be significantly delayed if any other return process is used.\n" +
"1 2 3Go to www.groupon.co.uk/mygroupons.\n" +
"Find the order you wish to\n" +
"return, click the blue button that\n" +
"states View Details and then\n" +
"select Click to return. Please\n" +
"follow the on-screen instructions\n" +
"to print your prepaid label. For\n" +
"large / bulky items only, the\n" +
"on-screen instructions may\n" +
"prompt you to contact us, so we\n" +
"can organise a home collection.\n" +
"Repackage all items received,\n" +
"including any parts or instructions.\n" +
"Please ensure that the package is\n" +
"properly packed.\n" +
"Attach the prepaid Royal Mail\n" +
"label to the outside of your\n" +
"package. Drop off the package at\n" +
"any UK Post Office, and ask for\n" +
"‘Proof of Postage’.\n" +
"Please do not write the returns\n" +
"address or send the item back\n" +
"without the label, as it contains\n" +
"essential information to process\n" +
"your refund.\n" +
"RETURN POLICY\n" +
"You have 14 days starting the day you receive your Goods to cancel your contract and return your item. In the event you have ordered multiple Goods\n" +
"in one order, or the delivery of your Goods consists of multiple pieces, the cancellation period does not begin until you receive the last item or piece.\n" +
"To view our full return policy, please visit www.groupon.co.uk/terms_and_conditions/rp";
    List<String> itemNos=new ArrayList();
        String itemContent=allStr.substring(allStr.indexOf("Quantity")+"Quantity".length()+1, allStr.lastIndexOf("If you have any questions about yourgoods,"));
        String[] items = itemContent.split("\n");
        for(String item:items){
            String[] words=item.split(" ");
            if(words.length>0)
                itemNos.add(words[0]);
        }
        for(String word:itemNos)
           System.out.print(word+"\n");
    */
    }

}
    

