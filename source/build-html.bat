gradlew.bat html:dist
set results=%errorlevel%
if %results% neq 0 goto error:

end:
echo Files are located html/build/dist
exit /b 0

error:
echo Error, build not successful!
exit /b 1