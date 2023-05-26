# GIT SETUP   


To clone the repository into your linux envirnoment. run:
``` sh
git clone https://github.com/OLLY27/Advanced_KDB_CMTP
``` 
This will create a folder called "Advanced_KDB_CMTP"

Set the path to this folder as $Advanced_KDB_CMTP using:
``` sh
export Advanced_KDB_CMTP="$(dirname "$dir")"
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
export tpPort=5000

q $Advanced_KDB_CMTP/tickerplant.q sym . -p $tpPort
```
___________________________________________________

### PART 2 - RDB 
$Advanced_KDB_CMTP/tick/rdbTQ.q
$Advanced_KDB_CMTP/tick/rdbAGG.q

Manual Commands for running the Trade Quote and aggregation RDBs

In a linux envirnoment inside "$Advanced_KDB_CMTP" run:
``` sh
export Advanced_KDB_CMTP="$(dirname "$dir")"
export tpPort=5000
q $Advanced_KDB_CMTP/tick/rdbTQ.q localhost:$tpPort

export Advanced_KDB_CMTP="$(dirname "$dir")"
export tpPort=5000
q $Advanced_KDB_CMTP/tick/rdbAGG.q localhost:$tpPort
``` 
___________________________________________________

### PART 3 - FEED HANDLER
$Advanced_KDB_CMTP/tick/feedHandler.q

Manual Commands to run the Feedhandler

In a seperate linux envirnoment inside "$Advanced_KDB_CMTP" run:
``` sh
export Advanced_KDB_CMTP="$(dirname "$dir")"
export tpPort=5000
q $Advanced_KDB_CMTP/tick/feedHandler.q
```
___________________________________________________

### PART 4 - CEP 
$Advanced_KDB_CMTP/tick/cep.q

Manual Commands to run the Complex Event Processor

In a linux envirnoment inside "$Advanced_KDB_CMTP" run:
``` sh
export Advanced_KDB_CMTP="$(dirname "$dir")"
export tpPort=5000
q $Advanced_KDB_CMTP/tick/cep.q localhost:$tpPort
```
### PART 5 - LOGGING  
$Advanced_KDB_CMTP/logging.q

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
To use these scripts, first ensure there are no processes running on the port: 5000
You can always change this port for another in $Advanced_KDB_CMTP/scripts/config.sh
Also in scripts/config.sh, make sure Q (the q home directory) and q (theq executable has been set)

To start the processes run (you will see options for yes or no for what you would like to start):
``` sh
bash start.sh
```
To stop the processes run  (you will see options for yes or no for what you would like to stop):
``` sh
bash stop.sh
``` 
To test the processes run:
``` sh
bash test.sh
``` 

### PART 7 -TICKERPLANT LOG REPLAY 
$Advanced_KDB_CMTP/tpLogReplay.q

The tickerplant logs will be in starting folder
``` sh
q tpLogReplay.q sym2023.05.03
``` 
### PART 8 - CSV FILE LOAD 
$Advanced_KDB_CMTP/CSVFileLoader.q

To load a new csv file into the TP
q CSVReader.q [table] [csv file] [port number]

q CSVFileLoader.q trade trade.csv 5000

### PART 9 - EOD Process 
$Advanced_KDB_CMTP/tick/hdbEOD.q

Move into the tick dir: $Advanced_KDB_CMTP/tick/

To run the EOD process and update the HDB

Change the sym date in the command to todays date 
``` sh
q hdbEOD.q ../sym2023.05.26
``` 
When using the previously created bash scripts, as the sym dated file will be created in the same directory as the scripts it runs in, the command will look like this:
``` sh
q hdbEOD.q ../scripts/sym2023.05.25
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
For all of the feeds to work, the tickerplant must be running
### Python Feed
To run the Python feedhandler, run the following command
``` sh
$ pyq exercise3/python/pythonAPI.py trade.csv
```
Note that python3 and pyq must be installed beforehand

### Java Feed
To run the Java feedhandler, run the following command
``` sh
$ java exercise3/java/CsvApi
```

### Websocket
Start the websocket process
``` sh
$ q exercise3/html/q/websocket.q -p 7013
```
Connect from the browser by navigating to `${ADV_KDB_HOME}/exercise3/html/index.html`
