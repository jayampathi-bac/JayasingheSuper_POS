package lk.ijse.pos.Controller;

import lk.ijse.pos.DB.DBConnection;
import lk.ijse.pos.Model.Payment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentController {
    public String generateOrderId() throws ClassNotFoundException, SQLException {

        ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT paymentid FROM payment ORDER BY paymentid DESC LIMIT 1");
        if (rst.next()) {
            String paymentid = rst.getString(1);
            String[] temparr = paymentid.split("P");
            int tempNumber = Integer.parseInt(temparr[1]);
            tempNumber += 1;

            if (tempNumber < 10) {
                return ("P00" + tempNumber);
            } else if (tempNumber < 100) {
                return ("P0" + tempNumber);
            } else {
                return ("P" + tempNumber);
            }
        } else {
            return "P001";
        }
    }


    public boolean addPayment(Payment payment) throws SQLException, ClassNotFoundException {
        String SQL = "INSERT INTO payment VALUES(?,?,?,?,?,?)";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
        pstm.setObject(1,payment.getPaymentid());
        pstm.setObject(2,payment.getOrderid());
        pstm.setObject(3,payment.getGrossPrice());
        pstm.setObject(4,payment.getPoints());
        pstm.setObject(5,payment.getTotalDiscount());
        pstm.setObject(6,payment.getNetPrice());
        return pstm.executeUpdate()>0;
    }

    public void updatePoints(Payment payment) throws SQLException, ClassNotFoundException {
        double pointGained = payment.getNetPrice()/1000;
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE loyality_card SET points=points-?+? WHERE lcid=?");
        pstm.setObject(1,payment.getPoints());
        pstm.setObject(2,pointGained);
        pstm.setObject(3,payment.getLcid());
        pstm.executeUpdate();
    }

}
