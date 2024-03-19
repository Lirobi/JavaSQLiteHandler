# Features :

DataBaseHandler(String filename) : Constructor : Connecting to database via filename (.db) (Directly connects to the database when initializing the instance)
sendCommand(String sql) : void : Custom SQL command sending (String)
sendCommandWithResultSet(String sql) : ResultSet : Custom SQL command sending with ResultSet
simpleSelect(String tableName, String[] columns, String[] conditions) : ResultSet : Select method
createSimpleTable(String tableName, String[] columns) : void : Create table method
closeConnection() : boolean : Close connection method

# Getters :

getDatabaseUrl() : String : Database url
getConnection() : Connection : Instance of the connection
