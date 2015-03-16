#!/usr/bin/python
import re
with open('mountain.html','r') as f:
    htmlFile = f.read()
#tags = re.findall(r'<div id=\"sY\d+\" class=\"sE\".*>(.*)</a>', htmlFile)
tags = re.findall(r'<div id=\"synonym\">(.*)drift', htmlFile, re.M)
for tag in tags:
	print tag
