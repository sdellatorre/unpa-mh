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
}
