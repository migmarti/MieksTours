<?php
include_once '../con.php';
	
	class User {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}

		function get_ints(){
				$query = "SELECT * FROM Interests ORDER BY Name";
				$result = mysqli_query($this -> connection, $query);
				while($row = $result->fetch_assoc()){
				     $json[] = $row;
				}
				$data['data'] = $json;
				echo json_encode($data);
				mysqli_close($this -> connection);
		}
				
	}

	$user = new User();
	$user-> get_ints();

?>