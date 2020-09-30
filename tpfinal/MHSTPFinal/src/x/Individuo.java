package x;

public class Individuo {

	private String id;	
	private int[] pesos;
	
	public Individuo(String id, int[] pesos) {
		this.id = id;
		this.pesos=pesos;
	}

	public Individuo(String id, int cantidadMaquinas) {
		this.id = id;
		this.pesos= new int [cantidadMaquinas];
	}
	
	
	public String getId() {
		return id;
	}
	
	public int getPeso(int i) {
		return pesos[i];
	}
	
	public void setPeso(int i, int v) {
		pesos[i] = v;
	}
	
	public int getCantidadMaquinas() {
		return this.pesos.length;
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
		Individuo other = (Individuo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
