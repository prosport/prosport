
IF "%1"=="all" (
	gulp --gulpfile gulpfile.js js css
) ELSE (
	gulp --gulpfile gulpfile.js %1 %2
)