#!/bin/bash
file_list=`ls`

case $1 in
	"-l" )
		toUp=false
		;;
	"-u" )
		toUp=true
		;;
	*)
		toUp=false
		echo "sh convertFileNames.sh -l (lowercase) or -u (upcase)";
		exit;
		;;
esac

for file in $file_list; do
	if [[ "$file" != $0 ]]; then
		if $toUp; then
			echo "111"
			newFile=`echo $file | tr '[:lower:]' '[:upper:]'`
		else
			newFile=`echo $file | tr '[:upper:]' '[:lower:]'`
		fi
		echo "Converting $file to "$newFile
		mv $file $newFile
	fi
done
