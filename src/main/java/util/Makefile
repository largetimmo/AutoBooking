linux: util_ScriptExecutor.h middleware.c
	gcc -fPIC -I /usr/lib/jvm/java-8-openjdk-amd64/include/ -I /usr/lib/jvm/java-8-openjdk-amd64/include/linux/ -o libmiddleware.so -shared middleware.c
mac: util_ScriptExecutor.h middleware.c
	gcc -fPIC -I /Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/include -I /Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/include/darwin -o mac.so -shared middleware.c
clean:
	rm *.so
