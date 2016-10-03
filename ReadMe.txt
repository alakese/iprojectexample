HowTo: see the codes for details

1. Create a "Eclipse 4 Applicateion Project"

2. Create the Commands :
- com.example.project.treeviewer.command.newproject
- com.example.project.treeviewer.command.openproject
- com.example.project.treeviewer.command.closeproject
- com.example.project.treeviewer.command.deleteproject

3. Create the Handlers and bind with the corresponding commands above:
- com.example.project.treeviewer.handler.newproject
- com.example.project.treeviewer.handler.openproject
- com.example.project.treeviewer.handler.closeproject
- com.example.project.treeviewer.handler.deleteproject

4- Create the handler classes in package : com.example.project.treeviewer.handlers
- NewProjectHandler.java
- OpenProjectHandler.java
- DeleteProjectHandler.java
- CloseProjectHandler.java

5- Create Trimmed Window under Windows and Dialogs in file Application.e4xmi
- Create the contextmenus as defined (see Application.e4xmi)

6- Use IEventBroker to send events to update the tree immediately

7- Use EMenuService to register the contextmenus in system

8- Use ESelectionService to send the selected project to the handlers like CloseProjectHandler.
