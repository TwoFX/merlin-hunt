#!/bin/env python3
import sys, os

def system(command):
  print(command)
  os.system(command)

def zipadd(zipfile, file):
  system(f"zip -j {zipfile} {file}")

rounds = sys.argv[2:]
nexts = [f"problems/{r}.zip" for r in rounds[1:]] + [f"flags/{rounds[-1]}.flag"]
flags = [None] + [f"flags/{r}.flag" for r in rounds[:-1]]

for index in range(len(rounds) - 1, -1, -1):
  cur = rounds[index]
  probfile = f"problems/{cur}.zip"

  # Encrypt the next
  to_encrypt = nexts[index]
  system(f"xargs -I % gpg -c --batch --passphrase \"%\" {to_encrypt} < answers/{cur}.ans")

  # Add to archive
  zipadd(probfile, to_encrypt + ".gpg")

  # If necessary, add flag to archive
  if index > 0:
    zipadd(probfile, flags[index])

# Encrypt start file
initialpass = sys.argv[1]
system(f"gpg -c --batch --passphrase \"{initialpass}\" problems/{rounds[0]}.zip")
