package GUI;

import DAO.MyConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

public class TablesView extends JFrame {
    private JButton goBack;
    private JButton createNew;
    private JButton deleteNew;
    private JTable table;
    DefaultTableModel model;
    public TablesView(MyConnection conn, String tableName, Vector columnNames, Vector strings){
        super("Работа с таблицей " + tableName);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createNew = new JButton("Добавить запись");
        createNew.setVisible(false);
        deleteNew = new JButton("Удалить запись");
        deleteNew.setVisible(false);
        if(tableName == "Owners"){
            createNew.setVisible(true);
            deleteNew.setVisible(true);
        }
        goBack = new JButton("Назад");
        addActionListener(conn, tableName);

       //  table = new JTable(strings, columnNames);
        table = new JTable();
        model = new DefaultTableModel(strings, columnNames);
        table.setModel(model);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        JPanel goBackPanel = new JPanel();
        goBackPanel.add(goBack, BorderLayout.LINE_END);
        goBackPanel.add(createNew);
        goBackPanel.add(deleteNew);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(goBackPanel);
        add(mainPanel);
        setVisible(true);
    }
    private void addActionListener(MyConnection conn, String tableName){
        goBack.addActionListener((e)->{
            setVisible(false);
                try {
                    new MenuWindow(conn);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
        });
        createNew.addActionListener((e)->{
                setVisible(false);
                new CreateOwnersWindow(conn);

        });
        deleteNew.addActionListener((e)->{
            int selectedRow = table.getSelectedRow();
            if(selectedRow != -1){

               int del_ID = Integer.parseInt((String) model.getValueAt(selectedRow, 0));
               System.out.println(del_ID);
           String del= "DELETE FROM Owners WHERE owner_ID = " + del_ID;
                PreparedStatement preparedStatement = null;
                try {
                    preparedStatement = conn.conn.prepareStatement(del);
                    preparedStatement.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                model.removeRow(selectedRow);
            }
        });
    }
}
