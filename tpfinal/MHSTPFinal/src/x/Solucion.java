package x;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Solucion {
	
	private List<Individuo> tareas = new ArrayList<>();
	
	public Solucion (List<Individuo> individuos) {
		this.tareas.addAll(individuos);
	}
	
	public Solucion clone() {
		return new Solucion(new ArrayList<>(this.tareas));
	}
	
	public int getCantidadIndividuos() {
		return this.tareas.size();
	}
	
	public int getCantidadMaquinas() {
		return this.tareas.get(0).getCantidadMaquinas();
	}
	
	public void permutar (int i, int j) {
		Collections.swap(this.tareas, i, j);
	}

	public void shuffle () {
		Collections.shuffle(this.tareas);
	}
	
	public int getPosicion (Individuo tarea) {
		return this.tareas.indexOf(tarea);
	}
	
	public Individuo getTarea (int indice) {
		return this.tareas.get(indice);
	}
	
	public Resultado evaluar () {
		Resultado resultado = new Resultado();
		
		for(int tarea = 0; tarea < getCantidadIndividuos(); tarea ++) {
			for (int maquina = 0; maquina < getCantidadMaquinas(); maquina++ ) {
				//variables por claridad
				
				if (maquina == 0) {
					resultado.inicio[maquina][tarea] = tarea == 0 ? 0 : resultado.fin[maquina][tarea-1];
				} else {
					resultado.inicio[maquina][tarea] = Math.max(resultado.fin[maquina-1][tarea], tarea ==0 ? 0 : resultado.fin[maquina][tarea-1]);
				}
				resultado.fin[maquina][tarea] = resultado.inicio[maquina][tarea] + this.tareas.get(tarea).getPeso(maquina);
			}
		}
		resultado.tiempoTotal = resultado.fin[getCantidadMaquinas()-1][getCantidadIndividuos()-1];
		
		return resultado;
	}	
		
	public class Resultado {
		
		private List<Individuo> tareas = new LinkedList<Individuo>(Solucion.this.tareas);
		private int tiempoTotal;
		private int[] [] inicio = new int[Solucion.this.getCantidadMaquinas()][Solucion.this.getCantidadIndividuos()];
		private int[] [] fin = new int[Solucion.this.getCantidadMaquinas()][Solucion.this.getCantidadIndividuos()];
		
		public int getTiempoTotal() {
			return tiempoTotal;
		}

		public void imprimir () {
			System.out.print(" \n           ");
			for (Individuo t: this.tareas)
				System.out.printf( "%9.9s   ", t.getId() );
			System.out.println();
			
			for (int maquina = 0; maquina < getCantidadMaquinas(); maquina++ ) {
				System.out.print("Maquina " + maquina  + " ");
				for(int tarea = 0; tarea < getCantidadIndividuos(); tarea ++) {
					System.out.printf(" %4d %4d :", this.inicio [maquina][tarea] , this.fin [maquina][tarea]);
				}
				System.out.println();
			}
		}
		
		
		public int getInicio(int maquina, int tarea) {
			return this.inicio[maquina][tarea];
		}
		
		public int getFin(int maquina, int tarea) {
			return this.fin[maquina][tarea];
		}
		
		public String getTagTarea(int tarea) {
			return Solucion.this.tareas.get(tarea).getId().replace("area", "");
		}
		
	
		public int getCantidadMaquinas() {
			return Solucion.this.getCantidadMaquinas(); 
		}

		public int getCantidadTareas() {
			return Solucion.this.getCantidadIndividuos(); 
		}
		
		
	}
	
	
	
}
