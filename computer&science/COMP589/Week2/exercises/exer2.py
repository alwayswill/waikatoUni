#!/usr/bin/python
import re
with open('mountain.html','r') as f:
    htmlFile = f.read()
tags = re.findall('</?.*[=]+.*>', htmlFile)
for tag in tags:
	print tag

