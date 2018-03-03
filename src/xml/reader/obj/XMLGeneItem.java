package xml.reader.obj;

public class XMLGeneItem {

	private String name;
	private String label;
	private String source;
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	

	public String getSource() {
		return source;
	}
	

	public void setSource(String source) {
		this.source = source;
	}
	

	@Override
	public String toString() {
		return "XMLGeneItem [name=" + name + ", label=" + label + "]";
	}
	
	
	
	
	
}
