#!/usr/bin/python
import re
f = re.search('((a)b)c', 'aaabcd')
if f:
	print 'found:', f.group(1)
	print 'found:', f.group(2)
else:
	print 'not found'
