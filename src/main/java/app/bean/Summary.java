package app.bean;

public class Summary {
	private int id;
	private String name;
	private String max;
	private String min;
	private String avg;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMax() {
		return max;
	}
	public void setMax(String max) {
		this.max = max;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	public String getAvg() {
		return avg;
	}
	public void setAvg(String avg) {
		this.avg = avg;
	}
	@Override
	public String toString() {
		return "Summary [id=" + id + ", name=" + name + ", max=" + max + ", min=" + min + ", avg=" + avg + "]";
	}
	
	
}
