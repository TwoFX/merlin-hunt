# The Merlin Hunt

The Merlin Hunt was a puzzle hunt for people who know how to program. You can
find more information at [the website](https://static.markushimmel.de/information.html)
and in the [walkthrough video](https://www.youtube.com/watch?v).

This repository contains all the behind-the-scenes infrastructure to make the
Merlin Hunt happen. This includes the puzzles and the logic to assemble the puzzle
archives, code for server components, docker-compose files, scripts for the
judging system, and more.

The code is provided for informational purposes. It's not really possible to
self-host this thing without some serious work since my server is hard-coded in
quite a few places.

## Structure

* `demonstration`: The dummy contest used for the tutorial video
* `deploy`: Server infrastructure: docker-compose files, nginx configuration
* `domjudge`: Scripts used for judging responses using DOMJudge
* `information`: The contest website
* `make`: Makefile infrastructure for automatically creating the problem archive, DOMJudge problems, and Docker images
* `practice`: Puzzles for the practice session
* `rounds`: Problems for the main contest
* `scripts`: Helper script for assembling the problem archive
