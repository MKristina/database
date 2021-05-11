package GUI;

import DAO.MyConnection;
import GUI.Creation.CreateDTPWindow;
import GUI.Creation.CreateRegistrationWindow;
import GUI.Requests.*;
import GUI.Tables.*;

import javax.swing.*;

import java.sql.SQLException;

public class MenuWindow extends JFrame {


    public MenuWindow(MyConnection conn, String role) throws SQLException {
        JFrame window = new JFrame("ГИБДД");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(500,500);
        window.setLayout(null);
       // JLabel msg = new JLabel("Информационная система ГИБДД");
      //  msg.setVisible(true);
        JButton vehiclesButton = new JButton("Транспортные средства");
        vehiclesButton.setBounds(20, 10, 200, 40);

        vehiclesButton.addActionListener(e-> {
            VehiclesWindow vehiclesWindow = new VehiclesWindow(conn, role);
            window.setVisible(false);
        });
        JButton ownersButton = new JButton("Владельцы ТС");
        ownersButton.setBounds(20, 60, 200, 40);
        ownersButton.addActionListener(e -> {
            OwnersWindow ownersWindow = new OwnersWindow(conn, role);
            window.setVisible(false);
        });
        JButton vehicleTypesButton = new JButton("Типы ТС");
        vehicleTypesButton.setBounds(20, 110, 200, 40);

        vehicleTypesButton.addActionListener(e-> {
        VehicleTypesWindow vehicleTypesWindow = new VehicleTypesWindow(conn, role);
        window.setVisible(false);
        });

        JButton registrationButton = new JButton("Регистрационный журнал");
        registrationButton.setBounds(20, 160, 200, 40);

        registrationButton.addActionListener(e-> {
            RegistrationView registrationView = new RegistrationView(conn, role);
            window.setVisible(false);
        });

        JButton registrationNewButton = new JButton("Зарегистрировать ТС");
        registrationNewButton.setBounds(240, 10, 200, 40);

        registrationNewButton.addActionListener(e-> {
            try {
                CreateRegistrationWindow createRegistrationWindow = new CreateRegistrationWindow(conn, role);
            } catch (Exception ee) {}
            window.setVisible(false);
        });

        JButton accTypesButton = new JButton("Типы ДТП");
        accTypesButton.setBounds(20, 210, 200, 40);

        accTypesButton.addActionListener(e-> {
            AccidentTypesWindow accidentTypesWindow = new AccidentTypesWindow(conn, role);
            window.setVisible(false);
        });
        JButton accReasonsButton = new JButton("Причины ДТП");
        accReasonsButton.setBounds(20, 260, 200, 40);

        accReasonsButton.addActionListener(e-> {
            AccidentReasonsWindow accidentReasonsWindow = new AccidentReasonsWindow(conn, role);
            window.setVisible(false);
        });
        JButton accidentButton = new JButton("ДТП");
        accidentButton.setBounds(20, 310, 200, 40);

        accidentButton.addActionListener(e-> {
            AccidentsWindow accidentsWindow = new AccidentsWindow(conn, role);
            window.setVisible(false);
        });

        JButton accidentNewButton = new JButton("Зарегистирировать ДТП");
        accidentNewButton.setBounds(240, 60, 200, 40);

        accidentNewButton.addActionListener(e-> {
            CreateDTPWindow createDTPWindow = new CreateDTPWindow(conn, role);
            window.setVisible(false);
        });

        JButton ownInfButton = new JButton("Информация о владельце");
        ownInfButton.setBounds(20, 360, 200, 40);

        ownInfButton.addActionListener(e-> {
            SearchNumber searchNumber  = new SearchNumber(conn, role);
            window.setVisible(false);
        });
        JButton inspectionButton = new JButton("Техосмотр");
        inspectionButton.setBounds(240, 160, 200, 40);

        inspectionButton.addActionListener(e-> {
            InspectionWindow inspectionWindow  = new InspectionWindow(conn, role);
            window.setVisible(false);
        });
        JButton overInspectionButton = new JButton("Просроченный техосмотр");
        overInspectionButton.setBounds(240, 210, 200, 40);

        overInspectionButton.addActionListener(e-> {
            OverdueInspectionWindow overdueInspectionWindow  = new OverdueInspectionWindow(conn, role);
            window.setVisible(false);
        });
        JButton DtpPeriodButton = new JButton("Статистика ДТП");
        DtpPeriodButton.setBounds(240, 260, 200, 40);

        DtpPeriodButton.addActionListener(e-> {
            DTPDateStatistics dtpDateStatistics  = new DTPDateStatistics(conn, role);
            window.setVisible(false);
        });
        JButton TheftPeriodButton = new JButton("Угон ТС");
        TheftPeriodButton.setBounds(240, 110, 200, 40);

        TheftPeriodButton.addActionListener(e-> {
            TheftDateStatistics theftDateStatistics  = new TheftDateStatistics(conn, role);
            window.setVisible(false);
        });
        JButton DtpReasonButton = new JButton("Статистика причин ДТП");
        DtpReasonButton.setBounds(240, 310, 200, 40);

        DtpReasonButton.addActionListener(e-> {
            DTPReasonsStatistics dtpReasonsStatistics  = new DTPReasonsStatistics(conn, role);
            window.setVisible(false);
        });
        JButton vehicleInfButton = new JButton("Информация о ТС");
        vehicleInfButton.setBounds(20, 410, 200, 40);

        vehicleInfButton.addActionListener(e-> {
            SearchInfoByNum searchInfoByNum = new SearchInfoByNum(conn, role);
            window.setVisible(false);
        });

        JButton goBackButton = new JButton("Сменить пользователя");
        goBackButton.setBounds(240, 410, 200, 40);

        goBackButton.addActionListener(e-> {
            AuthorizationWindow authorizationWindow  = new AuthorizationWindow(conn);
            window.setVisible(false);
        });

        if(role == "staffGIBDD"){
            registrationNewButton.setVisible(true);
            accidentNewButton.setVisible(false);
        } else if(role == "staff"){
            registrationNewButton.setVisible(false);
            accidentNewButton.setVisible(true);
        } else if (role == "admin"){
            registrationNewButton.setVisible(false);
            accidentNewButton.setVisible(false);
        }

     // window.add(msg);
      window.add(ownersButton);
      window.add(vehiclesButton);
      window.add(vehicleTypesButton);
      window.add(registrationButton);
      window.add(registrationNewButton);
      window.add(accTypesButton);
      window.add(accReasonsButton);
      window.add(accidentButton);
      window.add(accidentNewButton);
      window.add(ownInfButton);
      window.add(vehicleInfButton);
      window.add(goBackButton);
      window.add(inspectionButton);
      window.add(overInspectionButton);
      window.add(DtpPeriodButton);
      window.add(TheftPeriodButton);
      window.add(DtpReasonButton);
      window.setVisible(true);
      window.setLocationRelativeTo(null);
    }


}
