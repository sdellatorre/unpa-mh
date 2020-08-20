/****************************/
/* Herramientas Inteligentes */
/****************************/


/*****************************************************************************
			     Inclusion de Archivos
*****************************************************************************/
#include <stdlib.h>
#include <math.h>
#include <stdio.h>


#include "def.h"
#define EXTERN extern
#include "var.h"
  

char flip( double );
double random_c();
void decode( CROMOSOMA, FENOTIPO);


/* test functions */
double f_1(FENOTIPO);
double f_2(FENOTIPO);

/*****************************************************************************
				decode()
*****************************************************************************/
void  decode( CROMOSOMA str, FENOTIPO resul)
{

  int j;
  long powerof2;
  long entero;
  
  switch ( nro_func )
    {
    case 1:  
      resul[0] = resul[1] = 0;
      for ( j = 0; j < no_gens/2; j++) if ( str[j] ) resul[0]++;
      for ( j = no_gens/2; j < no_gens; j++) if ( !str[j] ) resul[1]++;
      break;

      /*
	case 2:
	powerof2 = 1; entero = 0;
	for ( j=0; j < no_gens; j++ )
	{
	if ( str[j] ) entero += powerof2;
	powerof2 *= 2;
	}
	resul[0] = entero;
	break;
      */

      
      /*	 para completar con otras funciones 
		 case 3:
		 case 4:
		 case 5:  etc
      */
    default: printf("Funcion no conocida\n");
      exit(1);
    }
}
/*****************************************************************************
			     objetivo()
*****************************************************************************/

double objetivo( FENOTIPO x )
{
	int i;
	switch( nro_func )
	{
		case 1:  return f_1(x); 
		  /*
		case 2:  return f_2(x);
		  */
		default: printf("Funcion no conocida\n");
			    exit(1);	
	}
}

/***************************************************************************
			   TEST FUNCTIONS
***************************************************************************/



double f_1( FENOTIPO x )
{
  return sin(x[0]) * cos(x[1]);
}

/*
double f_1( FENOTIPO x)
{

   return fabs(pow(sin(x[0]),2.0)  * cos(x[0]) * pow(x[0],2.0)  ) ;
}
*/
/**** f_3:   ****/

/**** f_4:   ****/

/**** f_5:   ****/


/*****************************************************************************
						flip() - Tirar la moneda
*****************************************************************************/
char flip( double pb )
{
	if ( random_c() > pb ) return 0;
		else return 1;
}

/*****************************************************************************
					random_c() valores en [0,1)
*****************************************************************************/
double random_c()
{
	return ( (double) rand() )/ RAND_MAX;
}






