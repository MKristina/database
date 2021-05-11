package GUI.Requests;

import DAO.MyConnection;
import GUI.TablesView;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class SearchInfoByNumResWindow extends JFrame {
    Vector columnNames = null;
    Vector strings = null;
    public SearchInfoByNumResWindow(MyConnection conn, String role, String id){
        addActionListeners(conn, role, id);
    }
    private void addActionListeners(MyConnection conn, String role,String id){
        columnNames = new Vector();
        columnNames.add("Бренд, модель");
        columnNames.add("Год выпуска");
        columnNames.add("Объем двигателя");
        columnNames.add("Мощность двигателя");
        columnNames.add("Номер двигателя");
        columnNames.add("Номер кузова");
        columnNames.add("Номер шасси");
        columnNames.add("Количество ДТП");
        strings = new Vector();
        String select = "with t1  as(\n" +
                "select  owner_ID as owner, count(acc_ID) as AccidentCount from FreeNumbers\n" +
                "JOIN Registration USING(num_ID)\n" +
                "JOIN AccidentParticipants USING(reg_ID)\n" +
                "WHERE num_ID =" + Integer.parseInt(id) + "\n"+
                "Group By owner_ID\n" +
                ")\n" +
                "select brand||' '||model, to_char(dateofissue, 'yyyy'), enginevolume, enginepower, enginenumber, bodynumber,chassisnumber, AccidentCount \n" +
                "from t1 \n" +
                "join Registration r ON (r.num_ID =" + Integer.parseInt(id) +")\n" +
                "join Vehicles USING(vehicle_id)";
     //   String select = "SELECT owner_id, lastname||' ' ||firstname||' '||patronymic, name, address from owners join ownerTypes using(ownType_id) join registration using(owner_id) join freenumbers using(num_id) where num_ID = " + Integer.parseInt(id) ;
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
            new TablesView(conn, "Информация о ТС", columnNames, strings, role);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
