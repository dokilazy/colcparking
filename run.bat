set ProjectPath=%~dp0
cd %projectLocation%
set classpath=%projectLocation%\bin;%projectLocation%\lib\*
java %projectLocation%\bin\main\resources\Homepage.class
pause