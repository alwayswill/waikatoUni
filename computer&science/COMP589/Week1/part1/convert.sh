file_list=`ls *.gif`
for infile in $file_list
do 
	outfile=`echo $infile | sed "s/.gif/.png/"`
	echo "Converting file $infile into $outfile"
	convert $infile $outfile
done
