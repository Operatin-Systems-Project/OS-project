#!/bin/bash

sleep 3600
rm tmp 2>>/dev/null
logfile="/home/ali/Desktop/logs/process_info_$(date +"%F_%H:%M:%S").log"
echo "Process tree:" >> $logfile
pstree >> $logfile 
echo "Zombie processes:" >> $logfile
ps -eo pid,stat,comm | grep Z >> $logfile
echo "Processes CPU usage:" >> $logfile
ps -eo pid,comm,%cpu --sort=-%cpu >> tmp
cat tmp >> $logfile
echo $cpu_proceses >> $logfile
echo "Processes Memory usage:" >> $logfile
ps -eo pid,comm,%mem --sort=-%mem >> $logfile
echo "Top 5 resource consumers (based on %CPU):" >> $logfile
head -n 6 tmp >> $logfile
rm tmp 2>>/dev/null

