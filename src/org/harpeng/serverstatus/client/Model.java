package org.harpeng.serverstatus.client;
import org.harpeng.serverstatus.utils.MyXMLParser;

public class Model implements IModel {
	private String configFile = "ClientConfig.xml";
	/* (non-Javadoc)
	 * @see org.harpeng.serverstatus.client.IModel#getAddress()
	 */
	public String getAddress() {
		return new MyXMLParser(this.configFile).getElementValue("serverAddress");
	}
	/* (non-Javadoc)
	 * @see org.harpeng.serverstatus.client.IModel#getPort()
	 */
	public String getPort() {
		return new MyXMLParser(this.configFile).getElementValue("port");
	}
	/* (non-Javadoc)
	 * @see org.harpeng.serverstatus.client.IModel#getServiceName()
	 */
	
	public String getMAC() {
		return new MyXMLParser(this.configFile).getElementValue("mac");
	}
	
	public String getRetries() {
		return new MyXMLParser(this.configFile).getElementValue("retries");
	}
}