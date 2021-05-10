package GUI;

import DAO.MyConnection;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class AccidentParticipantsWindow extends JFrame {
    Vector columnNames = null;
    Vector strings = null;
    public AccidentParticipantsWindow(MyConnection conn, int accident_ID, String role){
        addActionListeners(conn,  accident_ID, role);

    }
    private void addActionListeners(MyConnection conn, int accident_ID, String role){

        columnNames = new Vector();
        columnNames.add("ID участника");
        columnNames.add("ФИО участника");
        columnNames.add("ТС");
        columnNames.add("Гос. номер");
        columnNames.add("Сумма ущерба");
        strings = new Vector();
        String select = "SELECT record_ID, lastName || ' ' || firstName || ' ' || patronymic, brand||' '||model,series||num,  damage FROM AccidentParticipants JOIN Registration USING(reg_ID) JOIN FreeNumbers USING(num_ID) JOIN Owners USING(owner_ID) JOIN Vehicles USING(vehicle_ID) WHERE acc_ID = " + accident_ID;
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
            new TablesView(conn, "AccidentParticipants", columnNames, strings, role);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
