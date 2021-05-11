package GUI.Tables;

import DAO.MyConnection;
import GUI.TablesView;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class AccidentsWindow extends JFrame {
    Vector columnNames = null;
    Vector strings = null;
    public AccidentsWindow(MyConnection conn, String role){
        addActionListeners(conn, role);

    }
    private void addActionListeners(MyConnection conn, String role){

        columnNames = new Vector();
        columnNames.add("ID ДТП");
        columnNames.add("Тип");
        columnNames.add("Причина");
        columnNames.add("Дата");
        columnNames.add("Место");
        columnNames.add("Описание");
        columnNames.add("Дорожные условия");
        columnNames.add("Число пострадавших");
        strings = new Vector();
        String select = "SELECT acc_ID, name, accDescription, accDate, PLACE, DESCRIPTION, ROADCONDITION, NUMOFVICTIMS FROM RoadAccidents JOIN AccidentTypes USING(acctype_ID) Join AccidentReasons USING(accReason_ID)";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = conn.conn.prepareStatement(select);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
        ResultSetMetaData resultSetMetaData = null;
        try {
            resultSetMetaData = resultSet.getMetaData();
            while (resultSet.next()){
                Vector tmp = new Vector();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                    tmp.add(resultSet.getString(i));
                strings.add(tmp);
            }
            new TablesView(conn, "RoadAccidents", columnNames, strings, role);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
