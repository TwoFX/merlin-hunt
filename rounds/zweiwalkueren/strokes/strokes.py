#!/bin/env python3
from cihai.core import Cihai
import sys

cihai = Cihai()

if not cihai.unihan.is_bootstrapped:  # download and install Unihan to db
    cihai.unihan.bootstrap(None)

def strokecount(ch):
  if ch == 'の':
    result = 1
  else:
    query = cihai.unihan.lookup_char(ch)
    glyph = query.first()
    result = int(glyph.kTotalStrokes)

  print(ch, result, file=sys.stderr)
  return result

print(sum(map(strokecount, '看板娘の悪巫山戯')))
