# weblmagent
set the VM Mac address for WebLM

Configure the WebLM tomcat to add option.

put the weblmagent.jar in the tomcat lib folder of WebLM.
add below option to the JAVA_OPT

-javaagent:weblmagent:jar  -DmacAddress=000C29294A2B


000C29294A2B is the MAC address in the license file.
