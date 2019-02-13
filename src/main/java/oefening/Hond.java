package oefening;

import javax.persistence.*;

@Entity
@Table(name = "hond")
public class Hond {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "naam", length = 20)
	private String naam;

	@OneToOne(mappedBy="hond")
	private Baas baas;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public Baas getBaas() {
		return baas;
	}

	public void setBaas(Baas baas) {
		this.baas = baas;
	}

	@Override
	public String toString() {
		return "Hond [id=" + id + ", naam=" + naam + "]";
	}

}
