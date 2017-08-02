package dbtoxml;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 * 
 * @author Neelkant K
 *
 */
public class Dbfetcher {
	
	
	private static int i=0 ;
	private static Connection conn;
	public   Document doc ;
	public static void main(String[] args){
		 Order_details orderdetails ;
		 Order_line orderline ;
		 Order_line_total orderlinetotal ;
		 List <Order_details> od = new ArrayList<Order_details>();
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trans = tf.newTransformer();
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE", "System", "bridge123");
			PreparedStatement ps = conn.prepareStatement("select * from order_details");
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsm = rs.getMetaData();
			
			while(rs.next()){
				orderdetails= new Order_details();
				orderdetails.setOrderkey(rs.getString(1));
				orderdetails.setOrderno(rs.getInt(2));
				orderdetails.setOrdername(rs.getString(3));
				orderdetails.setCust_name(rs.getString(4));
				orderdetails.setTotal(Double.parseDouble(rs.getString(5)));
				orderdetails.setOrderLine(Integer.toString(rs.getInt(2)));
				od.add(orderdetails); 
			}	
			Element root = doc.createElement("order_details");
			for(Order_details abc : od){
				root.appendChild(abc.getXml(doc, root));
			}
			doc.appendChild(root);
			StreamResult sr = new StreamResult(new File("out.xml"));
			DOMSource src = new DOMSource(doc);
			trans.transform(src, sr);
		}
		catch(Exception e){
			System.out.println("encountered "+e);
		}
	
	}
}
