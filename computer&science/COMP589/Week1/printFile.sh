#!/bin/bash
if [[ $# != 3 ]]; then
	echo "usage: printFile.sh startline endline filename"
	exit
fi

sed -n "$1,$2p" $3