import sys
import matplotlib.pyplot as plot
import numpy

# check for arguments coming from command line. 
# need 3 values except the 1st argument that is the .py file name
if(len(sys.argv) == 4):
  values = [sys.argv[1], sys.argv[2], sys.argv[3]]
else:
  values = [10, 17, 3]

#print len(sys.argv)

categories = ['Total', 'Correct', 'Incorrect']
plot.bar(numpy.arange(0,len(categories)), values, align = 'center')
plot.xticks(numpy.arange(0,len(categories)), categories)
plot.show()
