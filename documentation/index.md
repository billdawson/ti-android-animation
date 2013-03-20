Android Animation for Titanium
===============================

Description
------------

This Titanium Android module makes the [Honeycomb (Android 3.0) animation API][1]
available to Titanium Android apps. It uses Jake Wharton's excellent
[NineOldAndroids][2] library to make the animations available on pre-Honeycomb
devices as well.

The module doesn't follow the Android API _exactly_, but fairly closely.  

Accessing the Module in Your App Code
-------------------------------------

### Get the module's ZIP file

You can either build the module from [source][3] (this assumes you know how to build Titanium
modules; no instructions are provided here for that) or get a recent ZIP from here (TODO TODO).

### Place the module ZIP where it's accessible

For a module to be accessible to the Titanium project build scripts (which
will package the module in to your application when it is built), the module's
ZIP file must either be placed in the project's root folder or in your root Titanium
installation folder (e.g. `Library/Application Support/Titanium` on OS X, if that's
indeed where your Titanium installation folder is.)

Where your Titanium application project is built, the build scripts will take
care of unzipping the module in either of those places.

### Prepare your tiapp.xml

Here's an example of the `<modules>` section of your project's tiapp.xml file
modified so as to register this module:

    <modules>
	    <module platform="android" version="1.0">com.billdawson.timodules.animation</module>
	</modules>

### Accessing the module in code

Here's an example of getting a reference to an instance of the module in your
application's Javascript code:

    var animMod = require("com.billdawson.timodules.animation");

Reference
---------

TODO: If your module has an API, you should document
the reference here.

Usage
------

Please see the [example project][5] for several usage examples. Meanwhile, find a few short
samples below.

### ViewPropertyAnimator

    var mod = require("com.billdawson.timodules.animation"),
	    view = Ti.UI.createView();

	// setup the window and view, etc....
	// ... then later ...

	mod.viewPropertyAnimator.animate(view)
		.setDuration(1000)
		.withEndAction(function() {
			Ti.API.info("Animation done");
		})
		.scaleXBy(0.5)
		.scaleXBy(0.5)
		.alpha(0.75);

### ObjectAnimator

Using backgroundColor as a good example here because
the ViewPropertyAnimator (above) *cannot* be
used to animate the backgroundColor (the same
is true for native Android), so you must use
ObjectAnimator for that.

    var mod = require("com.billdawson.timodules.animation"),
	    view = Ti.UI.createView({
			backgroundColor: "#FF00FF00"
		}),
		animator = mod.objectAnimator.ofInt(
			view, "backgroundColor", "#AAFF0000");

	// setup the window and view, etc....
	// ... then later ...

	// Use the Android evaluator that is
	// specifically for ARGB (alpha/red/green/blue)
	// values.
	animator.setEvaluator(mod.ARGB_EVALUATOR);
	animator.setDuration(2000); // 2 secs.
	animator.addEventListener("start", function() {
		Ti.API.info("Animation started!");
	});

	// Animate backgroundColor from
	// #FF00FF00 to #AAFF0000 over
	// 2 seconds.
	animator.start();

### AnimatorSet
    var mod = require("com.billdawson.timodules.animation"),
	    view = Ti.UI.createView({
			backgroundColor: "#FF00FF00"
		}),
		animateColor = mod.objectAnimator.ofInt(
			view, "backgroundColor", "#AAFF0000"),
		animateScaleX = mod.objectAnimator.ofFloat(
			view, "scaleX", 2),
		animateScaleY = mod.objectAnimator.ofFloat(
			view, "scaleY", 2),
		animatorSet = mod.createAnimatorSet();
    
	// setup the window and view, etc....
	// ... then later ...

	animatorSet.playTogether([animateColor, animateScaleX, animateScaleY]);
	animatorSet.start();

Author
------

[Bill Dawson][4]

Source Code
-----------

The source is open and can be found on [Github][3].

License
-------

	The original code herein is...

	Copyright 2013 William Dawson.

	The NineOldAndroids library, upon which this module is built, is...

    Copyright 2012 Jake Wharton.

	Both projects:

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: http://android-developers.blogspot.com/2011/02/animation-in-honeycomb.html
[2]: http://nineoldandroids.com
[3]: TODO/githubsource
[4]: http://github.com/billdawson
[5]: TODO/exampleprojongithub
