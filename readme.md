///////////////////////////////////////////////////////////////////////////////////////
///////////////////     GIT SETUP   /////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////

To clone the repository into your linux envirnoment. run:

git clone https://github.com/rclerkin97/advancedKDB.git

This will create a folder called "advancedKDB"

Set the path to this folder as $advancedKDB using:

export advancedKDB="$(dirname "$dir")"

/////////////////////////////   Created by Ross Clerkin   /////////////////////////////
/////////////////////////////     QUESTION 1 - TICK       /////////////////////////////

/////////////////////////////    PART 1 - TICKER PLANT   /////////////////////////////

GCP Directory 
/home/rclerkin_kx_com/submissionV2/advancedKDB

$advancedKDB/tickerplant.q

Manual Commands to run tickerplant

In a linux envirnoment inside "$advancedKDB" run:

export advancedKDB="$(dirname "$dir")"
export tpPort=5000
echo $tpPort
5000

q $advancedKDB/tickerplant.q sym . -p $tpPort

___________________________________________________

/////////////////////////////   PART 2 - RDB   /////////////////////////////
$advancedKDB/tick/rdbTQ.q
$advancedKDB/tick/rdbAGG.q

Manual Commands for running the Trade Quote and aggregation RDBs

In a linux envirnoment inside "$advancedKDB" run:
export advancedKDB="$(dirname "$dir")"
export tpPort=5000
echo $tpPort
5000
q $advancedKDB/tick/rdbTQ.q localhost:$tpPort

export advancedKDB="$(dirname "$dir")"
export tpPort=5000
echo $tpPort
5000
q $advancedKDB/tick/rdbAGG.q localhost:$tpPort

___________________________________________________

//////////////////////////   PART 3 - FEED HANDLER  /////////////////////////////
$advancedKDB/tick/feedHandler.q

Manual Commands to run the Feedhandler

In a seperate linux envirnoment inside "$advancedKDB" run:
export advancedKDB="$(dirname "$dir")"
export tpPort=5000
echo $tpPort
5000
q $advancedKDB/tick/feedHandler.q

___________________________________________________

//////////////////////////   PART 4 - CEP  /////////////////////////////
$advancedKDB/tick/cep.q

Manual Commands to run the Complex Event Processor

In a linux envirnoment inside "$advancedKDB" run:
export advancedKDB="$(dirname "$dir")"
export tpPort=5000
echo $tpPort
5000
q $advancedKDB/tick/cep.q localhost:$tpPort

___________________________________________________

//////////////////////////   PART 5 - LOGGING  /////////////////////////////
$advancedKDB/logging.q

The logging script is here: 
$advancedKDB/logging.q

The logs themselves are located here:
$advancedKDB/logs

___________________________________________________

//////////////////////////   PART 6 - STARTUP/SHUTDOWN SCRIPTS  /////////////////////////////
$advancedKDB/scripts/start.sh
$advancedKDB/scripts/test.sh
$advancedKDB/scripts/stop.sh

To use these scripts, first ensure there are no processes running on the port: 5000
You can always change this port for another in $advancedKDB/scripts/config.sh
Also in scripts/config.sh, make sure Q (the q home directory) and q (theq executable has been set)

To start the processes run (you will see options for yes or no for what you would like to start):
bash start.sh
To stop the processes run  (you will see options for yes or no for what you would like to stop):
bash stop.sh
To test the processes run:
bash test.sh

___________________________________________________

//////////////////////////   PART 7 -TICKERPLANT LOG REPLAY  /////////////////////////////
$advancedKDB/tpLogReplay.q

The tickerplant logs will be in starting folder

q tpLogReplay.q sym2023.05.03

___________________________________________________

//////////////////////////   PART 8 - CSV FILE LOAD  /////////////////////////////
$advancedKDB/CSVFileLoader.q

To load a new csv file into the TP
q CSVReader.q [table] [csv file] [port number]

q CSVFileLoader.q trade trade.csv 5000

___________________________________________________

//////////////////////////   PART 9 - EOD Process  /////////////////////////////
$advancedKDB/tick/hdbEOD.q

Move into the tick dir: $advancedKDB/tick/

To run the EOD process and update the HDB

Change the sym date in the command to todays date 

q hdbEOD.q ../sym2023.05.03

When using the previously created bash scripts, as the sym dated file will be created in the same directory as the scripts it runs in, the command will look like this:

q hdbEOD.q ../scripts/sym2023.05.10
___________________________________________________

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
