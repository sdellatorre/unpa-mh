/********************************/
/*   Herramientas Inteligentes  */
/********************************/


/* Genetic Algorithm based on Goldberg's book   */

/****************************************************************************
		    "Include" files
****************************************************************************/
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include <stdio.h>
#include <malloc.h>
#include <string.h>
 

#include "def.h"
#undef EXTERN
#define EXTERN 
#include "var.h"

/*****************************************************************************
			Prototypes
*****************************************************************************/
void inicializa( void );
void generacion( void );
void estadisticas( POBLACION );
void rec_argum( char **, int );
void itoa(int, char*);
double random_c(void);
void keep_the_best( void );

/* plot */
FILE *initGpp( int );
void plotGpp(FILE*, double);
void SavePlot( FILE*, double);

/***************************************************************************
                            Function: main
***************************************************************************/
main( int argc, char ** argv)
{
  time_t t1;  /* tiempo insumido por el AGS */
  int i;
  
  FILE *datos;   /* archivo de estadisticas: reporte final */
  FILE *gnu_out, /* archivo con datos por generacion de la ejecucion del AG */
    *plot;    /* archivo usado por las llamadas a 'gnuplot' */

  
  rec_argum( argv, argc);
  

  /* Asignacion de Memoria para la poblacion */
  
  if ( 
      ( oldpop=(POBLACION) calloc( popsize , sizeof( INDIVIDUO ) ) ) == NULL 
      )
    { printf("ERROR: No pudo conseguir memoria\n"); exit(0); }
  if ( 
      ( newpop=(POBLACION) calloc( popsize , sizeof( INDIVIDUO ) ) ) == NULL 
      )
    { printf("ERROR: No pudo conseguir memoria\n"); exit(0); }
  


  
  datos = fopen( "res", "a");
 
  gnu_out = fopen("out", "w");
  
  if ( viewplot || savep) 
    plot = initGpp( nro_func );
  
  /* Data gathering */
  fprintf( datos, "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n");
  fprintf( datos, "Funcion: %d, Nro. de genes: %d\n", nro_func, no_gens);
  fprintf( datos, "Poblacion: %d, MaxGen: %d\n", popsize, maxgen);
  fprintf( datos, "tipo_cross: %d, pcross: %lf,  pmut: %lf\n", 
	   cross_tipo, pcross, pmutacion); 
  fprintf( datos, "elit: %d, rep_pol: %d, sel: %d\n", elitism, rep_pol, sel);
  fprintf( datos, "seed: %d\n", 
	   seed);
  fprintf(datos,"\n\n");
  
 
  
  clock(); /* set counter of CLOCK TICKS to zero */
  if (!seed )  /* 
		  uses time() as initial seed if it is not provided by the use 
	       */
    srand((unsigned) time(NULL) );  
  else 
    srand( seed );
  
  gen = 0;
  
  inicializa();
  estadisticas( oldpop );

  /********* initialize "best_sol" ********/
  best_sol.objective = oldpop[opt].objetivo;
  for (i=0; i < no_gens; i++)
    {
      best_sol.crom[i] = oldpop[opt].crom[i];
    }
  /*****************************************/


  fprintf(gnu_out, "%3d\t%10.7f\t%10.7f\t%10.7f\n", 
	  gen,       min,       avg,       max);
  fflush(gnu_out);
        
  if ( viewplot ) plotGpp(plot, best_sol.objective );


  do 
    {
      
      gen++;
      
      if ( rep_pol == GEN_REP ) /* After call 'generation()', the new 
				   population (newpop) turns now the 
				   previous one (oldpop) */
	{
	  generacion();
	  
	  auxpop = oldpop;
	  oldpop = newpop;
	  newpop = auxpop;
	}
      else /* SS_REP */
	{
	  gen_SS();
	  /* It doesn't need to swap 'newpop' with 'oldpop' */
	}
 

      estadisticas( oldpop );
      
      /* update "best_sol" (reagrding  a maximization problem) */
      if ( best_sol.objective < oldpop[opt].objetivo )
	{
	  best_sol.objective = oldpop[opt].objetivo;
	  for(i=0; i<no_gens; i++)
	    {
	      best_sol.crom[i] = oldpop[opt].crom[i];
	    }
	  best_sol.gen = gen; /* saves the current generation */
	}
	  
      fprintf(gnu_out, "%3d\t%10.7f\t%10.7f\t%10.7f\n", 
	      gen,       min,       avg,       max);
      fflush(gnu_out);
        
      if ( viewplot && (! (gen % wplot) )  ) plotGpp(plot, best_sol.objective);

      if  ( elitism )
	{
	  keep_the_best(); /* keeps the best in the actual 
			      population */
	}
      
    }  while ( gen < maxgen);
  
  
 

  t1 = clock(); 	/* finaliza y obtiene diferencia */
  
  fprintf( datos, "# %lf  %ld  %d\n", 
	   best_sol.objective, t1/CLOCKS_PER_SEC, best_sol.gen);

  
  for (i=0; i<no_gens; i++)
    {   
      fprintf( datos, " %d,", best_sol.crom[i]);
    }

  fprintf(datos, "\n\n");
  
  if ( savep ) SavePlot( plot, best_sol.objective);
  if ( viewplot || savep) quitGpp( plot );
}

/*****************************************************************************
			       	keep_the_best  
*****************************************************************************/
void keep_the_best()
{
  int j;
  /* 'worst' value is obtained in estadisiticas */
  worst = random_c() * popsize;  /*
				   for avoiding kill always the 
				   worst individual 
				 */

  for (j=0; j < no_gens; j++)
    {
      oldpop[worst].crom[j] = best_sol.crom[j];
    }
  oldpop[worst].objetivo = best_sol.objective;
  oldpop[worst].fitness = best_sol.objective;
}      


/*****************************************************************************
				 	itoa  
*****************************************************************************/
void itoa( n, str)
int n;
char *str;
{
	div_t i;

	if ( n < 10 ){ str[0] = n + '0'; str[1] = 0; }
	else	
		if ( n < 100 ) 
		{
			i = div(n,10);
			str[0] = i.quot + '0';
			str[1] = i.rem + '0';
			str[2] = 0;
		}
		else /* hasta 999 */	
		{
			i = div(n,100);
			str[0] = i.quot + '0';
			i = div( i.rem, 10 );
			str[1] = i.quot + '0';
			str[2] = i.rem + '0';
			str[3] = 0;
		} 
}

