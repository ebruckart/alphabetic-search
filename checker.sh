#!/bin/bash
if [ $# -eq 0 ];
  then
    echo "No arguments supplied."
    echo "Expecting validity, horspools, boyermoore, traditional, or categorical."
  else
    type=$1
    extra=('MT' 'Unplaced' 'X')

    echo "starting" $type "test script now."
    for i in `seq 1 38`;
      do
        java -Xms512M -Xmx3500M -jar checker.jar $i $type
      done  

    for j in ${extra[@]};
      do
        java -Xms512M -Xmx3500M -jar checker.jar $j $type
      done

    echo $type "test script is done!"
fi