easy-test
===========
[![Build Status](https://travis-ci.org/DANS-KNAW/easy-test.png?branch=master)](https://travis-ci.org/DANS-KNAW/easy-test)

<Remove this comment and extend the descriptions below>


SYNOPSIS
--------

    easy-test \
            <synopsis of command line parameters> \
            <...possibly continued again, or all joined on one line>


DESCRIPTION
-----------

x


ARGUMENTS
---------

    Options:

        --help      Show help message
        --version   Show version of this program

    Subcommand: run-service - Starts EASY Test as a daemon that services HTTP requests
        --help   Show help message
    ---

EXAMPLES
--------

    easy-test -o value


INSTALLATION AND CONFIGURATION
------------------------------


1. Unzip the tarball to a directory of your choice, typically `/usr/local/`
2. A new directory called easy-test-<version> will be created
3. Add the command script to your `PATH` environment variable by creating a symbolic link to it from a directory that is
   on the path, e.g. 
   
        ln -s /usr/local/easy-test-<version>/bin/easy-test /usr/bin



General configuration settings can be set in `cfg/application.properties` and logging can be configured
in `cfg/logback.xml`. The available settings are explained in comments in aforementioned files.


BUILDING FROM SOURCE
--------------------

Prerequisites:

* Java 8 or higher
* Maven 3.3.3 or higher

Steps:

        git clone https://github.com/DANS-KNAW/easy-test.git
        cd easy-test
        mvn install
