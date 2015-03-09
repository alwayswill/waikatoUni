#!/usr/bin/python
import re
f = re.search('ab', 'abcde')
if f:
	print 'found:', f.group()
else:
	print 'not found'
