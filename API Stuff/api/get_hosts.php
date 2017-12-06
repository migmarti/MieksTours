<?php
include_once '../con.php';
	
	class User {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}
		
		public function get_hosts($startDate,$endDate,$Latitud,$Longitud)
		{
				$Long1 = $Longitud + 0.30;
				$Long2 = $Longitud - 0.30;
				$Lat1 = $Latitud + 0.30;
				$Lat2 = $Latitud - 0.30;
				$query = "SELECT *  FROM Users
							WHERE HostingStatus = 1
							AND Latitud BETWEEN '$Lat2' AND '$Lat1'
							AND Longitud BETWEEN '$Long2' AND '$Long1'
							AND startDate <= STR_TO_DATE('$startDate', '%Y-%m-%d')
							AND endDate >= STR_TO_DATE('$endDate','%Y-%m-%d')";

				$result = mysqli_query($this -> connection, $query);
				while($row = $result->fetch_assoc()){
				     $json[] = $row;
				}
				$data['data'] = $json;
				echo json_encode($data);

				mysqli_close($this->connection);

		}
		
	}
	
	$user = new User();
	if(isset($_POST['Latitud'])) {

		$startDate = $_POST['startDate'];
		$endDate = $_POST['endDate'];
		$Latitud = $_POST['Latitud'];
		$Longitud = $_POST['Longitud'];
		
		if(!empty($startDate) && !empty($endDate)){
			$user-> get_hosts($startDate,$endDate,$Latitud,$Longitud);
			
		}else{
			echo json_encode("you must type both inputs");
		}
		
	}
?>