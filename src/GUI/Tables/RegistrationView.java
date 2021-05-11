package GUI.Tables;

import DAO.MyConnection;
import GUI.TablesView;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class RegistrationView extends JFrame {
    Vector columnNames = null;
    Vector strings = null;

    public RegistrationView(MyConnection conn, String role){

        addActionListeners(conn, role);
    }
    private void addActionListeners(MyConnection conn, String role){

        columnNames = new Vector();
        columnNames.add("ID Регистрации");
        columnNames.add("Владелец");
        columnNames.add("Тип владельца");
        columnNames.add("ТС");
        columnNames.add("Гос. номер");
        columnNames.add("Дата регистрации");

        strings = new Vector();
        String select = "select reg_ID, o.name, ot.name, brand || ' ' || model, series || ' ' || num, dateReg from registration join owners o using(owner_id) join ownerTypes ot using(ownType_id) join vehicles using (vehicle_id) join freenumbers using (num_id)";
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
            new TablesView(conn, "Registration", columnNames, strings, role);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
