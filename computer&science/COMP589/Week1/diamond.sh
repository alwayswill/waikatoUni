#!/bin/bash
for (( i = 0; i < 6; i++ )); do
	padLength=$((6 + $i))
	str=$(printf '%0.s .' $(seq 1 ${i-1}))
	# echo $str
	printf "%${padLength}s\n" $str
done