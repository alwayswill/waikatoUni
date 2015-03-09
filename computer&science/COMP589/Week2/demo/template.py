#!/usr/bin/python
import re
f = re.search('\\\\', 'ab\cde')
if f:
	print 'found:', f.group()
else:
	print 'not found'
