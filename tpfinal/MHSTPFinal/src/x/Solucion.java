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
		Procesador [] procesadores =  new Procesador[this.getCantidadMaquinas()]; 
		
		procesadores[0] = new Procesador(resultado, null, 0);
		for (int i = 1 ; i < this.getCantidadMaquinas(); i++) {
			procesadores[i] = new Procesador(resultado, procesadores[i-1], i);
		}
		
		for (Procesador p: procesadores) {
			p.start();
		}
		
		for (Procesador p: procesadores) {
			try {
				p.join();
			} catch (InterruptedException e) { 
				e.printStackTrace();
			}
		}
		return resultado ;
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
		
		
	}
	
	public class Procesador extends Thread {
		private Procesador previo;
		private Resultado resultado;
		private int indice = 0;
		private int[] inicio = new int[Solucion.this.getCantidadIndividuos()];
		private int[] fin = new int[Solucion.this.getCantidadIndividuos()];
		
		
		public Procesador(Resultado resultado, Procesador previo, int indice) {
			this.resultado = resultado;
			this.previo = previo;
			this.indice = indice;
		}
		
		private boolean termino(int tarea) {
			return this.fin[tarea] != 0;
		}
		
		public int tiempoTotal() {
			return this.fin[Solucion.this.tareas.size()-1];
		}
		
		
		public void run() {
			int x = 0;
			int i =0;
			while (i < tareas.size() ) {
				if (this.previo == null) {
					this.inicio[i] = i == 0 ? 0 : this.fin[i-1];
					this.fin[i] = this.inicio[i] + Solucion.this.tareas.get(i).getPeso(this.indice);
					i++;
				} else if (this.previo != null && this.previo.termino(i) && (i==0 || this.termino(i-1))) {
					this.inicio[i] = Math.max(this.previo.fin[i], i==0 ? 0 : this.fin[i-1]);
					this.fin[i] = this.inicio[i] + Solucion.this.tareas.get(i).getPeso(this.indice);
					i++;
				}
				if(x++ == 20 && this.indice == 0) {
					System.out.println();
				}
			}

			synchronized(this.resultado) {
				
				for (i = 0 ; i < Solucion.this.getCantidadIndividuos(); i++) {
					this.resultado.inicio[this.indice][i] = this.inicio[i];
					this.resultado.fin[this.indice][i] = this.fin[i];
				}
				if (this.indice == getCantidadMaquinas() -1)
					this.resultado.tiempoTotal = this.fin[getCantidadIndividuos()-1]; 
				
			}
		}
	}
	
	
}
