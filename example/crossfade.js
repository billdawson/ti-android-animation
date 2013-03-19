exports.run = function() {
	var w = Ti.UI.createWindow({
			backgroundColor: "white",
			title: "Cross Fade",
			fullscreen: false
		}),
		img1 = Ti.UI.createImageView({
			top: "16dp", left: "16dp", right: "16dp", bottom: "80dp", image: "img1.jpg"
		}),
		img2 = Ti.UI.createImageView({
			top: "16dp", left: "16dp", right: "16dp", bottom: "80dp", image: "img2.jpg",
			visible: false
		}),
		currentImg = img1,
		button = Ti.UI.createButton({
			bottom: "16dp", right: "16dp", left: "16dp",
			height: "48dp", title: "Cross Fade"
		}),
		animMod = require("com.billdawson.timodules.animation");

	w.add(button);
	w.add(img1);
	w.add(img2);

	w.addEventListener("open", function() {
		// HACK - Titanium opacity property doesn't do the same thing
		// as Android's alpha property. So we can't use opacity: 0 up
		// above when defining img2.  Instead, when the window is
		// finished opening we'll real quick use the ViewPropertyAnimator to
		// set img2's true alpha to 0.
		animMod.viewPropertyAnimator.animate(img2).setDuration(10).alpha(0);
	});


	button.addEventListener("click", function() {
		var imageIn, imageOut;
		if (currentImg == img1) {
			imageIn = currentImg = img2;
			imageOut = img1;
		} else {
			imageIn = currentImg = img1;
			imageOut = img2;
		}

		animMod.viewPropertyAnimator.animate(imageOut).setDuration(2000).withEndAction(function() {
			imageOut.visible = false;
		}).opacity(0);
		animMod.viewPropertyAnimator.animate(imageIn).setDuration(2000).withStartAction(function() {
			imageIn.visible = true;
		}).opacity(1);

	});

	w.open();
};
