#!/usr/bin/python
import re
print re.sub('ab', 'cd', 'abefg')
print re.sub(r'a([A-Z])b', r'ab\1', 'aCb')
print re.sub(r'([0-9]+)([a-b]+)', r'\2\1', '12ab')
print re.sub(r'(((a)b)c)', r'\1\2\3', 'abc')
