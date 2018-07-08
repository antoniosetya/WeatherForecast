JC = javac
JLIBS = -cp ./lib/json-simple-1.1.1.jar;./lib/junit-4.12.jar
CLASSES = src/com/pyra/weatherforecast/*.java src/com/pyra/weatherforecast/data/*.java
OBJECT1 = src/com/pyra/weatherforecast/*.class
OBJECT2 = src/com/pyra/weatherforecast/data/*.class

all: build run

clean:
	$(RM) $(OBJECT1)
	$(RM) $(OBJECT2)

build:
	$(JC) $(JLIBS) $(CLASSES)

run:
	java -classpath ./lib/json-simple-1.1.1.jar;./src com.pyra.weatherforecast.Main
