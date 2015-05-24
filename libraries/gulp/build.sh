#!/bin/sh

if [ "$1" == "all" ]; then
	exec gulp --gulpfile gulpfile.js js css
else
	exec gulp --gulpfile gulpfile.js $1 $2
fi