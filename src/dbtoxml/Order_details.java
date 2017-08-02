package dbtoxml;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Order_details {
	private String orderkey;
	private int orderno;
	private String ordername;
	private String cust_name;
	private Double total;
	private List <Order_line> ol = new ArrayList<Order_line>();
	private Order_line orderline ;
	private static int i =0;
	public Order_details()  {

	}
	public String getOrderkey() {
		return orderkey;
	}
	public void setOrderkey(String orderkey) {
		this.orderkey = orderkey;
	}
	public int getOrderno() {
		return orderno;
	}
	public void setOrderno(int orderno) {
		this.orderno = orderno;
	}
	public String getOrdername() {
		return ordername;
	}
	public void setOrdername(String ordername) {
		this.ordername = ordername;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Element getXml(Document doc , Element appendto){
		Element el = doc.createElement("Order_details");
		el.setAttribute("orderkey",orderkey);
		el.setAttribute("ordername",ordername);
		el.setAttribute("cust_name",cust_name);
		el.setAttribute("orderno",Integer.toString(orderno));
		appendto.appendChild(el);
		for(Order_line abc : ol){
			appendto.appendChild(abc.getXml(doc , el));
		}
		return el;
	}
	public void setOrderLine(String where){
		try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE", "System", "bridge123");
		PreparedStatement ps = conn.prepareStatement("select * from order_line where orderno = "+where);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			orderline= new Order_line();
			orderline.setOlk(rs.getString(1));
			orderline.setOrderno(rs.getInt(2));
			orderline.setIid(rs.getString(3));
			orderline.setqty(Double.parseDouble(rs.getString(4)));
			orderline.setOrderLineTotal(rs.getString(1));
			ol.add(orderline);
		}	
		}
		catch(Exception e){
			
			System.out.println("Encountred in orderdetails "+e);
		}
	}
	
	
}

