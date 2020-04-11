package com.example.restartDaemon;

public class ServiceStatus {
	private final long id;
	
	private final String serviceName;
	private final String action;
	private final String content;

	public ServiceStatus(long id, String serviceName, String action,  String content) {
		this.id = id;
		this.serviceName = serviceName;
		this.action = action;
		this.content = content;
	}
	

	public String getServiceName() {
		return serviceName;
	}

	public String getAction() {
		return action;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
}
