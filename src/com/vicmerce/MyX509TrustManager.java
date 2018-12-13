/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vicmerce;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author liyimin
 */
public class MyX509TrustManager implements  X509TrustManager{

 public X509Certificate[] getAcceptedIssuers() {
 // TODO Auto-generated method stub
 return new X509Certificate[0];
 }

 public boolean isClientTrusted(X509Certificate[] arg0) {
 // TODO Auto-generated method stub
 return true;
 }

 public boolean isServerTrusted(X509Certificate[] arg0) {
 // TODO Auto-generated method stub
 return true;
 }

    @Override
    public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 }
