/****************************/
/* Herramientas Inteligentes *
/****************************/

#define TRUE 1
#define FALSE 0
 

#define GEN_REP TRUE 

#define PROP 1  /* seleccion proporcional */
#define TOUR 2  /* seleccion tournament (q=2) */





typedef char ALELO; /* 0 o 1 */

typedef ALELO CROMOSOMA[500];
typedef double  FENOTIPO[20];

typedef struct {
		CROMOSOMA crom;         /* genotipo = string de bits */
		FENOTIPO x;               /* fenotipo */
		double  objetivo;        /* valor de la funcion objetivo */
	        double  fitness;         /* adaptabilidad */

	} INDIVIDUO;

typedef INDIVIDUO *POBLACION;

typedef struct {
		double  costo;
		FENOTIPO dis;
} INFO;


#define P printf


