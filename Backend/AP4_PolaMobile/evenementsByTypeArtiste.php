<?php
header("Content-Type: application/json");

// Charger la configuration
$config = include __DIR__ . '/config.php';

try {
    $conn = new PDO(
        "sqlsrv:Server={$config['serverName']};Database={$config['database']}",
        $config['username'],
        $config['password']
    );
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    $type = isset($_GET['type']) ? $_GET['type'] : '';

    $sql = "
        SELECT 
            E.IdEvenement, 
            E.DateEvenement,  
            E.TypeEvenement, 
            E.tarif,
            A.nomArtiste
        FROM Evenement E
        JOIN Jouer J ON E.IdEvenement = J.IdEvenement
        JOIN Artiste A ON J.IdArtiste = A.IdArtiste
        WHERE A.TypeShow = ?
    ";

    $stmt = $conn->prepare($sql);
    $stmt->execute([$type]);
    $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);

    
    foreach ($rows as &$row) {
        $img = strtolower(str_replace(' ', '_', $row['nomArtiste']));
        $row['imgArtiste'] = $img; // Ex : "Daft Punk" → "daft_punk"
    }

    echo json_encode(["evenements" => $rows]);

} catch (PDOException $e) {
    echo json_encode(["error" => $e->getMessage()]);
}
?>

