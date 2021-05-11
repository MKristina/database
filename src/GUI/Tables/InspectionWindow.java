package GUI.Tables;

import DAO.MyConnection;
import GUI.TablesView;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class InspectionWindow extends JFrame {
    Vector columnNames = null;
    Vector strings = null;

    public InspectionWindow(MyConnection conn, String role){

        addActionListeners(conn, role);
    }
    private void addActionListeners(MyConnection conn, String role){

        columnNames = new Vector();
        columnNames.add("ID ТО");
        columnNames.add("ТС");
        columnNames.add("Гос. номер");
        columnNames.add("Квитанция");
        columnNames.add("Дата прохождения");
        columnNames.add("Пройден");
        columnNames.add("Следующий ТО");

        strings = new Vector();
        String select = "select insp_id, brand || ' ' || model, series || ' ' || num,receipt, dateOfInsp, passed, to_char(nextYear, 'yyyy')  from inspection join registration using (reg_id)join vehicles using (vehicle_id) join freenumbers using (num_id)";
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
            new TablesView(conn, "Inspection", columnNames, strings, role);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
