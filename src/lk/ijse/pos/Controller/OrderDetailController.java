package lk.ijse.pos.Controller;

import javafx.collections.ObservableList;
import lk.ijse.pos.DB.DBConnection;
import lk.ijse.pos.Model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDetailController {

    public boolean addOrderDetail(ObservableList<OrderDetail> orderDetails) throws SQLException, ClassNotFoundException {
        for(OrderDetail temp:orderDetails){
            boolean isAdded=addOrderDetail(temp);
            if(!isAdded){return false;}
        }
        return true;
    }

    private boolean addOrderDetail(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO orderdetail VALUES(?,?,?,?)";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,orderDetail.getOrderid());
        pstm.setObject(2,orderDetail.getItemid());
        pstm.setObject(3,orderDetail.getUnitprice());
        pstm.setObject(4,orderDetail.getQty());
        return pstm.executeUpdate() > 0;
    }

    public boolean updateStock(ObservableList<OrderDetail> orderDetailList) throws SQLException, ClassNotFoundException {
        for (OrderDetail orderDetail : orderDetailList) {
            boolean isUpdate=new OrderDetailController().updateStock(orderDetail);
            if(!isUpdate){
                return false;
            }
        }
        return true;
    }

    public  boolean updateStock(OrderDetail orderDetail) throws ClassNotFoundException, SQLException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE item SET qtyOnHand=qtyOnHand-? WHERE itemid=?");
        stm.setObject(1, orderDetail.getQty());
        stm.setObject(2, orderDetail.getItemid());
        return stm.executeUpdate()>0;
    }
    
}
