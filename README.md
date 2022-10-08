## Log4Shell Proof of Concept

The purpose of this POC is to demonstrate the Log4Shell exploit with Log4J versions older than `2.15.0`. This POC uses the Minecraft server (Paper 1.15.2-391) as an example of a vulnerable application.

### Requirements

You'll need one of the following Java SDKs:
  * 11.0.1 or earlier
  * 8u191 or earlier
  * 7u201 or earlier
  * 6u211 or earlier

### Building the POC

In the root folder, run:

```
./mvnw clean install
```

**NOTE:** This project includes the Maven wrapper, so you don't need to have previously installed Maven.

### Running the POC

The server module runs a lean LDAP & HTTP server.

The LDAP server listens on port `9999` by default and will return an `LDAPResult` that includes a URL reference to a
Java class that will be deserialized and executed.

The HTTP server listens on port `8000` and responds to any request with a byte array that is the `Test.class`.

`Test` implements `ObjecFactory` which the JNDI mechanism hooks into to execute its `getObjectInstance` method. While
the method simply returns `null`, it uses `Runtime` to execute arbitrary code on the host machine. In this case, it
writes to a file called: `/tmp/log4j` to prove that it _could_ execute basically anything available on the machine.

This POC should run as-is on Linux.

Open a terminal window and run the following:

```
cd log4shell-server
../mvnw exec:java -Dexec.mainClass="Server"
```

You should see output that looks like the following:

```
[INFO] --- exec-maven-plugin:3.0.0:java (default-cli) @ log4shell-server ---
LDAP server listening on 0.0.0.0:9999
HTTP server listening on 0.0.0.0:8000
```
