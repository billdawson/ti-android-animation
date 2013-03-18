Running the Test Suite
=======================

The test suite uses Titanium Mobile's Anvil testing framework.  To run it successfully:

* You will need `node` (presumably you have this already if you're using titanium.)  It needs to be in your path.

* Your `build.properties` file will need a few `anvil...` entries; please see `build.properties.sample`.

* Run the test using `ant test`. It will build the module first, then test it.

Issues
======

* Anvil seems to return non-zero even if everything succeeds. So you'll need some other mechanism if you want to automate dealing with success versus failure (such as searching for "FAILED - 0", which would indicate success.)
