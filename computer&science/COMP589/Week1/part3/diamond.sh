#!/bin/sh
i=1
line=7
mark='.'
# up-triangle
# line
while [ $i -le $line ] ; do
	# the number of points plus the number of spaces equal to line number -1 because the first line have no points
	j=1
	while [ $j -le $(($line-$i)) ] ; do
		# -n    Do not print the trailing newline character. 
		echo -n ' '
		j=$(($j+1))
	done
	j=1
	while [ $j -le $(($i-1)) ] ; do
		echo -n " "$mark
		j=$(($j+1))
	done
	printf "\n"
	i=$(($i+1))
done

# down-triangle
# use for to replace while loop

for (( i = $line; i > 0; i-- )); do
	for (( j = 0; j < $(($line-$i)); j++ )); do
		echo -n ' '
	done
	for (( j = 0; j < $(($i-1)); j++ )); do
		echo -n " "$mark
	done
	echo 
done


exit 0