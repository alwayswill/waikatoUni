#!/usr/bin/python
import re
with open('mountain.html','r') as f:
    htmlFile = f.read()
tags = re.findall(r'<div id=\"sY\d+\" class=\"sE\".*>(.*)</a>', htmlFile)
for tag in tags:
	print tag
