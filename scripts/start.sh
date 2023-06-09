#!/usr/bin/bash
#nohup q tickerplant.q sym . -p 5011 1>>tick.log 2>&1 &
#nohup q tick/feedHandler.q 1>>feed.log 2>&1 &
#nohup q tick/rdbTQ.q localhost:5011 1>>newestTQ2.log 2>&1 &
#nohup q tick/cep.q localhost:5011 1>>cep.log 2>&1 &
#nohup q tick/rdbAGG.q localhost:5011 1>>rAGG.log 2>&1 &

source ./config.sh

#Start tickerplant process
echo "Enter y to run the Tickerplant, (y/n)"
read tickbool

if [ "$tickbool" = "y" ]; then
        q $Advanced_KDB_CMTP/tickerplant.q sym . -p $tpPort 1>>$Advanced_KDB_CMTP/logs/tickerplant.log 2>&1 &
fi

#Start trade and qoute RDBs
echo "Enter y to run the Trade/Quote RDB (y/n)?"
read rTQbool

if [ "$rTQbool" = "y" ]; then
        q $Advanced_KDB_CMTP/tick/rdbTQ.q localhost:$tpPort 1>>$Advanced_KDB_CMTP/logs/rdbTQ.log 2>&1 &
fi

#Start Aggregation RDB
echo "Enter y to run the Aggregation RDB (y/n)?"
read rAGGbool

if [ "$rAGGbool" = "y" ]; then
        q $Advanced_KDB_CMTP/tick/rdbAGG.q localhost:$tpPort 1>>$Advanced_KDB_CMTP/logs/rdbAGG.log 2>&1 &
fi

#Start Complex Event Processer/Calculation Engine
echo "Enter y to run the Start CEP (y/n)?"
read cepbool

if [ "$cepbool" = "y" ]; then
        q $Advanced_KDB_CMTP/tick/cep.q localhost:$tpPort 1>>$Advanced_KDB_CMTP/logs/cep.log 2>&1 &
fi

#Start Feedhandler
echo "Enter y to run the Mock Feedhandler (y/n)?"
read feedbool

if [ "$feedbool" = "y" ]; then
        q $Advanced_KDB_CMTP/tick/feedHandler.q 1>>$Advanced_KDB_CMTP/logs/feedhandler.log 2>&1 &
fi
