# saaj-test

## Server side

Start EAP server.

```bash
$ ${EAP_HOME}/bin/standalone.sh -Dorg.apache.cxf.logging.enabled=pretty
```

Deploy web services.

```bash
$ cd calc-ws
$ mvn clean package
$ ${EAP_HOME}/bin/jboss-cli.sh -c 'deploy target/calc-ws.war --force'
```

## Client sice

Run web services client.

```bash
$ cd calc-ws-client
$ mvn clean compile exec:java
```

## Results

The server.log shows:

```bash
2019-09-03 15:24:11,064 INFO  [org.apache.cxf.services.CalcService.CalcPort.Calc] (default task-2) Outbound Message
---------------------------
ID: 2
Response-Code: 200
Encoding: UTF-8
Content-Type: multipart/related; type="text/xml"; boundary="uuid:a92056e2-b87b-407a-956e-1bc59b32827a"; start="<root.message
@cxf.apache.org>"; start-info="text/xml"
Headers: {}
Payload: --uuid:a92056e2-b87b-407a-956e-1bc59b32827a
Content-Type: text/xml; charset=UTF-8
Content-Transfer-Encoding: binary
Content-ID: <root.message@cxf.apache.org>

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><ns2:addResponse xmlns:ns2="http://calc.exa
mple.redhat.com/"><return>50</return></ns2:addResponse></soap:Body></soap:Envelope>
--uuid:a92056e2-b87b-407a-956e-1bc59b32827a
Content-Type: plain/text
Content-Transfer-Encoding: binary
Content-ID: <data.txt>

This is a test data for attachment.
--uuid:a92056e2-b87b-407a-956e-1bc59b32827a--
--------------------------------------
```

The client console shows the attachmen text.

```bash
9 03, 2019 3:24:11 午後 com.redhat.example.handler.GetAttachmentHandler handleMessage
情報: ### attachment=com.sun.xml.internal.messaging.saaj.soap.AttachmentPartImpl@2f77589a
### 20 + 30 = 50
### Attachment: This is a test data for attachment.
```