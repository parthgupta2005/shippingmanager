To run the program the user must follow one of the following three ways:
1. Open the command prompt/terminal after having navigated to the Product Directory and type the following command: java -cp .:derby.jar:poi-3.2-FINAL.jar:poi-3.7.jar:dom4j-1.6.1.jar:xmlbeans-2.3.0.jar:poi-scratchpad-3.7.jar:poi-scratchpad-3.7-sources.jar:poi-ooxml-schemas-3.7.jar:poi-ooxml-3.7.jar:poi-examples-3.7.jar Welcome
2. The other method to run the program is to open the command prompt/terminal after having navigated to the Product Directory type the following command: java -jar ShippingManager.jar
3. The final way to run the program is to open the Finder, and navigate to the Product Directory, after this click the "ShippingManager.jar" file and the program will run.
If the user wishes to empty the database and re-install it, proceed with the following instructions.
1. Delete the database as it exists.
2. In the command prompt/terminal after having navigated to the Product directory, type the following command: java -cp .:derby.jar InstallDB
NAVIGATING THROUGH PROGRAM
Follow the next steps to succesfully navigate through the program.
1. After having run the program, the user must navigate to the Menu Bar and select "Add Order", from here you will be prompted to add an order by selecting the type of containers and providing a container ID which is an integer from 0-9999.
2. After having added the order, you may now insert containers to the order by provding dimensional information, the order it belongs to, and how many of those types you would like to add.
3. After the crates are added, the positions of the crates have been updates, and you can now view their respective positions in the display frame. 
4. You are also able to delete and update the containers, archive orders, view the efficiency of orders, and transfer the information to a spreadsheet.
5. For more support, please reference the Help tab in the menu bar in the home page.
