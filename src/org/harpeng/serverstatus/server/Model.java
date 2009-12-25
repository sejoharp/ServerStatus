package org.harpeng.serverstatus.server;

import org.harpeng.serverstatus.utils.MyXMLParser;

public class Model {
	private String configFile = "ServerConfig.xml";
	public String getAddress() {
		return new MyXMLParser(this.configFile).getElementValue("address");
	}
	public String getPort() {
		return new MyXMLParser(this.configFile).getElementValue("port");
	}
}
