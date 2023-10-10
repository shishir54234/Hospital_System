#!/bin/bash

folder_path="/Users/Work/Desktop/Hospital/src" # replace with the actual folder path

line_count=0

# loop through all files in the folder
for file in $folder_path/*; do
  # count the number of lines in the file and add it to the line_count variable
  line_count=$((line_count + $(wc -l < "$file")))
done

echo "Total number of lines in $folder_path: $line_count"