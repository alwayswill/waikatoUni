#!/usr/bin/python
import re
from collections import OrderedDict
class myRex:
	def __init__(self):
		self.rexs = OrderedDict({
			'Literal': 'mark',
			'ab': 'abcd', 
			'[+]': 'ab+cde', 
			'[[]': 'ab[cde', 
			'.': 'ab[cde', 
			'\\\\': 'ab\cde', #you cannot use \\ because it should escape 3 times to get \
			'\\\\': 'ab\cde', 
			'cf|cd': 'abcd', 
			'[0-9]': 'abc1d2ef', 
			'[ew]': 'abc1d2ef', 
			'[e-w]': 'abc1def', 
			#Shorthand C
			'Shorthand': 'mark',
			'\d': 'abc1def', 
			'\w': 'abc1def', 
			'\s': 'abc1def', 
			'^b': 'abc1def', 
			'c': 'abc1def', 
			'^c': 'abc1def', 
			'^a': 'abc1def', 
			'c$': 'abc1def', 
			'f$': 'abc1def', 
			#Repetition
			'Repetition': 'mark',
			'a?': 'aaaabbb', 
			'a?b': 'aaaabbb', 
			'a?b': 'bbb', 
			'a*': 'aaaabbb', 
			'a*': 'bbb', 
			'a*b': 'aaaabbb', 
			'a?b': 'aaaabbb', 
			'a*b*': 'aaaa', 
			'a*b+': 'aaaa', 
			'a{4}b': 'caabef', 
			'a{2}b': 'caabef', 
			'a{2,4}b': 'caabef', 
			'a{2,3}b': 'caaaabef', 
			'Negated': 'mark',
			'[^b]+': 'aaabbb',
			'[^ac]+': 'aaabbbccc',
			'((a)b)c': 'aaabcd'
			})

	def exeReg(self):
		for key, value in self.rexs.items():
			if value == 'mark':
				print '-'*60
				print key
				print '-'*60
			else:
				f = re.search(key, value)
				msg = key+':'+value
				if f:
					print msg , '----------->found:', f.group()
				else:
					print msg, '----------->not found'
			
			
	def showRes(re):
		print 'showRes'
		

r = myRex()
r.exeReg()
