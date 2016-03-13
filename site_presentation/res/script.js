var sections = [], index = 0;

// Fonctions de contrôle
function next() {
	"use strict";

	// On n'efface pas la dernière
	if (index !== sections.length - 1) {
		sections[index].classList.add("disappear");
	}

	if (sections[index].classList.contains("appear")) {
		sections[index].classList.remove("appear");
	}

	index = Math.min(index + 1, sections.length - 1);
}

function previous() {
	"use strict";

	index = Math.max(index - 1, 0);

	if (sections[index].classList.contains("disappear")) {
		sections[index].classList.remove("disappear");
	}

	sections[index].classList.add("appear");
}

function reset() {
	"use strict";

	var next;

	for (next in sections) {
		if (sections.hasOwnProperty(next)) {
			// On enlève l'effet disparu
			if (sections[next].classList.contains("disappear")) {
				sections[next].classList.remove("disappear");
			}

			// On enlève l'effet réapparu
			if (sections[next].classList.contains("appear")) {
				sections[next].classList.remove("appear");
			}
		}
	}

	index = 0;
}

// Initialisation
function init() {
	"use strict";

	sections = document.getElementsByTagName("section");
	index = 0;

	document.onclick = function (e) {
		// Clic gauche
		if (e.button === 0) {
			next();

		// Clic droit
		} else if (e.button === 2) {
			previous();

		// Clic du milieu
		} else if (e.button === 1) {
			reset();
		}
	};

	document.onkeyup = function (e) {
		// Retour arriète/supprimer
		if (e.keyCode === 8 || e.keyCode === 37) {
			previous();

		// Echap
		} else if (e.keyCode === 27) {
			reset();

		// Toute autre touches
		} else {
			next();
		}
	};

	// NOTE : commenter le return false pour le développement
	document.oncontextmenu = function () {
		return false;
	};
}

// Lancement de l'initialisation
(function () {
	"use strict";
	init();
}());
