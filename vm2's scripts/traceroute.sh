    echo "routing table"route -n
    echo "hostname"hostname
    echo "testing DNS server"
    LOCAL_DNS=$(grep 'nameserver' /etc/resolv.conf | awk '{print $2}' | head -n 1)
   nlookup google.com $LOCAL_DNS
    echo "Tracing route to google.com"traceroute google.com
    ping -c 1 google.com &> /dev/null
    


if !traceroute -m 30 $1 > /dev/null;then 
sudoroot
fi
