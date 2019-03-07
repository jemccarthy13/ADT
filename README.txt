
This file will be replaced with a more in-depth documentation at some point in the future.

For now this will contain a log of updates and large todos.

Structure:
- adt: package for main airspace decon tool
---- asmanager.gui: Frame & GUI controls for the airspace manager
---- asmanager.model: underlying Table & model for airspace manager
---- atolookup.gui: Frame & GUI controls for the ATO explorer
---- atolookup.model: underlying ATO Table & model
---- compiler: creates a command line compile script for the ADT
---- main: main entry point for the ADTApp main class
---- outputframe: GUI for any output window output
---- rundown.gui: Frame & GUI controls for the main rundown window
---- rundown.model: underlying Table & Model for the rundown
---- server: a networked option to send / receive messages between rundowns
---- settingsmanager: GUI controls to change the grid settings
---- stacksmanager: GUI controls for outputting formatted stacks
---- structures: data structures to help with the ADT
---- utilities: helper classes 
- resources: package/folder for images & resources
- atogenerator: a tool to help produce test/sim ATOs
---- datastructures: classes to help store ATO data
---- gui: main frame & GUI for the ATO generator
---- main: entry point for ATO generator
---- table: the model for the ATO generator
- custom: custom additions to swing, messaging, and utilities
---- messages: ADTMessages sent by the server and processed by the client
---- swing: base java.swing classes extended to be more useful to the ADT
---- utilities: some helpers for ADT things
- test: all manner of tests


===================================================================
Version 1.2.0                                           03 Dec 2018
===================================================================
Skipped straight to version 1.2.0. 

Features:
- Rundown:
	- Add assets to rundown
	- Sort on rundown columns
	- Calculate and display conflicts / overlap between assets
	- Calculate and dispaly highlighting from the Airspace Manager
	- Right click asset:
		- Show exact conflict, resolve (ignore) conflicts
		- Show airspace overlap (where asset conflicts with named A/S)
		- Copy approval (format for output into mIRC)
		- No Longer in the BMA
			--tracks this guy for metrics
			--holds his data in case he returns
	- Compact mode: display/hide columns
- Settings 
	- ATO import / load
	- Grid Settings, set/change grids
	- Type Manager: todo, not implemented
	- Zeroize: buggy, todo fix
- Developer Menu Bar
	- Test some features for debugging purposes
	- Force unlock of all locked cells
- ATO Lookup
	- Search the ATO using regex (. and * are understood)
	- add assets from ATO to rundown if selected
- Airspace Manager
	- add/remove ACO airspaces in terms of killboxes
	- set a specified color for that airspace
	- activate airspaces so when an asset has killboxes in that airspace
	  their airspace column is highlighted appropriate color
	- Sets named airspaces to be expanded to killboxes for conflict checking
	(i.e. if LIZARD is a ROZ that's added, in 101AM, any asset in 101AM will
	  be highlighted when LIZARD goes active
- MILDECON Assist
	- A button to shorten the airspace manager steps 
	- A ROZ/airspace added via MILDECON will:
		- 1) convert lat/lon or MGRS coords to killboxes (todo: buggy, fix)
		- 2) add that airspace to the airspace manager
		- 3) active that airspace
		- 4) highlight any person in that airspace with the selected color
	- This is especially useful for immediate ROZ's or HOT RADARC, or HIMARS
- STACKS:
	- Generate a stack for the BMA
	- Generate a stack based on airspace overlap (map airspace to assets in the airspace)
	- Generate a stack based on given grids (create a temporary airspace for comparison)
- Metrics:
	- Report end of mission metrics (# aircraft controlled, airspaces approved)
	- todo: airspace approval count
- Lowdown Manager: todo
- Get Lowdown: todo

===================================================================
Version 1.0.0                                           03 Dec 2018
===================================================================
- Initial "working" version.
Features:
- Baseline client allows addition / edit of aircraft to rundown
--- client imports USMTF00 formatted ATO (default ATO for now)
--- ATO Lookup search / add functionality (fully functional)
--- AS Manager partially functional (display only)
--- ATO generator is partially implemented (for MSNDAT/ACFT)

todo:
- enhance conflict checking of rundown aircraft
- don't allow duplicates on the rundown
- add AR data to the ATO generator
- fill out the rest of the implementation from excel:
--- AS manager coloration
--- conflict checks and overlap printout
--- in SAM alert
--- stacks printout
--- 
===================================================================
                     END RELEASE NOTES
===================================================================
