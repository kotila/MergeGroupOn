/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vicmerce;


import java.io.File;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
/**
 *
 * @author tianyong
 */
public class GrouponJFrame extends javax.swing.JFrame {

    /**
     * Creates new form GrouponJFrame
     */
    public GrouponJFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        ExportPDF = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        OrderFilePath = new javax.swing.JTextField();
        FaPiaoFilePath = new javax.swing.JTextField();
        LabelFilePath = new javax.swing.JTextField();
        OrderFile = new javax.swing.JButton();
        FaPiaoFile = new javax.swing.JButton();
        LabelFile = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ExportPDF.setText("导出PDF");
        ExportPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportPDFActionPerformed(evt);
            }
        });

        jLabel1.setText("订单:");

        jLabel2.setText("发票:");

        jLabel3.setText("发货贴:");

        OrderFilePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OrderFilePathActionPerformed(evt);
            }
        });

        FaPiaoFilePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FaPiaoFilePathActionPerformed(evt);
            }
        });

        LabelFilePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LabelFilePathActionPerformed(evt);
            }
        });

        OrderFile.setText("选择文件");
        OrderFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OrderFileActionPerformed(evt);
            }
        });

        FaPiaoFile.setText("选择文件");
        FaPiaoFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FaPiaoFileActionPerformed(evt);
            }
        });

        LabelFile.setText("选择文件");
        LabelFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LabelFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(FaPiaoFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(FaPiaoFile))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(28, 28, 28)
                            .addComponent(OrderFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(OrderFile)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ExportPDF)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(LabelFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(LabelFile)))))
                .addContainerGap(165, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(OrderFile)
                    .addComponent(OrderFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(FaPiaoFile)
                    .addComponent(FaPiaoFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(LabelFile)
                    .addComponent(LabelFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(ExportPDF)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        this.setBounds(400,200,300,500);
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exportPDF(){
        String msg = "";
        Fapiao fapiao = null;
        Label label = null;
        long allstartTime = System.currentTimeMillis();
        try {
            String filePath;
            
            //打开对话框  
            int result = fileChooser.showSaveDialog(this);//null  
            //文件确定  
            if(result==JFileChooser.APPROVE_OPTION) {  
                filePath = fileChooser.getSelectedFile().getAbsolutePath();
            }else{
                return;
            }
            
            // TODO add your handling code here:
            //订单路径
            String orderFilePath = OrderFilePath.getText();

            //发票路径
            String fapiaoFilePath = FaPiaoFilePath.getText();

            //货贴路径
            String labelFilePath = LabelFilePath.getText();

            List<OrderVo> orderVos = null;
            Map<String, OrderVo> fapiaoVos = null, labelVos = null;

            //导入订单
            Order order = new Order();
            long startTime = System.currentTimeMillis();//记录开始时间
            try {
                orderVos = order.importExcel(orderFilePath);
                if (null == orderVos) {
                    throw new Exception("订单为空");
                }
                msg += "订单共 " + orderVos.size() + " 条 ";
            } catch (Exception e) {
                throw new Exception("导入订单失败" + e.getMessage(), e);
            }
            long endTime = System.currentTimeMillis();//记录结束时间
            float excTime = (float) (endTime - startTime) / 1000;
            System.out.println("导入订单执行时间：" + excTime + "s");

            //提示执行时间,是否继续
//            int n = JOptionPane.showConfirmDialog(null, msg + "预计耗时 " + orderVos.size() * 1.3 + " 秒,是否执行?", "提示", JOptionPane.YES_NO_OPTION);//i=0/1  
//            if (1 == n) {//不执行
//                return;
//            }

            //导入发票
            fapiao = new Fapiao(fapiaoFilePath);
            long fapiaostartTime = System.currentTimeMillis();//记录开始时间
            try {
                fapiaoVos = fapiao.importPdf();
                msg += "发票共 " + fapiaoVos.size() + " 张 ";
            } catch (Exception e) {
                throw new Exception("导入发票失败" + e.getMessage(), e);
            }
            long fapiaoendTime = System.currentTimeMillis();//记录结束时间
            float fapiaoexcTime = (float) (fapiaoendTime - fapiaostartTime) / 1000;
            System.out.println("导入发票执行时间：" + fapiaoexcTime + "s");

            //导入发货贴
            label = new Label(labelFilePath);
            long labelstartTime = System.currentTimeMillis();//记录开始时间
            try {
                labelVos = label.importPdf();
                msg += "发货贴共 " + labelVos.size() + " 张 ";
            } catch (Exception e) {
                throw new Exception("导入发货贴失败" + e.getMessage(), e);
            }
            long labelendTime = System.currentTimeMillis();//记录结束时间
            float labelexcTime = (float) (labelendTime - labelstartTime) / 1000;
            System.out.println("导入发货贴执行时间：" + labelexcTime + "s");

            //保存文件
            long pdfstartTime = System.currentTimeMillis();//记录开始时间
            try {
                // todo 传入发票和发货贴的地址,在保存文件时处理图片
                order.exportPdf(filePath,fapiao,label, orderVos, fapiaoVos, labelVos);
            } catch (Exception e) {
                throw new Exception("保存文件失败" + e.getMessage(), e);
            }
            long pdfendTime = System.currentTimeMillis();//记录结束时间
            float pdfexcTime = (float) (pdfendTime - pdfstartTime) / 1000;
            System.out.println("保存文件执行时间：" + pdfexcTime + "s");

            long allendTime = System.currentTimeMillis();
            float allexcTime = (float) (allendTime - allstartTime) / 1000;
            JOptionPane.showMessageDialog(null, msg+" 文件保存到 "+filePath,"导出成功,共耗时:"+allexcTime,JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, msg + ex.getMessage(), "导出失败", JOptionPane.ERROR_MESSAGE);
        }finally {
            if (fapiao != null) {
                fapiao.close();
            }
            if (label != null) {
                label.close();   
            }
        }
    }

    private void ExportPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportPDFActionPerformed

        exportPDF();  
    }//GEN-LAST:event_ExportPDFActionPerformed

    private void OrderFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OrderFileActionPerformed
        // TODO add your handling code here:
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            // What to do with the file, e.g. display it in a TextArea
            String orderFilePath = file.getAbsolutePath();
            OrderFilePath.setText(orderFilePath);
        } else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_OrderFileActionPerformed

    private void OrderFilePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OrderFilePathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OrderFilePathActionPerformed

    private void FaPiaoFilePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FaPiaoFilePathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FaPiaoFilePathActionPerformed

    private void FaPiaoFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FaPiaoFileActionPerformed
        // TODO add your handling code here:
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            // What to do with the file, e.g. display it in a TextArea
            String orderFilePath = file.getAbsolutePath();
            FaPiaoFilePath.setText(orderFilePath);
        } else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_FaPiaoFileActionPerformed

    private void LabelFilePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LabelFilePathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LabelFilePathActionPerformed

    private void LabelFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LabelFileActionPerformed
        // TODO add your handling code here:
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            // What to do with the file, e.g. display it in a TextArea
            String orderFilePath = file.getAbsolutePath();
            LabelFilePath.setText(orderFilePath);
        } else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_LabelFileActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GrouponJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GrouponJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GrouponJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GrouponJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GrouponJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ExportPDF;
    private javax.swing.JButton FaPiaoFile;
    private javax.swing.JTextField FaPiaoFilePath;
    private javax.swing.JButton LabelFile;
    private javax.swing.JTextField LabelFilePath;
    private javax.swing.JButton OrderFile;
    private javax.swing.JTextField OrderFilePath;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
