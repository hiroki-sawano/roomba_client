package jp.gr.java_conf.hs.roomba.client;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "command")
public class Command {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	@NotNull	
	private long id;

	@Column(length = 50, nullable = false)
	@NotEmpty
	private String name;

	@Column(length = 50, nullable = true)
	@NotEmpty
	private String serialSequence;

	public Command() {
		
	}

	public Command(String name, String serialSequence){
		this.name = name;
		this.serialSequence = serialSequence;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSerialSequence() {
		return serialSequence;
	}

	public void setSerialSequence(String serialSequence) {
		this.serialSequence = serialSequence;
	}
	
	@Override
	public String toString() {
		if(id == 0){
			return "name=" + this.name + " serialSequence=" + this.serialSequence;
		}else {
			return "id=" + this.id + " name=" + this.name + " serialSequence=" + this.serialSequence;
		}
	}
}
