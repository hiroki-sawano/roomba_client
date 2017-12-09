package jp.gr.java_conf.hs.roomba.client;

import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

public class UserInput {

	@NotEmpty
	private String name;

	private Map<String, String> selectItems;
	
	private String selectedSequence;

	private String arbitrarySequence;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getSelectItems() {
		return selectItems;
	}

	public void setSelectItems(Map<String, String> selectItems) {
		this.selectItems = selectItems;
	}

	public String getSelectedSequence() {
		return selectedSequence;
	}

	public void setSelectedSequence(String selectedSequence) {
		this.selectedSequence = selectedSequence;
	}

	public String getArbitrarySequence() {
		return arbitrarySequence;
	}

	public void setArbitrarySequence(String arbitrarySequence) {
		this.arbitrarySequence = arbitrarySequence;
	}
	
	@Override
	public String toString() {
		return "name=" + this.name + " selectedSequence=" + this.selectedSequence + " arbitrarySequence=" + this.arbitrarySequence;
	}
}
