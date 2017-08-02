package dbtoxml;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


class Order_line {
	private String olk;
	private String iid;
	private double qty;
	private static int i = 0;
	private int orderno;
	private Order_line_total orderlinetotal;
	private List <Order_line_total> olt = new ArrayList<Order_line_total>();
	public Order_line() {
	}
	public String getOlk() {
		return olk;
	}
	public void setOlk(String olk) {
		this.olk = olk;
	}
	public String getIid() {
		return iid;
	}
	public void setIid(String iid) {
		this.iid = iid;
	}
	public double getqty() {
		return qty;
	}
	public void setqty(double number) {
		this.qty = number;
	}
	public int getOrderno() {
		return orderno;
	}
	public void setOrderno(int orderno) {
		this.orderno = orderno;
	}
	public Element getXml(Document doc , Element appendto){
		try{
	
		Element toReturn = doc.createElement("Order_line");
		toReturn.setAttribute("olk", this.olk);
		toReturn.setAttribute("iid", this.iid);
		toReturn.setAttribute("qty", Double.toString(this.qty));
		toReturn.setAttribute("orderno", Integer.toString(this.orderno));
		appendto.appendChild(toReturn);
		for(Order_line_total abc : olt){
			appendto.appendChild(abc.getXml(doc, toReturn));
		}
		return appendto;
		}
		catch(Exception e){
			System.out.println("error in getxml of orderline "+e);
			return null;
		}
		
	}
	public void setOrderLineTotal (String where){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE", "System", "bridge123");
			PreparedStatement ps = conn.prepareStatement("select * from order_line_total where olk = '" +where+"'");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){	
				orderlinetotal = new Order_line_total();
				orderlinetotal.setOltk(rs.getString(1));
				orderlinetotal.setOlk(rs.getString(2));
				orderlinetotal.setTotal(Double.parseDouble(rs.getString(3)));
				olt.add(orderlinetotal);
			}	
				
		}
		catch(Exception e){
			System.out.println("Encountered in Order_line" + e);
		}
	}
}
