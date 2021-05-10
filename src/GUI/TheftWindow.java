package GUI;

import DAO.MyConnection;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class TheftWindow extends JFrame {
    Vector columnNames = null;
    Vector strings = null;

    public TheftWindow(MyConnection conn, String role){

        addActionListeners(conn, role);
    }
    private void addActionListeners(MyConnection conn, String role){

        columnNames = new Vector();
        columnNames.add("ID Угона");
        columnNames.add("ТС");
        columnNames.add("Гос. номер");
        columnNames.add("Дата угона");
        columnNames.add("Дата возвращения");

        strings = new Vector();
        String select = "select theft_ID, brand || ' ' || model, series || ' ' || num, dateOfTheft, dateOfReturn from vehicleTheft join registration using (reg_id)join vehicles using (vehicle_id) join freenumbers using (num_id)";
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
            new TablesView(conn, "VehicleTheft", columnNames, strings, role);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
