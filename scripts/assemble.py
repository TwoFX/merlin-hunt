#!/bin/env python3
import sys, os

def read_answer(name):
  with open(f'../answers/{name}.ans', 'r') as f:
    ans = f.readlines()
    return ans[0].strip()

def read_inner_answer(name):
  with open(f'../flags/{name}.flag.dj') as f:
    return sum([ord(c) for c in f.readlines()[0][:64]])

def system(command):
  print(command)
  os.system(command)

def zipadd(zipfile, file, droppaths=True):
  system(f'zip{" -j" if droppaths else ""} {zipfile} {file}')

rounds = sys.argv[1:]

def probfile_name(problem):
    return f'../problems/{problem}.zip'

def encrypt(file, answer):
  system(f'gpg -c --batch --passphrase "{answer}" {file}')

for index in range(len(rounds) - 1, -1, -1):
  cur = rounds[index]
  probfile = probfile_name(cur)
  # Goal of this iteration: Get probfile in the final state.

  # Create zip file with flag
  system(f'mkdir -p {cur}')
  probflagfile = f'{cur}/{cur}.flag.zip'
  zipadd(probflagfile, f'../flags/{cur}.flag')

  if index + 1 < len(rounds):
    # Encrpyt the next problem
    next_probfile = probfile_name(rounds[index + 1])
    inner_answer = read_inner_answer(cur)
    encrypt(next_probfile, inner_answer)
    zipadd(probflagfile, next_probfile + '.gpg')

  # Encrypt zip file with flag
  answer = read_answer(cur)
  encrypt(probflagfile, answer)

  # Add to archive
  zipadd(probfile, probflagfile + '.gpg', False)
