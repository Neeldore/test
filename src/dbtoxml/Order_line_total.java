package dbtoxml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Order_line_total {
	private String oltk , olk;
	private Double Total;
	public String getOltk() {
		return oltk;
	}
	public void setOltk(String oltk) {
		this.oltk = oltk;
	}
	public String getOlk() {
		return olk;
	}
	public void setOlk(String olk) {
		this.olk = olk;
	}
	public Double getTotal() {
		return Total;
	}
	public void setTotal(Double total) {
		Total = total;
	}
	public Element getXml(Document doc , Element el){
		
		try{
			Element toReturn =doc.createElement("Order_line_total");		
			toReturn.setAttribute("oltk", this.oltk);
			toReturn.setAttribute("olk", this.olk);
			toReturn.setAttribute("total" , Double.toString(this.Total));
			el.appendChild(toReturn);
			return el;
		}
		catch(Exception e){
			System.out.println("Encountered in orderlinetotal "+e);
		}
		return null;
		
	}
	
}
