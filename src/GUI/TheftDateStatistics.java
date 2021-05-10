package GUI;

import DAO.MyConnection;

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

public class TheftDateStatistics extends JFrame {
    Vector columnNames = null;
    Vector strings = null;
    JButton goBack;
    JButton updateButton;
    private JTable table;
    DefaultTableModel model;
    JTextField resultCountField;
    public TheftDateStatistics(MyConnection conn, String role){
        addActionListeners(conn, role);
    }
    private void addActionListeners(MyConnection conn, String role){
        JFrame window = new JFrame("Статистика угонов");
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
        columnNames.add("ID угона");
        columnNames.add("ТС");
        columnNames.add("Дата угона");
        columnNames.add("Дата возвращения");
        strings = new Vector();
        String select = "select theft_ID, brand || ' ' || model||' '||series || ' ' || num, to_char(dateOfTheft, 'dd.mm.yyyy'), to_char(dateOfReturn, 'dd.mm.yyyy') from vehicleTheft join registration using (reg_id)join vehicles using (vehicle_id) join freenumbers using (num_id)";
        createTable(conn, select);
        updateButton.addActionListener((e)->{
            strings.clear();
            String firstDate = FirstDateField.getText();
            String secondDate = SecondDateField.getText();
            String selectNew = "select theft_ID, brand || ' ' || model||' '||series || ' ' || num, to_char(dateOfTheft, 'dd.mm.yyyy'), to_char(dateOfReturn, 'dd.mm.yyyy')  from vehicleTheft join registration using (reg_id)join vehicles using (vehicle_id) join freenumbers using (num_id) where dateOfTheft>=TO_DATE('" + firstDate + "','dd.mm.yyyy') AND dateOfTheft<=TO_DATE('" + secondDate + "','dd.mm.yyyy')";
            createTable(conn, selectNew);
            model = new DefaultTableModel(strings, columnNames);
            table.setModel(model);
            ResultSet resultSet = null;
            String resultCount ="";
            String selectCount = "select COUNT(theft_ID) from vehicleTheft where dateOfTheft>=TO_DATE('" + firstDate + "','dd.mm.yyyy') AND dateOfTheft<=TO_DATE('" + secondDate + "','dd.mm.yyyy')";
            try {
                PreparedStatement preparedStatement = conn.conn.prepareStatement(selectCount);
                resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    resultCount = resultSet.getString(1);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            resultCountField.setText(resultCount);
        });

        table = new JTable();
        model = new DefaultTableModel(strings, columnNames);
        table.setModel(model);

        ResultSet resultSet = null;
        String resultCount ="";
        String selectCount = "select COUNT(theft_ID) from vehicleTheft";
        try {
            PreparedStatement preparedStatement = conn.conn.prepareStatement(selectCount);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                resultCount = resultSet.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        JPanel resultPanel = new JPanel();
        JLabel resultCountLabel = new JLabel("Общее количество");
        resultCountField = new JTextField();
        resultCountField.setColumns(5);
        resultCountField.setText(resultCount);
        resultPanel.add(resultCountLabel);
        resultPanel.add(resultCountField);

        RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
        table.setRowSorter(sorter);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        JPanel goBackPanel = new JPanel();
        goBackPanel.add(goBack, BorderLayout.LINE_END);
        mainPanel.add(DatePanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(resultPanel);
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

