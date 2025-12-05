#!/usr/bin/env bash
# Usage: ./plotbarchart.sh
# Produces: bench_avl_tree.svg

# Input files
equality_file="AvlTreeEqualityResults.txt"
distinct_file="DistinctBenchResults.txt"
sum_file="SumBenchResults.txt"
output_file="bench_avl_tree.svg"

# Check all files exist
for file in "$equality_file" "$distinct_file" "$sum_file"; do
  if [[ ! -f "$file" ]]; then
    echo "Error: File not found: $file"
    exit 1
  fi
done

# Function to extract data and calculate relative throughput
extract_and_calculate() {
  local input_file=$1
  local output_suffix=$2
  
  # Extract Benchmark and Score values from JMH format
  # Format: Benchmark          (TREE_SIZE)  Mode  Cnt     Score      Error  Units
  awk '
    /^[a-z]/ && !/^Benchmark/ {
      benchmark=$1
      score=$5
      # Capitalize first letter for display
      benchmark=toupper(substr(benchmark,1,1)) substr(benchmark,2)
      if (score > 0) {
        print benchmark, score
      }
    }
  ' "$input_file" > "raw_${output_suffix}.txt"
  
  # Find Adhoc baseline (now capitalized)
  local baseline=$(awk '$1 == "Adhoc" {print $2}' "raw_${output_suffix}.txt")
  
  if [[ -z "$baseline" ]]; then
    echo "Error: Could not find 'Adhoc' in $input_file"
    exit 1
  fi
  
  # Calculate relative throughput: (baseline/method_mean) * 100
  awk -v base="$baseline" 'NF == 2 && $2 > 0 { printf "%s %.0f\n", $1, (base/$2)*100 }' "raw_${output_suffix}.txt" > "data_${output_suffix}.txt"
}

# Extract data from all three files
extract_and_calculate "$equality_file" "equality"
extract_and_calculate "$distinct_file" "distinct"
extract_and_calculate "$sum_file" "sum"

# Combine data into single file with columns: Method Equality Distinct Sum
# First, get all unique methods
awk '{print $1}' data_equality.txt data_distinct.txt data_sum.txt | sort -u > methods.txt

# For each method, get values from all three files
while read method; do
  # Skip Adhoc since it's the baseline (always 100%)
  if [[ "$method" == "Adhoc" ]]; then
    continue
  fi
  
  equality=$(awk -v m="$method" '$1 == m {print $2}' data_equality.txt)
  distinct=$(awk -v m="$method" '$1 == m {print $2}' data_distinct.txt)
  sum=$(awk -v m="$method" '$1 == m {print $2}' data_sum.txt)
  
  # Use 0 if value not found
  printf "%s %s %s %s\n" "$method" "${equality:-0}" "${distinct:-0}" "${sum:-0}"
done < methods.txt > combined_data.txt

# Generate gnuplot script
gnuplot <<-'EOF'
  set terminal svg size 800,600 enhanced font 'Arial,12'
  set output "bench_avl_tree.svg"
  set title "Benchmark Comparison - Relative Throughput (Adhoc = 100%)" font ",14"
  set style data histograms
  set style histogram clustered gap 1.5
  set style fill solid 0.7 border -1
  set ylabel "Relative Throughput (%)" font ",12"
  set grid ytics
  set xtics rotate by -45 font ",11"
  set yrange [0:100]
  set nokey
  set offset 0,0,graph 0.15,0
  set boxwidth 0.6
  
  # Calculate bar positions for clustered histogram
  # With thinner bars and larger gap, adjust offsets
  plot 'combined_data.txt' using 2:xtic(1) lc rgb "#1f77b4", \
       '' using ($0-0.27):($2+5):(sprintf("%.0f%%",$2)) with labels font "Arial-Bold,11" tc rgb "#1f77b4" notitle, \
       '' using ($0-0.27):($2/2):("Equality") with labels rotate by 90 font "Arial,10" tc rgb "black" notitle, \
       '' using 3 lc rgb "#ff7f0e", \
       '' using ($0):($3+5):(sprintf("%.0f%%",$3)) with labels font "Arial-Bold,11" tc rgb "#ff7f0e" notitle, \
       '' using ($0):($3/2):("Distinct") with labels rotate by 90 font "Arial,10" tc rgb "black" notitle, \
       '' using 4 lc rgb "#2ca02c", \
       '' using ($0+0.27):($4+5):(sprintf("%.0f%%",$4)) with labels font "Arial-Bold,11" tc rgb "#2ca02c" notitle, \
       '' using ($0+0.27):($4/2):("Sum") with labels rotate by 90 font "Arial,10" tc rgb "black" notitle
EOF

# Cleanup temporary files
rm -f raw_*.txt data_*.txt methods.txt combined_data.txt

echo "✅ Combined bar chart generated: ${output_file}"
