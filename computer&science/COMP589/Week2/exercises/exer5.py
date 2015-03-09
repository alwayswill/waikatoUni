#!/usr/bin/python
import re
with open('mountain.html','r') as f:
    htmlFile = f.read()
tags = re.findall(r'<a.*[href]+">', htmlFile, re.I)
for tag in tags:
	print "";
	print tag

