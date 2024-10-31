diskLog="disk_info.log" > "$diskLog"
memCpuLog="mem_cpu_int.log" > "$memCpuLog"
echo "Disk info:"
echo "Disk Space: $(df -h ~ | awk '{print $2}' | grep '[0-9]')" >> $diskLog
echo "Disk Usage: $(df -h ~ | awk '{print $3}' | grep '[0-9]')" >> $diskLog
echo "Directories and Subdirectories Usage:" >> $diskLog
du -h --max-depth=1 ~ >> $diskLog
memorytotal=$(free | grep Mem | awk '{print $2}')
memoryused=$(free | grep Mem | awk '{print $3}')
memoryfree=$(free | grep Mem | awk '{print $4}')
echo "Memory Usage:" >> $memCpuLog
echo "The precentage of the total memory used is: $((memoryused*100/memorytotal))%" >> $memCpuLog
echo "The precentage of the total free memory space is: $((memoryfree*100/memorytotal))%" >>$memCpuLog
 #the file that have the cpu model name is /proc/cpouinfo and it appears twice ---> grep and only take it once
 echo "CPU model: $(grep -m 1 'model name' /proc/cpuinfo | awk -F ': ' '{print $2}') " >> $memCpuLog
 echo "Number of CPU cores: $(grep -m 1 'cpu cores' /proc/cpuinfo | awk -F ': ' '{print $2}')" >> $memCpuLog
 cat $diskLog $memCpuLog
