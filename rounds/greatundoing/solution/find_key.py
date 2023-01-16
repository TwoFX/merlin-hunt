#!/bin/env python3
import tempfile, os
from itertools import permutations

parts = [
  "QyNTUxOQAAACAk301w9IHhJiJNiCPSFNZI2JyCcND7TAupRZg9hURWaAAAAKDLnPp+y5z6",
  "b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW",
  "AAAECspzatwyFkGca+rIH+gUfiHBHZVbF8wRHvifbnKMReOyTfTXD0geEmIk2II9IU1kjY",
  "fgAAAAtzc2gtZWQyNTUxOQAAACAk301w9IHhJiJNiCPSFNZI2JyCcND7TAupRZg9hURWaA"]

for perm in permutations(parts):
  p = ["-----BEGIN OPENSSH PRIVATE KEY-----"] + list(perm) + ["nIJw0PtMC6lFmD2FRFZoAAAAHGdpdEBnaXRsYWIubWFya3VzaGltbWVsLmRlOj8B", "-----END OPENSSH PRIVATE KEY-----\n"]
  tf = tempfile.NamedTemporaryFile(mode='w', delete=False)
  tf.write("\n".join(p))
  tf.close()

  status = os.system(f"ssh-keygen -f {tf.name} -y -P \"\"")
  exitcode = os.waitstatus_to_exitcode(status)

  if exitcode == 0:
    print("\n".join(p))

  #os.unlink(tf.name)
