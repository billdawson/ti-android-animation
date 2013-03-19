exports.run = function() {
	var w = Ti.UI.createWindow({
			backgroundColor: "white",
			title: "Flip View",
			fullscreen: false
		}),
		tableView = Ti.UI.createTableView({
			top: "16dp", left: "16dp", right: "16dp", height: "200dp"
		}),
		english = ["One", "Two", "Three", "Four", "Five"],
		german = ["Eins", "Zwei", "Drei", "Vier", "Fünf"],
		currentList = english,
		rows = [],
		i = 0,
		buttonY = Ti.UI.createButton({
			bottom: "16dp", left: "16dp", width: "150dp",
			height: "48dp", title: "Flip y-axis"
		}),
		buttonX = Ti.UI.createButton({
			bottom: "16dp", left: "182dp", width: "150dp",
			height: "48dp", title: "Flip x-axis"
		}),
		animMod = require("com.billdawson.timodules.animation");

	w.add(buttonX);
	w.add(buttonY);

	for (; i < currentList.length; i++) {
		rows.push(
			Ti.UI.createTableViewRow({title: currentList[i]})
		);
	}

	tableView.minRowHeight = "48dp";
	tableView.setData(rows);
	w.add(tableView);

	function flipIt(e) {
		currentList = currentList == english ? german : english;
		var anim = animMod.viewPropertyAnimator.animate(tableView).withStartAction(function() {
			for (var i = 0; i < currentList.length; i++) {
				rows[i].title = currentList[i];
			}
		}).setDuration(2000);

		if (e.source == buttonX) {
			anim.rotationXBy(360);
		} else {
			anim.rotationYBy(360);
		}
	}

	buttonX.addEventListener("click", flipIt);
	buttonY.addEventListener("click", flipIt);
	
	w.open();
};
