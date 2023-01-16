#!/bin/env python3
import sys, random

infile = sys.argv[1]
outfile = sys.argv[2]

random.seed(116)

with open(sys.argv[1], 'r') as infile, open(sys.argv[2], 'w') as outfile:
  lines = infile.readlines()
  random.shuffle(lines)
  outfile.writelines(lines)
