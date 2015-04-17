gs-gama
========

`gs-gama` is a plug-in of the [agent-based simulation platform called GAMA](https://code.google.com/p/gama-platform/). It allows to connect a GAMA simulation with an external software supporting the NetStream protocol, such as GraphStream-based applications.

Gama agents can use its primitives to send graph events and to receive it. By receiving and processing these events, the external application can maintain and analyze a dynamic graph view.

A node or an edge is not necessarily a Gama agent since each GAMA agent can create a whole graph if needed. The agents can also receive graph events from external applications and use them to take their decisions.

Installation
------------

On GAMA 1.6.1, select `Help > Install New Software`. Then, in `Work with`, select `https://gama-platform.googlecode.com/svn/update_site/`. If it does not appear, click on the `Add...` button, and fill the `Location` field with this URL and click on `OK`.

In the list, click on `Extensions for GAMA 1.6.1` and then select `org.graphstream.gama`. You can now follow the installation instructions, and accept the license. 

You might receive a warning informing you that the software contains unsigned content. You must click on `OK` to install the plug-in.

After a restart of GAMA, you should be able to use the plug-in.

Documentation
-------------

You may check the documentation on the [wiki](https://github.com/graphstream/gs-gama/wiki).

Authors
-------

This plug-in is made by:

- Thibaut Démare <thibaut.demare@gmail.com>

It is inspired by the gs-netlogo plug-in made by:

- Stefan Balev <stefan.balev@graphstream-project.org>
