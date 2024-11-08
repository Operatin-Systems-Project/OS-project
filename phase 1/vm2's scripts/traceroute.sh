  Log="network.log"

    echo "routing table" >> $Log
    route -n >> $Log
    echo "hostname" >> $Log
    hostname >> $Log
    echo "testing DNS server" >> $Log
    LOCAL_DNS=$(grep 'nameserver' /etc/resolv.conf | awk '{print $2}' | head -n 1)
    nslookup google.com $LOCAL_DNS >> $Log
    echo "Tracing route to google.com" >> $Log
    traceroute google.com >> $Log
    ping -c 1 google.com >> $Log
    
    traceroute -m 30 "$1" 2>&1 >> $Log
last_line= traceroute -m 30 "$1" 2>&1  | tail -n 1 >> $Log
if echo "$last_line" | grep -q "$1"; then
    echo "Successfully traced route to $1" >> $Log
else
    echo "Unable to reach $1 via traceroute. Rebooting..." >> $Log
    sudo reboot
fi
