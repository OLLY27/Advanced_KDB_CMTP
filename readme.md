# GIT SETUP   


To clone the repository into your linux envirnoment. run:
``` sh
git clone https://github.com/OLLY27/Advanced_KDB_CMTP
``` 
This will create a folder called "Advanced_KDB_CMTP"

Set the path to this folder as $Advanced_KDB_CMTP using and change permissions:
``` sh
export Advanced_KDB_CMTP="$(dirname "$dir")"
chmod 777 -R Advanced_KDB_CMTP
``` 

## QUESTION 1 - TICK     

### PART 1 - TICKER PLANT  
``` sh
$Advanced_KDB_CMTP/tickerplant.q
```

Manual Commands to run tickerplant

In a linux envirnoment inside "$Advanced_KDB_CMTP" run:
``` sh
export Advanced_KDB_CMTP="$(dirname "$dir")"
export tpPort=5010

q $Advanced_KDB_CMTP/tickerplant.q sym . -p $tpPort
```
___________________________________________________

### PART 2 - RDB 
``` sh
$Advanced_KDB_CMTP/tick/rdbTQ.q
$Advanced_KDB_CMTP/tick/rdbAGG.q
```
Manual Commands for running the Trade Quote and aggregation RDBs

In a linux envirnoment inside "$Advanced_KDB_CMTP" run:
``` sh
export Advanced_KDB_CMTP="$(dirname "$dir")"
export tpPort=5010
q $Advanced_KDB_CMTP/tick/rdbTQ.q localhost:$tpPort

export Advanced_KDB_CMTP="$(dirname "$dir")"
export tpPort=5010
q $Advanced_KDB_CMTP/tick/rdbAGG.q localhost:$tpPort
``` 
___________________________________________________

### PART 3 - FEED HANDLER
``` sh
$Advanced_KDB_CMTP/tick/feedHandler.q
``` 
Manual Commands to run the Feedhandler

In a seperate linux envirnoment inside "$Advanced_KDB_CMTP" run:
``` sh
export Advanced_KDB_CMTP="$(dirname "$dir")"
export tpPort=5010
q $Advanced_KDB_CMTP/tick/feedHandler.q
```
___________________________________________________

### PART 4 - CEP 
``` sh
$Advanced_KDB_CMTP/tick/cep.q
``` 
Manual Commands to run the Complex Event Processor

In a linux envirnoment inside "$Advanced_KDB_CMTP" run:
``` sh
export Advanced_KDB_CMTP="$(dirname "$dir")"
export tpPort=5010
q $Advanced_KDB_CMTP/tick/cep.q localhost:$tpPort
```
### PART 5 - LOGGING  
``` sh
$Advanced_KDB_CMTP/logging.q
``` 
The logging script is here: 
``` sh
$advancedKAdvanced_KDB_CMTPDB/logging.q
```

The logs themselves are located here:
$Advanced_KDB_CMTP/logs

### PART 6 - STARTUP/SHUTDOWN SCRIPTS  
``` sh
$Advanced_KDB_CMTP/scripts/start.sh
$Advanced_KDB_CMTP/scripts/test.sh
$Advanced_KDB_CMTP/scripts/stop.sh
```
To use these scripts, first ensure there are no processes running on the port: 5010
You can always change this port for another in $Advanced_KDB_CMTP/scripts/config.sh
Also in scripts/config.sh, make sure Q (the q home directory) and q (theq executable has been set)

To start the processes run change to the scripts directory (you will see options for yes or no for what you would like to start):
``` sh
bash start.sh
```
To stop the processes run change to the scripts directory  (you will see options for yes or no for what you would like to stop):
``` sh
bash stop.sh
``` 
To test the processes change to the scripts directory and run:
``` sh
bash test.sh
``` 

### PART 7 -TICKERPLANT LOG REPLAY 
``` sh
$Advanced_KDB_CMTP/tpLogReplay.q
``` 
The tickerplant logs will be in starting folder, be sure to change the date to todays date 
``` sh
q tpLogReplay.q sym2023.05.30
``` 
### PART 8 - CSV FILE LOAD 
$Advanced_KDB_CMTP/CSVFileLoader.q

To load a new csv file into the TP
q CSVReader.q [table] [csv file] [RDB port number]
``` sh
q CSVFileLoader.q trade trade.csv 5011
``` 
### PART 9 - EOD Process 
$Advanced_KDB_CMTP/tick/hdbEOD.q
```sh
Move into the tick dir: $Advanced_KDB_CMTP/tick/
```
To run the EOD process and update the HDB

Change the sym date in the command to todays date 
``` sh
q hdbEOD.q ../sym2023.05.30
``` 
When using the previously created bash scripts, as the sym dated file will be created in the same directory as the scripts it runs in, the command will look like this:
``` sh
q hdbEOD.q ../scripts/sym2023.05.29
```

### Answer to Exercise 1, Question 10

Sequence to update the schema:
1. Stop the RDB and tickerplant processes
2. Change the tp log file 
3. Restart the processes to make sure that the new schema change is picked update
4. The HDB needs to be reformatted in order to take into account the schema change. This can be done with a 
    dbmaint.q script that can add the necessary columns to the tables. The .Q.chk function can then be used to
    check the database integrity across partitions.

## Exercise 2
The solutions to each question in Exercise 2 are in txt files in the `exercise2` directory

## Exercise 3
For the feed to work, the tickerplant must be running
### Java Feed
For the java API navigate to:
```sh
cd Advanced_KDB_CMTP/Q3/javaApi/
```
Change the hostname and port and csv filepath to match the setup you have been using in Advanced_KDB_CMTP/Q3/javaApi/src/main/java/kx/examples/Feed.java
```sh
mkdir -p build/classes
javac -d build/classes src/main/java/kx/c.java
javac -d build/classes -cp build/classes src/main/java/kx/examples/Feed.java
java -cp build/classes kx.examples.Feed
```
### Websocket - HTML 5

#### Note: the following needs to be run on a local host (not GCP) and not on an FD laptop (a personal one). 

With the tickerplant and trade quote rdb running, edit the hostname to the one you are using in: 
```sh
$Advanced_KDB_CMTP/Q3/webInterface/simpleWebPage.html
```

Open simpleWebPage.html in a browser (Chrome or Edge), connect (to the RDB) and type in the name of the ticker you are searching (for e.g. KX or GS.N) and click send and results will be returned. 

The connection can then be closed and when closed it will return no results.


