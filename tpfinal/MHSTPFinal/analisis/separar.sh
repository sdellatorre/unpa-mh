grep Mejor ../corridas/C1000_G1000_np*.txt| sed 's/:Mej.*lucion /|/' | sed 's/_/|/g' | sed 's/^\.\..*\///'| sed 's/ .*on /|/' | sed 's/|SEL|/|/' > separado.txt
grep Mejor ../corridas/incompletos/C1000_G1000_np*.txt| sed 's/:Mej.*lucion /|/' | sed 's/_/|/g' | sed 's/^\.\..*\///'| sed 's/ .*on /|/' | sed 's/|SEL|/|/' > separado22.txt
