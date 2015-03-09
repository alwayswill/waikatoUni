#!/usr/bin/python
import re
with open('mountain.html','r') as f:
    htmlFile = f.read()
tags = re.findall(r'<div.*id="[\w\d]*im[\w\d]*">', htmlFile, re.I)
for tag in tags:
	print "";
	print tag

