# FaceRecognition-AI-OU2

To run the program, first compile all the files:
```
javac *.java
```

Then create a result file which holds the result:

```
java Faces training-file.txt training-facit.txt test-file.txt > result.txt
```

Finally compare the result with the facit:

```
java FaceTest test-facit.txt result.txt
```
