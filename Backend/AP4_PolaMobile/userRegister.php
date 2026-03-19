<?php

session_start();

header("Content-Type:application/json");


$response = array();

if (isset($_POST['Nom']) && isset($_POST['Prenom']) && isset($_POST['Age']) && isset($_POST['Email']) && isset($_POST['PassWrd'])) {
    
    $Nom = $_POST['Nom'];
    $Prenom = $_POST['Prenom'];
    $age = $_POST['Age'];
    $email = $_POST['Email'];
    $password = $_POST['PassWrd'];
    
    $hashedPassword = password_hash($password, PASSWORD_DEFAULT);

    try {

        $conn = new PDO("sqlsrv:Server=localhost;Database=PolaMobile", "lucasql", "test");

        $stmt = $conn->prepare("SELECT * FROM Adhérent WHERE Email = ?");
        $stmt->execute([$email]);

        if ($stmt->rowCount() > 0) {
            $response['erreur'] = true;
            $response['message'] = "Cet email est déjà utilisé.";

        } else {

            $stmt = $conn->prepare("INSERT INTO Adhérent (Nom, Prenom, Age, Email, PassWrd) VALUES (?, ?, ?, ?, ?)");
            if ($stmt->execute([$Nom, $Prenom, $age, $email, $hashedPassword])) {
                $response['erreur'] = false;
                $response['message'] = "Inscription réussie.";

            } else {
                $response['erreur'] = true;
                $response['message'] = "Erreur d'enregistrement.";
            }
        }

    } catch (PDOException $e) {
        $response['erreur'] = true;
        $response['message'] = "Erreur de connexion : " . $e->getMessage();
    }

} else {
    $response['erreur'] = true;
    $response['message'] = "Champs requis manquants.";
}

echo json_encode($response);
?>
