#!/usr/bin/python
import re
with open('mountain.html','r') as f:
    htmlFile = f.read()
tags = re.findall('<[\w\d]{2,}>.*?</[\w\d]+>', htmlFile)
for tag in tags:
	print "";
	print tag

