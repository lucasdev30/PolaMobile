<?php

session_start();

header("Content-Type: application/json");

$response = array();

if (isset($_POST['Email']) && isset($_POST['PassWrd'])) {
    
    // file_put_contents("debug_login.txt", "Reçu : " . print_r($_POST, true));

    $email = $_POST['Email'];
    $password = $_POST['PassWrd'];

    try {
        
        $conn = new PDO("sqlsrv:Server=localhost;Database=PolaMobile", "lucasql", "test");


        $stmt = $conn->prepare("SELECT PassWrd FROM Adhérent WHERE Email = ?");
        $stmt->execute([$email]);

        $row = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($row) {
            
            $hashedPassword = $row['PassWrd'];

            if (password_verify($password, $hashedPassword)) {

                $_SESSION['Email'] = $email; 
                $response['erreur'] = false;
                $response['message'] = "Connexion réussie.";

            } else {
                $response['erreur'] = true;
                $response['message'] = "Mot de passe incorrect.";
            }

        } else {
            
            $response['erreur'] = true;
            $response['message'] = "Aucun utilisateur trouvé.";
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
