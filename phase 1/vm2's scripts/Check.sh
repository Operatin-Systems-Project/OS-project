rm temp_perm_change 2>>/dev/null 
find ~/ -perm 777 | tee temp_perm_change.log
while read -r line;
do
	chmod 700 $line >> perm_change.log
	echo $line >> perm_change.log
done < "temp_perm_change.log"
rm temp_perm_change.log