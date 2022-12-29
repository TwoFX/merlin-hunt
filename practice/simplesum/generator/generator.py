#!/bin/env python3
import random

random.seed(45)

print('This is not a puzzle, just a practice session problem. The solution is simply the sum of the following numbers:\n')

for i in range(1000000):
  print(random.randrange(10**18))
