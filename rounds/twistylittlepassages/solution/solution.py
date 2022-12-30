#!/bin/env python3
from sqlalchemy import inspect, create_engine, text
import re

engine = create_engine('postgresql://azurediamond:hunter2@markushimmel.de/postgres')
insp = inspect(engine)

key_pattern = re.compile('^([a-z]*)_that_is_([a-z]*)$')
reference_pattern = re.compile('^([a-z]*)_([a-z]*)$')

tables = insp.get_table_names()

keys = {}

for table in tables:
    columns = insp.get_columns(table)
    for column in columns:
        key_match = key_pattern.match(column['name'])
        if key_match != None:
            reference_column = key_match.group(2) + '_' + key_match.group(1)
            keys[reference_column] = (table, table + '.' + column['name'])

query = 'SELECT cute_elephant.happy_shrimp FROM cute_elephant '

def dfs(table):
  columns = insp.get_columns(table)
  result = ''
  for column in columns:
    name = column['name']
    if name in keys:
      (target_table, ref) = keys[name]
      result = result + f'INNER JOIN {target_table} ON {table}.{name} = {ref} '
      result = result + dfs(target_table)
  return result

query = query + dfs('cute_elephant') + ';'

with engine.connect() as conn:
  for r in conn.execute(text(query)):
    print(r[0])
