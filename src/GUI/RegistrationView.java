package GUI;

import DAO.MyConnection;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class RegistrationView extends JFrame {
    Vector columnNames = null;
    Vector strings = null;

    public RegistrationView(MyConnection conn){

        addActionListeners(conn);
    }
    private void addActionListeners(MyConnection conn){

        columnNames = new Vector();
        columnNames.add("ID Регистрации");
        columnNames.add("Владелец");
        columnNames.add("ТС");
        columnNames.add("Гос. номер");
        columnNames.add("Дата регистрации");

        strings = new Vector();
        String select = "select reg_ID, LastName || ' ' || FirstName, brand || ' ' || model, series || ' ' || num, dateReg from registration join owners using(owner_id) join vehicles using (vehicle_id) join freenumbers using (num_id)";
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
            new TablesView(conn, "Registration", columnNames, strings);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
