#! /bin/sh -e

find . -name ".DS_Store" | xargs rm
find ./web/WEB-INF/classes -name *.class | xargs rm
rm -f HessianHelloServer.war

CLASSPATH=./src/:/opt/apache-tomcat-8.0.28/lib/servlet-api.jar:./web/WEB-INF/lib/hessian-4.0.37.jar

javac -cp ${CLASSPATH} -d web/WEB-INF/classes src/org/example/hessian/service/HelloService.java
javac -cp ${CLASSPATH} -d web/WEB-INF/classes src/org/example/hessian/servlet/HelloServlet.java

(cd web && jar cvf ../HessianHelloServer.war .)
