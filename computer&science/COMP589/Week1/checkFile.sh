#!/bin/bash
if [[ $# -eq 0 ]]; then
	echo "No arguments supplied"
	exit
fi

if [[ -f $1 ]]; then
	echo "it exists"
else
	echo "it does not exist"
fi

exit