# chTest

RAKOTO HARISOA Rodolphe Yoann
ETU 1999

Projet chess with socket // raha tsisy serveur hita dia tonga dia mandefa partie solo le client

Version JFrame de jeu d'échec avec multi

- fonctionnalités
  - mode singleplayer (tonga dia mandefa partie solo)
  - mode multiplayer
    - host (mi-host partie ray dia miandry joueur iray miditra)
    - client (miconnecte partie iray)
  - jeux d'échec
    - mandeha daholo ny deplacement de piece ankoatran'ny prise en passant
    - mandeha ny castling (ilay miaro roi ambadiky ny tour)
    - mandeha ny checkmate & stalemate
    - ny promotion tsy afaka misafidy fa tode m-promote reine ho azy ilay pion
  - rehefa vita le partie dia mamoaka message raha mode multi avy eo miverina menu,
      raha solo dia misy soratra fotsiny hoe nandresy ny *loko* (Ohatra: White won !)
  
- Changement
  - gameloop retiré donc l'affichage n'est plus automatique rechargé en 30frames/s
  - affichage mreload rehefa misy action mitranga fa tsy boucle infini intsony

- Fanamarihana
  - mety misy bug le affichagenle texture amin'ny linux fa bug nitranga tamin'ny navadiko swing ilay jeu dia mbola tsy voahitsy (opengl nanaovako an'ity jeu ity tamin'ny voalohany)

$chessV-jframe = version jframe$
