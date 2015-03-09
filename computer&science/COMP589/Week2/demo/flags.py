#!/usr/bin/python
import re
#f = re.search('abc','aaAbcd', re.I)
#f = re.search('.*','aaAbcd\nd')
#f = re.search('.*','aaAbcd\nd', re.S)
f = re.search('abc$','aaAbcd\nd')
f = re.search('abc$','aaAbcd\nd', re.M|re.I)
if f:
	print 'found:', f.group()
else:
	print 'not found'
