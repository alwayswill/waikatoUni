#!/usr/bin/python
import re
with open('mountain.html','r') as f:
    htmlFile = f.read()
tags = re.findall(r'<style[^>]+>[^>]+/style>', htmlFile, re.I)
#tags = re.findall(r'<div[^>]+>[^>]+div>', htmlFile, re.I)
for tag in tags:
	print "";
	print tag