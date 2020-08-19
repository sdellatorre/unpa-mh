/*****************************/
/* Herramientas Inteligentes */
/*****************************/


/*****************************************************************************
			"Include" files
*****************************************************************************/

#include <stdlib.h>
#include <math.h>
#include <time.h>
#include <stdio.h>
#include <string.h>


#include "def.h"
#define EXTERN extern
#include "var.h"


/****************************************************************************
			Prototypes
****************************************************************************/
int seleccion(POBLACION);
int tour_sel(POBLACION);

void cross_1pt(CROMOSOMA, CROMOSOMA, CROMOSOMA , CROMOSOMA );
void cross_2pt(CROMOSOMA, CROMOSOMA, CROMOSOMA , CROMOSOMA );

ALELO mutacion(ALELO);

void generacion(void);
void gen_SS(void);

void decode( CROMOSOMA, FENOTIPO);
double objetivo(FENOTIPO);

char flip(double);
double random_c(void);

void estadisticas( POBLACION );


/****************************************************************************
				  tour_sel()              
****************************************************************************/
int tour_sel(POBLACION pop)
{

  int ind1, ind2;

  ind1 = random_c() * popsize;
  do
    {
      ind2 =  random_c() * popsize;
    }
  while ( ind1 == ind2 );

  return ( pop[ind1].fitness > pop[ind2].fitness )? ind1:ind2;
}



/*****************************************************************************
				seleccion()
*****************************************************************************/
int seleccion(poblac)
POBLACION poblac;
{
  
  double rnd, parcsum;
  int j;
  
  parcsum = 0.0; j = -1;
  rnd = random_c() * sumfitness;
  do
    {
      j++;
      parcsum += poblac[j].fitness; 
    }
  while (parcsum < rnd);

  return j;
  
} /* de seleccion */

/*****************************************************************************
				crossover1()
*****************************************************************************/
void cross_1pt( padre1, padre2, hijo1, hijo2)
CROMOSOMA padre1, padre2;
CROMOSOMA hijo1, hijo2;
{

    int j, jcross;

    if ( flip(pcross) ){    /* Hace el crossover con p(cros) */

	 jcross = random_c()*(no_gens-1); /* rnd(1, no_gens - 1) */
	 }
    else
	  jcross = no_gens - 1;


    /* primer intercambio */

    for(j=0; j<= jcross;j++) {
	hijo1[j] = mutacion( padre1[j] );
	hijo2[j] = mutacion( padre2[j] ); 
    }

    /* segundo intercambio */

    if (jcross < no_gens - 1)
	for (j=jcross+1; j<no_gens; j++) {
		hijo1[j] = mutacion( padre2[j] );
		hijo2[j] = mutacion( padre1[j] );
	}

}

/*****************************************************************************
				crossover2()
*****************************************************************************/
void cross_2pt( padre1, padre2, hijo1, hijo2 )
CROMOSOMA padre1, padre2;
CROMOSOMA hijo1, hijo2;
{

    int j,icross,jcross;

    if ( flip(pcross) ){    /* Hace el crossover con p(cros) */

	 icross = random_c() * (no_gens-1); /* rnd(1, no_gens) */
	 if (icross == no_gens - 1)  jcross = icross;
		else jcross = random_c()*(no_gens-icross)+icross;
	 }
	 else
	 {
	  icross = no_gens ;
	  jcross = no_gens - 1;
	 }


    /* primer intercambio */

    if ( icross > 0 )
	    for(j=0; j<icross;j++) {
			hijo1[j] = mutacion( padre1[j] );
			hijo2[j] = mutacion( padre2[j] );
	    }



    /* segundo intercambio (crossover) */

    for (j=icross; j<jcross; j++) {
	hijo1[j] = mutacion( padre2[j] );
	hijo2[j] = mutacion( padre1[j] );
    }

    /* tercer intercambio */

    if ( jcross < no_gens )
	    for(j=jcross; j<no_gens;j++) {
			hijo1[j] = mutacion( padre1[j] );
			hijo2[j] = mutacion( padre2[j] );
	    }
}

/*****************************************************************************
				mutacion()
*****************************************************************************/
ALELO mutacion( ALELO alelo)
{
	if ( flip(pmutacion) ) 
           return ( 1 ^ alelo );  /* cambia valor de alelo */
	else  return alelo;       /* sin cambio */
}


/*****************************************************************************
				generacion()
*****************************************************************************/
void generacion(void)
{
  
  int j;
  int mate1, mate2;  /* mate: progenitor */
  
  j = 0;
  do 
    {
      mate1 = ( sel == PROP )?seleccion(oldpop) : tour_sel(oldpop); 
      mate2 = ( sel == PROP )?seleccion(oldpop) : tour_sel(oldpop);       
      switch ( cross_tipo ) 
	{
	  
	case 1: cross_1pt(
			  oldpop[mate1].crom, oldpop[mate2].crom,
			  newpop[j].crom, newpop[j+1].crom);
	break;
	
	case 2: cross_2pt(
			  oldpop[mate1].crom, oldpop[mate2].crom,
			  newpop[j].crom, newpop[j+1].crom);
	break;

	default: 
	  printf("\nTipo de crossover no conocido\n");
	  exit(1);
	  
	}
      /* obtiene fenotipo, funcion objetivo y fitness para los nuevos
	 individuos */
      
	  
      decode( newpop[j].crom, newpop[j].x );
      newpop[j].objetivo = objetivo( newpop[j].x );
      
      decode( newpop[j+1].crom, newpop[j+1].x);
      newpop[j+1].objetivo = objetivo( newpop[j+1].x );
      
      j += 2;
      
    }
  while ( j < popsize );
  
}

/*****************************************************************************
				inicializa()
*****************************************************************************/
void inicializa()
{
  int i,j;
  
  for (i=0; i<popsize; i++) 
    {
      for(j=0; j<no_gens; j++)
	oldpop[i].crom[j]= flip(0.5);

      decode( oldpop[i].crom, oldpop[i].x);
      oldpop[i].objetivo = objetivo(oldpop[i].x);
    }
}



/*****************************************************************************
				estadisticas()
*****************************************************************************/
void estadisticas(POBLACION pop)
{
  
  int j;
  int p;
  
  
  sumfitness = max = min = avg = pop[0].fitness = pop[0].objetivo;
  opt = worst = 0;
  

  for ( j=1 ; j < popsize ; j++ ) {
    
    pop[j].fitness = pop[j].objetivo;  /* si maximiza, fitness es 
						igual al objetivo */
    
    sumfitness += pop[j].fitness;
    
    if ( min > pop[j].objetivo )  { min = pop[j].objetivo; worst = j; }
    if ( max < pop[j].objetivo )  { max = pop[j].objetivo; opt = j; }
    avg += pop[j].objetivo;
    
  
    
  }
  avg = avg / popsize;

  if ( ver )  // muestra a lo sumo 20 individuos
      {
	  for(j=0; j < popsize; j++)
	  {
	      if ( j < 20 )
	      {
		  printf("%3d:  ", j+1);
		  for( p=0; p < no_gens; printf("%d", pop[j].crom[p++]) );
		  printf("  --> %lf\n", pop[j].objetivo);
	      }
	  }
	  
	  printf("Enter\n"); getchar(); 
	  
      }
  
}


/****************************************************************************
				gen_SS()
****************************************************************************/
void gen_SS()
{
  int i,j,ind;
  int mate1, mate2;  /* mate: progenitor */
  CROMOSOMA child1, child2;
  char *ptr_ind, *ptr_child;
  
  
  mate1 = ( sel == PROP )?seleccion(oldpop) : tour_sel(oldpop); 
  do
    {
      mate2 = ( sel == PROP )?seleccion(oldpop) : tour_sel(oldpop); 
    }
  while ( mate1 == mate2 );
  
  switch( cross_tipo )
    {
      
    case 1: 
      cross_1pt(
		oldpop[mate1].crom, oldpop[mate2].crom,
		child1, child2);
      break;
    
    case 2: 
      cross_2pt(
		oldpop[mate1].crom, oldpop[mate2].crom,
		child1, child2);
      break;

    default:
      printf("unkwon crossover type (%d) in 'gen_SS'\n", cross_tipo);
      exit(0);
    }


  ptr_child = (char*) (flip(0.5) ? child1: child2);
  ind = random_c() * popsize;
  ptr_ind = (char*)oldpop[ind].crom;

  /* One individual is replaced by the new one */
  for (i=0; i<sizeof(CROMOSOMA);i++) *ptr_ind++ = 
				       *ptr_child++;

  decode(oldpop[ind].crom, oldpop[ind].x);
  oldpop[ind].objetivo = objetivo( oldpop[ind].x );


}
