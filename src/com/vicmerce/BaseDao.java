/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vicmerce;

/**
 *
 * @author liyimin
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.naming.NamingException;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
public class BaseDao {
 private String driver="org.apache.derby.jdbc.EmbeddedDriver";
 private String url="jdbc:derby://localhost:1527/groupon";
 private String uName="root";
 private String uPwd="root123";
private static String protocol="jdbc:derby:";
 private String dbName="E:\\derby\\groupon";

 private Connection conn;
 
 //获得连接
 public Connection getConn(){
  
  try {
   Class.forName(driver);
   //conn=DriverManager.getConnection(url, uName,uPwd);
   conn=DriverManager.getConnection(protocol+dbName+";create=true",uName,uPwd);
  } catch (ClassNotFoundException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  } catch (SQLException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
  return conn;
 }
 
 //执行增删改
 public int excuteSql(String sql,Object [] args){
  
  conn=this.getConn();
  PreparedStatement state=null;
  
  try {
   state =  conn.prepareStatement(sql);
   if(conn!=null&&args.length>0){
    for (int i = 0; i < args.length; i++) {
     state.setObject(i+1, args[i]);
    }
   }
   
   int valid=state.executeUpdate();
   return valid;
  } catch (SQLException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
  finally{
   
   this.closeAll(null, state, conn);
  }
  
  return 0;
 }
 


public Result executeQuery(String sql,Object[] args)
 {
  //改Result是在JSTL jar包中申明的无状态结果集
  Result result=null;
  Connection conn=null;
  PreparedStatement state=null;
  ResultSet rs=null;
  
  try {
   conn=getConn();
   state=conn.prepareStatement(sql);
   if(args!=null && args.length>0)
   {
    for (int i = 0; i < args.length; i++) {
     state.setObject(i+1, args[i]);
    }
   }
   rs=state.executeQuery();
   //通过jstl jar包中的辅助类ResultSupport可以将有状态的ResultSet转化为无状态的Result对象
   result= ResultSupport.toResult(rs);
   
   /*
   int rows= result.getRowCount();
   for(int i=0;i<rows;i++)
   {
     Map rowMap= result.getRows()[i];
     rowMap.get("列名");
   }
   */
  }catch (SQLException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
     // TODO Auto-generated catch block
     
  finally
  {
   closeAll(rs, state, conn);
  }
  return result;
 } 


 public void closeAll(ResultSet result,PreparedStatement state,Connection conn){
  try {
   if(result!=null){
    result.close();
   }
   if(state!=null){
    state.close();
   }
   if(conn!=null&&!conn.isClosed()){
    conn.close();
   }
  } catch (SQLException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
 }
}

