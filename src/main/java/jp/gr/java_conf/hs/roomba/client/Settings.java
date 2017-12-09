package jp.gr.java_conf.hs.roomba.client;

import java.util.Map;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "roomba")
public class Settings {
	@NotEmpty
	@Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$")
	private String ip;
	@Min(0)
	@Max(65535)
	private int port;
	private Map<String, String> sequences;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Map<String, String> getSequences() {
		return sequences;
	}

	public void setSequences(Map<String, String> sequences) {
		this.sequences = sequences;
	}

	public void show() {
		System.out.println("roomba.ip: " + ip);
		System.out.println("roomba.port: " + port);
		System.out.println("roomba.sequences: ");
		sequences.forEach((k, v) -> System.out.println("  " + k + ": " + v));
	}
}