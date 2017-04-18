package br.com.alexandre.cxf.cas.client;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Game implements Serializable {

	private static final long serialVersionUID = 6621629183000193724L;

	private String id;
	private String name;
	private Integer year;
	private String publisher;
	private List<String> platforms;
	
	public Game() { }
	
	public Game(String name, Integer year, String publisher, List<String> platforms) {
		this.name = name;
		this.year = year;
		this.publisher = publisher;
		this.platforms = platforms;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public List<String> getPlatforms() {
		return platforms;
	}

	public void setPlatforms(List<String> platforms) {
		this.platforms = platforms;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", name=" + name + ", year=" + year + ", publisher=" + publisher + ", platforms="
				+ platforms + "]";
	}
		
}
