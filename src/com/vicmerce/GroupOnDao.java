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
public class GroupOnDao extends BaseDao {

    public boolean test(OrderVo orderVo) {
        boolean sign = false;
        String sql = "select count(*) as num from groupon_order where orderId=? and itemId=?";
        Result result = executeQuery(sql, new Object[]{orderVo.getOrderId(), orderVo.getItemId()});
        for (int i = 0; i < result.getRowCount(); i++) {
            Map rowMap = result.getRows()[i];
            int count = Integer.parseInt(rowMap.get("num").toString());
            if (count > 0) {
                sign = true;
            }
        }

        return sign;
    }

    public Result test_query(OrderVo orderVo) {
        String sql = "select * from groupon_order where 1=1";
        List object=new ArrayList();
        
        if(orderVo.getOrderId() != null){
            sql=sql+" and orderId=? ";
            object.add(orderVo.getOrderId());
        }
       
         if(orderVo.getItemId() != null){
            sql=sql+" and sku=? ";
            object.add(orderVo.getSku());
        }

          if(null != orderVo.getExport()){
            sql=sql+" and export=? ";
            object.add(orderVo.getExport());
        }
        Result result = executeQuery(sql, (Object[])object.toArray());
        /*
        for (int i = 0; i < result.getRowCount(); i++) {
            Map rowMap = result.getRows()[i];
            int count = Integer.parseInt(rowMap.get("num").toString());
            if (count > 0) {
                sign = true;
            }
        }
        */
        return result;
    }
    public int test_updateExport(OrderVo orderVo) {
        int count = 0;
        String sql = "update groupon_order set export=? where  and itemId=?";
        count = excuteSql(sql, new Object[]{orderVo.getExport(), orderVo.getItemId()});
        return count;
    }

    public int test_save(OrderVo orderVo) {
        int count = 0;
        String sql = "insert into  groupon_order values(?,?,?,?)";
        count = excuteSql(sql, new Object[]{orderVo.getOrderId(), orderVo.getItemId(), orderVo.getSku(), orderVo.getExport()});
        return count;
    }

}
