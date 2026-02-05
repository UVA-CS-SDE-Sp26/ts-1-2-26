TopSecret documentation User Interface
======================================	

Run from command:
>java topsecret

## With no arguments

Run with no arguments:
>java topsecret
<p>list the numbered files available to display
eg:
01 filea.txt
02 fileb.txt
03 filec.txt

program exits
</p>

## With only one argument

the program uses the default key for deciphering

### With a number as argument

Run with number argument:
>java topsecret 01
<p>contents of corresponding deciphered file displayed on screen

program exits
</p>

## With two arguments

the second argument specifies an alternate key for deciphering

Run with two arguments:
>java topsecret 01 altcipher
<p>contents of corresponding deciphered file displayed on screen using altcipher

program exits
</p>

## If arguments are not valid

<p> print error
</p>