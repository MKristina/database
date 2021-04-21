package GUI;

import DAO.MyConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TablesView extends JFrame {
    private JButton goBack;
    private JButton createNew;
    private JButton deleteNew;
    private JButton details;
    private JTable table;
    DefaultTableModel model;
    public TablesView(MyConnection conn, String tableName, Vector columnNames, Vector strings, String role){
        super("Работа с таблицей " + tableName);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createNew = new JButton("Добавить запись");
        createNew.setVisible(false);
        deleteNew = new JButton("Удалить запись");
        deleteNew.setVisible(false);
        details = new JButton("Подробности");
        details.setVisible(false);
        if((tableName == "Owners" || tableName == "Vehicles" || tableName == "Registration" || tableName == "RoadAccidents")&& role == "admin"){
            createNew.setVisible(true);
            deleteNew.setVisible(true);
        }
        if (tableName=="RoadAccidents"){
            details.setVisible(true);
        }
        goBack = new JButton("Назад");
        addActionListener(conn, tableName, role);

       //  table = new JTable(strings, columnNames);
        table = new JTable();
        model = new DefaultTableModel(strings, columnNames);
        table.setModel(model);

        RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
        table.setRowSorter(sorter);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        JPanel goBackPanel = new JPanel();
        goBackPanel.add(goBack, BorderLayout.LINE_END);
        goBackPanel.add(createNew);
        goBackPanel.add(deleteNew);
        goBackPanel.add(details);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(goBackPanel);
        add(mainPanel);
        setVisible(true);
    }
    private void addActionListener(MyConnection conn, String tableName,  String role){
        goBack.addActionListener((e)->{
            setVisible(false);
                try {
                    if (tableName=="AccidentParticipants") {
                     new AccidentsWindow(conn, role);
                    } else{
                        new MenuWindow(conn, role);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
        });
        createNew.addActionListener((e)->{
                setVisible(false);
            if (tableName=="Owners") {
                new CreateOwnersWindow(conn, role);
            } else if (tableName == "Vehicles"){
                new CreateVehicleWindow(conn, role);
            } else if (tableName == "Registration"){
                new CreateRegistrationWindow(conn,role);
            } else if (tableName == "RoadAccidents"){
                new CreateDTPWindow(conn, role);
            }


        });
        deleteNew.addActionListener((e)->{
            int selectedRow = table.getSelectedRow();
            String del = "";
            if(selectedRow != -1){
               int del_ID = Integer.parseInt((String) model.getValueAt(selectedRow, 0));
            if(tableName == "Owners"){
                 del= "DELETE FROM Owners WHERE owner_ID = " + del_ID;
            } else if (tableName == "Vehicles")
            {
                 del= "DELETE FROM Vehicles WHERE vehicle_ID = " + del_ID;
            }

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
        details.addActionListener((e)->{
            int selectedRow = table.getSelectedRow();
            int accident_ID = 0;
            if(selectedRow != -1){
                 accident_ID = Integer.parseInt((String) model.getValueAt(selectedRow, 0));

            }
            setVisible(false);
            new AccidentParticipantsWindow(conn, accident_ID, role);
        });
    }
}
