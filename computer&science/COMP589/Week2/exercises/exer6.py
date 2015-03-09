#!/usr/bin/python
import re
with open('mountain.html','r') as f:
    htmlFile = f.read()
htmlFile = re.sub(r'tx', r'TX', htmlFile);
tags = re.findall(r'<div.*class=".*tx.*">', htmlFile, re.I)
for tag in tags:
	print "";
	print tag