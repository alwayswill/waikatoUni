


function timeSpeaker {
	myHour=$(date +"%H")	
	# myHour=$1	#debug
	if [[ $myHour -le 12 ]]; then
		msg="Good Morning"
	elif [[ $myHour -gt 12 && $myHour -le 18 ]]; then
		msg="Good Afternoon"
	elif [[ $myHour -gt 18 && $myHour -le 24 ]]; then
		msg="Good Evening"
	else
		currentTime=$(date +"%H:%M:%S")
		msg="Current time is $currentTime"
	fi
	
	echo $msg
	exit	
}
# debug
# timeSpeaker $1
timeSpeaker