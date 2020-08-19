/*********************************/
/*   Herramientas Inteligentes   */
/*********************************/

	
/***************************************************************************
				Inclusion de Archivos
***************************************************************************/
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include <stdio.h>
#include <malloc.h>
#include <string.h>

#include "def.h"
#define EXTERN extern
#include "var.h"

/*****************************************************************************
				Declaracion de Prototipos
*****************************************************************************/
void rec_argum( char **, int );
void use( void );


/*****************************************************************************
				rec_argum()
*****************************************************************************/
void rec_argum( char ** arg , int nro_arg)
{

	char *p;
	
	/* Valores default */


	pmutacion = 0.005;
	pcross = 0.65;
	cross_tipo = 1;   
	maxgen = 100;
	popsize = 50;
	elitism = TRUE;
	savep = FALSE;  /* si TRUE, guarda la grafica de la corrida en 
			   el archivo 'plot_out' en formato postscript
			*/
	seed = 0;
	viewplot = TRUE;
	rep_pol = GEN_REP;
	sel = PROP;
	wplot = 100;  /* plot cada 100 generaciones */

	p = (*++arg) + 1; /* busca funcion */
	nro_arg--;

	if ( !nro_arg )  /* ESPERA AL MENOS UNA FUNCION A OPTIMIZAR */
	  {
	    printf("\nSe espera un numero de funcion\n\n");
	    use();
	  }

	switch ( *p )
	  {
	  case 'f':  /* numero de funcion a optimizar */
	    
	    sscanf( p+1, "%d", &nro_func);
	    
	    while ( --nro_arg > 0 && (*++arg)[0] == '-' )
	      {
		p = arg[0] + 1;
		switch ( *p ) 
		  {
		  case 't': sscanf( p+1, "%d", &cross_tipo);break;
		  case 'c': sscanf( p+1, "%lf", &pcross); break;
		  case 'm': sscanf( p+1, "%lf", &pmutacion); break;
		  case 'g': sscanf( p+1, "%d", &maxgen); break;
		  case 'p': sscanf( p+1, "%d", &popsize); 
				/* trabaja con poblacion par */	
		    if ( popsize % 2 ) popsize++;
		    break;
		    
		  case 'w': sscanf( p+1, "%d", &savep); break;
		  case 'v': sscanf( p+1, "%d", &ver); break;
		  case 's': sscanf( p+1, "%d", &seed); break;
		  case 'n': sscanf( p+1, "%d", &no_gens); break;
		  case 'e': sscanf( p+1, "%d", &elitism); break;
		  case 'P': sscanf( p+1, "%d", &viewplot); break;
		  case 'G': sscanf( p+1, "%d", &rep_pol) ; break;
		  case 'S': sscanf( p+1, "%d", &sel); break;
		  case 'W': sscanf( p+1, "%d", &wplot); break;
		  default : 
		    printf("Parametro desconocido en la entrada\n");
		    use();
		  }
		/* Incluir consistencias para los valores ingresados */
		/* Por ejemplo */
		if (( cross_tipo < 1 ) || ( cross_tipo > 2) )
		  {
		    printf("Crossover no conocido en \"-t%d\"\n", cross_tipo);
		    exit(-1);
		  }
		if (( pmutacion < 0.0 ) || ( pmutacion > 1.0 ) )
		  {
		    printf("Probabilidad de Mutacion invalida en \"-m%.2f\"\n",
			   pmutacion); 
		    exit(-1);
		  }
		if (( pcross < 0.0 ) || ( pcross > 1.0 ) )
		  {
		    printf("Probabilidad de Crossover invalida en \"-c%.2f\"\n"
			   , pcross); 
		    exit(-1);
		  }
		/* idem para rep_pol, sel, etc. */

	      }
 
	    break;
	    
	  default:   
	    printf("\nDebe especificar como minimo una funcion\n");
	    use();
	    
	    
	  } /* switch() */
	
	
}

/*****************************************************************************
				use()
*****************************************************************************/
void use( void )
{
	   printf("USE:  ga-c -f<funcion>  [-n<long-crom>] [-t<tipo_cross>]  [-c<pcross>] [-m<pmut>]\n");
	   printf("         [-S<selec>] [-G<reemp-generac>]\n");
	   printf("         [-p<popsize>] [-g<maxgen>] [-w<grabar-plot>] [-v<ver_poblacion>]\n");
	   printf("         [-P<ver-plot>] [-s<seed>] \n");
	   printf("\n\n");
	   exit(1);
}



