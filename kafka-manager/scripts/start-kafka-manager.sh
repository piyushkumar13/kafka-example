$CMAK_HOME/bin/cmak -Dconfig.file=$CMAK_HOME/conf/application.conf -Dhttp.port=8080

# Uncomment this for loading css properly if above command is not loading css properly
# $CMAK_HOME/bin/cmak -Dconfig.file=$CMAK_HOME/conf/application.conf -Dhttp.port=8080 -J--add-opens=java.base/sun.net.www.protocol.file=ALL-UNNAMED -J--add-exports=java.base/sun.net.www.protocol.file=ALL-UNNAMED