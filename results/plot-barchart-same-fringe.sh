#!/usr/bin/env bash
# Usage: ./plot-barchart-same-fringe.sh
# Produces: bench_same_fringe.svg

# Input file
input_file="SameFringeResults.txt"
output_file="bench_same_fringe.svg"

# Check file exists
if [[ ! -f "$input_file" ]]; then
  echo "Error: File not found: $input_file"
  exit 1
fi

# Extract Benchmark and Score values from JMH format
# Format: Benchmark                         (COLLECTION_SIZE)  Mode  Cnt    Score    Error  Units
awk '
  /^[a-z]/ && !/^Benchmark/ {
    benchmark=$1
    score=$5
    # Capitalize first letter for display
    benchmark=toupper(substr(benchmark,1,1)) substr(benchmark,2)
    # Handle special case for java-streams
    if ($1 == "java-streams") {
      benchmark="Java-streams"
    }
    if (score > 0) {
      print benchmark, score
    }
  }
' "$input_file" > raw_data.txt

# Find Adhoc baseline
baseline=$(awk '$1 == "Adhoc" {print $2}' raw_data.txt)

if [[ -z "$baseline" ]]; then
  echo "Error: Could not find 'Adhoc' in $input_file"
  exit 1
fi

# Calculate relative throughput: (baseline/method_mean) * 100
awk -v base="$baseline" 'NF == 2 && $2 > 0 { 
  method=$1
  score=$2
  relative=(base/score)*100
  printf "%s %.0f\n", method, relative
}' raw_data.txt | grep -v "^Adhoc" > plot_data.txt

# Generate gnuplot script
gnuplot <<-'EOF'
  set terminal svg size 800,500 enhanced font 'Arial,12'
  set output "bench_same_fringe.svg"
  set title "Same Fringe Benchmark - Relative Throughput (Adhoc = 100%)" font ",14"
  set style data histograms
  set style histogram clustered gap 1
  set style fill solid 0.8 border -1
  set ylabel "Relative Throughput (%)" font ",12"
  set grid ytics
  set xtics rotate by -45 font ",10"
  set yrange [0:100]
  set key off
  set boxwidth 0.8
  
  plot 'plot_data.txt' using 2:xtic(1) lc rgb "#2E86AB" title "", \
       '' using ($0):($2+3):(sprintf("%.0f%%",$2)) with labels font "Arial-Bold,10" tc rgb "#2E86AB" notitle
EOF

# Cleanup temporary files
rm -f raw_data.txt plot_data.txt

echo "✅ Same Fringe bar chart generated: ${output_file}"
