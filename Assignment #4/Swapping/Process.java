
public class Process {
	char name; 
	int size;
	int duration;
	public char getName() {
		return name;
	}
	public void setName(char name) {
		this.name = name;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public String toString() {
		return "Process: Name: " + name + " Size: " + size 
                        + " Duration: " + duration;
	}
}
