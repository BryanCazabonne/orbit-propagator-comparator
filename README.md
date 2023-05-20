# orbit-propagator-comparator
The purpose of the project is to compare orbit propagation models using Orekit library

# Folders
src/main/java contains the class used to execute the application.
src/main/resources contains the input file of the application. It is a mandatory location.

# Run the comparator

1. Go to the src/main/resouces folder and modify the inputs.yaml file according to your needs
2. Two possibilities:
- In Eclipse IDE, go to src/main/java and run the OrbitComparator class with arguments. The only argument to set is the name of the inputs file (e.g. "inputs.yaml")
- Create a Shell file in order to run the OrbitComparator class.