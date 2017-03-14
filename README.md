# mirage [![Build Status](https://travis-ci.org/nkrul/universal-parser.svg?branch=master)](https://travis-ci.org/nkrul/universal-parser)
Java Pre Classloader Reflection

This Project aims to give you all of the 'useful' parts of reflection, without actually needing to load the class.

A paralell class-based wrapper is also provided.

This allows for classes to be inspected without classloading them - and without any static initializers executing.

To Use, use one of the below formats:

    Mirage loadedClassMirage = new ClassReflectionMirage(classReference);
    Mirage unloadedClassMirage = new ClassFormatMirage(inputStreamSource);

