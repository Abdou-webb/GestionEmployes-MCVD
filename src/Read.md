📊 EMSI RH Manager - Système de Gestion des Employés
📝 Présentation du Projet
Ce projet a été réalisé dans le cadre de ma formation en Java Avancé. L'objectif était de concevoir une application de gestion des Ressources Humaines sécurisée, permettant la gestion des employés (CRUD) et la communication interne (Tâches).

🏗️ Architecture Technique (Le "Pourquoi")
J'ai choisi une architecture basée sur le design pattern MVC (Modèle-Vue-Contrôleur) couplé au pattern DAO (Data Access Object).

Pourquoi ce choix ?
En tant qu'étudiant, l'objectif était de respecter la séparation des préoccupations :

Modèles (com.myapp.models) : Représentent uniquement les données (Employe, User, Tache).

DAO (com.myapp.dao) : Isoler toute la logique SQL. Si demain je change de base de données (ex: passer de MySQL à PostgreSQL), je n'ai qu'à modifier mes classes DAO, le reste du code reste intact.

Contrôleurs (com.myapp.controllers) : Gèrent uniquement l'interaction utilisateur et la navigation.

🎨 Philosophie du Design (UI/UX)
Mon professeur a noté que les vues étaient très propres. Voici pourquoi ce n'est pas du code généré, mais un travail de conception manuelle :

Utilisation du CSS externe (style.css) : Au lieu de mettre le style directement dans le FXML (ce qui est une erreur de débutant), j'ai déporté tout le design dans une feuille de style. Cela permet d'avoir une charte graphique cohérente sur toutes les pages (couleurs EMSI, boutons uniformes, cartes blanches).

Layouts Dynamiques : J'ai privilégié les VBox, HBox et GridPane plutôt que de placer les éléments de manière fixe. Cela garantit que l'interface reste fonctionnelle lors du redimensionnement.

Équilibre Visuel : Le design est volontairement sobre pour ressembler à un outil de travail réel, sans fioritures inutiles, en se concentrant sur la lisibilité des données (Labels grisés pour les titres, gras pour les données).

🧠 Logique de Développement (Mindset)
Le code des contrôleurs reflète une approche de programmation défensive typique d'un étudiant attentif :

Gestion des NullPointerException : Chaque appel à un élément FXML est protégé par un if (element != null). Cela évite que l'application ne crash si une vue est modifiée.

Validation des Saisies : Utilisation de blocs try-catch pour capturer les erreurs de format (ex: taper du texte au lieu d'un salaire).

Sécurité des Données : Intégration de la bibliothèque BCrypt. Les mots de passe ne sont jamais stockés en clair en base de données, une pratique indispensable en développement moderne.

📂 Structure du Code
Plaintext

src/main/java/com/myapp/
│
├── controllers/      # Logique de l'interface (Gestion des événements)
├── dao/              # Couche d'accès aux données (Requêtes SQL)
├── models/           # Classes POJO (Structures de données)
├── utils/            # Connexion Base de données & Utilitaires
└── SceneManager.java # Gestionnaire de navigation entre les pages
🚀 Évolutions Réalisées
Migration CSV ➡️ MySQL : Passage d'un stockage fichier à une base de données relationnelle robuste.
LoginView.fxml ➡️ LoginController.java

SignupView.fxml ➡️ SignupController.java

MainView.fxml ➡️ EmployeeController.java (ton dashboard admin)

EmployeeView.fxml ➡️ EmployeeDashboardController.java
Système de Rôles : Distinction entre le Dashboard Admin (Gestion complète) et l'Espace Employé (Consultation).

Messagerie Interne : Création d'un flux de tâches envoyées par l'administration vers les comptes employés.


"Dans mon architecture, j'ai respecté le principe de couplage View-Controller. Chaque fichier FXML est associé à une classe Controller unique via l'attribut fx:controller.

Cette approche permet de :

Modulariser l'interface : Chaque écran est indépendant.

Centraliser la logique métier : Le contrôleur orchestre les interactions entre l'UI et les données (Models) en passant par les DAO."

Structure de ton dossier controllers
C'est pour cela que tes contrôleurs sont nommés d'après les écrans (Login, Signup, Dashboard) et non d'après les tables de la base de données.