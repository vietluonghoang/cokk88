/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dagaz.model;

import dagaz.controllers.Configuration;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Hyungin Choi
 */
public class ArenaTableModel implements TableModel {

    private Configuration config;

    public ArenaTableModel(Configuration config) {
        this.config = config;
    }

    @Override
    public int getRowCount() {
        return config.getArenas().size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Arena Name";
            case 1:
                return "Side";
            case 2:
                return "Coin";
            case 3:
                return "Condition";
            default:
                return "---";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return config.getArenas().get(rowIndex);
            case 1:
                return config.getBetSide(config.getArenas().get(rowIndex));
            case 2:
                return config.getBetCoin(config.getArenas().get(rowIndex));
            case 3:
                if (config.getBetOnly(config.getArenas().get(rowIndex)) != null) {
                    if (config.getBetOnly(config.getArenas().get(rowIndex)).size() < 1) {
                        return "---";
                    } else {
                        String condition = "";
                        for (Integer cond : config.getBetOnly(config.getArenas().get(rowIndex))) {
                            if (cond == 0) {
                                condition += "=";
                            }
                            if (cond == 1) {
                                condition += "⇑";
                            }
                            if (cond == -1) {
                                condition += "⇓";
                            }
                        }
                        return condition;
                    }
                } else {
                    return "---";
                }
            default:
                return "---";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
