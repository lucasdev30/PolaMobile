<?php
session_start();

if(isset($_SESSION['Email'])){
    session_unset();

    session_destroy();

    echo json_encode(array("status" => "success", "message" => "Déconnexion réussie"));
} else {
    echo json_encode(array("status" => "error", "message" => "Aucune session active"));
}
?>
