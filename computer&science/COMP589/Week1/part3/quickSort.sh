#!/bin/bash

#variable
myArgs=( $* )
argLength=${#myArgs[@]}

#lib

function errorCode {
	case $1 in
		"1")
			msg="3 numbers are required. current:" ${myArgs[*]}
			;;
		2 )
			msg="error"
			;;
		* )
			exit
			;;
	esac
	echo "ERROR (1) :" $msg
	exit
}

if [ $argLength != 3 ]; then
	errorCode "1"
fi

#TO DO
#check is number


for((i=0;i<$argLength;i++))
do
	for((j=$argLength-1;j>$i;j--))
	do
		if [ ${myArgs[$j]} -gt ${myArgs[$j-1]} ]; then
			temp=${myArgs[$j]}
			myArgs[$j]=${myArgs[$j-1]}
			myArgs[$j-1]=$temp
		fi
	done
done

echo "the biggest number is " ${myArgs[0]}