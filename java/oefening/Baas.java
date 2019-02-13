package oefening;

import javax.persistence.*;

@Entity
@Table(name = "baas")
public class Baas {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "naam", length = 20)
	private String naam;

	@OneToOne(cascade = { CascadeType.ALL})
	private Hond hond;

	@Override
	public String toString() {
		return "Baas [id=" + id + ", naam=" + naam;
	}

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

	public Hond getHond() {
		return hond;
	}

	public void setHond(Hond hond) {
		this.hond = hond;
	}

}