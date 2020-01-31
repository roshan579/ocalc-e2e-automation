@echo off
title ChromeDriver cleanup
set JAVA_HOME=D:\ACP\Tools\jdk1.8.0_91
set M2_HOME=D:\ACP\Tools\apache-maven-3.3.9
set PATH=%JAVA_HOME%\bin;%M2_HOME%\bin;%PATH%;
taskkill /im chromedriver.exe /f
taskkill /im chrome.exe /f
exit /b 0

