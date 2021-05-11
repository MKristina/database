package GUI.Requests;

import DAO.MyConnection;
import GUI.MenuWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Vector;

public class DTPDateStatistics extends JFrame {
    Vector columnNames = null;
    Vector strings = null;
    JButton goBack;
    JButton updateButton;
    private JTable table;
    DefaultTableModel model;
    public DTPDateStatistics(MyConnection conn, String role){
        addActionListeners(conn, role);
    }
    private void addActionListeners(MyConnection conn, String role){
        JFrame window = new JFrame("Статистика ДТП");
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setSize(600, 400);

        JPanel DatePanel = new JPanel();
        JLabel datePeriodLabel = new JLabel("Введите период:");
        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("##.##.####");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JFormattedTextField FirstDateField = new JFormattedTextField(maskFormatter);
        FirstDateField.setColumns(10);
        JFormattedTextField SecondDateField = new JFormattedTextField(maskFormatter);
        SecondDateField.setColumns(10);
        DatePanel.add(datePeriodLabel);
        DatePanel.add(FirstDateField);
        DatePanel.add(SecondDateField);

        goBack = new JButton("Назад");
        updateButton = new JButton("Обновить");
        DatePanel.add(updateButton);
        columnNames = new Vector();
        columnNames.add("Тип ДТП");
        columnNames.add("Количество");
        strings = new Vector();
        String select = "SELECT at.name, COUNT(acc_id) from RoadAccidents ra join AccidentTypes at using (accType_id) group by name";
        createTable(conn, select);
        updateButton.addActionListener((e)->{
            strings.clear();
            String firstDate = FirstDateField.getText();
            String secondDate = SecondDateField.getText();
            String selectNew = "SELECT at.name, COUNT(acc_id) from RoadAccidents ra join AccidentTypes at using (accType_id) where accDate >= TO_DATE ('" +  firstDate + "', 'dd.mm.yyyy') AND accDate <= TO_DATE('" + secondDate+ "', 'dd.mm.yyyy') group by name";
            createTable(conn, selectNew);
            model = new DefaultTableModel(strings, columnNames);
            table.setModel(model);
        });

        table = new JTable();
        model = new DefaultTableModel(strings, columnNames);
        table.setModel(model);

        RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
        table.setRowSorter(sorter);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        JPanel goBackPanel = new JPanel();
        goBackPanel.add(goBack, BorderLayout.LINE_END);
        mainPanel.add(DatePanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(goBackPanel);
        window.add(mainPanel);
        window.setVisible(true);
        window.setLocationRelativeTo(null);

        goBack.addActionListener((e)->{
            window.setVisible(false);
            try {
                new MenuWindow(conn, role);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void createTable(MyConnection conn, String selectNew) {
        ResultSet resultSetNew = null;
        try {
            PreparedStatement preparedStatement = conn.conn.prepareStatement(selectNew);
            resultSetNew = preparedStatement.executeQuery();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
        ResultSetMetaData resultSetMetaDataNew = null;
        try {
            resultSetMetaDataNew = resultSetNew.getMetaData();
            while (resultSetNew.next()){
                Vector tmp = new Vector();
                for (int i = 1; i <= resultSetMetaDataNew.getColumnCount(); i++)
                    tmp.add(resultSetNew.getString(i));
                strings.add(tmp);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}

