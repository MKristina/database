package GUI;

import DAO.MyConnection;
import GUI.Creation.*;
import GUI.Tables.AccidentParticipantsWindow;
import GUI.Tables.AccidentsWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Vector;

public class TablesView extends JFrame {
    private JButton goBack;
    private JButton createNew;
    private JButton deleteNew;
    private JButton details;
    private JTable table;
    private JButton part;
    private JPanel mainPanel;
    DefaultTableModel model;
    public TablesView(MyConnection conn, String tableName, Vector columnNames, Vector strings, String role){
        super("Работа с таблицей " + tableName);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        createNew = new JButton("Добавить запись");
        createNew.setVisible(false);
        deleteNew = new JButton("Удалить запись");
        deleteNew.setVisible(false);
        details = new JButton("Подробности");
        details.setVisible(false);
        part = new JButton("Добавить участника");
        part.setVisible(false);
        if((tableName == "Owners" || tableName == "Vehicles" || tableName == "Registration" || tableName == "RoadAccidents" || tableName == "AccidentParticipants"  || tableName == "Inspection")&& role != "staff"){
            createNew.setVisible(true);
            deleteNew.setVisible(true);
        }
        if (tableName=="RoadAccidents"){
            details.setVisible(true);
        }
        if (tableName=="AccidentParticipants"){
            part.setVisible(true);
            createNew.setVisible(false);
        }
        goBack = new JButton("Назад");
        addActionListener(conn, tableName, role);

       //  table = new JTable(strings, columnNames);
        table = new JTable();
        model = new DefaultTableModel(strings, columnNames);
        table.setModel(model);

        RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
        table.setRowSorter(sorter);
         mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        JPanel goBackPanel = new JPanel();
        goBackPanel.add(goBack, BorderLayout.LINE_END);
        goBackPanel.add(createNew);
        goBackPanel.add(deleteNew);
        goBackPanel.add(details);
        goBackPanel.add(part);
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
                new OwnerTypeSelect(conn, role);
            } else if (tableName == "Vehicles"){
                new CreateVehicleWindow(conn, role);
            } else if (tableName == "Registration"){
                try {
                    new CreateRegistrationWindow(conn,role);
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            } else if (tableName == "RoadAccidents"){
                new CreateDTPWindow(conn, role);
            } else if (tableName == "Inspection") {
                try {
                    new CreateInspectionWindow(conn, role);
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
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
            } else if (tableName == "RoadAccidents"){
                del= "DELETE FROM RoadAccidents WHERE acc_ID = " + del_ID;
            } else if (tableName == "Registration"){
                del= "DELETE FROM Registration WHERE reg_ID = " + del_ID;
            } else if (tableName == "AccidentParticipants"){
                del= "DELETE FROM AccidentParticipants WHERE record_ID = " + del_ID;
            } else if (tableName == "Inspection"){
                del = "DELETE FROM Inspection WHERE insp_id = " + del_ID;
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
        part.addActionListener((e)->{
            setVisible(false);
            new CreateParticipantWindow(conn, role);
        });
    }
}
