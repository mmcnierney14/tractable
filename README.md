tractable
=========

you must set:
- Globals.senderId = from your google api console project number
- Globals.SERVER_URL = url of the server

- GoogleMapsKey in apikeys.xml

- server api key in SendDeleteMessagesServlet.java

- entity.setProperty line in ApiKeyInitializer.java

Lastly, when running the servlet, set the run configuration to include:
--address=[IP_ADDRESS] where [IP_ADDRESS] is the address of the server