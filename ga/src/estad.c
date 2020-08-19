#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <malloc.h>


main(int argc, char** argv, char** env)
{
  FILE *fp;            /* file descriptor */
  float value;         /* found value */
  double best=-1000000000000.0;        /* For maximization problems!!!!! */ 
  double worst=1000000000000.0;
  float accum=0, avg, devstd, ti_accum, gen_accum; /* for 
statistics */
  int ti;              /* time */
  int vars=0;          /* size of the sample */
  int gen;             /* number of generation in which the 
best 
			  value was found */
  int i;
  float x[200];        /* to keep the xi's values of the 
sample */


  /*
  while ( env[i] )
    printf("%s\n", env[i++]);
  
  */
  
  fp = fopen("sal", "r");  /* sal: file containing the 
results */
 
  while ( !feof( fp ) )
    {
      fscanf(fp, "#");
      fscanf(fp, "%f", &value);
      fscanf(fp, "%d", &ti);
      fscanf(fp, "%d\n", &gen);

      accum += value;
      x[vars]=value;
      if ( best < value ) best = value;
      if ( worst > value ) worst = value;

      ti_accum += ti;
      gen_accum += gen;

      vars++;
    }
  avg = accum / vars;

  accum = 0;
  for (i=0; i<vars;i++)
    accum += pow( x[i] - avg , 2.0); 
  devstd = sqrt( accum / ( vars - 1 ) );

  /* obtains the standard deviation */


  printf("best: %2.7f, worst: %2.7f, avg: %2.7f, devstd: %2.7f, coef_var: %2.7f\n", best, worst, avg, devstd, devstd/avg);
  printf("ti-avg: %lf, gen-avg: %lf\n", ti_accum/vars, 
gen_accum/vars);
}





