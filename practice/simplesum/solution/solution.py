#!/bin/env python3
import sys

with open(sys.argv[1], 'r') as f:
  print(sum(map(int, f.readlines()[2:])))
