/*****************************/
/* Herramientas Inteligentes */
/*****************************/

/* plot.c: based on the GENEsYs package */

#include <stdio.h>
#include <string.h>


#define	G_GNUPLOT	"gnuplot"
#define G_DATSTYL	"set data style lines\n"
#define G_SAMPLES	"set samples 2000\n"
#define G_LOGSTYL	"set nologscale y\n"
#define G_PAUSE		"pause 1\n"
#define G_XTITLE	"set xlabel \"Generation\"\n"
#define G_YTITLE	"set ylabel \"Obj Val - function test: %d \"\n"
#define G_TITLE         "set title \"Best Value So Far: %lf\"\n"


	/*
	 *	Initialize the pipe.
	 */


FILE *initGpp( int nro_func) /* parameter structure */

{	FILE	       *fp;
	char		Msg[100];
	void		Error();

	if ((fp = popen(G_GNUPLOT, "w")) == NULL) {
		printf("initGpp: Couldn't open pipe\n");
		
	}
	fprintf(fp, G_LOGSTYL);
	fprintf(fp, G_DATSTYL);
	fprintf(fp, G_SAMPLES);
	fprintf(fp, G_XTITLE);
	fprintf(fp, G_YTITLE, nro_func );
	fflush(fp);

	return(fp);

} /*** end initGpp ***/



	/*
	 *	Actualize the data plot.
	 */

void plotGpp( FILE * fp, double best)
{

/*******
	fprintf(fp, "plot \"out\" using 1:8 title \"Best\"\n");
 ******/
 
  fprintf(fp, G_TITLE, best);

  fprintf(fp, "plot \"out\" using 1:2 title \"Worst\" w l 1,\\\n "); 
  fprintf(fp, "\"out\" using 1:3 title \"Avrg\" w l 2,\\\n");
  fprintf(fp, "\"out\" using 1:4 title \"Best. Gen.\" w l 3\n");

    

  fflush(fp);
  
} /*** end plotGpp ***/



/*

  Save the plot in a postscript file 

*/

SavePlot( FILE *fp, double best)

{

  fprintf(fp,"set terminal postscript\n");
  fprintf(fp,"set output \"plot_out\"\n");

  fprintf(fp, G_TITLE, best);

  fprintf(fp, "plot \"out\" using 1:2 title \"W\" w l 1,\\\n "); 
  fprintf(fp, "\"out\" using 1:3 title \"A\" w l 2,\\\n");
  fprintf(fp, "\"out\" using 1:4 title \"BCG\" w l 3\n");

    

  fflush(fp);



}
/*
 *	Quit the plot program.
 */

void
quitGpp(		
			fp)	/* parameter structure */
FILE		       *fp;		/* pipe file pointer */

{
	fprintf(fp, G_PAUSE); 
	fflush(fp);	
	pclose(fp);

} /*** end quitGpp ***/


/*** end of file ***/











