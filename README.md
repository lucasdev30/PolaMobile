Ce projet est une application mobile Android développée en Java, connectée à un backend en PHP permettant la communication avec une base de données. L’application permet notamment la connexion des utilisateurs et la récupération de données depuis un serveur via des requêtes HTTP.

Le backend est développé en PHP et sert d’API entre l’application mobile et la base de données. Il gère les requêtes envoyées par l’application et retourne les données au format JSON.

La base de données contient les informations nécessaires au fonctionnement de l’application, comme les utilisateurs et les événements. Elle peut être installée et importée à l’aide du fichier SQL fourni dans le projet.

L’architecture du projet repose sur un modèle client-serveur : l’application Android communique avec le backend, qui lui-même interagit avec la base de données.

Pour faire fonctionner le projet, il est nécessaire de configurer un serveur local (comme Laragon ou XAMPP), d’importer la base de données, et de configurer correctement l’URL du backend dans l’application Android.

Enfin, certaines informations sensibles comme les identifiants de connexion ne sont pas incluses dans le dépôt GitHub pour des raisons de sécurité.
