/*global Roue*/
var Voiture = function (p) {
    "use strict";

    Voiture.prototype.nombre += 1;

    this.position = p.createVector(0, 0);
    this.size = p.createVector(200, 70);
    this.vitesse = p.createVector(0, 0);

    this.angle = 0;

    this.roues = [
        new Roue(),
        new Roue()
    ];
};

Voiture.prototype.update = function (p) {
    "use strict";

    // Gauche
    if (p.keyIsDown(p.LEFT_ARROW)) {
        this.vitesse.x -= 1;
    }

    // Droite
    if (p.keyIsDown(p.RIGHT_ARROW)) {
        this.vitesse.x += 1;
    }

    // Haut
    if (p.keyIsDown(p.UP_ARROW)) {
        this.vitesse.y -= 1;
    }

    // Bas
    if (p.keyIsDown(p.DOWN_ARROW)) {
        this.vitesse.y += 1;
    }

    this.vitesse.y += 0.2;

    this.vitesse.x = p.constrain(this.vitesse.x, -4, 4);
    this.vitesse.y = p.constrain(this.vitesse.y, -4, 4);

    this.position.add(this.vitesse);
};

Voiture.prototype.draw = function (p) {
    "use strict";

    p.push();

    p.translate(this.position.x + this.size.x / 2, this.position.y + this.size.y / 2);
    p.rotate(this.angle);

    p.fill(255, 200, 200);
    p.rect(-this.size.x / 2, -this.size.y / 2, this.size.x, this.size.y);

    p.pop();
};
