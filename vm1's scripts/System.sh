diskLog="disk_info.log" > "$diskLog"
memCpuLog="mem_cpu_int.log" > "$memCpuLog"
df -h ~ >> $diskLog
memorytotal=$(free | grep Mem | awk '{print $2}')
memoryused=$(free | grep Mem | awk '{print $3}')
memoryfree=$(free | grep Mem | awk '{print $4}')
echo "Memory Usage:" >> $memCpuLog
echo "The precentage of the total memory used is: $((memoryused*100/memorytotal))%" >> $memCpuLog
echo "The precentage of the total free memory space is: $((memoryfree*100/memorytotal))%" >>$memCpuLog
 #the file that have the cpu model name is /proc/cpouinfo and it appears twice ---> grep and only take it once
 echo "$(grep -m 1 'model name' /proc/cpuinfo)" >> $memCpuLog
 echo "$(grep -m 1 'cpu cores' /proc/cpuinfo)" >> $memCpuLog
 cat $diskLog $memCpuLog
