/*global Voiture, Sol*/
var Jeu = function (p) {
    "use strict";

    p.voiture = new Voiture(p);
    p.sol = new Sol(p);

    p.setup = function () {
        p.createCanvas(800, 600);
    };

    p.draw = function () {
        p.background(20, 250, 180);

        p.voiture.update(p);
        p.voiture.draw(p);

        p.sol.draw(p);
    };
};
