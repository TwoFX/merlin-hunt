#!/bin/env python3
import sys, os

readme_message = '''Congratulations on solving the puzzle.

To unlock the next puzzle:
* Upload the *.flag file to DOMJudge.
* Click on the "Correct" verdict in the submissions list.
* In the dialog that opens, look for the "Diff output" section.
* Unlock the encrypted zip file with the passphrase given there.
'''

readme_message_done = '''Congratulations on completing the puzzle hunt!

Please upload the final *.flag file to DOMJudge. After that, you're done.
'''

def write_readme(filename, final):
  with open(filename, 'w') as f:
    f.write(readme_message_done if final else readme_message)

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

  final = index + 1 == len(rounds)

  if not final:
    # Encrpyt the next problem
    next_probfile = probfile_name(rounds[index + 1])
    inner_answer = read_inner_answer(cur)
    encrypt(next_probfile, inner_answer)
    zipadd(probflagfile, next_probfile + '.gpg')

  readme_name = "README.flag.txt"
  write_readme(readme_name, final)
  zipadd(probflagfile, readme_name)


  # Encrypt zip file with flag
  answer = read_answer(cur)
  encrypt(probflagfile, answer)

  # Add to archive
  zipadd(probfile, probflagfile + '.gpg', False)
