#!/bin/bash
recipient="aa2204061@qu.edu.qa"
output_file="bigfile"
search_date=$(date +"%Y-%m-%d %H:%M:%S")
results=$(find ~ -type f -size +1M 2> /dev/null)
file_count=$(echo "$results" |wc -l )
{
  echo "Search Date: $search_date"
  echo "Number of files found: $file_count"
  echo ""
  echo "$results"
} > "$output_file"

if [ -s "$output_file" ]; then
  {
    echo "To: $recipient"
    echo "Subject: $file_count Files larger than 1MB found on VM2 on $search_date"
    echo ""
    echo "$results"
  } | sendmail "$recipient" 2> /dev/null
  echo "File sent to $recipient"
fi

