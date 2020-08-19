/***************************************/
/*      Herramientas Inteligentes      */
/***************************************/

EXTERN POBLACION oldpop,newpop;    /* 2 poblaciones no superpuestas */
EXTERN POBLACION auxpop;			/* puntero a poblacion (auxiliar) */


EXTERN double sumfitness;     	/* suma de los valores de fitness */

EXTERN double avg, max, min;		/* maximo, minimo y promedio de cada generacion */

EXTERN int popsize, maxgen, cross_tipo, mut_type;  

							/* 	popsize: tamaño de poblacion,
								maxgen: numero de generaciones,
								cross_tipo: tipo de crossover
							*/

EXTERN double pcross, pmutacion;   /* 	probabilidades de crossover y
								mutacion respectivamente */

EXTERN int no_gens;            		/* longitud de cromosoma  */
EXTERN int nro_func;	    		/* funcion a optimizar */
EXTERN int rep_pol;
EXTERN int ver, gen, sel;			/* ver: mostrar los 20 primeros cromosomas
								   por pantalla.
							   gen: numero corriente de generacion */	   	

EXTERN int savep;			/* grabo datos de cada generacion */
EXTERN int opt,				/* indice del mejor individuo en la 
					   poblacion. Debe tenerse en cuenta si
					   el AG maximiza o minimiza la funcion
					*/
  worst,      /* Index to to the worst individual in the previus population */
  elitism,
  viewplot,   /* Ver plot en la pantalla */
  wplot,      /* Cada cuantas generaciones se muestra el plot */
  dynamic_ratio,
  slope;

EXTERN int nro_vbles;			/* Para imprimir resultado 
							   Debe ser inicializada en rec_argum.c */
EXTERN int que_hago;			/* maximiza o minimiza */
EXTERN unsigned seed;

EXTERN struct
{
  CROMOSOMA crom;
  double objective;
  int gen;
} best_sol;


EXTERN double q;


