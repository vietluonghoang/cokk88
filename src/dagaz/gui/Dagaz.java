/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagaz.gui;

import dagaz.controllers.Configuration;
import dagaz.controllers.Controller;
import dagaz.model.ArenaTableModel;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Hyungin Choi
 */
public class Dagaz extends javax.swing.JFrame {

    /**
     * Creates new form Dagaz
     */
    public Dagaz() {
        initComponents();
        initData();
    }

    private void initData() {
        Arrays.sort(arenaList);
        cbbArenaList.setModel(new DefaultComboBoxModel(arenaList));
        DefaultCaret caret = (DefaultCaret) taDetails.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        configs = new ArrayList<Configuration>();
        Configuration cf = new Configuration("http://www.s128.net", "hcr030108", "Qaz12345", new ArrayList<String>(), this.taDetails);
        configs.add(cf);
        currentConfig = configs.get(0);
        updateForm();
    }

    private void updateForm() {
        ArrayList<String> accountName = new ArrayList<String>();
        for (Configuration configuration : configs) {
            accountName.add(configuration.getUsername());
        }
        cbbAccount.setModel(new DefaultComboBoxModel(accountName.toArray()));
        cbbAccount.setSelectedItem(currentConfig.getUsername());
        ArenaTableModel arenaTableModel = new ArenaTableModel(currentConfig);
        tblArenaDetails.setModel(arenaTableModel);
    }

    private void showCredentialsDialog() {
        JTextField txtUsername = new JTextField();
        JTextField txtPassword = new JTextField();
        JTextField txtTargetURL = new JTextField();
        JLabel lblUsername = new JLabel("Username");
        JLabel lblPassword = new JLabel("Password");
        JLabel lblTargetURL = new JLabel("Link");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(lblTargetURL);
        panel.add(txtTargetURL);
        panel.add(lblUsername);
        panel.add(txtUsername);
        panel.add(lblPassword);
        panel.add(txtPassword);
        int result = JOptionPane.showConfirmDialog(this, panel, "Username/Password", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            configs.add(new Configuration(txtTargetURL.getText(), txtUsername.getText(), txtPassword.getText(), new ArrayList<String>(), this.taDetails));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblArenaTop = new javax.swing.JLabel();
        cbbArenaList = new javax.swing.JComboBox();
        btnAddArena = new javax.swing.JButton();
        spArenaDetails = new javax.swing.JScrollPane();
        tblArenaDetails = new javax.swing.JTable();
        lblDetails = new javax.swing.JLabel();
        lblArenaName = new javax.swing.JLabel();
        cbbSide = new javax.swing.JComboBox();
        txtCoin = new javax.swing.JTextField();
        btnRemoveArena = new javax.swing.JButton();
        btnUpdateArena = new javax.swing.JButton();
        cbbAccount = new javax.swing.JComboBox();
        btnAddAccount = new javax.swing.JButton();
        btnRemmoveAccount = new javax.swing.JButton();
        lblAccount = new javax.swing.JLabel();
        btnRun = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        spTaDetails = new javax.swing.JScrollPane();
        taDetails = new javax.swing.JTextArea();
        btnRenew = new javax.swing.JButton();
        cbbCondition = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(770, 600));
        setPreferredSize(new java.awt.Dimension(770, 600));

        lblArenaTop.setText("Arena:");

        cbbArenaList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbArenaListActionPerformed(evt);
            }
        });

        btnAddArena.setText("Add");
        btnAddArena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddArenaActionPerformed(evt);
            }
        });

        tblArenaDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblArenaDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblArenaDetailsMouseClicked(evt);
            }
        });
        spArenaDetails.setViewportView(tblArenaDetails);

        lblDetails.setText("Details:");

        lblArenaName.setText("-----");

        cbbSide.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Wala", "Meron", "Draw" }));
        cbbSide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbSideActionPerformed(evt);
            }
        });

        btnRemoveArena.setText("Remove");
        btnRemoveArena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveArenaActionPerformed(evt);
            }
        });

        btnUpdateArena.setText("Update");
        btnUpdateArena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateArenaActionPerformed(evt);
            }
        });

        cbbAccount.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbAccountItemStateChanged(evt);
            }
        });
        cbbAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbAccountActionPerformed(evt);
            }
        });

        btnAddAccount.setText("Add");
        btnAddAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAccountActionPerformed(evt);
            }
        });

        btnRemmoveAccount.setText("Remove");
        btnRemmoveAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemmoveAccountActionPerformed(evt);
            }
        });

        lblAccount.setText("Account");

        btnRun.setText("Run");
        btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunActionPerformed(evt);
            }
        });

        btnStop.setText("Stop");
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });

        taDetails.setEditable(false);
        taDetails.setColumns(20);
        taDetails.setRows(5);
        spTaDetails.setViewportView(taDetails);

        btnRenew.setText("Renew now");
        btnRenew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRenewActionPerformed(evt);
            }
        });

        cbbCondition.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "=", "⇑", "⇓", "=⇑", "=⇓", "⇑⇓" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spTaDetails)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDetails)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblArenaName, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbbSide, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtCoin, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbbCondition, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnUpdateArena, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 304, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(spArenaDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(btnRemoveArena, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnAddAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbbAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnRemmoveAccount)
                                        .addGap(178, 178, 178)
                                        .addComponent(btnStop))
                                    .addComponent(lblAccount)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblArenaTop)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbbArenaList, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnAddArena, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnRenew)))
                                .addGap(18, 18, 18)
                                .addComponent(btnRun, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(91, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblAccount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddAccount)
                            .addComponent(btnRemmoveAccount))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbArenaList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblArenaTop)
                            .addComponent(btnAddArena))
                        .addGap(16, 16, 16)
                        .addComponent(spArenaDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(btnStop)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnRenew))
                            .addComponent(btnRun, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRemoveArena)
                        .addGap(63, 63, 63)))
                .addComponent(lblDetails)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblArenaName)
                    .addComponent(cbbSide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCoin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateArena)
                    .addComponent(cbbCondition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(spTaDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddArenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddArenaActionPerformed
        // TODO add your handling code here:
        String selected = cbbArenaList.getSelectedItem().toString();
        if (!currentConfig.getArenas().contains(selected)) {
            currentConfig.getArenas().add(selected);
            updateForm();
        }
    }//GEN-LAST:event_btnAddArenaActionPerformed

    private void btnAddAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAccountActionPerformed
        // TODO add your handling code here:
        showCredentialsDialog();
        updateForm();
    }//GEN-LAST:event_btnAddAccountActionPerformed

    private void tblArenaDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblArenaDetailsMouseClicked
        // TODO add your handling code here:
        try {
            if (tblArenaDetails.getSelectedRow() < tblArenaDetails.getRowCount()) {
                String arena = currentConfig.getArenas().get(tblArenaDetails.getSelectedRow());
                lblArenaName.setText(arena);
                cbbSide.setSelectedItem(currentConfig.getBetSide(arena));
                txtCoin.setText(currentConfig.getBetCoin(arena));
                String betOnly = "";
                if (currentConfig.getBetOnly(arena) == null) {

                    cbbCondition.setSelectedItem("All");
                } else {
                    if (currentConfig.getBetOnly(arena).size() > 2) {
                        cbbCondition.setSelectedItem("All");
                    } else {
                        for (Integer betTrend : currentConfig.getBetOnly(arena)) {
                            if (betTrend == 0) {
                                betOnly += "=";
                            }
                            if (betTrend == 1) {
                                betOnly += "⇑";
                            }
                            if (betTrend == -1) {
                                betOnly += "⇓";
                            }
                        }
                        cbbCondition.setSelectedItem(betOnly);
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            taDetails.append("\nClicked out of the table.");
        }
    }//GEN-LAST:event_tblArenaDetailsMouseClicked

    private void btnRemoveArenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveArenaActionPerformed
        // TODO add your handling code here:
        try {
            String arena = currentConfig.getArenas().get(tblArenaDetails.getSelectedRow());
            currentConfig.getArenas().remove(arena);
            currentConfig.removeBetCoin(arena);
            currentConfig.removeBetSide(arena);
            updateForm();
            currentConfig.setIsNeedRenew(true);
        } catch (Exception e) {
            taDetails.append("\nHave not clicked on the table.");
        }
    }//GEN-LAST:event_btnRemoveArenaActionPerformed

    private void btnUpdateArenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateArenaActionPerformed
        // TODO add your handling code here:  
        if (currentConfig.getBetCoin(lblArenaName.getText()) == null || currentConfig.getBetOnly(lblArenaName.getText()) == null || currentConfig.getBetSide(lblArenaName.getText()) == null) {
            currentConfig.setIsNeedRenew(true);
        }
        try {
            if (cbbSide.getSelectedIndex() > -1 && Integer.parseInt(txtCoin.getText()) > 5) {
                currentConfig.setBetCoin(lblArenaName.getText(), txtCoin.getText());
                currentConfig.setBetSide(lblArenaName.getText(), cbbSide.getSelectedItem().toString());
                String condition = (String) cbbCondition.getSelectedItem();
                ArrayList<Integer> conditionArray = new ArrayList<Integer>();
                if (condition.equals("All")) {
                    conditionArray.add(0);
                    conditionArray.add(-1);
                    conditionArray.add(1);
                } else {
                    for (char character : condition.toCharArray()) {
                        if ("⇑".charAt(0) == character) {
                            conditionArray.add(1);
                        }
                        if ("⇓".charAt(0) == character) {
                            conditionArray.add(-1);
                        }
                        if ("=".charAt(0) == character) {
                            conditionArray.add(0);
                        }
                    }
                }
                currentConfig.setBetOnly(lblArenaName.getText(), conditionArray);
                updateForm();
            }
        } catch (Exception e) {

        }
    }//GEN-LAST:event_btnUpdateArenaActionPerformed

    private void btnRemmoveAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemmoveAccountActionPerformed
        // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog(this, "Are you sure?", "Delete", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (configs.size() > 1) {
                configs.remove(cbbAccount.getSelectedIndex());
                currentConfig = configs.get(0);
            }else{
                JOptionPane.showMessageDialog(this, "Cannot delete the last one!");
            }
        }
        updateForm();
    }//GEN-LAST:event_btnRemmoveAccountActionPerformed

    private void btnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunActionPerformed
        // TODO add your handling code here:
        for (Configuration config : configs) {
            config.setIsKeepRunning(true);
        }
        if (ctrler == null) {
            ctrler = new Controller(configs);
        }
        ctrler.runAll();
    }//GEN-LAST:event_btnRunActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
        // TODO add your handling code here:
        for (Configuration config : configs) {
            config.setIsKeepRunning(false);
        }
    }//GEN-LAST:event_btnStopActionPerformed

    private void cbbSideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbSideActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbSideActionPerformed

    private void cbbAccountItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbAccountItemStateChanged
        // TODO add your handling code here:
        if (currentConfig != configs.get(cbbAccount.getSelectedIndex())) {
            currentConfig = configs.get(cbbAccount.getSelectedIndex());
            updateForm();
        }
    }//GEN-LAST:event_cbbAccountItemStateChanged

    private void btnRenewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRenewActionPerformed
        // TODO add your handling code here:
        for (Configuration cf : configs) {
            if (cf.getUsername().equals(cbbAccount.getSelectedItem().toString())) {
                cf.setIsNeedRenew(true);
            }
        }
    }//GEN-LAST:event_btnRenewActionPerformed

    private void cbbAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbAccountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbAccountActionPerformed

    private void cbbArenaListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbArenaListActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbArenaListActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddAccount;
    private javax.swing.JButton btnAddArena;
    private javax.swing.JButton btnRemmoveAccount;
    private javax.swing.JButton btnRemoveArena;
    private javax.swing.JButton btnRenew;
    private javax.swing.JButton btnRun;
    private javax.swing.JButton btnStop;
    private javax.swing.JButton btnUpdateArena;
    private javax.swing.JComboBox cbbAccount;
    private javax.swing.JComboBox cbbArenaList;
    private javax.swing.JComboBox cbbCondition;
    private javax.swing.JComboBox cbbSide;
    private javax.swing.JLabel lblAccount;
    private javax.swing.JLabel lblArenaName;
    private javax.swing.JLabel lblArenaTop;
    private javax.swing.JLabel lblDetails;
    private javax.swing.JScrollPane spArenaDetails;
    private javax.swing.JScrollPane spTaDetails;
    private javax.swing.JTextArea taDetails;
    private javax.swing.JTable tblArenaDetails;
    private javax.swing.JTextField txtCoin;
    // End of variables declaration//GEN-END:variables
    private ArrayList<Configuration> configs;
    private Configuration currentConfig;
    private Controller ctrler;
    private String[] arenaList = {"AP5", "AR1", "AR2", "AM4", "AR3", "AB6", "AG1", "BC1", "BC2", "BC3", "BT1", "BE2", "BS3", "CT1", "BH1", "BH2", "BB1", "CM2", "BK3", "BN1", "CG2", "BG1", "BV2", "BM3", "BS1", "BB9", "BB4", "IT3", "IO4", "IM2", "II1", "IZ3", "IL2", "IB4", "IC5", "IG4", "IG5", "IN3", "IG1", "IP4"};
}
