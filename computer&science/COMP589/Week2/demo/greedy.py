#!/usr/bin/python
import re
#f = re.search('<p>.*</p>', '<p>how</p><p>are</p><p>you?</p>')
#f = re.search('<p>.*?</p>', '<p>how</p><p>are</p><p>you?</p>')
#f = re.search('(<p>.*?</p>)', '<p>how</p><p>are</p><p>you?</p>')

f = re.findall('(<p>.*?</p>)', '<p>how</p><p>are</p><p>you?</p>')

for tag in f:
	print tag
