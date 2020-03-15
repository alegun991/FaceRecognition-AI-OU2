# FaceRecognition-AI-OU2

To run the program, first compile all the files:
```
javac *.java
```

Then create a result file which holds the result:

```
java Faces training-A.txt facit-A.txt test-B.txt > result.txt
```

Finally compare the result with the facit:

```
java FaceTest facit-B.txt result.txt
```
