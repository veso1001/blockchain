package imbachain.infrastructure;

import org.springframework.util.StringUtils;

public class BaseNode {
	public static final int DEFAULT_PORT = 8080;
	public static final int TRANSACTIONS_IN_BLOCK=10;
	public static final int DEFAULT_DIFFICULTY=3;
	
	public BaseNode(String name, String address, int port) {
		super();
		this.name = StringUtils.isEmpty(name) ? java.util.UUID.randomUUID().toString() : name;
		this.address = address;
		this.port = port;
	}

	public BaseNode(String address, int port) {
		this(java.util.UUID.randomUUID().toString(), address, port);
	}

	public BaseNode(String address) {
		this(java.util.UUID.randomUUID().toString(), address, DEFAULT_PORT);
	}

	private String name;
	private String address;
	private int port;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
