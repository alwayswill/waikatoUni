#!/usr/bin/python
import re
f = re.findall('</?[\w\d]+>', '<p>how</p><p>are</p><p>you?</p><1>bb</1><H2>aaa</H2><div>cccccccc</div>')

for tag in f:
        print tag
