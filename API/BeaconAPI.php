<?php
/*
 * @Author: Niels Bekkers
 *
 * @Description: API service that handle request-methods with data(JSON) and push it to mysql-database
 *               Test it with httpRequester and use GET method
 *				       url(POST): http://localhost/beaconapi.php/beacons
 *               JSON GET example: http://localhost/beaconapi.php/beacons (dit retourneert de volledige database in JSON)
 *               JSON POST (input) example: {"major":"12345","minor":"000","UUID":"B9407F30-F5F8-466E-AFF9-25556B57FE6D","name":"Blueberry Pi","locationTitle":"location","locationDescription":"description"}
 *               JSON DELETE example: use http-delete method and execute via url, http://localhost/beaconapi.php/beacons/12345 
 */
 
// Haal de HTTP methode op, het urlpad en de request(JSON)
$method = $_SERVER['REQUEST_METHOD'];
$request = explode('/', trim($_SERVER['PATH_INFO'],'/'));
$input = json_decode(file_get_contents('php://input'),true);
$toegelaten = null;
 
// Verbind met de mysql database
$link = mysqli_connect('localhost', 'root', 'root', 'ucllbeacons');
mysqli_set_charset($link,'utf8');
 
// Tabel en sleutel filteren uit het urlpad
$table = preg_replace('/[^a-z0-9_]+/i','',array_shift($request));
$key = array_shift($request)+0;
 
//$set = "kolomnaam = 1"; (zo ziet setgedeelte eruit)
 
// Maak de SQL-string naargelang de HTTP-methode
switch ($method) {
  case 'GET':
    $sql = "select * from `$table`"; break;                                       //Geef hele database terug
    //$sql = "select * from `$table`".($key?" WHERE major=$key":''); break;       //Selecteer geen specifieke record
  case 'PUT':
    $toegelaten = "PUT is niet toegelaten in deze service!"; break;
  case 'POST':
    $toegelaten = "POST is niet toegelaten in deze service!"; break;
    //$sql = "insert into `$table` set $set"; break;
  case 'DELETE':
    $toegelaten = "DELETE is niet toegelaten in deze service!"; break;
}
 
// Voer SQL commando uit indien toegelaten!
if ($toegelaten == null){

  $result = mysqli_query($link,$sql);

  // Genereer foutmelding indien SQL commando foutief is
  if (!$result) {
    http_response_code(404);
    die(mysqli_error());
  }
 
  // Toon resultaat in browser (visueel testen)
  if ($method == 'GET') {
    if (!$key)
      for ($i=0;$i<mysqli_num_rows($result);$i++) {
      echo ($i>0?',':'').json_encode(mysqli_fetch_object($result));
      }}
 
  // Sluit de SQL verbinding
  mysqli_close($link);
}
else{
  echo $toegelaten;
}
