import sys
import matplotlib.pyplot as plot
import numpy

# check for arguments coming from command line. 
# need 3 values except the 1st argument that is the .py file name
inputValues = False
if(len(sys.argv) == 4):
  try:
    values = [int(sys.argv[1]), int(sys.argv[2]), int(sys.argv[3])]
    inputValues = True
  except ValueError:
    inputValues = False

if(not inputValues):
  values = [100, 85, 15]


categories = ['Total', 'Correct', 'Incorrect']
plot.bar(numpy.arange(0,len(categories)), values, align = 'center')
plot.xticks(numpy.arange(0,len(categories)), categories)
if(inputValues):
  plot.title('Results of the test')
else:
  plot.title('Input values did not come in. \nThis what it looks like with default values (85 out of 100)')
plot.show()
