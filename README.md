# PFNIST

Perfectionist: Conquer your file chaos

Say goodbye to disorganized directories and hello to seamless organization with Perfectionist. This powerful tool effortlessly arranges your files according to your preferences, ensuring you can always find what you need in seconds.
A configuration file should be provided for the tool to be able to organize files as you want them.

## Example of a configuration file

```txt
[files]
audio=mp3,m4a
video=mp4
photo=jpg,jpeg,png
etc=pdf
text=txt

[directories]
audio=./audio
video=./video
photo=./photo
etc=./photo
text=./txt

[actions]
makeDirectories=true
useMove=true
```
The name ot the file must be `pfnst.txt` and it should be placed at the directory that pfnist will be organizing.

After creating the file run the jar in the same folder with
`java -jar path/to/pfnist.jar`